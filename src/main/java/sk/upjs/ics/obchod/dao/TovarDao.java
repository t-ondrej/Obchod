package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Tovar;

public interface TovarDao {
    
    public List<Tovar> dajTovar();
    
    public void pridajTovar(Tovar tovar);
    
    public void odstranTovar(Tovar tovar);
       
}
