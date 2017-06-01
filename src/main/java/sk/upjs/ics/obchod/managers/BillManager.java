package sk.upjs.ics.obchod.managers;

import java.time.LocalDateTime;
import java.util.List;
import sk.upjs.ics.obchod.entity.Bill;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.dao.IBillDao;
import sk.upjs.ics.obchod.entity.Account;

public class BillManager implements IBillManager {

    private final IBillDao billDao;

    public BillManager(IBillDao billDao) {
        this.billDao = billDao;
    }

    @Override
    public void createBill(Account account) {
        Bill bill = new Bill(null, account.getId(), 
                account.getCart().getTotalPrice(), 
                LocalDateTime.now());

        billDao.saveOrUpdate(bill);

        Cart cart = account.getCart();
        List<Product> products = cart.getCartProducts();
        
        products.stream().forEach((product) -> {
            int quantity = cart.getCartProductQuantity(product);
            billDao.addProductToBill(product, bill, quantity);
        });
        
        cart.clearCart();
    }
   
}
