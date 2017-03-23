package sk.upjs.ics.obchod.managers;

import java.time.LocalDateTime;
import java.util.List;
import sk.upjs.ics.obchod.entity.Faktura;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Pouzivatel;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.dao.IKosikDao;
import sk.upjs.ics.obchod.dao.IFakturaDao;

public class FakturaManager implements IFakturaManager {

    private final IFakturaDao fakturaDao;
    private final IKosikDao kosikDao;

    public FakturaManager(IFakturaDao fakturaDao, IKosikDao kosikDao) {
        this.fakturaDao = fakturaDao;
        this.kosikDao = kosikDao;
    }

    @Override
    public Long vytvorFakturu(Pouzivatel pouzivatel) {
        Faktura faktura = new Faktura();
        faktura.setIdPouzivatel(pouzivatel.getId());
        faktura.setSuma(pouzivatel.getKosik().getCelkovaCena());
        faktura.setDatumNakupu(LocalDateTime.now());

        Long idFaktury = fakturaDao.pridajFakturu(faktura);
        faktura.setId(idFaktury);

        Kosik kosik = pouzivatel.getKosik();
        List<Tovar> tovar = kosikDao.dajTovarKosika(kosik);
        
        tovar.stream().forEach((t) -> {
            int pocet = kosikDao.pocetJednehoTovaruVKosiku(t, kosik);
            fakturaDao.pridajTovarFakture(t, faktura, pocet);
        });
        kosikDao.vyprazniKosik(kosik);

        return idFaktury;
    }

    @Override
    public List<Faktura> dajFaktury() {
        return fakturaDao.dajFaktury();
    }

    @Override
    public List<Faktura> dajFakturyZaObdobie(String obdobie) {
        switch (obdobie) {
            case "posledný deň":
                return fakturaDao.dajFakturyZaPoslednyDen();
            case "posledný týždeň": 
                return fakturaDao.dajFakturyZaPoslednyTyzden();
            case "posledný mesiac": 
                return fakturaDao.dajFakturyZaPoslednyMesiac();
            case "posledný rok": 
                return fakturaDao.dajFakturyZaPoslednyRok();
            default: 
                return null;
        }           
    }

    @Override
    public List<Tovar> dajTovarFaktury(Faktura faktura) {
        return fakturaDao.dajTovarFaktury(faktura);
    }
   
}
