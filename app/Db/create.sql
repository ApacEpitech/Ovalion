DROP SCHEMA ovalion;
CREATE SCHEMA ovalion;
USE ovalion;

CREATE TABLE team(
	ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	name varchar(50),
	location varchar(100),
	nbSeats INTEGER
);

CREATE TABLE battle(
	ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	dateBattle DATE,
	teamHome INTEGER,
	teamVisitors INTEGER,
	FOREIGN KEY(teamHome) REFERENCES team(ID),
	FOREIGN KEY(teamVisitors) REFERENCES team(ID)
);

CREATE TABLE user(
	ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	name varchar(50),
	mail varchar(100),
	password varchar(255)
);

CREATE TABLE type(
	ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	shortType varchar(3),
	name varchar(30)
);

CREATE TABLE booking(
	ID INTEGER AUTO_INCREMENT PRIMARY KEY,
	personID INTEGER,
	battleID INTEGER,
	reservationType INTEGER,
	ticketType INTEGER,
	departureDate DATE,
	hotelAddress varchar(200),
	returnDate DATE DEFAULT NULL,
	FOREIGN KEY(personID) REFERENCES user(ID),
	FOREIGN KEY(battleID) REFERENCES battle(ID),
	FOREIGN KEY(ticketType) REFERENCES type(ID),
	FOREIGN KEY(reservationType) REFERENCES type(ID)
);

INSERT INTO user (name, mail, password) VALUE ('ben', 'ben@epi.eu', 'password');
INSERT INTO user (name, mail, password) VALUE ('alex', 'alex@epi.eu', 'password');

INSERT INTO type (shortType, name) VALUE ('bus', 'simple bus trip');
INSERT INTO type (shortType, name) VALUE ('bus', 'full bus trip');

INSERT INTO type (shortType, name) VALUE ('btl', 'VIP stands');
INSERT INTO type (shortType, name) VALUE ('btl', 'Front stands');
INSERT INTO type (shortType, name) VALUE ('btl', 'Turn stands');


INSERT INTO team (name, location, nbSeats) VALUE ('team1', 'Paris', 30000);
INSERT INTO team (name, location, nbSeats) VALUE ('team2', 'Lyon', 20000);
INSERT INTO team (name, location, nbSeats) VALUE ('team3', 'Marseille', 10000);


INSERT INTO battle (dateBattle, teamHome, teamVisitors) VALUE ('2018-11-04', 1 , 2);
INSERT INTO battle (dateBattle, teamHome, teamVisitors) VALUE ('2018-11-05', 2 , 3);
INSERT INTO battle (dateBattle, teamHome, teamVisitors) VALUE ('2018-11-06', 3 , 1);
