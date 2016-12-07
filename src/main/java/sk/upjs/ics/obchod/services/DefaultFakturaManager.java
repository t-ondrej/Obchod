package sk.upjs.ics.obchod.services;

import java.time.LocalDate;
import java.util.List;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.FakturaDao;
import sk.upjs.ics.obchod.dao.KosikDao;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.entity.Faktura;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Pouzivatel;
import sk.upjs.ics.obchod.entity.Tovar;

public class DefaultFakturaManager implements FakturaManager{
    
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
        faktura.setDatumNakupu(LocalDate.now());
        
        Long idFaktury = fakturaDao.pridajFakturu(faktura);
        faktura.setId(idFaktury);
        
        Kosik kosik = pouzivatel.getKosik();
        List<Tovar> tovary = kosikDao.dajTovaryKosika(kosik);
        for (Tovar t : tovary) {
            int pocet = kosikDao.pocetJednehoTovaruVKosiku(t, kosik); 
            fakturaDao.pridajTovarFakture(t, faktura, pocet);
        }
        kosikDao.vyprazniKosik(kosik);       
        
        return idFaktury;
    }
    
}
