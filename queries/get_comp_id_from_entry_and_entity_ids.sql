SELECT "Comp_ID",COUNT("Comp_ID")
FROM "Entity_comp_index"
WHERE "Entry_ID" = '1669' AND "Entity_ID" = '1' AND "Comp_ID" IS NOT NULL
GROUP BY "Comp_ID"
HAVING COUNT("Comp_ID") > 0
