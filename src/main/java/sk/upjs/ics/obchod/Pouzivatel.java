package sk.upjs.ics.obchod;

public class Pouzivatel {
    
    private Long id;
    
    private String login;
    
    private String password;
    
    private boolean jeOnline;
    
    private Kosik kosik;

    public Pouzivatel(Long id, String login, String password, boolean jeOnline, Kosik kosik) {
        this.id = id;
        this.login = login;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
