package sk.upjs.ics.obchod.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Account extends Entity implements Serializable {
    
    private StringProperty username = new SimpleStringProperty();
    
    private String passwordHash;

    private String salt;

    private ObjectProperty<LocalDateTime> lastSignIn = new SimpleObjectProperty();

    private Cart cart;

    private BooleanProperty isAdmin = new SimpleBooleanProperty();
    
    private Person person;

    public Account() {
        
    }
    
    // New account
    public Account(String username, String password, Cart cart, Person person) {
        this.username.setValue(username);
        this.cart = cart;
        this.person = person;
        this.setPassword(password);
    }

    // Existing account
    public Account(Long id, String username, String passwordHash, String salt, Cart cart, Person person) {
        super(id);
        this.username.setValue(username);
        this.passwordHash = passwordHash;
        this.cart = cart;
        this.salt = salt;
        this.person = person;
    }
    
    public String getUsername() {
        return username.getValue();
    }

    public void setUsername(String username) {
        this.username.setValue(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }
    
     public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String password) {
        if (salt == null) {
            salt = BCrypt.gensalt();
        }
        this.passwordHash = BCrypt.hashpw(password, salt);
    }

    public boolean checkPassword(String password) {
        String result = BCrypt.hashpw(password, salt);
        return result.equals(passwordHash);
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public LocalDateTime getLastLogin() {
        return lastSignIn.getValue();
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastSignIn.setValue(lastLogin);
    }

    public ObjectProperty<LocalDateTime> lastLoginProperty() {
        return lastSignIn;
    }
    
    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public boolean isAdministrator() {
        return isAdmin.getValue();
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin.setValue(isAdmin);
    }

    public BooleanProperty isAdminProperty() {
        return isAdmin;
    }

    public Person getPerson() {
        return person;
    }   
}
