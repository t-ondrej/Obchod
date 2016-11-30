package sk.upjs.ics.obchod.services;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.PouzivatelDao;
import sk.upjs.ics.obchod.entity.Kosik;
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
        prihlaseny.setValue(!prihlaseny.getValue());
    }

    @Override
    public boolean prihlasPouzivatela(String prihlasovacieMeno, String heslo) {
        Pouzivatel pouzivatel = dao.dajPouzivatela(prihlasovacieMeno);
        // Miesto, kde sa vytvara pre pouzivatela kosik
        Kosik kosik = new Kosik();
        pouzivatel.setKosik(kosik);

        if (pouzivatel != null && pouzivatel.checkPassword(heslo)) {
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
        kosik.setId(0L);
        pouzivatel.setKosik(kosik);
             
        dao.pridajPouzivatela(pouzivatel);
    }

    public boolean jeVolneMeno(String meno) {
        Pouzivatel pouzivatel = dao.dajPouzivatela(meno);
        return pouzivatel == null;
    }
}
