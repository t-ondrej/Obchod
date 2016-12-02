package sk.upjs.ics.obchod.dao;

import java.util.ArrayList;
import java.util.List;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public class PamatoviKosikDao implements KosikDao{

    @Override
    public void dajTovarDoKosika(Tovar tovar, Kosik kosik) {
        kosik.getTovary().put(tovar, 1);
    }

    @Override
    public int pocetJednehoTovaruVKosiku(Tovar tovar, Kosik kosik) {
        return kosik.getTovary().get(tovar);
    }

    @Override
    public void nastavTovaruVKosikuPocetKusov(Tovar tovar, Kosik kosik, int pocet_kusov) {
        kosik.getTovary().put(tovar, pocet_kusov);
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
