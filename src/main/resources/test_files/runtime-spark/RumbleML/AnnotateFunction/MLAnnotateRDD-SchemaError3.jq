(:JIQS: ShouldCrash; ErrorCode="RBML0005"; :)
annotate(
    json-file("./src/main/resources/queries/rumbleML/sample-ml-data-age-weight.json"),
    {"id": "int", "age": "str", "weight": "dec"}
)

(: schema has unrecognized types :)
