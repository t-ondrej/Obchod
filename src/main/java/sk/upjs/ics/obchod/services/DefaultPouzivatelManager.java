package sk.upjs.ics.obchod.services;

import java.time.LocalDate;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.KosikDao;
import sk.upjs.ics.obchod.dao.PouzivatelDao;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public enum DefaultPouzivatelManager implements PouzivatelManager {

    INSTANCETEST(true),
    INSTANCE(false);

    private final PouzivatelDao pouzivatelDao;
    private final KosikDao kosikDao;
    private Pouzivatel aktivnyPouzivatel;
    private BooleanProperty prihlaseny = new SimpleBooleanProperty(false);
    
    private DefaultPouzivatelManager(boolean preTestovaciuDatabazu){
        if(preTestovaciuDatabazu){
            pouzivatelDao = TestDaoFactory.INSTANCE.getMysqlPouzivatelDao();
            kosikDao = TestDaoFactory.INSTANCE.getPamatoviKosikDao();
        }else{
            pouzivatelDao = DaoFactory.INSTANCE.getMysqlPouzivatelDao();
            kosikDao = DaoFactory.INSTANCE.getPamatoviKosikDao(); 
        }
    }

    @Override
    public Pouzivatel getAktivnyPouzivatel() {
        return aktivnyPouzivatel;
    }

    @Override
    public BooleanProperty isPrihlaseny() {
        return prihlaseny;
    }

    @Override
    public void odhlasPouzivatela() {
        this.aktivnyPouzivatel = null;
        prihlaseny.setValue(!prihlaseny.getValue());
    }

    @Override
    public boolean prihlasPouzivatela(String prihlasovacieMeno, String heslo) {
        Pouzivatel pouzivatel = pouzivatelDao.dajPouzivatela(prihlasovacieMeno);

        if (pouzivatel != null && pouzivatel.checkPassword(heslo)) {
            Kosik kosik = pouzivatel.getKosik();
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

        pouzivatelDao.ulozPouzivatela(pouzivatel);
    }

    @Override
    public boolean jeVolneMeno(String meno) {
        Pouzivatel pouzivatel = pouzivatelDao.dajPouzivatela(meno);
        return pouzivatel == null;
    }
}
