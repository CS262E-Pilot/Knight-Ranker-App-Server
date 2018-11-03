-- knight-ranker.sql builds a database
-- Author: Team Pilot
-- Professor: Keith Vander Linden
-- Class: 262, for Calvin College
-- Date: Fall, 2018

-- Drop previous versions of the tables if they they exist, in reverse order of foreign keys
DROP TABLE IF EXISTS Player CASCADE;
DROP TABLE IF EXISTS Sport CASCADE;
DROP TABLE IF EXISTS Match CASCADE;
DROP TABLE IF EXISTS Follow CASCADE;

-- Create the schema
-- SERIAL type creates a unique int value, increments the int value from the last one made
CREATE TABLE Player (
	ID SERIAL PRIMARY KEY,
	emailAddress varchar(254) NOT NULL,
	accountCreationDate timestamp
);

CREATE TABLE Sport (
	ID SERIAL PRIMARY KEY,
	name varchar(50),
	type varchar(50)
);

CREATE TABLE Match (
	ID SERIAL PRIMARY KEY,
	sportID integer REFERENCES Sport(ID),
	PlayerOneID integer REFERENCES Player(ID),
	PlayerTwoID integer REFERENCES Player(ID),
	PlayerOneScore integer REFERENCES Player(ID),
	PlayerTwoScore integer REFERENCES Player(ID),
	winner integer REFERENCES Player(ID),
	time timestamp,
	verified boolean
);

-- Modified to use a primary key for RESTful API CRUD Update method.
CREATE TABLE Follow (
	ID integer PRIMARY KEY,
	sportID integer REFERENCES Sport(ID),
	PlayerID integer REFERENCES Player(ID),
	rank integer
);

-- Allow Players to select data from the tables
GRANT SELECT ON Player TO PUBLIC;
GRANT SELECT ON Sport TO PUBLIC;
GRANT SELECT ON Match TO PUBLIC;
GRANT SELECT ON Follow TO PUBLIC;

-- When inserting, can specify the ID which is SERIAL by giving DEFAULT as the parameter
	-- or you can specify before VALUES that you're only specifying the emailAddress and accountCreationDate
	-- when inserting
INSERT INTO Player VALUES(DEFAULT, 'ceb45@students.calvin.edu', NOW());
INSERT INTO Player (emailAddress, accountCreationDate) VALUES('igc2@students.calvin.edu', NOW());
INSERT INTO Player VALUES(DEFAULT, 'jj47@students.calvin.edu', NOW());
INSERT INTO Player VALUES(DEFAULT, 'boo3@students.calvin.edu', NOW());
INSERT INTO Player VALUES(DEFAULT, 'mcw33@students.calvin.edu', NOW());
INSERT INTO Player VALUES(DEFAULT, 'isa3@students.calvin.edu', NOW());
INSERT INTO Player VALUES(DEFAULT, 'kvlinden@calvin.edu', NOW());

INSERT INTO Sport VALUES(DEFAULT, 'Super Smash Bros Melee', 'E-Sport');
INSERT INTO Sport VALUES(DEFAULT, 'Street Fighter V', 'E-Sport');
INSERT INTO Sport VALUES(DEFAULT, 'Soccer', 'Outdoor');
INSERT INTO Sport VALUES(DEFAULT, 'Tennis', 'Outdoor');

INSERT INTO Follow VALUES(1, 1, 5, 1);
INSERT INTO Follow VALUES(2, 1, 1, 2);
INSERT INTO Follow VALUES(3, 1, 2, 3);
INSERT INTO Follow VALUES(4, 1, 7, 4);

INSERT INTO Follow VALUES(5, 3, 6, 1);
INSERT INTO Follow VALUES(6, 3, 4, 2);
INSERT INTO Follow VALUES(7, 3, 3, 3);

-- Added to test the Match relation
INSERT INTO Match VALUES(1, 1, 1, 2, 1, 2, 1, NOW(), true);
INSERT INTO Match VALUES(2, 2, 3, 4, 3, 4, 3, NOW(), true);
INSERT INTO Match VALUES(3, 3, 5, 6, 5, 6, 5, NOW(), true);
INSERT INTO Match VALUES(4, 4, 1, 7, 1, 7, 7, NOW(), true);
