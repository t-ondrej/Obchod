package sk.upjs.ics.obchod.dao;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public class PamatoviKosikDao implements KosikDao {

    @Override
    public void dajTovarDoKosika(Tovar tovar, Kosik kosik) {
        kosik.getTovary().put(tovar, new SimpleIntegerProperty(1));
    }

    @Override
    public int pocetJednehoTovaruVKosiku(Tovar tovar, Kosik kosik) {
        if (kosik.getTovary().get(tovar) != null)
            return kosik.getTovary().get(tovar).getValue();
        else
            return 0;
    }

    @Override
    public void nastavTovaruVKosikuPocetKusov(Tovar tovar, Kosik kosik, int pocet_kusov) {
        kosik.getTovary().put(tovar, new SimpleIntegerProperty(pocet_kusov));
    }

    @Override
    public void odoberTovarZKosika(Tovar tovar, Kosik kosik) {
        kosik.getTovary().remove(tovar);
    }

    @Override
    public List<Tovar> dajTovaryKosika(Kosik kosik) {
        List<Tovar> t = new ArrayList<>(kosik.getTovary().keySet());
        return t;
    }

}
