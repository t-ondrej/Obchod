package sk.upjs.ics.obchod.entity;

import java.sql.Date;
import java.time.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import sk.upjs.ics.obchod.entity.Kosik;

public class Pouzivatel {
    
    private Long id;
        
    private String prihlasovacie_meno;
    
    private String heslo;
    
    private String sol;
        
    private String email;
     
    private LocalDate posledne_prihlasenie;
    
    private Kosik kosik;
      
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrihlasovacieMeno() {
        return prihlasovacie_meno;
    }

    public void setPrihlasovacieMeno(String prihlasovacieMeno) {
        this.prihlasovacie_meno = prihlasovacieMeno;
    }

    public String getSol() {
        return sol;
    }
    
    public void setSol(String sol) {
        this.sol = sol;
    }
    
    public String getPasswordHash() {
        return heslo;
    }

    public void setPassword(String password) {
        if (sol == null) {
            sol = BCrypt.gensalt();
        }
        this.heslo = BCrypt.hashpw(password, sol);
    }

    public boolean checkPassword(String password) {
        String result = BCrypt.hashpw(password, sol);
        return result.equals(heslo);
    }
    
    public void setPasswordHash(String passwordHash) {
        this.heslo = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getPoslednePrihlasenie() {
        return posledne_prihlasenie;
    }

    public void setPoslednePrihlasenie(LocalDate poslednePrihlasenie) {
        this.posledne_prihlasenie = poslednePrihlasenie;
    }    

    public Kosik getKosik() {
        return kosik;
    }

    public void setKosik(Kosik kosik) {
        this.kosik = kosik;
    }   
}
