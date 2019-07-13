DELETE FROM award;
DELETE FROM award_type;
DELETE FROM employee_region;
DELETE FROM region;
DELETE FROM employee;
DELETE FROM log;
DELETE FROM account;

INSERT INTO account VALUES
  (1, 'admin', 'admin', 'admin', NULL, true),
  (2, 'demo@demo.com', 'demo', 'demo', NULL, false);

-- https://theoffice.fandom.com/wiki/Dundie
-- I left out the ones that foul language, just in case...
INSERT INTO award_type VALUES
  (1, 'Busiest Beaver'),
  (2, 'Hottest in the Office'),
  (3, 'Spicy Curry'),
  (4, 'Dont Go In There After Me'),
  (5, 'Fine Work'),
  (6, 'Whitest Sneakers'),
  (7, 'Grace Under Fire'),
  (8, 'Jim Halpert Award'),
  (9, 'Best Dad'),
  (10, 'Best Mom'),
  (11, 'Diabetes Award'),
  (12, 'Promising Assistant Manager'),
  (13, 'Cutest Redhead In The Office'),
  (14, 'Best Dundies'),
  (15, 'Doobie Doobie Pothead Stoner of the Year'),
  (16, 'Extreme Repulsiveness Award'),
  (17, 'Redefining Beauty Award'),
  (18, 'Moving On Up Award'),
  (19, 'Worst Salesman of the Year'),
  (20, 'Great Work Award'),
  (21, 'Longest Engagement'),
  (22, 'Show Me The Money');


-- https://theoffice.fandom.com/wiki/Branch
INSERT INTO region VALUES
  (1, 'Scranton'),
  (2, 'Akron'),
  (3, 'Albany'),
  (4, 'Nashua'),
  (5, 'Rochester'),
  (6, 'Utica'),
  (7, 'Binghamton'),
  (8, 'Buffalo'),
  (9, 'Camden'),
  (10, 'Northeast'),
  (11, 'Pittsfield'),
  (12, 'Stamford'),
  (13, 'Yonkers'),
  (14, 'New York City'),
  (15, 'Tallahassee');


-- https://theoffice.fandom.com/wiki/Category:Characters
-- https://theoffice.fandom.com/wiki/Background_Employees
-- https://theoffice.fandom.com/wiki/Dunder_Mifflin_Stamford
INSERT INTO employee VALUES
  (1, 'martin@dundermifflin.com', 'Martin Nash'),
  (2, 'hannah@dundermifflin.com', 'Hannah Smoterich-Barr'),
  (3, 'michael@dundermifflin.com', 'Michael Scott'),
  (4, 'jim@dundermifflin.com', 'Jim Halpert'),
  (5, 'pam@dundermifflin.com', 'Pam Beasley'),
  (6, 'dwight@dundermifflin.com', 'Dwight Schrute'),
  (7, 'andy@dundermifflin.com', 'Andy Bernard'),
  (8, 'ryan@dundermifflin.com', 'Ryan Howard'),
  (9, 'karen@dundermifflin.com', 'Karen Filippelli'),
  (10, 'angela@dundermifflin.com', 'Angela Martin'),
  (11, 'meredith@dundermifflin.com', 'Maredith Palmer'),
  (12, 'creed@dundermifflin.com', 'Creed Bratton'),
  (13, 'darryl@dundermifflin.com', 'Darryl Philbin'),
  (14, 'gabe@dundermifflin.com', 'Gabe Lewis'),
  (15, 'robert@dundermifflin.com', 'Robert California'),
  (16, 'kelly@dundermifflin.com', 'Kelly Kapoor'),
  (17, 'kevin@dundermifflin.com', 'Kevin Malone'),
  (18, 'stanley@dundermifflin.com', 'Stanley Hudson'),
  (19, 'jenniee@dundermifflin.com', 'Jennie Tate'),
  (20, 'mark@dundermifflin.com', 'Mark Johnson'),
  (21, 'david@dundermifflin.com', 'David Wallace'),
  (22, 'oscar@dundermifflin.com', 'Oscar Martinez'),
  (23, 'jan@dundermifflin.com', 'Jan Levinson'),
  (24, 'holly@dundermifflin.com', 'Holly Flax'),
  (25, 'josh@dundermifflin.com', 'Josh Porter'),
  (26, 'tony@dundermifflin.com', 'Tony Gardner'),
  (27, 'phyllis@dundermifflin.com', 'Phyllis Vance'),
  (28, 'erin@dundermifflin.com', 'Erin Hannon'),
  (29, 'deangelo@dundermifflin.com', 'Deangelo Vickers'),
  (30, 'toby@dundermifflin.com', 'Toby Flenderson');


INSERT INTO employee_region VALUES
  (1, 12),
  (2, 12),
  (4, 1),
  (5, 1),
  (6, 1),
  (7, 1),
  (8, 14),
  (9, 12),
  (10, 1),
  (11, 1),
  (12, 1),
  (13, 1),
  (14, 15),
  (15, 15),
  (16, 1),
  (17, 1),
  (18, 1),
  (19, 8),
  (20, 8),
  (21, 14),
  (22, 1),
  (23, 14),
  (24, 4),
  (25, 4),
  (26, 4);


-- https://theoffice.fandom.com/wiki/Dundie
INSERT INTO award VALUES
  (1, 1, 27, 2, NULL, '2005-09-20', '20:00:00'),
  (2, 2, 8, 2, NULL, '2005-09-20', '20:00:00'),
  (3, 3, 16, 2, NULL, '2005-09-20', '20:00:00'),
  (4, 4, 17, 2, NULL, '2005-09-20', '20:00:00'),
  (5, 5, 18, 2, NULL, '2005-09-20', '20:00:00'),
  (6, 6, 5, 2, NULL, '2005-09-20', '20:00:00'),
  (7, 7, 11, 2, NULL, '2005-09-20', '20:00:00'),
  (8, 8, 4, 2, NULL, '2005-09-20', '20:00:00'),
  (9, 9, 4, 2, NULL, '2011-04-21', '19:00:00'),
  (10, 10, 11, 2, NULL, '2011-04-21', '19:00:00'),
  (11, 11, 18, 2, NULL, '2011-04-21', '19:00:00'),
  (12, 12, 6, 2, NULL, '2011-04-21', '19:00:00'),
  (13, 13, 28, 2, NULL, '2011-04-21', '19:00:00'),
  (14, 14, 29, 2, NULL, '2011-04-21', '19:00:00'),
  (15, 15, 7, 2, NULL, '2011-04-21', '19:00:00'),
  (16, 16, 30, 2, NULL, '2011-04-21', '19:00:00'),
  (17, 17, 27, 2, NULL, '2011-04-21', '19:00:00'),
  (18, 18, 13, 2, NULL, '2011-04-21', '19:00:00'),
  (19, 19, 22, 2, NULL, '2011-04-21', '19:00:00'),
  (20, 20, 18, 2, NULL, '2005-09-20', '20:00:00'),
  (21, 21, 5, 2, NULL, '2005-09-20', '20:00:00'),
  (22, 22, 22, 2, NULL, '2005-09-20', '20:00:00'),
  (23, 2, 8, 2, NULL, '2005-09-20', '20:00:00'),
  (24, 2, 8, 2, NULL, '2006-09-20', '20:00:00'),
  (25, 2, 8, 2, NULL, '2007-09-20', '20:00:00'),
  (26, 2, 8, 2, NULL, '2008-09-20', '20:00:00'),
  (27, 2, 8, 2, NULL, '2009-09-20', '20:00:00'),
  (28, 2, 8, 2, NULL, '2010-09-20', '20:00:00');
