package sk.upjs.ics.obchod.managers;

import java.time.LocalDateTime;
import java.util.List;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.FakturaDao;
import sk.upjs.ics.obchod.dao.KosikDao;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.entity.Faktura;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Pouzivatel;
import sk.upjs.ics.obchod.entity.Tovar;

public class DefaultFakturaManager implements FakturaManager {

    private final FakturaDao fakturaDao;
    private final KosikDao kosikDao;

    public DefaultFakturaManager(boolean preTestovaciuDatabazu) {
        if (preTestovaciuDatabazu) {
            fakturaDao = TestDaoFactory.INSTANCE.getMysqlFakturaDao();
            kosikDao = TestDaoFactory.INSTANCE.getPamatoviKosikDao();
        } else {
            fakturaDao = DaoFactory.INSTANCE.getMysqlFakturaDao();
            kosikDao = DaoFactory.INSTANCE.getPamatoviKosikDao();
        }
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
        List<Tovar> tovary = kosikDao.dajTovaryKosika(kosik);
        
        tovary.stream().forEach((t) -> {
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
