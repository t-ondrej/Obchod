package sk.upjs.ics.obchod.entity;

import java.time.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Pouzivatel {
    
    private Long id;
        
    private String prihlasovacieMeno;
    
    private String hashHesla;
    
    private String sol;
        
    private String email;
     
    private LocalDate poslednePrihlasenie;
    
    private Kosik kosik;
    
    private boolean jeAdministrator;
      
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrihlasovacieMeno() {
        return prihlasovacieMeno;
    }

    public void setPrihlasovacieMeno(String prihlasovacieMeno) {
        this.prihlasovacieMeno = prihlasovacieMeno;
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
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getPoslednePrihlasenie() {
        return poslednePrihlasenie;
    }

    public void setPoslednePrihlasenie(LocalDate poslednePrihlasenie) {
        this.poslednePrihlasenie = poslednePrihlasenie;
    }    

    public Kosik getKosik() {
        return kosik;
    }

    public void setKosik(Kosik kosik) {
        this.kosik = kosik;
    }   

    public boolean isJeAdministrator() {
        return jeAdministrator;
    }

    public void setJeAdministrator(boolean jeAdministrator) {
        this.jeAdministrator = jeAdministrator;
    }    
}
