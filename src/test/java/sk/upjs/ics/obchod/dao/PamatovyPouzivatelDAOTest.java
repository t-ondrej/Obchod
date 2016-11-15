/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.upjs.ics.obchod.dao;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sk.upjs.ics.obchod.entity.Pouzivatel;

/**
 *
 * @author User
 */
public class PamatovyPouzivatelDAOTest {
    
    public PamatovyPouzivatelDAOTest() {
    }   

    /**
     * Test of dajPouzivatelov method, of class PamatovyPouzivatelDAO.
     */
    @Test
    public void testDajPouzivatelov() {
        
        System.out.println("dajPouzivatelov");
        PamatovyPouzivatelDao pamatovyPouzivatelDAO = new PamatovyPouzivatelDao();
        List<Pouzivatel> pouzivatelia = pamatovyPouzivatelDAO.dajPouzivatelov();        
        Assert.assertEquals(2, pouzivatelia.size());
        
    }

    /**
     * Test of pridajPouzivatela method, of class PamatovyPouzivatelDAO.
     */
    @Test
    public void testPridajPouzivatela() {
        
        System.out.println("pridajPouzivatela");
        PamatovyPouzivatelDao pamatovyPouzivatelDAO = new PamatovyPouzivatelDao();
        int pocetPouzivatelov = pamatovyPouzivatelDAO.dajPouzivatelov().size();
        
        Pouzivatel pouzivatel = new Pouzivatel(null, "Igi", "igi","igi@m.sk", LocalDateTime.MIN, false, null);                      
        pamatovyPouzivatelDAO.pridajPouzivatela(pouzivatel);
        int pocetPouzivatelovPoPridani = pamatovyPouzivatelDAO.dajPouzivatelov().size();
       
        Assert.assertEquals(pocetPouzivatelovPoPridani, pocetPouzivatelov+1);
         
        pamatovyPouzivatelDAO.odstranPouzivatela(pouzivatel);
    }

    /**
     * Test of odstranPouzivatela method, of class PamatovyPouzivatelDAO.
     */
    @Test
    public void testOdstranPouzivatela() {
        
        System.out.println("odstranPouzivatela");
        PamatovyPouzivatelDao pamatovyPouzivatelDAO = new PamatovyPouzivatelDao();
        int pocetPouzivatelov = pamatovyPouzivatelDAO.dajPouzivatelov().size();
        
        Pouzivatel pouzivatel = pamatovyPouzivatelDAO.dajPouzivatelov().get(0);       
        pamatovyPouzivatelDAO.odstranPouzivatela(pouzivatel);
        int pocetPouzivatelovPoOdstraneni = pamatovyPouzivatelDAO.dajPouzivatelov().size();
       
        Assert.assertEquals(pocetPouzivatelovPoOdstraneni, pocetPouzivatelov-1);
         
        pamatovyPouzivatelDAO.pridajPouzivatela(pouzivatel);
    }

    /**
     * Test of aktualizujPouzivatelov method, of class PamatovyPouzivatelDAO.
     */
    /*
    @Test
    public void testAktualizujPouzivatelov() {
        // TODO
        System.out.println("aktualizujPouzivatelov");
        PamatovyPouzivatelDAO instance = new PamatovyPouzivatelDAO();
        instance.aktualizujPouzivatelov();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
    
}
