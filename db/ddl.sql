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
  "id" INT PRIMARY KEY,
  "email" VARCHAR(255) NULL,
  "name" VARCHAR(255) NULL,
  "password" VARCHAR(255) NULL,
  "signature" BYTEA NULL,
  "is_admin" BOOLEAN NULL,
  CHECK (is_admin OR "name" IS NOT NULL)
);

ALTER TABLE account OWNER TO "tttAdmin";
GRANT ALL ON TABLE account TO "tttAdmin";



/********************************************
  award_type
********************************************/

DROP TABLE IF EXISTS award_type CASCADE;

CREATE TABLE award_type (
  "id" INT PRIMARY KEY,
  "name" VARCHAR(255) NOT NULL
);

ALTER TABLE award_type OWNER TO "tttAdmin";
GRANT ALL ON TABLE award_type TO "tttAdmin";



/********************************************
  region
********************************************/

DROP TABLE IF EXISTS region CASCADE;

CREATE TABLE region (
  "id" INT PRIMARY KEY,
  "name" VARCHAR(255) NOT NULL
);

ALTER TABLE region OWNER TO "tttAdmin";
GRANT ALL ON TABLE region TO "tttAdmin";



/********************************************
  employee
********************************************/

DROP TABLE IF EXISTS employee CASCADE;

CREATE TABLE employee (
  "id" INT PRIMARY KEY,
  "email" VARCHAR(255) NULL,
  "name" VARCHAR(255) NULL
);

ALTER TABLE employee OWNER TO "tttAdmin";
GRANT ALL ON TABLE employee TO "tttAdmin";



/********************************************
  employee_region
********************************************/

DROP TABLE IF EXISTS employee_region CASCADE;

CREATE TABLE employee_region (
  "employee_id" INT NOT NULL REFERENCES employee(id),
  "region_id" INT NOT NULL REFERENCES region(id)
);

ALTER TABLE employee_region OWNER TO "tttAdmin";
GRANT ALL ON TABLE employee_region TO "tttAdmin";



/********************************************
  award
********************************************/

DROP TABLE IF EXISTS award CASCADE;

CREATE TABLE award (
  "id" INT PRIMARY KEY,
  "award_type_id" INT NOT NULL REFERENCES award_type(id),
  "employee_id" INT NOT NULL REFERENCES employee(id),
  "account_id" INT NOT NULL REFERENCES account(id),
  "description" VARCHAR(1000) NULL,
  "awarded_date" DATE NULL,
  "awarded_time" TIME NULL
);

ALTER TABLE award OWNER TO "tttAdmin";
GRANT ALL ON TABLE award TO "tttAdmin";


/********************************************
  TYPES
 *******************************************/
DROP TYPE IF EXISTS CRUD_OPERATION CASCADE;
CREATE TYPE CRUD_OPERATION AS ENUM ('insert', 'update', 'delete');



/********************************************
  log
 *******************************************/
DROP TABLE IF EXISTS log CASCADE;

CREATE TABLE log (
  "id" BIGINT NOT NULL PRIMARY KEY,
  "account_id" INT NOT NULL REFERENCES account(id),
  "controller_class" VARCHAR(255) NULL,
  "operation" CRUD_OPERATION NOT NULL,
  "property" VARCHAR(255) NOT NULL,
  "changed_from" VARCHAR(255) NULL,
  "changed_to" VARCHAR(255) NULL,
  "modified_at" TIMESTAMP NOT NULL DEFAULT NOW()
);

ALTER TABLE log OWNER TO "tttAdmin";
GRANT ALL ON TABLE log TO "tttAdmin";
