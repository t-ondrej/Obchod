package sk.upjs.ics.obchod.entity;

public class Tovar {
    
    private Long id_Tovar;
    
    private String nazov;
    
    private String znacka;
    
    private String kategoria;
    
    private String podkategoria;
    
    private int cena;
    
    private String popis;
    
    private String URL;

    public Tovar(Long id, String nazov, String znacka, String kategoria, String podkategoria, int cena, String popis, String URL) {
        this.id_Tovar = id;
        this.nazov = nazov;
        this.znacka = znacka;
        this.kategoria = kategoria;
        this.podkategoria = podkategoria;
        this.cena = cena;
        this.popis = popis;
        this.URL = URL;
    }
    
    public Long getId() {
        return id_Tovar;
    }

    public void setId(Long id) {
        this.id_Tovar = id;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getZnacka() {
        return znacka;
    }

    public void setZnacka(String znacka) {
        this.znacka = znacka;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public String getPodkategoria() {
        return podkategoria;
    }

    public void setPodkategoria(String podkategoria) {
        this.podkategoria = podkategoria;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
