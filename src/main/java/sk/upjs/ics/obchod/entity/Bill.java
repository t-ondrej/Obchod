package sk.upjs.ics.obchod.entity;

import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Bill extends Entity {
    
    private LongProperty userId = new SimpleLongProperty();
     
    private IntegerProperty totalPrice = new SimpleIntegerProperty();
    
    private ObjectProperty<LocalDateTime> purchaseDate = new SimpleObjectProperty();
       
    public Long getUserId() {
        return userId.getValue();
    }

    public void setUserId(Long idPouzivatel) {
        this.userId.setValue(idPouzivatel);
    }
    
    public LongProperty userIdProperty() {
        return userId;
    }

    public int getTotalPrice() {
        return totalPrice.getValue();
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice.setValue(totalPrice);
    }

    public IntegerProperty totalPriceProperty() {
        return totalPrice;
    }   
    
    public LocalDateTime getPurchaseDate() {
        return purchaseDate.getValue();
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate.setValue(purchaseDate);
    }    
    
    public ObjectProperty purchaseDateProperty() {
        return purchaseDate;
    }
}
