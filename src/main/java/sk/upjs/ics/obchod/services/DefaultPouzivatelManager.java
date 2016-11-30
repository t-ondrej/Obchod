package sk.upjs.ics.obchod.services;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.PouzivatelDao;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public enum DefaultPouzivatelManager implements PouzivatelManager {

    INSTANCE;

    private final PouzivatelDao dao = DaoFactory.INSTANCE.getMysqlPouzivatelDao();
    private Pouzivatel aktivnyPouzivatel;
    private BooleanProperty prihlaseny = new SimpleBooleanProperty(false);

    public Pouzivatel getAktivnyPouzivatel() {
        return aktivnyPouzivatel;
    }

    public BooleanProperty isPrihlaseny() {
        return prihlaseny;
    }
    
    public void odhlasPouzivatela() {
        this.aktivnyPouzivatel = null;
    }

    @Override
    public boolean prihlasPouzivatela(String prihlasovacieMeno, String heslo) {
        Pouzivatel pouzivatel = dao.dajPouzivatela(prihlasovacieMeno);

        if (pouzivatel != null && pouzivatel.checkPassword(heslo)) {
            aktivnyPouzivatel = pouzivatel;
            prihlaseny.set(true);
            return true;
        }
         
        return false;
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
