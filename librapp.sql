
USE librapp;

CREATE TABLE profile(
userName varchar(20),
email varchar(50) NOT NULL,
fName varchar(20),
lname varchar(20),
primary key (email)
);
CREATE TABLE book(
title varchar(50),
authorfName varchar (20),
authorlName varchar (20),
isbn varchar (13) NOT NULL,
primary key(isbn)
);
