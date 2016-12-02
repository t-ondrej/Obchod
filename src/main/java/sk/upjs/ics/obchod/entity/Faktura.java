package sk.upjs.ics.obchod.entity;

import java.time.LocalDate;

public class Faktura {
    
    private Long id;
    
    private Long idPouzivatel;
     
    private int suma;
    
    private LocalDate datumNakupu;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPouzivatel() {
        return idPouzivatel;
    }

    public void setIdPouzivatel(Long idPouzivatel) {
        this.idPouzivatel = idPouzivatel;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }

    public LocalDate getDatumNakupu() {
        return datumNakupu;
    }

    public void setDatumNakupu(LocalDate datumNakupu) {
        this.datumNakupu = datumNakupu;
    }    
}
