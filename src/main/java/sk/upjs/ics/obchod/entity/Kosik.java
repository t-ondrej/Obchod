package sk.upjs.ics.obchod.entity;

import java.util.*;

public class Kosik {
    
    private Long id_Kosik;
    
    private Pouzivatel pouzivatel;
    
    private List<Tovar> zoznamTovarov = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(Long id) {
        this.id_Kosik = id;
    }

    public List<Tovar> getZoznamTovarov() {
        return zoznamTovarov;
    }
    
}
