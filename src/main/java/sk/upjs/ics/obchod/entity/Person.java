package sk.upjs.ics.obchod.entity;

import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person extends Entity implements Serializable {

    private StringProperty name = new SimpleStringProperty();

    private StringProperty surname = new SimpleStringProperty();

    private StringProperty city = new SimpleStringProperty();

    private StringProperty street = new SimpleStringProperty();

    private IntegerProperty postalCode = new SimpleIntegerProperty();

    private StringProperty email = new SimpleStringProperty();
    
    public Person() {
    }

    public Person(Long id, String name, String surname, String city, 
            String street, int postalCode, String email) {
        super(id);
        this.name.setValue(name);
        this.surname.setValue(surname);
        this.city.setValue(city);
        this.street.setValue(street);
        this.postalCode.setValue(postalCode);
        this.email.setValue(email);
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

    public String getEmail() {
        return email.getValue();
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public StringProperty emailProperty() {
        return email;
    }
}
