

/* Create Tables */

CREATE TABLE TEST_USER
(
	ID int NOT NULL,
	NAME varchar(255) NOT NULL,
	LOGINNAME varchar(255) NOT NULL UNIQUE,
	EMAIL varchar(255),
	PRIMARY KEY (ID)
);



/* Create Indexes */

CREATE INDEX IN_USER_LOGINNAME ON TEST_USER (LOGINNAME);



