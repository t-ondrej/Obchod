package sk.upjs.ics.obchod.entity;

import java.time.LocalDateTime;
import org.springframework.security.crypto.bcrypt.BCrypt;
import sk.upjs.ics.obchod.entity.Kosik;

public class Pouzivatel {
    
    private Long id;
    
    private Long id_kosik;
    
    private String prihlasovacie_meno;
    
    private String heslo;
    
    private String sol;
        
    private String email;
     
    private LocalDateTime posledne_prihlasenie;
      
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

    public LocalDateTime getPoslednePrihlasenie() {
        return posledne_prihlasenie;
    }

    public void setPoslednePrihlasenie(LocalDateTime poslednePrihlasenie) {
        this.posledne_prihlasenie = poslednePrihlasenie;
    }    

    public Long getIdKosik() {
        return id_kosik;
    }

    public void setKosik(Long idKosik) {
        this.id_kosik = idKosik;
    }
    
}
