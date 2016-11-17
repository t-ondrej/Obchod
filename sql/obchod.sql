CREATE DATABASE IF NOT EXISTS obchod;
USE obchod;

CREATE USER 'obchod'@'localhost' IDENTIFIED BY 'obchod1';
GRANT ALL ON obchod.* TO 'obchod'@'localhost';

CREATE TABLE IF NOT EXISTS Tovar (
	id INT NOT NULL AUTO_INCREMENT,
    id_kategoria INT NOT NULL,
    id_podkategoria INT NOT NULL,
    nazov VARCHAR(35),	
    znacka VARCHAR(35),
	cena INT NOT NULL,
	popis VARCHAR(90),
    obrazok_url VARCHAR(90),
    PRIMARY KEY (id),
    FOREIGN KEY (id_kategoria) REFERENCES Kategoria(id),
    FOREIGN KEY (id_podkategoria) REFERENCES Podkategoria(id)
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

CREATE TABLE IF NOT EXISTS Kosik (
	id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
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
	id_tovar INT NOT NULL,
    suma INT NOT NULL,
    datum_nakupu DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (id_pouzivatel) REFERENCES Pouzivatel(id),
    FOREIGN KEY (id_tovar) REFERENCES Tovar(id)
);

CREATE TABLE IF NOT EXISTS Kategoria (
	id INT NOT NULL AUTO_INCREMENT,
    nazov VARCHAR(60),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Podkategoria (
	id INT NOT NULL AUTO_INCREMENT,
    nazov VARCHAR(60),
    PRIMARY KEY (id)
);

INSERT INTO Kategoria VALUES(0, 'Nezaradene');
UPDATE Kategoria SET id = 0 WHERE id = 1;
INSERT INTO Podkategoria VALUES(0, 'Nezaradene');
UPDATE Podkategoria SET id = 0 WHERE id = 1;

INSERT INTO Kosik VALUES(0);
UPDATE Kosik SET id = 0 WHERE id = 1;
INSERT INTO Pouzivatel VALUES(0, 0, 'testPouzivatel', 'testHeslo1', 'testSol', 'pouzivatel@test.com', '2016.11.15');
UPDATE Pouzivatel SET id = 0 WHERE id = 1;
INSERT INTO Tovar VALUES(0, 0, 0, 'testNazov', 'testZnacka', 20, 'testPopis', '@../img/1.JPG');
UPDATE Tovar SET id = 0 WHERE id = 1;
INSERT INTO Tovar_Kosika VALUES(0, 0);