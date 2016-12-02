CREATE DATABASE IF NOT EXISTS obchod_test;
USE obchod_test;

CREATE USER 'obchod_test'@'localhost' IDENTIFIED BY 'obchod1_test';
GRANT ALL ON obchod_test.* TO 'obchod'@'localhost';

CREATE TABLE IF NOT EXISTS Kategoria (
	id INT NOT NULL AUTO_INCREMENT,
    nazov VARCHAR(60),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Znacka (
	id INT NOT NULL AUTO_INCREMENT,
    nazov VARCHAR(60),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Kosik (
	id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Tovar (
	id INT NOT NULL AUTO_INCREMENT,
    id_kategoria INT NOT NULL,
    id_znacka INT NOT NULL,
    nazov VARCHAR(35),	
	cena INT NOT NULL,
	popis VARCHAR(90),
    obrazok_url VARCHAR(90),
    PRIMARY KEY (id),
    FOREIGN KEY (id_kategoria) REFERENCES Kategoria(id),
    FOREIGN KEY (id_znacka) REFERENCES Znacka(id)
);

CREATE TABLE IF NOT EXISTS Pouzivatel (
	id INT NOT NULL AUTO_INCREMENT,
    id_kosik INT NOT NULL,
    prihlasovacie_meno VARCHAR(20),
    heslo VARCHAR(60),
	sol VARCHAR(64),
    email VARCHAR(90),
    posledne_prihlasenie DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (id_kosik) REFERENCES Kosik(id)
);

CREATE TABLE IF NOT EXISTS Tovar_Kosika (
	id_kosik INT NOT NULL,
    id_tovar INT,
	FOREIGN KEY (id_kosik) REFERENCES Kosik(id),
    FOREIGN KEY (id_tovar) REFERENCES Tovar(id)
);

CREATE TABLE IF NOT EXISTS Faktura (
	id INT NOT NULL AUTO_INCREMENT,
	id_pouzivatel INT NOT NULL,
    suma INT NOT NULL,
    datum_nakupu DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (id_pouzivatel) REFERENCES Pouzivatel(id)
);

CREATE TABLE IF NOT EXISTS Tovar_Faktury (
	id_faktura INT NOT NULL,
    id_tovar INT NOT NULL
);

UPDATE Tovar SET id = 1, id_kategoria = 1, id_znacka = 1, nazov = 'Tovar1Test', cena = 99, popis = 'Popis1Test', obrazok_url='@../img/2.JPG' WHERE id = 2;
UPDATE Znacka SET id = 1, nazov = 'Znacka1Test' WHERE id = 2;

ALTER TABLE tovar ADD pocet_kusov INT;
UPDATE Tovar SET pocet_kusov = 1 WHERE id >= 0;

INSERT INTO Kategoria(nazov) VALUES('Nezaradene');
UPDATE Kategoria SET id = 0 WHERE id = 1;

INSERT INTO Znacka(nazov) VALUES('Nezaradene');
UPDATE Znacka SET id = 0 WHERE id = 1;

INSERT INTO Kosik VALUES();
UPDATE Kosik SET id = 0 WHERE id = 1;

INSERT INTO Pouzivatel(id_kosik, prihlasovacie_meno, heslo, sol, email, posledne_prihlasenie) 
VALUES(0, 'testPouzivatel', 'testHeslo1', 'testSol', 'pouzivatel@test.com', '2016.11.15');
UPDATE Pouzivatel SET id = 0 WHERE id = 1;

INSERT INTO Tovar(id_kategoria, id_znacka, nazov, cena, popis, obrazok_url) 
VALUES(0, 0, 'testNazov', 20, 'testPopis', '@../img/1.JPG');
UPDATE Tovar SET id = 0 WHERE id = 1;

INSERT INTO Tovar_Kosika VALUES(0, 0);

INSERT INTO Faktura(id_pouzivatel, suma, datum_nakupu)
VALUES(0, 0, '2016.9.15');
UPDATE Faktura SET id = 0 WHERE id = 1;

INSERT INTO Tovar_Faktury VALUES(0, 0);
