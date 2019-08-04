#!/bin/bash

# TR: Dumps the current database pointed to by your env variables. Requires:
#    DB_NAME, DB_HOST, DB_PORT, SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME,
#    SPRING_DATASOURCE_PASSWORD
# If any of those are missing or incorrectly assigned this will do nothing.
# Usage: ./dbdump.sh <relative path for output>


exitOnEmptyVar() {
  if [ -z "$1" ] 
  then
    exit 2
  fi
}

for var in $DB_NAME $DB_HOST $DB_PORT $SPRING_DATASOURCE_URL $SPRING_DATASOURCE_USERNAME $SPRING_DATASOURCE_PASSWORD
do
  exitOnEmptyVar $var
done

echo; echo
echo "Running..."

schemaDirectory=$1
date=$(date '+%Y%m%d_%H%M')
fName="$DB_NAME.$date.sql"

PGPASSWORD="$SPRING_DATASOURCE_PASSWORD" \
pg_dump -h $DB_HOST -p $DB_PORT -U $SPRING_DATASOURCE_USERNAME $DB_NAME > "$schemaDirectory/$fName"

echo "$DB_NAME schema dumped to: $schemaDirectory/$fName"
echo; echo

exit 1