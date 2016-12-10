package sk.upjs.ics.obchod.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public class PamatoviKosikDao implements KosikDao {

    @Override
    public void dajTovarDoKosika(Tovar tovar, Kosik kosik) {
        Map<Tovar, IntegerProperty> tovary = kosik.getTovary();
        
        if (!tovary.containsKey(tovar)) {
            tovary.put(tovar, new SimpleIntegerProperty(1));

        } else {
            int pocetTovaru = tovary.get(tovar).getValue();
            tovary.put(tovar, new SimpleIntegerProperty(pocetTovaru + 1));
        }
        int celkovaCenaPred = kosik.getCelkovaCena();
        kosik.setCelkovaCena(celkovaCenaPred + tovar.getCena());
    }

    @Override
    public int pocetJednehoTovaruVKosiku(Tovar tovar, Kosik kosik) {
        return kosik.getTovary().get(tovar).getValue();
    }

    @Override
    public void nastavTovaruVKosikuPocetKusov(Tovar tovar, Kosik kosik, int pocet_kusov) {
        kosik.getTovary().put(tovar, new SimpleIntegerProperty(pocet_kusov));

    }

    @Override
    public void odoberTovarZKosika(Tovar tovar, Kosik kosik) {
        Map<Tovar, IntegerProperty> tovary = kosik.getTovary();

        if (tovary.get(tovar).getValue() <= 1) {
            tovary.remove(tovar);

        } else {
            int pocetTovaru = tovary.get(tovar).getValue();
            tovary.put(tovar, new SimpleIntegerProperty(pocetTovaru - 1));
        }
        int celkovaCenaPred = kosik.getCelkovaCena();
        kosik.setCelkovaCena(celkovaCenaPred - tovar.getCena());
    }

    @Override
    public List<Tovar> dajTovaryKosika(Kosik kosik) {
        List<Tovar> t = new ArrayList<>(kosik.getTovary().keySet());
        return t;
    }

    @Override
    public void vyprazniKosik(Kosik kosik) {
        kosik.getTovary().clear();
    }

}
