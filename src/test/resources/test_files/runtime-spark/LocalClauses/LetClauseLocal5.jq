(:JIQS: ShouldRun; Output="5" :)
let $i := json-file("./src/test/resources/queries/conf-ex.json") return count($i)

(: first let clause with an RDD(gets materialized) :)
