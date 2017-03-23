package sk.upjs.ics.obchod.managers;

import java.util.List;
import sk.upjs.ics.obchod.entity.Znacka;
import sk.upjs.ics.obchod.utils.StringUtilities;
import sk.upjs.ics.obchod.dao.IZnackaDao;
import sk.upjs.ics.obchod.dao.ITovarDao;

public class ZnackaManager implements IZnackaManager {

    private final IZnackaDao znackaDao;
    private final ITovarDao tovarDao;

    public ZnackaManager(IZnackaDao znackaDao, ITovarDao tovarDao) {
        this.znackaDao = znackaDao;
        this.tovarDao = tovarDao;
    }

    @Override
    public List<Znacka> dajZnacky() {
        return znackaDao.dajZnacky();
    }

    @Override
    public void odstranZnacku(Znacka znacka) {
        znackaDao.odstranZnacku(znacka);
    }
    
    @Override
    public void upravZnacku(Znacka znacka, String nazov) {
        String formatovanyNazov = StringUtilities.naformatujString(nazov);
        znacka.setNazov(formatovanyNazov);
        znackaDao.uloz(znacka);
    }
    
    @Override
    public Long pridajZnacku(Znacka znacka) {
        String formatovanyNazov = StringUtilities.naformatujString(znacka.getNazov());
        znacka.setNazov(formatovanyNazov);
        return znackaDao.uloz(znacka);
    }

    @Override
    public boolean existujeZnackaSNazvom(String nazov) {
        String formatovanyNazov = StringUtilities.naformatujString(nazov);
        return znackaDao.najdiPodlaNazvu(formatovanyNazov) != null;
    }
    
    @Override
    public boolean existujeTovarSoZnackou(Znacka znacka) {
        return !tovarDao.dajTovarPodlaZnacky(znacka).isEmpty();
    }

}
