package sk.upjs.ics.obchod.dao;

import java.util.List;
import sk.upjs.ics.obchod.entity.Bill;
import sk.upjs.ics.obchod.entity.Product;

public interface IBillDao extends IEntityDao<Bill> {
    
    List<Product> getBillProducts(Bill bill);
    
    void addProductToBill(Product product, Bill bill, int quantity);  
    
    List<Bill> getBillsForLastDay();
    
    List<Bill> getBillsForLastWeek();
    
    List<Bill> getBillsForLastMonth();
    
    List<Bill> getBillsForLastYear();  

    List<Bill> getBillsForLastDays(int daysCount);
    
}
