(:JIQS: ShouldCrash; ErrorCode="XPDY0050"; ErrorMetadata="LINE:3:COLUMN:0:" :)
for $j as integer in parallelize((1 to 10))
let $k as string := $j
return $k