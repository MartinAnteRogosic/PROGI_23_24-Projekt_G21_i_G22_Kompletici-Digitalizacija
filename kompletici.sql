CREATE DATABASE Kompletici;

CREATE TABLE Login
(
  userId VARCHAR(30) NOT NULL,
  userPassword VARCHAR(30) NOT NULL,
  userFunction CHAR(10) NOT NULL,
  userEmail VARCHAR(50) NOT NULL,
  PRIMARY KEY (userId),
  UNIQUE (userEmail)
);