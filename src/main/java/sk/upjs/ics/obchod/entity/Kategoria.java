
package sk.upjs.ics.obchod.entity;

import java.util.ArrayList;
import java.util.List;

public class Kategoria {
    
    private Long id_Kategoria; 
    
    private String nazov;
   
    private List<Tovar> tovary = new ArrayList<>();
    
}
