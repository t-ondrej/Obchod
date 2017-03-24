package sk.upjs.ics.obchod.managers;

import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.Product;
import sk.upjs.ics.obchod.dao.IProductDao;
import sk.upjs.ics.obchod.dao.ICartDao;

public class CartManager implements ICartManager {

    private final ICartDao cartDao;
    private final IProductDao productDao;
    private final IUserManager userDao;

    public CartManager(ICartDao cartDao, IProductDao productDao, IUserManager userDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
        this.userDao = userDao;
    }

    @Override
    public boolean addProductToCart(Product product) {
        Cart cart = userDao.getSignedInUser().getCart();
        int productQuantity = productDao.getProductQuantity(product);

        if (productQuantity <= 0) {
            return false;
        }

        cartDao.addProductToCart(product, cart);
        int quantityBeforeAddition = productDao.getProductQuantity(product);
        productDao.setProductQuantity(product, quantityBeforeAddition - 1);

        return true;
    }

    @Override
    public void removeProductFromCart(Product product) {
        Cart cart = userDao.getSignedInUser().getCart();        
        cartDao.removeProductFromCart(product, cart);
        int quantityBeforeAddition = productDao.getProductQuantity(product);
        productDao.setProductQuantity(product, quantityBeforeAddition + 1);
    }

    @Override
    public List<Product> getCartProducts() {
        Cart cart = userDao.getSignedInUser().getCart();
        return cartDao.getCartProducts(cart);
    }

    @Override
    public int getCartProductQuantity(Product product) {
        Cart cart = userDao.getSignedInUser().getCart();
        return cartDao.getCartProductQuantity(product, cart);
    }

    @Override
    public IntegerProperty cartProductQuantityProperty(Product product) {
        Cart cart = userDao.getSignedInUser().getCart();
        return cart.getProducts().get(product);
    }

    @Override
    public ObservableList<Product> cartProductsObservableList() {
        Cart cart = userDao.getSignedInUser().getCart();
        return FXCollections.observableArrayList(cartDao.getCartProducts(cart));
    }

    @Override
    public void clearCart() {
        Cart cart = userDao.getSignedInUser().getCart();
        List<Product> products = cartDao.getCartProducts(cart);
        
        for (Product p : products) {
            int quantity = cartDao.getCartProductQuantity(p, cart);
            int quantityBeforeAddition = productDao.getProductQuantity(p);
            productDao.setProductQuantity(p, quantityBeforeAddition + quantity);
        }
        cartDao.clearCart(cart);
    }
}
