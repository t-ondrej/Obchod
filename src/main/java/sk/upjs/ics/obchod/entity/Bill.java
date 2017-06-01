package sk.upjs.ics.obchod.entity;

import java.time.LocalDateTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Bill extends Entity {
    
    private LongProperty accountId = new SimpleLongProperty();
     
    private IntegerProperty totalPrice = new SimpleIntegerProperty();
    
    private ObjectProperty<LocalDateTime> purchaseDate = new SimpleObjectProperty();

    public Bill() {
    }

    public Bill(Long id, long userId, int totalPrice, LocalDateTime purchaseDate) {
        super(id);
        this.accountId.setValue(userId);
        this.totalPrice.setValue(totalPrice);
        this.purchaseDate.setValue(purchaseDate);
    }
      
    public long getAccountId() {
        return accountId.getValue();
    }

    public void setAccountId(long idPouzivatel) {
        this.accountId.setValue(idPouzivatel);
    }
    
    public LongProperty accountIdProperty() {
        return accountId;
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
