package sk.upjs.ics.obchod.entity;

import java.util.ArrayList;
import java.util.List;

public class Kosik {
    
    private Long id;
    
    private List<Tovar> zoznamTovaru = new ArrayList<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
   public void pridajTovar(Tovar tovar) {
       zoznamTovaru.add(tovar);
   }
   
   public void odstranTovar(Tovar tovar) {
       for (int i = 0; i < zoznamTovaru.size(); i++) {
           if (tovar.getId() == zoznamTovaru.get(i).getId())
               zoznamTovaru.remove(i);
       }
   }
}
