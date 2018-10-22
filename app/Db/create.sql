CREATE TABLE team(
	ID INTEGER AUTOINCREMENT PRIMARY KEY,
	name varchar(50),
	location varchar(100),
	nbSeats INTEGER
);

CREATE TABLE match(
	ID INTEGER AUTOINCREMENT PRIMARY KEY,
	dateMatch DATE,
	teamHome INTEGER,
	teamVisitors INTEGER,
	FOREIGN KEY(teamHome) REFERENCES team(ID),
	FOREIGN KEY(teamVisitors) REFERENCES team(ID)
);

CREATE TABLE user(
	ID INTEGER AUTOINCREMENT PRIMARY KEY,
	name varchar(50),
	mail varchar(100),
	password varchar(255)
);

CREATE TABLE type(
	ID INTEGER AUTOINCREMENT PRIMARY KEY,
	shortType varchar(3),
	name varchar(30)
);

CREATE TABLE booking(
	ID INTEGER AUTOINCREMENT PRIMARY KEY,
	personID INTEGER,
	matchID INTEGER,
	reservationType INTEGER,
	ticketType INTEGER,
	departureDate DATE,
	hotelAddress varchar(200),
	returnDate DATE DEFAULT NULL,
	FOREIGN KEY(personID) REFERENCES user(ID),
	FOREIGN KEY(matchID) REFERENCES match(ID),
	FOREIGN KEY(ticketType) REFERENCES type(ID),
	FOREIGN KEY(reservationType) REFERENCES type(ID)
);
