
package sk.upjs.ics.obchod.entity;

import java.util.ArrayList;
import java.util.List;

public class Znacka {
    
    private Long id; 
    
    private String nazov;
   
    private List<Tovar> tovary = new ArrayList<>();   

    public Long getId_Znacka() {
        return id;
    }

    public void setId_Znacka(Long id_Znacka) {
        this.id = id_Znacka;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public List<Tovar> getTovary() {
        return tovary;
    }

    public void setTovary(List<Tovar> tovary) {
        this.tovary = tovary;
    }
    
    
    
    
}
