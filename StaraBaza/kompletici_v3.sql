CREATE TABLE User
(
  id VARCHAR(10) NOT NULL,
  name CHAR(50) NOT NULL,
  surname CHAR(50) NOT NULL,
  function CHAR(10) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(30) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Document
(
  documentID VARCHAR(10) NOT NULL,
  verifierId VARCHAR(10) NOT NULL,
  documentType VARCHAR(20) NOT NULL,
  correct BOOLEAN NOT NULL,
  signed BOOLEAN NOT NULL,
  verified BOOLEAN NOT NULL,
  toBeSigned BOOLEAN NOT NULL,
  id VARCHAR(10) NOT NULL,
  PRIMARY KEY (documentID),
  FOREIGN KEY (id) REFERENCES User(id),
  UNIQUE (verifierId)
);

CREATE TABLE Archive
(
  archiveID VARCHAR(10) NOT NULL,
  documentID VARCHAR(10),
  PRIMARY KEY (archiveID),
  FOREIGN KEY (documentID) REFERENCES Document(documentID)
);

CREATE TABLE Statistics
(
  timestampLogin TIMESTAMP NOT NULL,
  timestampLogout TIMESTAMP NOT NULL,
  id VARCHAR(10) NOT NULL,
  FOREIGN KEY (id) REFERENCES User(id)
);

CREATE TABLE Photos
(
  photoID VARCHAR(10) NOT NULL,
  url VARCHAR(255) NOT NULL,
  imageName VARCHAR(100) NOT NULL,
  uploadTime TIMESTAMP NOT NULL,
  id VARCHAR(10) NOT NULL,
  documentID VARCHAR(10),
  PRIMARY KEY (photoID),
  FOREIGN KEY (id) REFERENCES User(id),
  FOREIGN KEY (documentID) REFERENCES Document(documentID),
  UNIQUE (url)
);