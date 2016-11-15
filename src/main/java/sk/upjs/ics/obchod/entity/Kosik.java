package sk.upjs.ics.obchod.entity;

import java.util.*;

public class Kosik {
    
    private Long id_Kosik;
    
    private List<Tovar> zoznamTovarov = new ArrayList<>();

    public Kosik(Long id) {
        this.id_Kosik = id;
    }

    public Long getId() {
        return id_Kosik;
    }

    public void setId(Long id) {
        this.id_Kosik = id;
    }

    public List<Tovar> getZoznamTovarov() {
        return zoznamTovarov;
    }
    
}
