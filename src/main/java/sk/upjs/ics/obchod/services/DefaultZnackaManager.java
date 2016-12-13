package sk.upjs.ics.obchod.services;

import java.util.List;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.dao.TovarDao;
import sk.upjs.ics.obchod.dao.ZnackaDao;
import sk.upjs.ics.obchod.entity.Znacka;

public class DefaultZnackaManager implements ZnackaManager {

    private ZnackaDao mysqlZnackaDao;
    private TovarDao mysqlTovarDao;

    public DefaultZnackaManager(boolean preTestovaciuDatabazu) {
        if (preTestovaciuDatabazu) {
            mysqlZnackaDao = TestDaoFactory.INSTANCE.getMysqlZnackaDao();
            mysqlTovarDao = TestDaoFactory.INSTANCE.getMysqlTovarDao();
        } else {
            mysqlZnackaDao = DaoFactory.INSTANCE.getMysqlZnackaDao();
            mysqlTovarDao = DaoFactory.INSTANCE.getMysqlTovarDao();
        }
    }

    @Override
    public List<Znacka> dajZnacky() {
        return mysqlZnackaDao.dajZnacky();
    }

    @Override
    public void odstranZnacku(Znacka znacka) {
        mysqlZnackaDao.odstranZnacku(znacka);
    }
    
    @Override
    public void upravZnacku(Znacka znacka, String nazov) {
        String formatovanyNazov = StringUtilities.naformatujString(nazov);
        znacka.setNazov(formatovanyNazov);
        mysqlZnackaDao.uloz(znacka);
    }
    
    @Override
    public Long pridajZnacku(Znacka znacka) {
        String formatovanyNazov = StringUtilities.naformatujString(znacka.getNazov());
        znacka.setNazov(formatovanyNazov);
        return mysqlZnackaDao.uloz(znacka);
    }

    @Override
    public boolean existujeZnackaSNazvom(String nazov) {
        String formatovanyNazov = StringUtilities.naformatujString(nazov);
        return mysqlZnackaDao.najdiPodlaNazvu(formatovanyNazov) != null;
    }
    
    @Override
    public boolean existujeTovarSoZnackou(Znacka znacka) {
        return !mysqlTovarDao.dajTovarPodlaZnacky(znacka).isEmpty();
    }

}
