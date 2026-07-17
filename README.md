# cloud-itonami-assoc-6419-che-sba

Industry rule/history catalog for the **Swiss Bankers Association**
(SBA, Schweizerische Bankiervereinigung) — the ELEVENTH entry aligned
to **ISIC 6419** (other monetary intermediation / banking), alongside
[`-6419-jpn-zenginkyo`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-jpn-zenginkyo)
(Japan),
[`-6419-deu-bankenverband`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-deu-bankenverband)
(Germany),
[`-6419-fra-fbf`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-fra-fbf)
(France),
[`-6419-aus-aba`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-aus-aba)
(Australia),
[`-6419-are-ubf`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-are-ubf)
(UAE),
[`-6419-vnm-vnba`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-vnm-vnba)
(Vietnam),
[`-6419-phl-bap`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-phl-bap)
(Philippines),
[`-6419-esp-aeb`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-esp-aeb)
(Spain),
[`-6419-tur-tbb`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-tur-tbb)
(Turkey), and
[`-6419-nga-cibn`](https://github.com/cloud-itonami/cloud-itonami-assoc-6419-nga-cibn)
(Nigeria).
Part of the [`cloud-itonami`](https://github.com/cloud-itonami)
compliance-fact family (ADR-2607141700,
`cloud-itonami-compliance-fact-federation`, in `com-junkawasaki/root`).

## Sourcing note

This repo fills Switzerland's previously-open association-axis gap
(noted honestly at tick 129). Switzerland now has real, individually
verified facts across all three axes: municipality
([`cloud-itonami-municipality-che-bern`](https://github.com/cloud-itonami/cloud-itonami-municipality-che-bern)),
country
([`cloud-itonami-iso3166-che`](https://github.com/cloud-itonami/cloud-itonami-iso3166-che)),
and association (this repo).

WebSearch had surfaced an earlier 1977 origin for the CDB
due-diligence code, but the CDB 20 document's own Preamble does not
state that earlier date — only the directly-confirmable 2020 release
is recorded here.

## Scope

A **read-only reference/archive** catalog — not an Advisor⊣Governor
actuation actor. It proposes or executes nothing on SBA's behalf.

Coverage is reported honestly (see `association.facts/coverage`): an
association not in `catalog` has **no spec-basis**, full stop — never
fabricate one.

## Data

- `src/association/facts.cljc` — the catalog, source of truth.
- `schema/association-rule.edn` — DataScript schema.
- `data/datascript-tx.edn` — derived DataScript tx-data (query this
  alongside other `cloud-itonami`/`etzhayyim` compliance-fact sources via
  `com-junkawasaki/root`'s `scripts/compliance-fact-query.cljs`).

Both entries directly confirmed: the 1912 Basel founding (via
Wikipedia) and the 2020 CDB 20 due-diligence code (via
`swissbanking.ch`'s own hosted PDF).

## License

AGPL-3.0-or-later (matches the `cloud-itonami-iso3166-*` /
`-municipality-*` / `-assoc-*` / `-lei-*` convention). Policy text
itself remains SBA's; this repo stores only citation metadata
(id/title/url/dates), not full text.
