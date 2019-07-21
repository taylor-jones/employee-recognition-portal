/********************************************
  TYPES
 *******************************************/
DROP TYPE IF EXISTS CRUD_OPERATION CASCADE;
CREATE TYPE CRUD_OPERATION AS ENUM ('insert', 'update', 'delete');


/********************************************
  user_account
********************************************/
DROP TABLE IF EXISTS user_account CASCADE;
CREATE TABLE user_account (
  "id" INT PRIMARY KEY,
  "email" VARCHAR(255) NULL,
  "username" VARCHAR(255) NULL,
  "password" VARCHAR(255) NULL,
  "signature" BYTEA NULL,
  "is_admin" BOOLEAN NULL,
  CHECK (is_admin OR "username" IS NOT NULL)
);

ALTER TABLE user_account OWNER TO "tttAdmin";
GRANT ALL ON TABLE user_account TO "tttAdmin";


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
  "first_name" VARCHAR(100) NOT NULL,
  "last_name" VARCHAR(100) NOT NULL
);

ALTER TABLE employee OWNER TO "tttAdmin";
GRANT ALL ON TABLE employee TO "tttAdmin";


/********************************************
  employee_region
********************************************/
DROP TABLE IF EXISTS employee_region CASCADE;
CREATE TABLE employee_region (
  "employee_id" INT NOT NULL REFERENCES employee(id),
  "region_id" INT NOT NULL REFERENCES region(id),
  "begin_date" DATE NULL,  -- when the employee began working at this region
  "end_date" DATE NULL  -- when the employee stopped working at this region
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
  "user_account_id" INT NOT NULL REFERENCES user_account(id),
  "description" VARCHAR(1000) NULL,
  "awarded_date" DATE NULL,
  "awarded_time" TIME NULL
);

ALTER TABLE award OWNER TO "tttAdmin";
GRANT ALL ON TABLE award TO "tttAdmin";


/********************************************
  log
 *******************************************/
DROP TABLE IF EXISTS log;
CREATE TABLE log (
  "id" BIGINT NOT NULL PRIMARY KEY,
  "user_account_id" INT NOT NULL REFERENCES user_account(id),
  "controller_class" VARCHAR(255) NULL,
  "subject_id" BIGINT NOT NULL,
  "operation" varchar(50) NULL,
  "property" VARCHAR(255) NULL,
  "changed_from" VARCHAR(255) NULL,
  "changed_to" VARCHAR(255) NULL,
  "modified_at" TIMESTAMP NOT NULL DEFAULT NOW()
);

ALTER TABLE log OWNER TO "tttAdmin";
GRANT ALL ON TABLE log TO "tttAdmin";
