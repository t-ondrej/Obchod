package sk.upjs.ics.obchod.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import sk.upjs.ics.obchod.entity.Cart;
import sk.upjs.ics.obchod.entity.Product;

public class MemoryCartDao implements ICartDao {

    @Override
    public void addProductToCart(Product product, Cart cart) {
        Map<Product, IntegerProperty> tovar = cart.getProducts();
        
        if (!tovar.containsKey(product)) {
            tovar.put(product, new SimpleIntegerProperty(1));

        } else {
            int pocetTovaru = tovar.get(product).getValue();
            tovar.put(product, new SimpleIntegerProperty(pocetTovaru + 1));
        }
        int celkovaCenaPred = cart.getTotalPrice();
        cart.setTotalPrice(celkovaCenaPred + product.getPrice());
    }

    @Override
    public int getCartProductQuantity(Product product, Cart cart) {
        return cart.getProducts().get(product).getValue();
    }

    @Override
    public void setProductQuantity(Product product, Cart cart, int quantity) {
        cart.getProducts().put(product, new SimpleIntegerProperty(quantity));

    }

    @Override
    public void removeProductFromCart(Product product, Cart cart) {
        Map<Product, IntegerProperty> tovar = cart.getProducts();

        if (tovar.get(product).getValue() <= 1) {
            tovar.remove(product);

        } else {
            int pocetTovaru = tovar.get(product).getValue();
            tovar.put(product, new SimpleIntegerProperty(pocetTovaru - 1));
        }
        int celkovaCenaPred = cart.getTotalPrice();
        cart.setTotalPrice(celkovaCenaPred - product.getPrice());
    }

    @Override
    public List<Product> getCartProducts(Cart cart) {
        List<Product> products = new ArrayList<>(cart.getProducts().keySet());
        return products;
    }

    @Override
    public void clearCart(Cart cart) {
        cart.getProducts().clear();
        cart.setTotalPrice(0);
    }

}
