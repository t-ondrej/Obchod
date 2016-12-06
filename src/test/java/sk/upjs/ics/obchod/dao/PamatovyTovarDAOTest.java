
package sk.upjs.ics.obchod.dao;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sk.upjs.ics.obchod.entity.Tovar;

/**
 *
 * @author User
 */
public class PamatovyTovarDAOTest {
    
    public PamatovyTovarDAOTest() {
    }
    
    /**
     * Test of dajTovar method, of class PamatovyTovarDAO.
     */
    @Test
    public void testDajTovar() {
        
        System.out.println("dajTovar");
        PamatovyTovarDao pamatovyTovarDAO = new PamatovyTovarDao();        
        List<Tovar> tovary = pamatovyTovarDAO.dajTovary();
       Assert.assertTrue(tovary != null);
       
    }

    /**
     * Test of pridajTovar method, of class PamatovyTovarDAO.
     */    
    @Test
    public void testPridajTovar() {
        
        System.out.println("pridajTovar");
         PamatovyTovarDao pamatovyTovarDAO = new PamatovyTovarDao(); 
         int pocetTovarov = pamatovyTovarDAO.dajTovary().size();
         
        Tovar tovar = new Tovar();  
        tovar.setNazov("t1");
        tovar.setCena(50);       
        pamatovyTovarDAO.ulozTovar(tovar);
        int pocetTovarovPoPridani = pamatovyTovarDAO.dajTovary().size();
        
         Assert.assertEquals(pocetTovarovPoPridani, pocetTovarov+1);
         
       // pamatovyTovarDAO.odstranTovar(tovar);
    }
    

    /**
     * Test of odstranTovar method, of class PamatovyTovarDAO.
     */    
    /*
    @Test
    public void testOdstranTovar() {
        System.out.println("odstranTovar");
       PamatovyTovarDao pamatovyTovarDAO = new PamatovyTovarDao();
       
       Tovar tovar = new Tovar();  
        tovar.setNazov("t1");
        tovar.setCena(50);       
        pamatovyTovarDAO.pridajTovar(tovar);
       
         int pocetTovarov = pamatovyTovarDAO.dajTovary().size();         
        
        pamatovyTovarDAO.odstranTovar(tovar);
        int pocetTovarovPoOdstraneni = pamatovyTovarDAO.dajTovary().size();
        
         Assert.assertEquals(pocetTovarovPoOdstraneni, pocetTovarov-1);
         
        pamatovyTovarDAO.pridajTovar(tovar);
    }
*/
    
}
