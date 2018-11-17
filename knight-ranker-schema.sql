-- knight-ranker.sql builds a database
-- Author: Team Pilot
-- Professor: Keith Vander Linden
-- Class: 262, for Calvin College
-- Date: Fall, 2018


-----------------------------------------------------------------------------
-----------------------------------------------------------------------------
-----------------------------------------------------------------------------
--*****************************************************************************
--IMPORTANT NOTE: Just about any changes to anything will require a corresponding
--overhaul of the associated RESTful web service API code-base.
--(unless you're just inserting new tuples into tables)
--PLEASE inform the dev (Joseph Jinn) of any changes to the schema so I can make
--the relevant changes to our web service. 
--*****************************************************************************
-----------------------------------------------------------------------------
-----------------------------------------------------------------------------
-----------------------------------------------------------------------------

-- Drop previous versions of the tables if they they exist, in reverse order of foreign keys
DROP TABLE IF EXISTS Player CASCADE;
DROP TABLE IF EXISTS Sport CASCADE;
DROP TABLE IF EXISTS Match CASCADE;
DROP TABLE IF EXISTS SportRank CASCADE;


-----------------------------------------------------------------------------
-- ID: SERIAL is used to auto-increment the ID
-- emailAddress: email of the player
-- accountCreationDate: when the player's account was made
-- playerName: the name or gamer tag the person wants to be known by
-----------------------------------------------------------------------------
CREATE TABLE Player (
	ID SERIAL PRIMARY KEY,
	emailAddress varchar(254) NOT NULL,
	accountCreationDate timestamp,
	playerName varchar(64)
);

-----------------------------------------------------------------------------
-- ID: SERIAL is used to auto-increment the ID
-- name: name of the sport
-- type: the type of the sport (i.e. indoor, outdoor, eSport)
-----------------------------------------------------------------------------
CREATE TABLE Sport (
	ID SERIAL PRIMARY KEY,
	name varchar(50),
	type varchar(50)
);

-----------------------------------------------------------------------------
-- ID: SERIAL is used to auto-increment the ID
-- sportID: the ID of the sport that was played
-- playerOneID: ID of the first player
-- playerTwoID: ID of the second player
-- playerOneScore: score of the first player
-- playerTwoScore: score of the second player
-- winner: the player who won, stored because some games have weird ways of scoring
-- time: when the game was played
-- verified: if the match has been verified by both player
-----------------------------------------------------------------------------
CREATE TABLE Match (
	ID SERIAL PRIMARY KEY,
	sportID integer REFERENCES Sport(ID),
	playerOneID integer REFERENCES Player(ID),
	playerTwoID integer REFERENCES Player(ID),
	playerOneScore integer REFERENCES Player(ID),
	playerTwoScore integer REFERENCES Player(ID),
	winner integer REFERENCES Player(ID),
	time timestamp,
	verified boolean
);

-----------------------------------------------------------------------------
-- ID: SERIAL is used to auto-increment the ID
-- sportID: ID of the sport that the player is ranked in
-- playerID: the player that is ranked in the sport
-- eloRank: the rank the player is given based off the ELO ranking system
-----------------------------------------------------------------------------
CREATE TABLE SportRank (
	ID integer PRIMARY KEY,
	sportID integer REFERENCES Sport(ID),
	playerID integer REFERENCES Player(ID),
	eloRank integer
);

-----------------------------------------------------------------------------
-----------------------------------------------------------------------------
-----------------------------------------------------------------------------
-- Modified for testing purposes to avoid foreign constraints
-- Google Cloud PostgreSQL does not grant superuser (root) privileges
-- therefore unable to disable triggers (constraints)
-- (a quick and dirty way of testing CRUD operations on these tables)
-- NOTE: DO NOT REMOVE BECAUSE I NEED THESE FOR TESTING*******************************
-----------------------------------------------------------------------------
-----------------------------------------------------------------------------

-- CREATE TABLE Match (
-- 	ID SERIAL PRIMARY KEY,
-- 	sportID integer,
-- 	PlayerOneID integer,
-- 	PlayerTwoID integer,
-- 	PlayerOneScore integer,
-- 	PlayerTwoScore integer,
-- 	winner integer,
-- 	time timestamp,
-- 	verified boolean
-- );

-- CREATE TABLE SportRank (
-- 	ID integer PRIMARY KEY,
-- 	sportID integer,
-- 	PlayerID integer,
-- 	eloRank integer
-- );


-- Allow Players to select data from the tables
GRANT SELECT ON Player TO PUBLIC;
GRANT SELECT ON Sport TO PUBLIC;
GRANT SELECT ON Match TO PUBLIC;
GRANT SELECT ON SportRank TO PUBLIC;

-- When inserting, can specify the ID which is SERIAL by giving DEFAULT as the parameter
	-- or you can specify before VALUES that you're only specifying the emailAddress and accountCreationDate
	-- when inserting
INSERT INTO Player VALUES(DEFAULT, 'ceb45@students.calvin.edu', NOW(), 'Gwyn');
INSERT INTO Player (emailAddress, accountCreationDate, playerName) VALUES('igc2@students.calvin.edu', NOW(), 'The 1st Ian');
INSERT INTO Player VALUES(DEFAULT, 'jj47@students.calvin.edu', NOW(), 'sorcerer666');
INSERT INTO Player VALUES(DEFAULT, 'boo3@students.calvin.edu', NOW(), 'Top CIT Tech');
INSERT INTO Player VALUES(DEFAULT, 'mcw33@students.calvin.edu', NOW(), 'Melee god');
INSERT INTO Player VALUES(DEFAULT, 'isa3@students.calvin.edu', NOW(), 'mrsillydog');
INSERT INTO Player VALUES(DEFAULT, 'kvlinden@calvin.edu', NOW(), 'Keith');

INSERT INTO Sport VALUES(DEFAULT, 'Super Smash Bros Melee', 'E-Sport');
INSERT INTO Sport VALUES(DEFAULT, 'Street Fighter V', 'E-Sport');
INSERT INTO Sport VALUES(DEFAULT, 'Soccer', 'Outdoor');
INSERT INTO Sport VALUES(DEFAULT, 'Tennis', 'Outdoor');

INSERT INTO SportRank VALUES(1, 1, 5, 1);
INSERT INTO SportRank VALUES(2, 1, 1, 2);
INSERT INTO SportRank VALUES(3, 1, 2, 3);
INSERT INTO SportRank VALUES(4, 1, 7, 4);

INSERT INTO SportRank VALUES(5, 3, 6, 1);
INSERT INTO SportRank VALUES(6, 3, 4, 2);
INSERT INTO SportRank VALUES(7, 3, 3, 3);

-- Added to test the Match relation
INSERT INTO Match VALUES(1, 1, 1, 2, 1, 2, 1, NOW(), true);
INSERT INTO Match VALUES(2, 2, 3, 4, 3, 4, 3, NOW(), true);
INSERT INTO Match VALUES(3, 3, 5, 6, 5, 6, 5, NOW(), true);
INSERT INTO Match VALUES(4, 4, 1, 7, 1, 7, 7, NOW(), true);

-----------------------------------------------------------------------------
-----------------------------------------------------------------------------
-----------------------------------------------------------------------------
-- Requires superuser (root) privileges, impossible on Google Cloud Platform
-- PostgreSQL implementation according to various online sources (QQ)
-- Get these working somehow (testing pipe-dream)
-- NOTE: DO NOT REMOVE AS I MIGHT LOOK INTO THIS AT A LATER DATE******************
-----------------------------------------------------------------------------
-----------------------------------------------------------------------------
-----------------------------------------------------------------------------

-- ALTER TABLE Player DISABLE TRIGGER ALL;
-- ALTER TABLE Sport DISABLE TRIGGER ALL;
-- ALTER TABLE Match DISABLE TRIGGER ALL;
-- ALTER TABLE SportRank DISABLE TRIGGER ALL;

-- ALTER TABLE Player ENABLE TRIGGER ALL;
-- ALTER TABLE Sport ENABLE TRIGGER ALL;
-- ALTER TABLE Match ENABLE TRIGGER ALL;
-- ALTER TABLE SportRank ENABLE TRIGGER ALL;

-----------------------------------------------------------------------------
-----------------------------------------------------------------------------
-----------------------------------------------------------------------------
-----------------------------------------------------------------------------