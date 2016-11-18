package sk.upjs.ics.obchod.entity;

import java.util.Date;

public class Faktura {
    
    private Long id;
    
    private int suma;
    
    private Date datumNakupu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }

    public Date getDatumNakupu() {
        return datumNakupu;
    }

    public void setDatumNakupu(Date datumNakupu) {
        this.datumNakupu = datumNakupu;
    }
    
    
}
