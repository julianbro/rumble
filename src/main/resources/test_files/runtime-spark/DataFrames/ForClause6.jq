(:JIQS: ShouldRun; Output="({ "$i:" : 6, "$j:" : 8 }, { "$i:" : 6, "$j:" : 9 }, { "$i:" : 7, "$j:" : 8 }, { "$i:" : 7, "$j:" : 9 }, { "$i:" : 6, "$j:" : 8 }, { "$i:" : 6, "$j:" : 9 }, { "$i:" : 7, "$j:" : 8 }, { "$i:" : 7, "$j:" : 9 }, { "$i:" : 6, "$j:" : 8 }, { "$i:" : 6, "$j:" : 9 }, { "$i:" : 7, "$j:" : 8 }, { "$i:" : 7, "$j:" : 9 }, { "$i:" : 6, "$j:" : 8 }, { "$i:" : 6, "$j:" : 9 }, { "$i:" : 7, "$j:" : 8 }, { "$i:" : 7, "$j:" : 9 }, { "$i:" : 6, "$j:" : 8 }, { "$i:" : 6, "$j:" : 9 }, { "$i:" : 7, "$j:" : 8 }, { "$i:" : 7, "$j:" : 9 }, { "$i:" : 6, "$j:" : 8 }, { "$i:" : 6, "$j:" : 9 }, { "$i:" : 7, "$j:" : 8 }, { "$i:" : 7, "$j:" : 9 })" :)
for $i in parallelize((1 to 2)) for $j in 3 to 5 for $i in 6 to 7 for $j in 8 to 9 return {"$i:": $i,"$j:": $j}

(: dataframes variable hiding 3 :)