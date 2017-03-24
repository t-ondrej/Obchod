package sk.upjs.ics.obchod.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Cart {
    
    private final ObservableMap<Product, IntegerProperty> products = FXCollections.observableHashMap();
    
    private final IntegerProperty totalPrice = new SimpleIntegerProperty(0);
    
    public ObservableMap<Product, IntegerProperty> getProducts() {
        return products;
    }
    
    public int getTotalPrice() {
        return totalPrice.getValue();
    }
    
    public void setTotalPrice(int totalPrice) {
        this.totalPrice.set(totalPrice);
    }
    
    public IntegerProperty totalPriceProperty() {
        return totalPrice;
    }
}
