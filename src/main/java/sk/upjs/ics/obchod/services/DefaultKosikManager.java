package sk.upjs.ics.obchod.services;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.KosikDao;
import sk.upjs.ics.obchod.dao.TovarDao;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public class DefaultKosikManager implements KosikManager {

    private final KosikDao kosikDao = DaoFactory.INSTANCE.getPamatoviKosikDao();
    private final TovarDao tovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();

    @Override
    public boolean pridajTovarDoKosika(Tovar tovar, Kosik kosik) {
        int pocetTovaru = tovarDao.dajPocetTovaru(tovar);

        if (pocetTovaru <= 0) {
            return false;
        }

        kosikDao.dajTovarDoKosika(tovar, kosik);
        tovarDao.nastavTovaruPocetKusov(tovar, pocetTovaru - 1);

        return true;
    }

    @Override
    public void odoberTovarZKosika(Tovar tovar, Kosik kosik) {
        int pocetTovaru = tovarDao.dajPocetTovaru(tovar);
        kosikDao.odoberTovarZKosika(tovar, kosik);

        tovarDao.nastavTovaruPocetKusov(tovar, pocetTovaru + 1);
    }

    @Override
    public List<Tovar> dajTovarKosika(Kosik kosik) {
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

}
