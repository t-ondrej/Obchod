
package sk.upjs.ics.obchod.dao;

import java.util.ArrayList;
import java.util.List;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.entity.Tovar;
import sk.upjs.ics.obchod.entity.Znacka;


public class PamatovyTovarDao implements TovarDao {
    
     private List<Tovar> tovary = new ArrayList<>();
     
     public PamatovyTovarDao(){
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

    @Override
    public List<Tovar> dajTovarPodlaKategorie(Kategoria kategoria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tovar> dajTovarPodlaNazvu(String nazov) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tovar> dajTovarPodlaZnacky(Znacka znacka) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tovar najdiPodlaId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    

    @Override
    public void nastavTovaruPocetKusov(Tovar tovar, int pocet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int dajPocetTovaru(Long idTovar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
