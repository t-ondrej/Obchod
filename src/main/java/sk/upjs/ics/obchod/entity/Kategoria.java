
package sk.upjs.ics.obchod.entity;

import java.util.ArrayList;
import java.util.List;

public class Kategoria {
    
    private Long id; 
    
    private String nazov;
   
    private List<Tovar> ulohy = new ArrayList<>();
    
}
