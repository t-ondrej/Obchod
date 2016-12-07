
package sk.upjs.ics.obchod.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public class PamatovyPouzivatelDao implements PouzivatelDao{

     private List<Pouzivatel> pouzivatelia = new ArrayList<Pouzivatel>();
     
     public PamatovyPouzivatelDao(){
         
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
    public Long ulozPouzivatela(Pouzivatel pouzivatel) {
        pouzivatelia.add(pouzivatel);
        return 0L;
    }

    @Override
    public void odstranPouzivatela(Pouzivatel pouzivatel) {
       pouzivatelia.remove(pouzivatel);
    }

    @Override
    public Pouzivatel dajPouzivatela(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pouzivatel dajPouzivatela(String meno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void novePoslednePrihlasenie(Pouzivatel pouzivatel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
