package sk.upjs.ics.obchod;

import java.util.*;

public class Kosik {
    
    private String id;
    
    private List<Tovar> zoznamTovarov = new ArrayList<>();

    public Kosik(String id) {
        this.id = id;
    }

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
