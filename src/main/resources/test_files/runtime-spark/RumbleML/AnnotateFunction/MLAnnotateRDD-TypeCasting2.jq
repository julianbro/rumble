(:JIQS: ShouldRun; Output="({ "id" : 1, "age" : 20, "weight" : 68.8 }, { "id" : 2, "age" : 35, "weight" : 72.4 }, { "id" : 3, "age" : 50, "weight" : 76.3 })" :)
annotate(
    json-file("./src/main/resources/queries/rumbleML/sample-ml-data-age-weight.json"),
    {"id": "integer", "age": "integer", "weight": "double"}
)

(: decimal to double cast on weight column :)
