package sk.upjs.ics.obchod.entity;

import java.util.*;

public class Kosik {
    
    private String id;
    
    private Pouzivatel pouzivatel;
    
    private List<Tovar> zoznamTovarov = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Tovar> getZoznamTovarov() {
        return zoznamTovarov;
    }
    
}
