
package sk.upjs.ics.obchod.services;

import java.util.List;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.KosikDao;
import sk.upjs.ics.obchod.dao.TovarDao;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Tovar;

public class DefaultKosikManager implements KosikManager{
    private KosikDao kosikDao = DaoFactory.INSTANCE.getMysqlKosikDao();
    private TovarDao tovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();

    @Override
    public boolean pridajTovarDoKosika(Tovar tovar, Kosik kosik) {
        
        if(tovar.getPocet_kusov()==0){
            return false;
        }        
        int predtym = tovar.getPocet_kusov();        
        kosikDao.dajTovarDoKosika(tovar.getId(), kosik.getId());   
        tovarDao.nastavTovaruPocetKusov(tovar, predtym-1);
                
        return true;
    }

    @Override
    public boolean odoberTovarZKosika(Tovar tovar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
