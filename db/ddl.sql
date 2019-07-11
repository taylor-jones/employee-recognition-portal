/**
 * General Notes:
 *
 * There following columns are present in each table:
 * - created_at
 * - created_by
 * - updated_at
 * - updated_by
 *
 * ...but I'm uncertain as to whether it's a nice 
 * practice or if it's adding unnecesary complexity.
 */


/********************************************
  account

  NOTE: The name 'account' is used because the 
  word 'user'is reserved in postgres. We could 
  use 'users', but I generally think it's best 
  practice to use singular table names (but, it's
  totally fine with me if you guys want to use 
  plural names and switch it to 'users' instead).

  NOTE: I'm not sure exactly how we want to store 
  the signature image yet. It seems we could either 
  store it directly in the DB (which is the current
  configuration) or we could store it elsewhere 
  and point to that location.

  NOTE: My understanding is that we could hash and 
  salt the passwords to keep them more secure but 
  still use a varchar as the datatype. I'm totally
  open to better methods.
********************************************/

DROP TABLE IF EXISTS account CASCADE;

CREATE TABLE account (
  "id" SERIAL PRIMARY KEY,
  "email" VARCHAR(255) NULL,
  "name" VARCHAR(255) NULL,
  "password" VARCHAR(255) NULL,
  "signature" BYTEA NULL,
  "is_admin" BOOLEAN NULL,
  "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "created_by" INT NOT NULL REFERENCES account(id),
  "updated_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "updated_by" INT NOT NULL REFERENCES account(id)
  CHECK ((is_admin) OR ("name" IS NOT NULL AND "signature" IS NOT NULL))
);

ALTER TABLE account OWNER TO "tttAdmin";
GRANT ALL ON TABLE account TO "tttAdmin";



/********************************************
  award_type
********************************************/

DROP TABLE IF EXISTS award_type CASCADE;

CREATE TABLE award_type (
  "id" SERIAL PRIMARY KEY,
  "name" VARCHAR(255) NOT NULL,
  "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "created_by" INT NOT NULL,
  "updated_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "updated_by" INT NOT NULL
);

ALTER TABLE award_type OWNER TO "tttAdmin";
GRANT ALL ON TABLE award_type TO "tttAdmin";



/********************************************
  region
********************************************/

DROP TABLE IF EXISTS region CASCADE;

CREATE TABLE region (
  "id" SERIAL PRIMARY KEY,
  "name" VARCHAR(255) NOT NULL,
  "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "created_by" INT NOT NULL REFERENCES account(id),
  "updated_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "updated_by" INT NOT NULL REFERENCES account(id)
);

ALTER TABLE region OWNER TO "tttAdmin";
GRANT ALL ON TABLE region TO "tttAdmin";



/********************************************
  person
********************************************/

DROP TABLE IF EXISTS person CASCADE;

CREATE TABLE person (
  "id" SERIAL PRIMARY KEY,
  "email" VARCHAR(255) NULL,
  "name" VARCHAR(255) NULL,
  "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "created_by" INT NOT NULL REFERENCES account(id),
  "updated_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "updated_by" INT NOT NULL REFERENCES account(id)
);

ALTER TABLE person OWNER TO "tttAdmin";
GRANT ALL ON TABLE person TO "tttAdmin";



/********************************************
  person_region
********************************************/

DROP TABLE IF EXISTS person_region CASCADE;

CREATE TABLE person_region (
  "person_id" INT NOT NULL REFERENCES person(id),
  "region_id" INT NOT NULL REFERENCES region(id),
  "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "created_by" INT NOT NULL REFERENCES account(id),
  "updated_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "updated_by" INT NOT NULL REFERENCES account(id)
);

ALTER TABLE person_region OWNER TO "tttAdmin";
GRANT ALL ON TABLE person_region TO "tttAdmin";



/********************************************
  award
********************************************/

DROP TABLE IF EXISTS award CASCADE;

CREATE TABLE award (
  "id" SERIAL PRIMARY KEY,
  "award_type_id" INT NOT NULL REFERENCES award_type(id),
  "person_id" INT NOT NULL REFERENCES person(id),
  "awarded_date" DATE NULL,
  "awarded_time" TIME NULL,
  "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "created_by" INT NOT NULL REFERENCES award(id),
  "updated_at" TIMESTAMP NOT NULL DEFAULT NOW(),
  "updated_by" INT NOT NULL REFERENCES award(id)
);

ALTER TABLE award OWNER TO "tttAdmin";
GRANT ALL ON TABLE award TO "tttAdmin";
