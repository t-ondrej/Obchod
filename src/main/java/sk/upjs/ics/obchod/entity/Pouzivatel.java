package sk.upjs.ics.obchod.entity;

import java.time.LocalDateTime;
import org.springframework.security.crypto.bcrypt.BCrypt;
import sk.upjs.ics.obchod.entity.Kosik;

public class Pouzivatel {
    
    private Long id;
    
    private String login;
    
    private String sol;
    
    private String passwordHash;
    
    private String email;
     
    private LocalDateTime poslednePrihlasenie;
    
    private boolean jeOnline;
    
    private Kosik kosik;

    public Pouzivatel(Long id, String login, String password,String email,LocalDateTime poslednePrihlasenie, boolean jeOnline, Kosik kosik) {
        this.id = id;
        this.login = login;
        
       // setPassword(password);
        
        this.email = email;
        this.poslednePrihlasenie = poslednePrihlasenie;
        this.jeOnline = jeOnline;
        this.kosik = kosik;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSol() {
        return sol;
    }
    
    public void setSol(String sol) {
        this.sol = sol;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPassword(String password) {
        if (sol == null) {
            sol = BCrypt.gensalt();
        }
        this.passwordHash = BCrypt.hashpw(password, sol);
    }

    public boolean checkPassword(String password) {
        String result = BCrypt.hashpw(password, sol);
        return result.equals(passwordHash);
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getPoslednePrihlasenie() {
        return poslednePrihlasenie;
    }

    public void setPoslednePrihlasenie(LocalDateTime poslednePrihlasenie) {
        this.poslednePrihlasenie = poslednePrihlasenie;
    }    

    public boolean isJeOnline() {
        return jeOnline;
    }

    public void setJeOnline(boolean jeOnline) {
        this.jeOnline = jeOnline;
    }

    public Kosik getKosik() {
        return kosik;
    }

    public void setKosik(Kosik kosik) {
        this.kosik = kosik;
    }
    
}
