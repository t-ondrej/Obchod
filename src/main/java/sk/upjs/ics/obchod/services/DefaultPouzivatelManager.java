package sk.upjs.ics.obchod.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.PouzivatelDao;
import sk.upjs.ics.obchod.entity.Pouzivatel;
import org.springframework.security.crypto.bcrypt.BCrypt;

public enum DefaultPouzivatelManager implements PouzivatelManager {

    INSTANCE;

    private final PouzivatelDao dao = DaoFactory.INSTANCE.getMysqlPouzivatelDao();
    private Pouzivatel aktivnyPouzivatel;

    public Pouzivatel getAktivnyPouzivatel() {
        return aktivnyPouzivatel;
    }

    public void odhlasPouzivatela() {
        this.aktivnyPouzivatel = null;
    }

    @Override
    public void prihlasPouzivatela(String prihlasovacieMeno, String heslo) {
        Pouzivatel pouzivatel = dao.dajPouzivatela(prihlasovacieMeno);

        if (pouzivatel.checkPassword(heslo)) {
            aktivnyPouzivatel = pouzivatel;
        }
    }

    @Override
    public void registrujPouzivatela(Pouzivatel pouzivatel) {
        dao.pridajPouzivatela(pouzivatel);
    }

    public boolean jeVolneMeno(String meno) {
        Pouzivatel pouzivatel = dao.dajPouzivatela(meno);
        return pouzivatel == null;
    }
}
