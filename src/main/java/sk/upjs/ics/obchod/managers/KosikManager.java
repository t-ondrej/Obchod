package sk.upjs.ics.obchod.managers;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.dao.ITovarDao;
import sk.upjs.ics.obchod.dao.IKosikDao;

public class KosikManager implements IKosikManager {

    private final IKosikDao kosikDao;
    private final ITovarDao tovarDao;
    private final IPouzivatelManager pouzivatelManager;

    public KosikManager(IKosikDao kosikDao, ITovarDao tovarDao, IPouzivatelManager pouzivatelManager) {
        this.kosikDao = kosikDao;
        this.tovarDao = tovarDao;
        this.pouzivatelManager = pouzivatelManager;
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
    public List<Tovar> dajTovarKosika() {
        Kosik kosik = pouzivatelManager.getAktivnyPouzivatel().getKosik();
        return kosikDao.dajTovarKosika(kosik);
    }

    @Override
    public int dajPocetTovaruVKosiku(Tovar tovar) {
        Kosik kosik = pouzivatelManager.getAktivnyPouzivatel().getKosik();
        return kosikDao.pocetJednehoTovaruVKosiku(tovar, kosik);
    }

    @Override
    public IntegerProperty pocetTovaruVKosikuProperty(Tovar tovar) {
        Kosik kosik = pouzivatelManager.getAktivnyPouzivatel().getKosik();
        return kosik.getTovar().get(tovar);
    }

    @Override
    public ObservableList<Tovar> tovarKosikaObservableList() {
        Kosik kosik = pouzivatelManager.getAktivnyPouzivatel().getKosik();
        return FXCollections.observableArrayList(kosikDao.dajTovarKosika(kosik));
    }

    @Override
    public void vyprazdniKosik() {
        Kosik kosik = pouzivatelManager.getAktivnyPouzivatel().getKosik();
        List<Tovar> tovar = kosikDao.dajTovarKosika(kosik);
        for (Tovar t : tovar) {
            int pocet = kosikDao.pocetJednehoTovaruVKosiku(t, kosik);
            System.out.println(pocet);
            int pocetPred = tovarDao.dajPocetTovaru(t);
            tovarDao.nastavTovaruPocetKusov(t, pocetPred + pocet);
        }
        kosikDao.vyprazniKosik(kosik);
    }
}
