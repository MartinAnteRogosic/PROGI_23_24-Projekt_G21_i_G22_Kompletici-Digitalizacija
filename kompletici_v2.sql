CREATE TABLE User
(
  password VARCHAR(30) NOT NULL,
  function CHAR(10) NOT NULL,
  name CHAR(30) NOT NULL,
  surname CHAR(30) NOT NULL,
  id VARCHAR(10) NOT NULL,
  email VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Photos
(
  url VARCHAR(255) NOT NULL,
  photoID VARCHAR(10) NOT NULL,
  imageName VARCHAR(100) NOT NULL,
  uploadTime TIMESTAMP NOT NULL,
  id VARCHAR(10) NOT NULL,
  PRIMARY KEY (photoID),
  FOREIGN KEY (id) REFERENCES User(id),
  UNIQUE (url)
);

CREATE TABLE Document
(
  documentID VARCHAR(10) NOT NULL,
  verifierID VARCHAR(10) NOT NULL,
  correct BOOLEAN NOT NULL,
  documentType VARCHAR(20) NOT NULL,
  signed BOOLEAN NOT NULL,
  verified BOOLEAN NOT NULL,
  superVerified BOOLEAN NOT NULL,
  id VARCHAR(10) NOT NULL,
  PRIMARY KEY (documentID),
  FOREIGN KEY (id) REFERENCES User(id),
  UNIQUE (verifierID)
);

CREATE TABLE ArchiveReciept
(
  arcRecID VARCHAR(10) NOT NULL,
  clientName VARCHAR(50) NOT NULL,
  totalPrice FLOAT NOT NULL,
  documentID VARCHAR(10) NOT NULL,
  PRIMARY KEY (arcRecID),
  FOREIGN KEY (documentID) REFERENCES Document(documentID)
);

CREATE TABLE ArchiveOffer
(
  arcOfferID VARCHAR(10) NOT NULL,
  totalPrice FLOAT NOT NULL,
  documentID VARCHAR(10) NOT NULL,
  PRIMARY KEY (arcOfferID),
  FOREIGN KEY (documentID) REFERENCES Document(documentID)
);

CREATE TABLE ArchiveInternalDoc
(
  archIntDocID VARCHAR(10) NOT NULL,
  text TEXT NOT NULL,
  documentID VARCHAR(10) NOT NULL,
  PRIMARY KEY (archIntDocID),
  FOREIGN KEY (documentID) REFERENCES Document(documentID)
);

CREATE TABLE Articles
(
  articleName VARCHAR(50) NOT NULL,
  price FLOAT NOT NULL,
  arcRecID VARCHAR(10) NOT NULL,
  arcOfferID VARCHAR(10) NOT NULL,
  FOREIGN KEY (arcRecID) REFERENCES ArchiveReciept(arcRecID),
  FOREIGN KEY (arcOfferID) REFERENCES ArchiveOffer(arcOfferID)
);