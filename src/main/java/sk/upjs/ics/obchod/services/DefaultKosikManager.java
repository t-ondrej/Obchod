package sk.upjs.ics.obchod.services;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
        int pocetTovaru = tovarDao.dajPocetTovaru(tovar.getId());
        if (pocetTovaru <= 0) {
            return false;
        }

        List<Tovar> tovaryKosika = kosikDao.dajTovaryKosika(kosik);
        boolean jeVKosiku = false;

        for (Tovar t : tovaryKosika) {
            if (t.getId().intValue() == (tovar.getId().intValue())) {
                jeVKosiku = true;
            }
        }

        if (!jeVKosiku) {
            kosikDao.dajTovarDoKosika(tovar, kosik);
            tovarDao.nastavTovaruPocetKusov(tovar.getId(), pocetTovaru - 1);

        } else {
            int pocetVKosikuPred = kosikDao.pocetJednehoTovaruVKosiku(tovar, kosik);
            kosikDao.nastavTovaruVKosikuPocetKusov(tovar, kosik, pocetVKosikuPred + 1);
            tovarDao.nastavTovaruPocetKusov(tovar.getId(), pocetTovaru - 1);
        }
        
       
            System.out.println(kosik.getTovary().size());
        
        return true;
    }

    @Override
    public void odoberTovarZKosika(Tovar tovar, Kosik kosik) {
        int pocetZTovaruVKosiku = kosikDao.pocetJednehoTovaruVKosiku(tovar, kosik);
        int pocetTovaru = tovarDao.dajPocetTovaru(tovar.getId());

        if (pocetZTovaruVKosiku <= 1) {
            kosikDao.odoberTovarZKosika(tovar, kosik);
            tovarDao.nastavTovaruPocetKusov(tovar.getId(), pocetTovaru + 1);
            kosik.getTovary().remove(tovar);

        } else {
            int pocetVKosikuPred = kosikDao.pocetJednehoTovaruVKosiku(tovar, kosik);
            kosikDao.nastavTovaruVKosikuPocetKusov(tovar, kosik, pocetVKosikuPred - 1);
            tovarDao.nastavTovaruPocetKusov(tovar.getId(), pocetTovaru + 1);
            kosik.getTovary().put(tovar, new SimpleIntegerProperty(pocetVKosikuPred - 1));
        }
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
