(ns association-facts-test
  (:require [clojure.java.io :as io] [clojure.java.shell :as shell]
            [clojure.test :refer [deftest is testing]]
            [kotoba.compiler.core :as compiler] [kotoba.compiler.ir :as ir]))
(def source (slurp "src/association_facts.kotoba"))
(defn call [kir f & xs] (ir/execute kir f (vec xs)))
(defn present [x] (when (second x) (nth x 2)))
(def fields ["id" "title" "association" "isic" "country" "kind" "url" "url-provenance"
             "established-date" "last-revised-date" "retrieved-at"])
(def expected
  [{"id" "sba.founding-1912-basel" "title" "Swiss Bankers Association founded in Basel (Wikipedia)"
    "association" "sba" "isic" "6419" "country" "CHE" "kind" "governance-program"
    "url" "https://en.wikipedia.org/wiki/Swiss_Bankers_Association"
    "url-provenance" "wikipedia-corroborated" "established-date" "1912"
    "last-revised-date" nil "retrieved-at" "2026-07-17"}
   {"id" "sba.cdb-20-2020-due-diligence-code"
    "title" "Agreement on the Swiss banks' code of conduct with regard to the exercise of due diligence (CDB 20)"
    "association" "sba" "isic" "6419" "country" "CHE" "kind" "governance-program"
    "url" "https://www.swissbanking.ch/_Resources/Persistent/6/2/e/e/62eec3df0685e359c5a376dfca79dec8b908ea9c/SBA_Agreement_CDB_2020_EN.pdf"
    "url-provenance" "official-swissbanking-ch" "established-date" "2020"
    "last-revised-date" nil "retrieved-at" "2026-07-17"}])
(deftest reference-preserves-authority
  (let [kir (:kir (compiler/compile-source source :js-kotoba-v1))
        observed (mapv (fn [i] (into {} (map (fn [f] [f (present (call kir 'entry-field "sba" i f))]) fields))) [0 1])]
    (is (= expected observed))
    (is (= ["1912" "2020"] (mapv #(present (call kir 'entry-field "sba" % "established-date")) [0 1])))
    (is (= ["wikipedia-corroborated" "official-swissbanking-ch"]
           (mapv #(present (call kir 'entry-field "sba" % "url-provenance")) [0 1])))
    (is (= ["sba.founding-1912-basel" "sba.cdb-20-2020-due-diligence-code"]
           (mapv #(present (call kir 'by-topic-id "sba" "governance" %)) [0 1])))
    (is (= #{} (set (:effects kir))))
    (testing "fail closed"
      (is (zero? (call kir 'entry-count "swissbanking")))
      (is (nil? (present (call kir 'entry-field "sba" 2 "id"))))
      (is (nil? (present (call kir 'entry-field "sba" 1 "last-revised-date"))))
      (is (nil? (present (call kir 'topic "sba" 0 1))))
      (is (zero? (call kir 'by-topic-count "sba" "due-diligence")))
      (is (nil? (present (call kir 'by-topic-id "sba" "governance" 2)))))))
(defn compiler-root [] (nth (iterate #(.getParent ^java.nio.file.Path %)
  (java.nio.file.Path/of (.toURI (io/resource "kotoba/compiler/core.clj")))) 4))
(defn base64 [x] (.encodeToString (java.util.Base64/getEncoder) x))
(deftest restricted-js-and-wasm-conform-semantically
  (let [js (compiler/compile-source source :js-kotoba-v1) wasm (compiler/compile-source source :wasm32-browser-kotoba-v1)
        js64 (base64 (.getBytes ^String (:source js) "UTF-8")) wasm64 (base64 ^bytes (:bytes wasm))
        p (shell/sh "node" "--input-type=module" "-e"
            (str "import(process.argv[1]).then(async h=>{const j=await import('data:text/javascript;base64," js64 "');const w=await h.instantiateKotoba(Buffer.from(process.argv[2],'base64'));const r=x=>{if(x['entry-field']('sba',0n,'established-date')[2]!=='1912'||x['entry-field']('sba',1n,'established-date')[2]!=='2020'||x['entry-field']('sba',1n,'last-revised-date')[1]!==false)throw Error('dates');if(x['entry-field']('sba',0n,'url-provenance')[2]!=='wikipedia-corroborated'||x['entry-field']('sba',1n,'url-provenance')[2]!=='official-swissbanking-ch'||x['by-topic-count']('sba','governance')!==2n||x['entry-count']('swissbanking')!==0n)throw Error('authority');};r(j.instantiateKotoba({}));r(w.instance.exports)}).catch(e=>{console.error(e);process.exit(99)})")
            (.toString (.toUri (.resolve (compiler-root) "runtime/browser-host.mjs"))) wasm64)]
    (is (zero? (:exit p)) (str (:out p) (:err p)))))
(deftest production-source-authority
  (is (= ["src/association_facts.kotoba"] (->> (file-seq (io/file "src")) (filter #(.isFile %)) (map str) sort vec))))
