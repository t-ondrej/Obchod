package sk.upjs.ics.obchod.entity;

import java.util.*;

public class Kosik {
    
    private Long id;
    
    private Pouzivatel pouzivatel;
    
    private List<Tovar> zoznamTovarov = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Tovar> getZoznamTovarov() {
        return zoznamTovarov;
    }
    
}
