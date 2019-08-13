DELETE FROM award;
DELETE FROM award_type;
DELETE FROM employee_region;
DELETE FROM region;
DELETE FROM employee;
DELETE FROM log;
DELETE FROM user_account;


-- Make sure all users get logs on data init!
INSERT INTO user_account VALUES
  (1, 'admin@admin.com', 'admin', 'admin', NULL, true, true),
  (1000, 'user1@user1.com', 'user1', 'user1', NULL, false, true),
  (2000, 'user2@user2.com', 'user2', 'user2', NULL, false, true);

INSERT INTO log VALUES
  (2001, 1, 'UserAccount', 1, 'insert', NULL, NULL, NULL, now()),
  (2002, 1, 'UserAccount', 1000, 'insert', NULL, NULL, NULL, now()),
  (2003, 1, 'UserAccount', 2000, 'insert', NULL, NULL, NULL, now());

-- https://theoffice.fandom.com/wiki/Dundie
-- I left out the ones that foul language, just in case...
INSERT INTO award_type VALUES
  (1, 'Busiest Beaver'),
  (2, 'Awesome Attitude'),
  (3, 'Wonderful Worker'),
  (4, 'Dont Go In There After Me'),
  (5, 'Fine Work'),
  (6, 'Whitest Sneakers'),
  (7, 'Grace Under Fire'),
  (8, 'Jim Halpert Award'),
  (9, 'Best Dad'),
  (10, 'Best Mom'),
  (11, 'Sweetest Tooth Award'),
  (12, 'Promising Assistant Manager'),
  (13, 'Coolest Socks'),
  (14, 'Best Dundies'),
  (15, 'Employee of the Year'),
  (16, 'Extreme Repulsiveness Award'),
  (17, 'Redefining Beauty Award'),
  (18, 'Moving On Up Award'),
  (19, 'Worst Salesman of the Year'),
  (20, 'Great Work Award'),
  (21, 'Longest Engagement'),
  (22, '#1 Greatest Professor'),
  (23, '#1 Greatest TA'),
  (24, '#1 Greatest Student'),
  (25, 'Pure Genius Award');


-- https://theoffice.fandom.com/wiki/Branch
INSERT INTO region VALUES
  (1, 'Scranton'),
  (4, 'Nashua'),
  (5, 'Rochester'),
  (8, 'Buffalo'),
  (12, 'Stamford'),
  (14, 'New York City'),
  (15, 'Tallahassee'),
  (16, 'Corvallis');


-- https://theoffice.fandom.com/wiki/Category:Characters
-- https://theoffice.fandom.com/wiki/Background_Employees
-- https://theoffice.fandom.com/wiki/Dunder_Mifflin_Stamford
INSERT INTO employee VALUES
  (1, 'martin@dundermifflin.com', 'Martin', 'Nash'),
  (2, 'hannah@dundermifflin.com', 'Hannah', 'Smoterich-Barr'),
  (3, 'michael@dundermifflin.com', 'Michael', 'Scott'),
  (4, 'jim@dundermifflin.com', 'Jim', 'Halpert'),
  (5, 'pam@dundermifflin.com', 'Pam', 'Beasley'),
  (6, 'dwight@dundermifflin.com', 'Dwight', 'Schrute'),
  (7, 'andy@dundermifflin.com', 'Andy', 'Bernard'),
  (8, 'ryan@dundermifflin.com', 'Ryan', 'Howard'),
  (9, 'karen@dundermifflin.com', 'Karen', 'Filippelli'),
  (10, 'angela@dundermifflin.com', 'Angela', 'Martin'),
  (11, 'meredith@dundermifflin.com', 'Maredith', 'Palmer'),
  (12, 'creed@dundermifflin.com', 'Creed', 'Bratton'),
  (13, 'darryl@dundermifflin.com', 'Darryl', 'Philbin'),
  (14, 'gabe@dundermifflin.com', 'Gabe', 'Lewis'),
  (15, 'robert@dundermifflin.com', 'Robert', 'California'),
  (16, 'kelly@dundermifflin.com', 'Kelly', 'Kapoor'),
  (17, 'kevin@dundermifflin.com', 'Kevin', 'Malone'),
  (18, 'stanley@dundermifflin.com', 'Stanley', 'Hudson'),
  (19, 'jenniee@dundermifflin.com', 'Jennie', 'Tate'),
  (20, 'mark@dundermifflin.com', 'Mark', 'Johnson'),
  (21, 'david@dundermifflin.com', 'David', 'Wallace'),
  (22, 'oscar@dundermifflin.com', 'Oscar', 'Martinez'),
  (23, 'jan@dundermifflin.com', 'Jan', 'Levinson'),
  (24, 'holly@dundermifflin.com', 'Holly', 'Flax'),
  (25, 'josh@dundermifflin.com', 'Josh', 'Porter'),
  (26, 'tony@dundermifflin.com', 'Tony', 'Gardner'),
  (27, 'phyllis@dundermifflin.com', 'Phyllis', 'Vance'),
  (28, 'erin@dundermifflin.com', 'Erin', 'Hannon'),
  (29, 'deangelo@dundermifflin.com', 'Deangelo', 'Vickers'),
  (30, 'gambordr@oregonstate.edu', 'Ryan', 'Gambord'),
  (31, 'aminzahi@oregonstate.edu', 'Iman', 'Aminzahed'),
  (32, 'rahurkap@oregonstate.edu', 'Prachi', 'Rahurkar'),
  (33, 'lavellt@oregonstate.edu', 'Tucker', 'Lavell'),
  (34, 'rietzt@oregonstate.edu', 'Taylor', 'Rietz'),
  (35, 'jonest6@oregonstate.edu', 'Taylor', 'Jones'),
  (36, 'amen@dundermifflin.com', 'Amen', 'Nguyen'),
  (37, 'kardelen@dundermifflin.com', 'Kardelen', 'Udo'),
  (38, 'danae@dundermifflin.com', 'Danae', 'Sanches Nieto'),
  (39, 'naja@dundermifflin.com', 'Naja', 'Petrussen');


INSERT INTO employee_region VALUES
  (1, 12, NULL, NULL),
  (2, 12, NULL, NULL),
  (4, 1, NULL, NULL),
  (5, 1, NULL, NULL),
  (6, 1, NULL, NULL),
  (7, 1, NULL, NULL),
  (8, 14, NULL, NULL),
  (9, 12, NULL, NULL),
  (10, 1, NULL, NULL),
  (11, 1, NULL, NULL),
  (12, 1, NULL, NULL),
  (13, 1, NULL, NULL),
  (14, 15, NULL, NULL),
  (15, 15, NULL, NULL),
  (16, 1, NULL, NULL),
  (17, 1, NULL, NULL),
  (18, 8, NULL, NULL),
  (19, 8, NULL, NULL),
  (20, 8, NULL, NULL),
  (21, 14, NULL, NULL),
  (22, 1, NULL, NULL),
  (23, 14, NULL, NULL),
  (24, 4, NULL, NULL),
  (25, 4, NULL, NULL),
  (26, 4, NULL, NULL),
  (27, 1, NULL, NULL),
  (28, 1, NULL, NULL),
  (29, 1, NULL, NULL),
  (30, 16, NULL, NULL),
  (31, 16, NULL, NULL),
  (32, 16, NULL, NULL),
  (33, 16, NULL, NULL),
  (34, 16, NULL, NULL),
  (35, 16, NULL, NULL),
  (36, 4, NULL, NULL),
  (37, 5, NULL, NULL),
  (38, 5, NULL, NULL),
  (39, 5, NULL, NULL);


-- https://theoffice.fandom.com/wiki/Dundie
-- IMPORTANT: Note below that the INSERT statement assumes there are 
-- two non-admun users that have created all of these awards. The two
-- users in all of the award examples below have the IDs of 1000 and 2000.
-- Make sure to update those values to whatever the IDs are of the actual
-- non-admin users in the production database.

INSERT INTO award VALUES
  (1, 1, 27, 1000, 'You are always prompt and on task. Great job!', '2005-09-20', '19:00:00'),
  (2, 2, 8, 1000, 'You bring a great attitude to the office each and every day!', '2005-09-20', '18:00:00'),
  (3, 3, 16, 1000, 'You are a great asset to this team!', '2005-09-20', '19:30:00'),
  (4, 4, 17, 1000, 'I surely will not!', '2005-09-20', '20:10:00'),
  (5, 5, 18, 1000, 'You do a job.', '2005-09-20', '19:40:00'),
  (6, 6, 5, 1000, 'Those things never get dirty!', '2005-09-20', '20:05:00'),
  (7, 7, 11, 1000, 'You always keep calm and it shows.', '2005-09-20', '18:50:00'),
  (8, 8, 4, 1000, 'Yet again. Great job!', '2005-09-20', '20:00:00'),
  (9, 9, 4, 1000, 'Great job, Mr. Family Man', '2011-04-21', '19:00:00'),
  (10, 10, 11, 1000, 'Mothers Day is right around the corner!', '2011-04-21', '19:20:00'),
  (11, 11, 18, 1000, 'I saw those gummy bears on your desk.', '2011-04-21', '19:30:00'),
  (12, 12, 6, 1000, 'One day, all this could be yours!', '2011-04-21', '19:40:00'),
  (13, 13, 28, 1000, 'Everyday is a treat!', '2011-04-21', '19:50:00'),
  (14, 14, 29, 1000, 'You bring it each and every year!', '2011-04-21', '19:10:00'),
  (15, 15, 7, 1000, 'You are simply outstanding', '2011-04-21', '19:00:00'),
  (16, 16, 29, 1000, 'This is going on your permanent record.', '2011-04-21', '18:00:00'),
  (17, 17, 27, 2000, 'Magnificent!', '2011-04-21', '19:00:00'),
  (18, 18, 13, 2000, 'Maybe soon you can have an office with a window!', '2011-04-21', '20:10:00'),
  (19, 19, 22, 2000, 'This was not your best effort.', '2011-04-21', '19:00:00'),
  (20, 20, 18, 2000, 'The proof is in the pudding. Great work!', '2005-09-20', '20:00:00'),
  (21, 21, 5, 2000, 'Who says good things cant last forever?', '2005-09-20', '20:25:00'),
  (22, 25, 22, 2000, 'You seem to know it all!', '2005-09-20', '20:20:00'),
  (23, 2, 8, 2000, 'Great attitude, buster!', '2005-09-20', '20:05:00'),
  (24, 2, 8, 2000, 'Wow, that attitude just keeps getting better!', '2006-09-20', '20:10:00'),
  (25, 2, 8, 2000, 'You did it again, mister!', '2007-09-20', '20:15:00'),
  (26, 2, 8, 2000, 'Save some positivity for the rest of us!', '2008-09-20', '20:15:00'),
  (27, 2, 8, 2000, 'What can we say -- you are great!', '2009-09-20', '20:08:00'),
  (28, 2, 8, 2000, 'Wowza!', '2010-09-20', '20:00:00'),
  (29, 8, 4, 2000, 'Hmmm...something seems fishy here.', '2010-09-20', '20:00:00'),
  (30, 8, 4, 2000, 'Well, we had to give it to someoone.', '2010-09-20', '19:00:00'),
  (31, 3, 36, 2000, 'We are so grateful to have you on board!', '2010-09-20', '20:00:00'),
  (32, 5, 36, 2000, 'Holly has been asking about you', '2011-09-20', '21:00:00'),
  (33, 22, 30, 2000, 'This is just as prestigious as it sounds.', '2019-08-16', '17:00:00'),
  (34, 23, 31, 2000, 'We dont just give these out to anyone!', '2019-08-16', '18:00:00'),
  (35, 23, 32, 2000, 'We dont just give these out to anyone!', '2019-08-16', '20:00:00'),
  (36, 24, 33, 2000, 'Bravo! Great job team!', '2019-08-16', '19:00:00'),
  (37, 24, 34, 2000, 'Bravo! Great job team!', '2019-08-16', '16:00:00'),
  (38, 24, 35, 2000, 'Bravo! Great job team!', '2019-08-16', '16:00:00'),
  (39, 12, 36, 2000, 'I think you have a bright future here!', '2019-08-16', '15:00:00'),
  (40, 13, 37, 2000, 'Radical footwear!', '2018-08-16', '15:00:00'),
  (41, 14, 37, 2000, 'You are the gift that keeps on giving!', '2019-05-16', '18:00:00'),
  (42, 15, 37, 2000, 'You have been our most valuable asset this year.', '2019-04-13', '17:00:00'),
  (43, 15, 38, 2000, 'I sense a raise on the horizon', '2017-02-14', '18:00:00'),
  (44, 15, 38, 2000, 'I think its time we gave you your own office!', '2019-06-01', '19:00:00'),
  (45, 18, 38, 2000, 'You have shown a lot of growth lately!', '2018-01-12', '20:00:00'),
  (46, 19, 39, 2000, 'Yikes. Maybe next year.', '2019-11-19', '20:20:00'),
  (47, 20, 39, 2000, 'The title says it all. Great work!', '2016-03-04', '20:45:00'),
  (48, 11, 39, 2000, 'Say hello to the Tooth Fairy for us!', '2011-04-22', '20:00:00'),
  (49, 2, 39, 2000, 'That attitude is contageous!', '2019-06-02', '20:15:00'),
  (50, 3, 39, 2000, 'You mean so much to this team!', '2017-10-28', '20:00:00');
