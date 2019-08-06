--
---- Users
--


-- List the users in order of most awards given (descending)

SELECT USER_ACCOUNT_ID, COUNT(*) AS total 
FROM AWARD 
GROUP BY USER_ACCOUNT_ID
ORDER BY total DESC;


-- List all users along with the number of awards given for each user

SELECT u.id, u.username, COUNT(a.id) AS total
FROM award AS a
RIGHT JOIN user_account AS u
ON u.id = a.user_account_id
GROUP BY u.id, u.username
ORDER BY total DESC;


-- List the users in order of most awards given (descending)
-- Includes only users which have given any awards.

SELECT u.id, u.username, COUNT(*) AS total 
FROM award AS a
INNER JOIN user_account AS u
ON u.id = a.user_account_id
GROUP BY u.id, u.username
ORDER BY total DESC;



--
---- Awards / Award Types
--


-- List the number of times each award type was given

SELECT t.id, t.name AS name , COUNT(a.award_type_id) AS total
FROM award_type AS t
INNER JOIN award AS a
ON t.id = a.award_type_id
GROUP BY t.id, name
ORDER BY total DESC;


-- List all awards given in the specified year

SELECT 
  a.id, 
  a.awarded_date, 
  a.awarded_time, 
  e.first_name, 
  e.last_name, 
  awt.name AS award_type 
FROM award AS a
INNER JOIN employee AS e
ON e.id = a.employee_id
INNER JOIN award_type AS awt
ON awt.id = a.award_type_id
WHERE EXTRACT(YEAR FROM a.awarded_date) = :awardYear
ORDER BY a.awarded_date DESC;



--
---- Employees
--


-- List the number of awards each employee received

SELECT e.id, e.first_name, e.last_name, COUNT(a.id) AS total
FROM employee AS e
LEFT JOIN award AS a
ON e.id = a.employee_id
GROUP BY e.id, e.first_name, e.last_name
ORDER BY total DESC;


-- List the number of different types of awards each
-- employee received

SELECT e.id, e.first_name, e.last_name, COUNT(DISTINCT a.award_type_id) AS total
FROM employee AS e
LEFT JOIN award AS a
ON e.id = a.employee_id
GROUP BY e.id, e.first_name, e.last_name
ORDER BY total DESC;


-- List the employees receiving a specified award type

SELECT e.id, e.first_name, e.last_name, COUNT(a.id) AS total
FROM employee AS e
LEFT JOIN award AS a
ON e.id = a.employee_id
WHERE a.award_type_id = :awardTypeId
GROUP BY e.id, e.first_name, e.last_name
ORDER BY total DESC;


--
---- Regions
--

-- List the number of awards given to each region

SELECT 
  r.id, 
  r.name AS name, 
  COUNT(region_awards.award_id) AS total
FROM (
  SELECT 
    er.employee_id, 
    er.region_id, 
    a.id AS award_id 
  FROM employee_region AS er
  INNER JOIN award AS a
  ON er.employee_id = a.employee_id
) AS region_awards
RIGHT JOIN region AS r
ON region_awards.region_id = r.id
GROUP BY r.id, name
ORDER BY total DESC;


-- List the employees receiving awards from a specified region

SELECT 
  region_awards.award_id,
  region_awards.award_type,
  e.first_name, 
  e.last_name,
  region_awards.awarded_date,
  region_awards.awarded_time
FROM (
  SELECT 
    er.employee_id, 
    er.region_id, 
    a.id AS award_id,
    a.awarded_date,
    a.awarded_time,
    a.award_type_id,
    awt.name AS award_type
  FROM employee_region AS er
  INNER JOIN award AS a
  ON er.employee_id = a.employee_id
  INNER JOIN award_type AS awt
  ON awt.id = a.award_type_id
) AS region_awards
INNER JOIN employee AS e
ON region_awards.employee_id = e.id
WHERE region_awards.region_id = :regionId;


