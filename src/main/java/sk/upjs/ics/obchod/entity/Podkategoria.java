
package sk.upjs.ics.obchod.entity;

import java.util.ArrayList;
import java.util.List;

public class Podkategoria {
    
    private Long id_Podkategoria; 
    
    private String nazov;
   
    private List<Tovar> tovary = new ArrayList<>();
    
}
