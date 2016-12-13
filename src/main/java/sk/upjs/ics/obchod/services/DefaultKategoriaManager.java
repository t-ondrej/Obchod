package sk.upjs.ics.obchod.services;

import java.util.List;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.KategoriaDao;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.dao.TovarDao;
import sk.upjs.ics.obchod.entity.Kategoria;

public class DefaultKategoriaManager implements KategoriaManager {

    private KategoriaDao mysqlKategoriaDao;
    private TovarDao mysqlTovarDao;

    public DefaultKategoriaManager(boolean preTestovaciuDatabazu) {
        if (preTestovaciuDatabazu) {
            mysqlKategoriaDao = TestDaoFactory.INSTANCE.getMysqlKategoriaDao();
            mysqlTovarDao = TestDaoFactory.INSTANCE.getMysqlTovarDao();
        } else {
            mysqlKategoriaDao = DaoFactory.INSTANCE.getMysqlKategoriaDao();
            mysqlTovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();
        }
    }

    @Override
    public List<Kategoria> dajKategorie() {
        return mysqlKategoriaDao.dajKategorie();
    }

    @Override
    public void odstranKategoriu(Kategoria kategoria) {
        mysqlKategoriaDao.odstranKategoriu(kategoria);
    }

    @Override
    public boolean existujeKategoriaSNazvom(String nazov) {
        String formatovanyNazov = StringUtilities.naformatujString(nazov);
        return mysqlKategoriaDao.najdiPodlaNazvu(formatovanyNazov) != null;
    }

    @Override
    public void upravKategoriu(Kategoria kategoria, String nazov) {
        String formatovanyNazov = StringUtilities.naformatujString(nazov);
        kategoria.setNazov(formatovanyNazov);
        mysqlKategoriaDao.uloz(kategoria);
    }

    @Override
    public Long pridajKategoriu(Kategoria kategoria) {
        String formatovanyNazov = StringUtilities.naformatujString(kategoria.getNazov());
        kategoria.setNazov(formatovanyNazov);
        return mysqlKategoriaDao.uloz(kategoria);
    }
    
    @Override
    public boolean existujeTovarVKategorii(Kategoria kategoria) {        
        return !mysqlTovarDao.dajTovarPodlaKategorie(kategoria).isEmpty();
    }

}
