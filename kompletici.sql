CREATE DATABASE Kompletici;

CREATE TABLE Login
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
		IF NOT EXISTS (SELECT 1 FROM Login WHERE userId = new_id) THEN
			EXIT;
		END IF;
		
		new_id := lpad(floor(random() * 1e10)::text, 10, '0');
	END LOOP;
	
	RETURN new_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION beforeInsertionIntoLogin()
RETURNS TRIGGER AS $$
BEGIN
	NEW.userId := generateUserId();
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER beforeInsertionIntoLogin
BEFORE INSERT ON Login
FOR EACH ROW
EXECUTE FUNCTION beforeInsertionIntoLogin();

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
BEFORE INSERT ON Login
FOR EACH ROW
EXECUTE FUNCTION checkForDirectors();

--INSERT INTO Login(userName, userPassword, userFunction, userEmail)
--VALUES ('Ivan Horvat', 'lozinka', 'Zaposlenik', 'johndoe@fer.hr');

--INSERT INTO Login(userName, userPassword, userFunction, userEmail)
--VALUES ('Niko Ivanovic', 'lozinka123', 'Direktor', 'nikoivanovic@fer.hr');

--INSERT INTO Login(userName, userPassword, userFunction, userEmail)
--VALUES ('Ivan Nikic', 'lozinke23', 'Direktor', 'ivannikic@fer.hr');