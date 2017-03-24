package sk.upjs.ics.obchod.managers;

import java.util.List;
import sk.upjs.ics.obchod.entity.Bill;
import sk.upjs.ics.obchod.entity.User;
import sk.upjs.ics.obchod.entity.Product;

public interface IBillManager {
    
    /**
     * Vytvori fakturu a priradi k nej prislusne tovar aj s ich poctami, vyprazni kosik
     * @param pouzivatel
     * @return id vytvorenej faktury
     */
    Long createBill(User user);
    
    List<Bill> getBills();
    
    List<Bill> getBillsForPeriod(String period);
    
    List<Product> getBillProducts(Bill bill);
}
