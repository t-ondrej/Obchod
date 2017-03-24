package sk.upjs.ics.obchod.managers;

import java.time.LocalDateTime;
import java.util.List;
import sk.upjs.ics.obchod.entity.Bill;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.User;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.dao.IBillDao;
import sk.upjs.ics.obchod.dao.ICartDao;

public class BillManager implements IBillManager {

    private final IBillDao billDao;
    private final ICartDao cartDao;

    public BillManager(IBillDao billDao, ICartDao cartDao) {
        this.billDao = billDao;
        this.cartDao = cartDao;
    }

    @Override
    public Long createBill(User user) {
        Bill bill = new Bill();
        bill.setUserId(user.getId());
        bill.setTotalPrice(user.getCart().getTotalPrice());
        bill.setPurchaseDate(LocalDateTime.now());

        Long billId = billDao.saveOrUpdate(bill);
        bill.setId(billId);

        Cart cart = user.getCart();
        List<Product> products = cartDao.getCartProducts(cart);
        
        products.stream().forEach((product) -> {
            int quantity = cartDao.getCartProductQuantity(product, cart);
            billDao.addProductToBill(product, bill, quantity);
        });
        cartDao.clearCart(cart);

        return billId;
    }

    @Override
    public List<Bill> getBills() {
        return billDao.getAll();
    }

    @Override
    public List<Bill> getBillsForPeriod(String period) {
        switch (period) {
            case "posledný deň":
                return billDao.getBillsForLastDay();
            case "posledný týždeň": 
                return billDao.getBillsForLastWeek();
            case "posledný mesiac": 
                return billDao.getBillsForLastMonth();
            case "posledný rok": 
                return billDao.getBillsForLastYear();
            default: 
                return null;
        }           
    }

    @Override
    public List<Product> getBillProducts(Bill bill) {
        return billDao.getBillProducts(bill);
    }
   
}
