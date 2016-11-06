package sk.upjs.ics.obchod;

import java.util.List;

public interface TovarDAO {
    
    public List<Tovar> dajTovar();
    
    public void pridajTovar(Tovar tovar);
    
    public void odstranTovar(Tovar tovar);
       
}
