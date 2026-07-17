(ns association.facts
  "Industry rule/history catalog for the Swiss Bankers Association
  (SBA, Schweizerische Bankiervereinigung) -- a 44th
  industry-association-level source (see cloud-itonami-assoc-6419-jpn-zenginkyo,
  -6419-deu-bankenverband, -6419-fra-fbf, -6419-aus-aba, -6419-are-ubf,
  -6419-vnm-vnba, -6419-phl-bap, -6419-esp-aeb, -6419-tur-tbb,
  -6419-nga-cibn for the first ten) per ADR-2607141700
  (cloud-itonami-compliance-fact-federation). The ELEVENTH entry
  aligned to ISIC 6419 (other monetary intermediation / banking).
  Fills Switzerland's previously-open association-axis gap (noted
  honestly at tick 129) -- Switzerland now has real, individually
  verified facts across ALL THREE axes (municipality:
  cloud-itonami-municipality-che-bern, tick 128; country:
  cloud-itonami-iso3166-che statute.facts, tick 129; association:
  this entry, tick 130).

  Founding entry directly confirmed via en.wikipedia.org (directly
  read, not merely WebSearch-cited): 'The trade association known as
  the Swiss Bankers Association was founded in 1912 in Basel,
  Switzerland.'

  The second entry, the CDB 20 due-diligence self-regulatory code,
  directly confirmed via swissbanking.ch's own hosted PDF cover page
  (via the Read-tool saved-path fallback, WebFetch itself reporting
  the PDF as illegible/binary): 'Agreement on the Swiss banks' code
  of conduct with regard to the exercise of due diligence (CDB 20)',
  dated 2020. WebSearch had surfaced an earlier 1977 origin for this
  Agreement, but the CDB 20 document's own Preamble (page 8 of the
  PDF) does not state that earlier date -- only the general
  contractual purpose is given -- so the earlier date was NOT used;
  only the directly-confirmable 2020 release is recorded.

  An association not in `catalog` has NO spec-basis, full stop; never
  fabricate one.")

(def catalog
  "association-slug -> vector of association-rule entries."
  {"sba"
   [{:association-rule/id "sba.founding-1912-basel"
     :association-rule/title "Swiss Bankers Association founded in Basel (Wikipedia)"
     :association-rule/association "sba"
     :association-rule/isic "6419"
     :association-rule/country "CHE"
     :association-rule/kind :governance-program
     :association-rule/url "https://en.wikipedia.org/wiki/Swiss_Bankers_Association"
     :association-rule/url-provenance :wikipedia-corroborated
     :association-rule/established-date "1912"
     :association-rule/retrieved-at "2026-07-17"
     :association-rule/topic #{:governance}}
    {:association-rule/id "sba.cdb-20-2020-due-diligence-code"
     :association-rule/title "Agreement on the Swiss banks' code of conduct with regard to the exercise of due diligence (CDB 20)"
     :association-rule/association "sba"
     :association-rule/isic "6419"
     :association-rule/country "CHE"
     :association-rule/kind :governance-program
     :association-rule/url "https://www.swissbanking.ch/_Resources/Persistent/6/2/e/e/62eec3df0685e359c5a376dfca79dec8b908ea9c/SBA_Agreement_CDB_2020_EN.pdf"
     :association-rule/url-provenance :official-swissbanking-ch
     :association-rule/established-date "2020"
     :association-rule/retrieved-at "2026-07-17"
     :association-rule/topic #{:governance}}]})

(defn spec-basis [association] (get catalog association))

(defn coverage
  ([] (coverage (keys catalog)))
  ([associations]
   (let [have (filter catalog associations)
         missing (remove catalog associations)]
     {:requested (count associations)
      :covered (count have)
      :covered-associations (vec (sort have))
      :missing-associations (vec (sort missing))
      :note (str "cloud-itonami-assoc-6419-che-sba Wave 0 (ADR-2607141700): "
                 (count (get catalog "sba")) " SBA entries seeded "
                 "with wikipedia.org/swissbanking.ch citations. "
                 "Extend `association.facts/catalog`, never fabricate an id/url.")})))

(defn by-topic [association topic]
  (filterv #(contains? (:association-rule/topic %) topic) (spec-basis association)))
