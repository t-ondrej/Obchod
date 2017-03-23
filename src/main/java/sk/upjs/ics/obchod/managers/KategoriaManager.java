package sk.upjs.ics.obchod.managers;

import java.util.List;
import sk.upjs.ics.obchod.entity.Kategoria;
import sk.upjs.ics.obchod.utils.StringUtilities;
import sk.upjs.ics.obchod.dao.ITovarDao;
import sk.upjs.ics.obchod.dao.IKategoriaDao;

public class KategoriaManager implements IKategoriaManager {

    private final IKategoriaDao kategoriaDao;
    private final ITovarDao tovarDao;

    public KategoriaManager(IKategoriaDao kategoriaDao, ITovarDao tovarDao) {
        this.kategoriaDao = kategoriaDao;
        this.tovarDao = tovarDao;
    }

    @Override
    public List<Kategoria> dajKategorie() {
        return kategoriaDao.dajKategorie();
    }

    @Override
    public void odstranKategoriu(Kategoria kategoria) {
        kategoriaDao.odstranKategoriu(kategoria);
    }

    @Override
    public boolean existujeKategoriaSNazvom(String nazov) {
        String formatovanyNazov = StringUtilities.naformatujString(nazov);
        return kategoriaDao.najdiPodlaNazvu(formatovanyNazov) != null;
    }

    @Override
    public void upravKategoriu(Kategoria kategoria, String nazov) {
        String formatovanyNazov = StringUtilities.naformatujString(nazov);
        kategoria.setNazov(formatovanyNazov);
        kategoriaDao.uloz(kategoria);
    }

    @Override
    public Long pridajKategoriu(Kategoria kategoria) {
        String formatovanyNazov = StringUtilities.naformatujString(kategoria.getNazov());
        kategoria.setNazov(formatovanyNazov);
        return kategoriaDao.uloz(kategoria);
    }
    
    @Override
    public boolean existujeTovarVKategorii(Kategoria kategoria) {        
        return !tovarDao.dajTovarPodlaKategorie(kategoria).isEmpty();
    }

}
