(:JIQS: ShouldCrash; ErrorCode="XPTY0004" :)
declare $hexb := "0cd7" cast as hexBinary
for $a in ($hexb, $hexb)
order by $a
return $a