CREATE DATABASE IF NOT EXISTS obchod;
USE obchod;

CREATE TABLE IF NOT EXISTS Tovar (
	ID_tovar INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nazov VARCHAR(20),
	kategoria VARCHAR(20),
    podkategoria VARCHAR(20),
	cena INT NOT NULL,
	popis VARCHAR(90),
    obrazok_uri VARCHAR(90),
    PRIMARY KEY(ID_tovar)
);

CREATE TABLE IF NOT EXISTS Pouzivatel (
	ID_pouzivatel INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    prihlasovacie_meno VARCHAR(20),
    heslo VARCHAR(60),
	sol VARCHAR(64),
    email VARCHAR(90),
    posledne_prihlasenie DATETIME,
    PRIMARY KEY(ID_pouzivatel)
);

CREATE TABLE IF NOT EXISTS Kosik (
	ID_kosik INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(ID_kosik),
	FOREIGN KEY (ID_pouzivatel) REFERENCES Pouzivatel(ID_pouzivatel),
    FOREIGN KEY (ID_tovar) REFERENCES Tovar(ID_tovar)
);

CREATE TABLE IF NOT EXISTS Faktura (
	ID_faktura INT NOT NULL AUTO_INCREMENT,
    suma INT NOT NULL,
    datum_nakupu DATETIME,
    PRIMARY KEY(ID_faktura),
    FOREIGN KEY(ID_pouzivatel) REFERENCES Pouzivatel(ID_pouzivatel),
    FOREIGN KEY(ID_kosik) REFERENCES Kosik(ID_kosik),
    FOREIGN KEY(ID_tovar) REFERENCES Tovar(ID_tovar)
);