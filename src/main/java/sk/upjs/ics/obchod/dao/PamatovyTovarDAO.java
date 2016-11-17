
package sk.upjs.ics.obchod.dao;

import java.util.ArrayList;
import java.util.List;
import sk.upjs.ics.obchod.entity.Tovar;


public class PamatovyTovarDAO implements TovarDao {
    
     private List<Tovar> tovary = new ArrayList<>();
     
     public PamatovyTovarDAO(){
       //  Tovar t1 = new Tovar(null, "Notebook", "hp", " ", " ", 500, " ", "@../img/1.JPG");
      //   Tovar t2 = new Tovar(null, "Mobil", "Samsung", " ", " ", 100, " ", "@../img/2.JPG");
         
        // tovary.add(t1);
        // tovary.add(t2);
     }

    @Override
    public List<Tovar> dajTovar() {
       return tovary;
    }

    @Override
    public void pridajTovar(Tovar tovar) {
        tovary.add(tovar);
    }

    @Override
    public void odstranTovar(Tovar tovar) {
        tovary.remove(tovar);
    }
    
}
