package sk.upjs.ics.obchod.entity;

public class Tovar {
    
    private Long id;
    
    private String nazov;
    
    private Long idZnacka;
    
    private Long idKategoria;
    
    private int cena;
    
    private String popis;
    
    private String obrazokUrl;
    
    private int pocet_kusov;
    
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
    
    public int getPocet_kusov() {
        return pocet_kusov;
    }

    public void setPocet_kusov(int pocet_kusov) {
        this.pocet_kusov = pocet_kusov;
    }
    

    void add(Tovar tovar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
