package sk.upjs.ics.obchod.entity;

import java.time.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Pouzivatel {

    private LongProperty id = new SimpleLongProperty();

    private StringProperty prihlasovacieMeno = new SimpleStringProperty();

    private StringProperty meno = new SimpleStringProperty();

    private StringProperty priezvisko = new SimpleStringProperty();

    private StringProperty mesto = new SimpleStringProperty();

    private StringProperty ulica = new SimpleStringProperty();

    private IntegerProperty psc = new SimpleIntegerProperty();

    private String hashHesla;

    private String sol;

    private StringProperty email = new SimpleStringProperty();

    private ObjectProperty<LocalDate> poslednePrihlasenie = new SimpleObjectProperty();

    private Kosik kosik;

    private BooleanProperty jeAdministrator = new SimpleBooleanProperty();

    public Long getId() {
        return id.getValue();
    }

    public void setId(Long id) {
        this.id.setValue(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getPrihlasovacieMeno() {
        return prihlasovacieMeno.getValue();
    }

    public void setPrihlasovacieMeno(String prihlasovacieMeno) {
        this.prihlasovacieMeno.setValue(prihlasovacieMeno);
    }

    public StringProperty prihlasovacieMenoProperty() {
        return prihlasovacieMeno;
    }

    public String getMeno() {
        return meno.getValue();
    }

    public void setMeno(String meno) {
        this.meno.setValue(meno);
    }

    public StringProperty menoProperty() {
        return meno;
    }

    public String getPriezvisko() {
        return priezvisko.getValue();
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko.setValue(priezvisko);
    }

    public StringProperty priezviskoProperty() {
        return priezvisko;
    }

    public String getMesto() {
        return mesto.getValue();
    }

    public void setMesto(String mesto) {
        this.mesto.setValue(mesto);
    }

    public StringProperty mestoProperty() {
        return mesto;
    }

    public String getUlica() {
        return ulica.getValue();
    }

    public void setUlica(String ulica) {
        this.ulica.setValue(ulica);
    }

    public StringProperty ulicaProperty() {
        return ulica;
    }

    public int getPsc() {
        return psc.getValue();
    }

    public void setPsc(int psc) {
        this.psc.setValue(psc);
    }
    
    public IntegerProperty pscProperty() {
        return psc;
    }

    public String getSol() {
        return sol;
    }

    public void setSol(String sol) {
        this.sol = sol;
    }

    public String getPasswordHash() {
        return hashHesla;
    }

    public void setPassword(String password) {
        if (sol == null) {
            sol = BCrypt.gensalt();
        }
        this.hashHesla = BCrypt.hashpw(password, sol);
    }

    public boolean checkPassword(String password) {
        String result = BCrypt.hashpw(password, sol);
        return result.equals(hashHesla);
    }

    public void setPasswordHash(String passwordHash) {
        this.hashHesla = passwordHash;
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

    public LocalDate getPoslednePrihlasenie() {
        return poslednePrihlasenie.getValue();
    }

    public void setPoslednePrihlasenie(LocalDate poslednePrihlasenie) {
        this.poslednePrihlasenie.setValue(poslednePrihlasenie);
    }

    public ObjectProperty<LocalDate> poslednePrihlasenieProperty() {
        return poslednePrihlasenie;
    }

    public Kosik getKosik() {
        return kosik;
    }

    public void setKosik(Kosik kosik) {
        this.kosik = kosik;
    }

    public boolean isJeAdministrator() {
        return jeAdministrator.getValue();
    }

    public void setJeAdministrator(boolean jeAdministrator) {
        this.jeAdministrator.setValue(jeAdministrator);
    }

    public BooleanProperty jeAdministratorProperty() {
        return jeAdministrator;
    }
}
