package sk.upjs.ics.obchod.entity;

public class Tovar {
    
    private Long id;
    
    private String nazov;
    
    private String znacka;
    
    private Long id_kategoria;
    
    private Long id_podkategoria;
    
    private int cena;
    
    private String popis;
    
    private String obrazok_url;
    
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

    public Long getIdKategoria() {
        return id_kategoria;
    }

    public void setKategoria(Long idKategoria) {
        this.id_kategoria = idKategoria;
    }

    public Long getIdPodkategoria() {
        return id_podkategoria;
    }

    public void setIdPodkategoria(Long idPodkategoria) {
        this.id_podkategoria = idPodkategoria;
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
        return obrazok_url;
    }

    public void setobrazokUrl(String obrazokUrl) {
        this.obrazok_url = obrazokUrl;
    }
}
