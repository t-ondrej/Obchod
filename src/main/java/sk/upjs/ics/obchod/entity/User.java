package sk.upjs.ics.obchod.entity;

import java.time.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class User extends Entity {

    private StringProperty login = new SimpleStringProperty();

    private StringProperty name = new SimpleStringProperty();

    private StringProperty surname = new SimpleStringProperty();

    private StringProperty city = new SimpleStringProperty();

    private StringProperty street = new SimpleStringProperty();

    private IntegerProperty postalCode = new SimpleIntegerProperty();

    private String passwordHash;

    private String salt;

    private StringProperty email = new SimpleStringProperty();

    private ObjectProperty<LocalDateTime> lastLogin = new SimpleObjectProperty();

    private Cart cart;

    private BooleanProperty isAdmin = new SimpleBooleanProperty();

    public String getLogin() {
        return login.getValue();
    }

    public void setLogin(String login) {
        this.login.setValue(login);
    }

    public StringProperty loginProperty() {
        return login;
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getSurname() {
        return surname.getValue();
    }

    public void setSurname(String surname) {
        this.surname.setValue(surname);
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public String getCity() {
        return city.getValue();
    }

    public void setCity(String city) {
        this.city.setValue(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getStreet() {
        return street.getValue();
    }

    public void setStreet(String street) {
        this.street.setValue(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

    public int getPostalCode() {
        return postalCode.getValue();
    }

    public void setPostalCode(int postalCode) {
        this.postalCode.setValue(postalCode);
    }
    
    public IntegerProperty postalCodeProperty() {
        return postalCode;
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

    public String getEmail() {
        return email.getValue();
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin.getValue();
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin.setValue(lastLogin);
    }

    public ObjectProperty<LocalDateTime> lastLoginProperty() {
        return lastLogin;
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
}
