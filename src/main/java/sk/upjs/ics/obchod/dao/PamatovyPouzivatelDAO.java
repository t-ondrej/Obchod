
package sk.upjs.ics.obchod.dao;

import java.util.ArrayList;
import java.util.List;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public class PamatovyPouzivatelDAO implements PouzivatelDAO{

     private List<Pouzivatel> pouzivatelia = new ArrayList<Pouzivatel>();
     
     public PamatovyPouzivatelDAO(){
         
         Pouzivatel p1 = new Pouzivatel(null, "Jano", "jano", false, null);
         Pouzivatel p2 = new Pouzivatel(null, "Eva", "eva", false, null);
         
         pouzivatelia.add(p1);
         pouzivatelia.add(p2);
         
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

    @Override
    public void aktualizujPouzivatelov() {
        // neviem celkom co to ma robit
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
