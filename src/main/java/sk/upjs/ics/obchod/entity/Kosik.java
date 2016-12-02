package sk.upjs.ics.obchod.entity;

import java.util.HashMap;
import java.util.Map;

public class Kosik {
    
    // Integer je pocet kusov z daneho tovaru v kosiku
    private Map<Tovar, Integer> tovary = new HashMap<>(); 

    public Map<Tovar, Integer> getTovary() {
        return tovary;
    }

    public void setTovary(Map<Tovar, Integer> tovary) {
        this.tovary = tovary;
    }   
}
