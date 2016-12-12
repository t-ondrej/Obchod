package sk.upjs.ics.obchod.services;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.KosikDao;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.dao.TovarDao;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public class DefaultKosikManager implements KosikManager {

    private final KosikDao kosikDao;
    private final TovarDao tovarDao;
    private final PouzivatelManager pouzivatelManager;

    public DefaultKosikManager() {
        kosikDao = DaoFactory.INSTANCE.getPamatoviKosikDao();
        tovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();
        pouzivatelManager = DefaultPouzivatelManager.INSTANCE;
    }

    public DefaultKosikManager(boolean preTestovaciuDatabazu) {
        if (preTestovaciuDatabazu) {
            kosikDao = TestDaoFactory.INSTANCE.getPamatoviKosikDao();
            tovarDao = TestDaoFactory.INSTANCE.getMysqlTovarDao();
            pouzivatelManager = DefaultPouzivatelManager.INSTANCETEST;
        } else {
            kosikDao = DaoFactory.INSTANCE.getPamatoviKosikDao();
            tovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();
            pouzivatelManager = DefaultPouzivatelManager.INSTANCE;
        }
    }

    @Override
    public boolean pridajTovarDoKosika(Tovar tovar) {
        Kosik kosik = pouzivatelManager.getAktivnyPouzivatel().getKosik();
        int pocetTovaru = tovarDao.dajPocetTovaru(tovar);

        if (pocetTovaru <= 0) {
            return false;
        }

        kosikDao.dajTovarDoKosika(tovar, kosik);
        int pocetPred = tovarDao.dajPocetTovaru(tovar);
        tovarDao.nastavTovaruPocetKusov(tovar, pocetPred - 1);

        return true;
    }

    @Override
    public void odoberTovarZKosika(Tovar tovar) {
        Kosik kosik = pouzivatelManager.getAktivnyPouzivatel().getKosik();        
        kosikDao.odoberTovarZKosika(tovar, kosik);
        int pocetPred = tovarDao.dajPocetTovaru(tovar);
        tovarDao.nastavTovaruPocetKusov(tovar, pocetPred + 1);
    }

    @Override
    public List<Tovar> dajTovaryKosika() {
        Kosik kosik = pouzivatelManager.getAktivnyPouzivatel().getKosik();
        return kosikDao.dajTovaryKosika(kosik);
    }

    @Override
    public int dajPocetTovaruVKosiku(Tovar tovar) {
        Kosik kosik = pouzivatelManager.getAktivnyPouzivatel().getKosik();
        return kosikDao.pocetJednehoTovaruVKosiku(tovar, kosik);
    }

    @Override
    public IntegerProperty pocetTovaruVKosikuProperty(Tovar tovar) {
        Kosik kosik = pouzivatelManager.getAktivnyPouzivatel().getKosik();
        return kosik.getTovary().get(tovar);
    }

    @Override
    public ObservableList<Tovar> tovarKosikaObservableList() {
        Kosik kosik = pouzivatelManager.getAktivnyPouzivatel().getKosik();
        return FXCollections.observableArrayList(kosikDao.dajTovaryKosika(kosik));
    }

    @Override
    public void vyprazdniKosik() {
        Kosik kosik = pouzivatelManager.getAktivnyPouzivatel().getKosik();
        List<Tovar> tovary = kosikDao.dajTovaryKosika(kosik);
        for (Tovar t : tovary) {
            int pocet = kosikDao.pocetJednehoTovaruVKosiku(t, kosik);
            System.out.println(pocet);
            int pocetPred = tovarDao.dajPocetTovaru(t);
            tovarDao.nastavTovaruPocetKusov(t, pocetPred + pocet);
        }
        kosikDao.vyprazniKosik(kosik);
    }
}
