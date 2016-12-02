package sk.upjs.ics.obchod.entity;

public class Tovar {
    
    private Long id;
    
    private String nazov;
    
    private Long idZnacka;
    
    private Long idKategoria;
    
    private int cena;
    
    private String popis;
    
    private String obrazokUrl;
    
    private int pocetKusov;
    
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

    public Long getIdZnacka() {
        return idZnacka;
    }

    public void setIdZnacka(Long idZnacka) {
        this.idZnacka = idZnacka;
    }

    public Long getIdKategoria() {
        return idKategoria;
    }

    public void setIdKategoria(Long idKategoria) {
        this.idKategoria = idKategoria;
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

    public String getobrazokUrl() {
        return obrazokUrl;
    }

    public void setobrazokUrl(String obrazokUrl) {
        this.obrazokUrl = obrazokUrl;
    }
    
    public int getPocetKusov() {
        return pocetKusov;
    }

    public void setPocetKusov(int pocetKusov) {
        this.pocetKusov = pocetKusov;
    }
    
}
