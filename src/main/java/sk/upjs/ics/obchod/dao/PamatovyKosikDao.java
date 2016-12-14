package sk.upjs.ics.obchod.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public class PamatovyKosikDao implements KosikDao {

    @Override
    public void dajTovarDoKosika(Tovar kusTovaru, Kosik kosik) {
        Map<Tovar, IntegerProperty> tovar = kosik.getTovar();
        
        if (!tovar.containsKey(kusTovaru)) {
            tovar.put(kusTovaru, new SimpleIntegerProperty(1));

        } else {
            int pocetTovaru = tovar.get(kusTovaru).getValue();
            tovar.put(kusTovaru, new SimpleIntegerProperty(pocetTovaru + 1));
        }
        int celkovaCenaPred = kosik.getCelkovaCena();
        kosik.setCelkovaCena(celkovaCenaPred + kusTovaru.getCena());
    }

    @Override
    public int pocetJednehoTovaruVKosiku(Tovar tovar, Kosik kosik) {
        return kosik.getTovar().get(tovar).getValue();
    }

    @Override
    public void nastavTovaruVKosikuPocetKusov(Tovar tovar, Kosik kosik, int pocet_kusov) {
        kosik.getTovar().put(tovar, new SimpleIntegerProperty(pocet_kusov));

    }

    @Override
    public void odoberTovarZKosika(Tovar kusTovaru, Kosik kosik) {
        Map<Tovar, IntegerProperty> tovar = kosik.getTovar();

        if (tovar.get(kusTovaru).getValue() <= 1) {
            tovar.remove(kusTovaru);

        } else {
            int pocetTovaru = tovar.get(kusTovaru).getValue();
            tovar.put(kusTovaru, new SimpleIntegerProperty(pocetTovaru - 1));
        }
        int celkovaCenaPred = kosik.getCelkovaCena();
        kosik.setCelkovaCena(celkovaCenaPred - kusTovaru.getCena());
    }

    @Override
    public List<Tovar> dajTovarKosika(Kosik kosik) {
        List<Tovar> t = new ArrayList<>(kosik.getTovar().keySet());
        return t;
    }

    @Override
    public void vyprazniKosik(Kosik kosik) {
        kosik.getTovar().clear();
    }

}
