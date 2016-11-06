package sk.upjs.ics.obchod;

public class Tovar {
    
    private Long id;
    
    private String nazov;
    
    private String znacka;
    
    private String kategoria;
    
    private String podkategoria;
    
    private int cena;
    
    private String popis;

    public Tovar(Long id, String nazov, String znacka, String kategoria, String podkategoria, int cena, String popis) {
        this.id = id;
        this.nazov = nazov;
        this.znacka = znacka;
        this.kategoria = kategoria;
        this.podkategoria = podkategoria;
        this.cena = cena;
        this.popis = popis;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
}
