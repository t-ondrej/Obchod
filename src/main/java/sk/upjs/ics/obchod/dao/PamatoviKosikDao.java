package sk.upjs.ics.obchod.dao;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public class PamatoviKosikDao implements KosikDao {

    @Override
    public void dajTovarDoKosika(Tovar tovar, Kosik kosik) {
        for (Tovar t : kosik.getTovary().keySet()) {
            if (t.getId().intValue() == tovar.getId().intValue()) {
                int pocetTovaru = kosik.getTovary().get(t).getValue();
                kosik.getTovary().put(t, new SimpleIntegerProperty(pocetTovaru + 1));
                return;
            }
        }

        kosik.getTovary().put(tovar, new SimpleIntegerProperty(1));
    }

    @Override
    public int pocetJednehoTovaruVKosiku(Tovar tovar, Kosik kosik) {
        return kosik.getTovary().get(tovar).getValue();
    }

    @Override
    public void nastavTovaruVKosikuPocetKusov(Tovar tovar, Kosik kosik, int pocet_kusov) {
        for (Tovar t : kosik.getTovary().keySet()) {
            if (t.getId().intValue() == tovar.getId().intValue()) {
                kosik.getTovary().put(t, new SimpleIntegerProperty(pocet_kusov));
            }
        }
    }

    @Override
    public void odoberTovarZKosika(Tovar tovar, Kosik kosik) {
        for (Tovar t : kosik.getTovary().keySet()) {
            if (t.getId().intValue() == tovar.getId().intValue()) {

                if (kosik.getTovary().get(t).getValue() <= 1) {
                    kosik.getTovary().remove(t);
                    
                } else {
                    int pocetTovaru = kosik.getTovary().get(t).getValue();
                    kosik.getTovary().put(t, new SimpleIntegerProperty(pocetTovaru - 1));
                   
                }
                return;

            }
        }
    }

    @Override
    public List<Tovar> dajTovaryKosika(Kosik kosik) {
        List<Tovar> t = new ArrayList<>(kosik.getTovary().keySet());
        return t;
    }

}
