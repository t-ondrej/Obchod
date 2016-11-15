/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        List<Tovar> tovary = pamatovyTovarDAO.dajTovar();
       Assert.assertEquals(2, tovary.size());
       
    }

    /**
     * Test of pridajTovar method, of class PamatovyTovarDAO.
     */
    @Test
    public void testPridajTovar() {
        
        System.out.println("pridajTovar");
         PamatovyTovarDao pamatovyTovarDAO = new PamatovyTovarDao(); 
         int pocetTovarov = pamatovyTovarDAO.dajTovar().size();
         
        Tovar tovar = new Tovar(null, "Klavesnica", "Logitech", " ", " ", 50, " ", "@../img/1.JPG");        
        pamatovyTovarDAO.pridajTovar(tovar);
        int pocetTovarovPoPridani = pamatovyTovarDAO.dajTovar().size();
        
         Assert.assertEquals(pocetTovarovPoPridani, pocetTovarov+1);
         
        pamatovyTovarDAO.odstranTovar(tovar);
    }

    /**
     * Test of odstranTovar method, of class PamatovyTovarDAO.
     */
    @Test
    public void testOdstranTovar() {
        System.out.println("odstranTovar");
       PamatovyTovarDao pamatovyTovarDAO = new PamatovyTovarDao(); 
         int pocetTovarov = pamatovyTovarDAO.dajTovar().size();
         
        Tovar tovar = pamatovyTovarDAO.dajTovar().get(0);
        pamatovyTovarDAO.odstranTovar(tovar);
        int pocetTovarovPoOdstraneni = pamatovyTovarDAO.dajTovar().size();
        
         Assert.assertEquals(pocetTovarovPoOdstraneni, pocetTovarov-1);
         
        pamatovyTovarDAO.pridajTovar(tovar);
    }
    
}
