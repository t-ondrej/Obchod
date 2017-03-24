package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Bill;
import sk.upjs.ics.obchod.entity.Product;

public interface IBillDao extends GenericDao<Bill> {
       
    List<Bill> getBillsForLastDay();
    
    List<Bill> getBillsForLastWeek();
    
    List<Bill> getBillsForLastMonth();
    
    List<Bill> getBillsForLastYear();    
    
    void addProductToBill(Product product, Bill bill, int quantity);
    
    List<Product> getBillProducts(Bill bill);
    
}
