package sk.upjs.ics.obchod.entity;

public class Tovar {
    
    private Long id_Tovar;
    
    private String nazov;
    
    private String znacka;
    
    private Long idKategoria;
    
    private Long idPodkategoria;
    
    private int cena;
    
    private String popis;
    
    private String obrazokUrl;
    
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

    public Long getIdKategoria() {
        return idKategoria;
    }

    public void setKategoria(Long idKategoria) {
        this.idKategoria = idKategoria;
    }

    public Long getIdPodkategoria() {
        return idPodkategoria;
    }

    public void setIdPodkategoria(Long idPodkategoria) {
        this.idPodkategoria = idPodkategoria;
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
}
