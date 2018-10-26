-- knight-ranker.sql builds a database
-- Author: Team Pilot
-- Professor: Keith Vander Linden
-- Class: 262, for Calvin College
-- Date: Fall, 2018

-- Drop previous versions of the tables if they they exist, in reverse order of foreign keys
DROP TABLE IF EXISTS User CASCADE;
DROP TABLE IF EXISTS Sport CASCADE;
DROP TABLE IF EXISTS Match CASCADE;
DROP TABLE IF EXISTS Follow CASCADE;

-- Create the schema
CREATE TABLE User (
	ID integer PRIMARY KEY,
	emailAddress varchar(254) NOT NULL,
	time timestamp
);

CREATE TABLE Sport (
	ID integer PRIMARY KEY,
	name varchar(50),
	type varchar(50)
);

CREATE TABLE Match (
	ID integer PRIMARY KEY,
	sportID integer REFERENCES Sport(ID),
	userOneID integer REFERENCES User(ID),
	userTwoID integer REFERENCES User(ID),
	userOneScore integer REFERENCES User(ID),
	userTwoScore integer REFERENCES User(ID),
	winner integer REFERENCES User(ID),
	time timestamp,
	verified boolean
);

CREATE TABLE Follow (
	ID integer PRIMARY KEY,
	sportID integer REFERENCES Sport(ID),
	userID integer REFERENCES User(ID),
	rank integer
);

-- Allow users to select data from the tables
GRANT SELECT ON User TO PUBLIC;
GRANT SELECT ON Sport TO PUBLIC;
GRANT SELECT ON Match TO PUBLIC;
GRANT SELECT ON Follow TO PUBLIC;
