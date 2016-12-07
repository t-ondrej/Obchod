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

    public DefaultKosikManager() {
        kosikDao = DaoFactory.INSTANCE.getPamatoviKosikDao();
        tovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();
    }

    public DefaultKosikManager(boolean preTestovaciuDatabazu) {
        if (preTestovaciuDatabazu) {
            kosikDao = TestDaoFactory.INSTANCE.getPamatoviKosikDao();
            tovarDao = TestDaoFactory.INSTANCE.getMysqlTovarDao();
        } else {
            kosikDao = DaoFactory.INSTANCE.getPamatoviKosikDao();
            tovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();
        }
    }

    @Override
    public boolean pridajTovarDoKosika(Tovar tovar, Kosik kosik) {
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
    public void odoberTovarZKosika(Tovar tovar, Kosik kosik) {
        kosikDao.odoberTovarZKosika(tovar, kosik);
        int pocetPred = tovarDao.dajPocetTovaru(tovar);
        tovarDao.nastavTovaruPocetKusov(tovar, pocetPred + 1);
    }

    @Override
    public List<Tovar> dajTovaryKosika(Kosik kosik) {
        return kosikDao.dajTovaryKosika(kosik);
    }

    public int dajPocetTovaruVKosiku(Tovar tovar, Kosik kosik) {
        return kosikDao.pocetJednehoTovaruVKosiku(tovar, kosik);
    }

    public IntegerProperty pocetTovaruVKosikuProperty(Tovar tovar, Kosik kosik) {
        return kosik.getTovary().get(tovar);
    }

    public ObservableList<Tovar> tovarKosikaObservableList(Kosik kosik) {
        return FXCollections.observableArrayList(kosikDao.dajTovaryKosika(kosik));
    }

    @Override
    public void vyprazdniKosik(Kosik kosik) {
        List<Tovar> tovary = kosikDao.dajTovaryKosika(kosik);
        for (Tovar t : tovary) {
            int pocet = kosikDao.pocetJednehoTovaruVKosiku(t, kosik);
            int pocetPred = tovarDao.dajPocetTovaru(t);
            tovarDao.nastavTovaruPocetKusov(t, pocetPred + pocet);
        }
        kosikDao.vyprazniKosik(kosik);
    }
}
