package sk.upjs.ics.obchod.services;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.KosikDao;
import sk.upjs.ics.obchod.dao.PouzivatelDao;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public enum DefaultPouzivatelManager implements PouzivatelManager {

    INSTANCE;

    private final PouzivatelDao pouzivatelDao = DaoFactory.INSTANCE.getMysqlPouzivatelDao();
    private final KosikDao kosikDao = DaoFactory.INSTANCE.getPamatoviKosikDao();
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
        prihlaseny.setValue(!prihlaseny.getValue());
    }

    @Override
    public boolean prihlasPouzivatela(String prihlasovacieMeno, String heslo) {
        Pouzivatel pouzivatel = pouzivatelDao.dajPouzivatela(prihlasovacieMeno);

        if (pouzivatel != null && pouzivatel.checkPassword(heslo)) {
            Kosik kosik = new Kosik();
            pouzivatel.setKosik(kosik);
            aktivnyPouzivatel = pouzivatel;
            prihlaseny.set(true);
            return true;
        }

        return false;
    }

    @Override
    public void registrujPouzivatela(String prihlasovacieMeno, String heslo, String email) {
        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setPrihlasovacieMeno(prihlasovacieMeno);
        pouzivatel.setPassword(heslo);
        pouzivatel.setEmail(email);
        pouzivatel.setPoslednePrihlasenie(LocalDate.now());
        
        Kosik kosik = new Kosik();        
        pouzivatel.setKosik(kosik);

        pouzivatelDao.pridajPouzivatela(pouzivatel);
    }

    public boolean jeVolneMeno(String meno) {
        Pouzivatel pouzivatel = pouzivatelDao.dajPouzivatela(meno);
        return pouzivatel == null;
    }
}
