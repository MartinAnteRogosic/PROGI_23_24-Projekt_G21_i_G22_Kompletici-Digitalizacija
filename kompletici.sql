CREATE DATABASE Kompletici;

-- U slučaju da ste već radili sa prošlom bazom samo otkomentirajte ovaj dio ispod
/*
DROP FUNCTION generateUserId() CASCADE;

DROP TRIGGER beforeInsertionIntoLogin ON Login;

DROP FUNCTION beforeInsertionIntoLogin() CASCADE;

DROP TRIGGER checkForDirectors ON Login;

DROP FUNCTION checkForDirectors() CASCADE;

DROP TABLE numOfDirectors;

DROP TABLE Login; */



CREATE TABLE Register
(
  userId VARCHAR(10) NOT NULL,
  userName VARCHAR(30) NOT NULL,
  userPassword VARCHAR(30) NOT NULL,
  userFunction CHAR(10) NOT NULL,
  userEmail VARCHAR(50) NOT NULL,
  PRIMARY KEY (userId),
  UNIQUE (userEmail),
  CHECK(userFunction IN ('Zaposlenik', 'Revizor', 'Racunovoda', 'Direktor'))
);

CREATE OR REPLACE FUNCTION generateUserId() RETURNS VARCHAR(10) AS $$
DECLARE
	new_id VARCHAR(10);
BEGIN
	new_id := lpad(floor(random() * 1e10)::text, 10, '0');
	
	LOOP
		IF NOT EXISTS (SELECT 1 FROM Register WHERE userId = new_id) THEN
			EXIT;
		END IF;
		
		new_id := lpad(floor(random() * 1e10)::text, 10, '0');
	END LOOP;
	
	RETURN new_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION beforeInsertionIntoRegister()
RETURNS TRIGGER AS $$
BEGIN
	NEW.userId := generateUserId();
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER beforeInsertionIntoRegister
BEFORE INSERT ON Register
FOR EACH ROW
EXECUTE FUNCTION beforeInsertionIntoRegister();

CREATE TABLE numOfDirectors (
	numOfDirectors INT
);

INSERT INTO numOfDirectors(numOfDirectors) VALUES (0);

CREATE OR REPLACE FUNCTION checkForDirectors()
RETURNS TRIGGER AS $$
BEGIN
	IF NEW.userFunction = 'Direktor' THEN
		IF (SELECT numOfDirectors FROM numOfDirectors) >= 1 THEN
			RAISE EXCEPTION 'U bazi podataka se moze nalaziti samo 1 direktor!';
		END IF;
		
		UPDATE numOfDirectors SET numOfDirectors = numOfDirectors + 1;
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER checkForDirectors
BEFORE INSERT ON Register
FOR EACH ROW
EXECUTE FUNCTION checkForDirectors();

CREATE OR REPLACE FUNCTION firstUser()
RETURNS TRIGGER AS $$
BEGIN
	IF TG_OP = 'INSERT' AND (SELECT COUNT(*) FROM Register) = 0 AND NEW.userFunction != 'Direktor' THEN
		RAISE EXCEPTION 'Prvi korisnik baze podataka mora biti direktor!';
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER firstUser
BEFORE INSERT ON Register
FOR EACH ROW
EXECUTE FUNCTION firstUser();

--INSERT INTO Register(userName, userPassword, userFunction, userEmail)
--VALUES ('Ivan Horvat', 'lozinka', 'Zaposlenik', 'johndoe@fer.hr');

/*DELETE FROM Register
WHERE
  userName = 'Ivan Horvat'
  AND userPassword = 'lozinka'
  AND userFunction = 'Zaposlenik'
  AND userEmail = 'johndoe@fer.hr';*/
 
--DELETE FROM Register 
--WHERE userId = '6940549637'

-- dodat da ako se briše direktor da se numOfDirectors stavi na 0
--UPDATE numOfDirectors SET numOfDirectors = 0;

--INSERT INTO Register(userName, userPassword, userFunction, userEmail)
--VALUES ('Niko Ivanovic', 'lozinka123', 'Direktor', 'nikoivanovic@fer.hr');

--INSERT INTO Register(userName, userPassword, userFunction, userEmail)
--VALUES ('Ivan Nikic', 'lozinke23', 'Zaposlenik', 'ivannikic@fer.hr');

CREATE TABLE Login
(
  userId VARCHAR(10) NOT NULL,
  userName VARCHAR(30) NOT NULL,
  userEmail VARCHAR(50) NOT NULL,
  userFunction CHAR(10) NOT NULL,
  logInTime TIMESTAMP NOT NULL,
  PRIMARY KEY (userId),
  UNIQUE (userEmail)
);

CREATE OR REPLACE FUNCTION checkIfUserInRegister()
RETURNS TRIGGER AS $$
BEGIN
	IF TG_OP = 'INSERT' AND NEW.userId NOT IN (SELECT userId FROM Register) THEN
		RAISE EXCEPTION 'Korisnika nema u bazi podataka!';
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER checkIfUserInRegister
BEFORE INSERT ON Login
FOR EACH ROW
EXECUTE FUNCTION checkIfUserInRegister();

--INSERT INTO Login(userId, userName, userEmail, userFunction, logInTime)
--VALUES ('3245257327', 'Pero Peric', 'peroperic@fer.hr', 'Zaposlenik', CURRENT_TIMESTAMP)
-- baca error jer korisnik ne postoji u bazi

--INSERT INTO Login(userId, userName, userEmail, userFunction, logInTime)
--VALUES ('9315310946', 'Ivan Nikic', 'ivannikic@fer.hr','Zaposlenik', CURRENT_TIMESTAMP)
-- normalno ubacuje korisnika u login jer se već nalazi u bazi