
package sk.upjs.ics.obchod.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public class PamatovyPouzivatelDAO implements PouzivatelDAO{

     private List<Pouzivatel> pouzivatelia = new ArrayList<Pouzivatel>();
     
     public PamatovyPouzivatelDAO(){
         
       //  Pouzivatel p1 = new Pouzivatel(null, "Jano", "jano", "jano@m.sk", LocalDateTime.MIN, false, null);
       //  Pouzivatel p2 = new Pouzivatel(null, "Eva", "eva", "eva@m.sk", LocalDateTime.MIN,  false, null);
         
       //  pouzivatelia.add(p1);
      //   pouzivatelia.add(p2);
         
     }
    
    @Override
    public List<Pouzivatel> dajPouzivatelov() {
        return pouzivatelia;
    }

    @Override
    public void pridajPouzivatela(Pouzivatel pouzivatel) {
        pouzivatelia.add(pouzivatel);
    }

    @Override
    public void odstranPouzivatela(Pouzivatel pouzivatel) {
       pouzivatelia.remove(pouzivatel);
    }
    
}
