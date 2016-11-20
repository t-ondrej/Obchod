package sk.upjs.ics.obchod.services;

import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.PouzivatelDao;
import sk.upjs.ics.obchod.entity.Pouzivatel;


public enum DefaultUserManager implements UserManager{

    INSTANCE;
    
    private final PouzivatelDao dao = DaoFactory.INSTANCE.getMysqlPouzivatelDao();
    private Pouzivatel aktivnyPouzivatel;
    
    public Pouzivatel getAktivnyPouzivatel() {
        return aktivnyPouzivatel;
    }
    
    public void odhlasPouzivatela() {
        this.aktivnyPouzivatel = null;
    }
        
    @Override
    public void prihlasPouzivatela(String meno, String heslo) {
        
    }

    @Override
    public void registrujPouzivatela(Pouzivatel pouzivatel) {
                          
    }
    
    private boolean jeVolneMeno(String meno) {
        return dao.dajPouzivatela(meno) == null;
    }
    
}
