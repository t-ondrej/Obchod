
package sk.upjs.ics.obchod.services;

import java.util.List;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.KosikDao;
import sk.upjs.ics.obchod.dao.TovarDao;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.gui.KosikModel;

public class DefaultKosikManager implements KosikManager{
    private KosikDao kosikDao = DaoFactory.INSTANCE.getPamatoviKosikDao();
    private TovarDao tovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();

    @Override
    public boolean pridajTovarDoKosika(Tovar tovar, Kosik kosik) { 
        int pocetTovaru = tovarDao.dajPocetTovaru(tovar.getId());
        if(pocetTovaru <= 0){
            return false;
        }
        
        List<Tovar> tovaryKosika = kosikDao.dajTovaryKosika(kosik);        
        boolean jeVKosiku = false;
        
        for(Tovar t: tovaryKosika){
            if(t.getId().equals(tovar.getId())){
                jeVKosiku = true;                
            }
        }
        
        if(!jeVKosiku){                   
            kosikDao.dajTovarDoKosika(tovar.getId(), kosik.getId());   
            tovarDao.nastavTovaruPocetKusov(tovar, pocetTovaru-1);
            KosikModel.INSTANCE.pridajTovarDoKosika(tovar, 1);
        }else{           
        }               
        return true;
    }

    @Override
    public void odoberTovarZKosika(Tovar tovar, Kosik kosik) {
        int pocetZTovaruVKosiku = kosikDao.pocetJednehoTovaruVKosiku(tovar, kosik);
        int pocetTovaru = tovarDao.dajPocetTovaru(tovar.getId());
        
        if(pocetZTovaruVKosiku <= 1){
             int pocetVKosikuPred = kosikDao.pocetJednehoTovaruVKosiku(tovar.getId(), kosik.getId());
             kosikDao.nastavTovaruVKosikuPocetKusov(tovar.getId(), kosik.getId(), pocetVKosikuPred+1);
             tovarDao.nastavTovaruPocetKusov(tovar, pocetTovaru-1);
             KosikModel.INSTANCE.pridajTovarDoKosika(tovar, pocetVKosikuPred + 1);
        }else{            
        }
    }

    @Override
    public List<Tovar> dajTovarKosika(Kosik kosik) {
        return kosikDao.dajTovaryKosika(kosik.getId());
    }

   
}
