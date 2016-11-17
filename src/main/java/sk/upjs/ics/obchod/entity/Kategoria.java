package sk.upjs.ics.obchod.entity;

import java.util.ArrayList;
import java.util.List;

public class Kategoria {
    
    private Long id;
    
    private String nazov;
    
    private List<Tovar> zoznamTovarov = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }    

    public List<Tovar> getTovary() {
        return zoznamTovarov;
    }

    public void setTovary(List<Tovar> tovary) {
        this.zoznamTovarov = tovary;
    }
}
