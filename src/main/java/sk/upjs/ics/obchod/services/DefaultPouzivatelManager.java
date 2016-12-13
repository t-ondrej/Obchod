package sk.upjs.ics.obchod.services;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import sk.upjs.ics.obchod.dao.DaoFactory;
import sk.upjs.ics.obchod.dao.PouzivatelDao;
import sk.upjs.ics.obchod.dao.TestDaoFactory;
import sk.upjs.ics.obchod.entity.Kosik;
import sk.upjs.ics.obchod.entity.Pouzivatel;

public enum DefaultPouzivatelManager implements PouzivatelManager {

    INSTANCETEST(true),
    INSTANCE(false);

    private final PouzivatelDao pouzivatelDao;
    private Pouzivatel aktivnyPouzivatel;
    private BooleanProperty prihlaseny = new SimpleBooleanProperty(false);

    private DefaultPouzivatelManager(boolean preTestovaciuDatabazu) {
        if (preTestovaciuDatabazu) {
            pouzivatelDao = TestDaoFactory.INSTANCE.getMysqlPouzivatelDao();
        } else {
            pouzivatelDao = DaoFactory.INSTANCE.getMysqlPouzivatelDao();
        }
    }

    @Override
    public Pouzivatel getAktivnyPouzivatel() {
        return aktivnyPouzivatel;
    }

    @Override
    public BooleanProperty isPrihlaseny() {
        return prihlaseny;
    }

    @Override
    public void odhlasPouzivatela() {
        this.aktivnyPouzivatel = null;
        prihlaseny.setValue(!prihlaseny.getValue());
    }

    @Override
    public boolean prihlasPouzivatela(String prihlasovacieMeno, String heslo) {
        Pouzivatel pouzivatel = pouzivatelDao.dajPouzivatela(prihlasovacieMeno);

        if (pouzivatel != null && pouzivatel.checkPassword(heslo)) {
            Kosik kosik = pouzivatel.getKosik();
            pouzivatel.setKosik(kosik);
            pouzivatel.setPoslednePrihlasenie(LocalDateTime.now());
            pouzivatelDao.novePoslednePrihlasenie(pouzivatel);

            aktivnyPouzivatel = pouzivatel;
            prihlaseny.setValue(!prihlaseny.getValue());
            return true;
        }

        return false;
    }

    @Override
    public void registrujPouzivatela(String prihlasovacieMeno, String heslo, String email,
            String meno, String priezvisko, String mesto, String ulica, int psc) {
        Pouzivatel pouzivatel = new Pouzivatel();
        pouzivatel.setPrihlasovacieMeno(prihlasovacieMeno);
        pouzivatel.setPassword(heslo);
        pouzivatel.setEmail(email);

        pouzivatel.setMeno(meno);
        pouzivatel.setPriezvisko(priezvisko);
        pouzivatel.setMesto(mesto);
        pouzivatel.setUlica(ulica);
        pouzivatel.setPsc(psc);

        pouzivatel.setPoslednePrihlasenie(LocalDateTime.now());

        Kosik kosik = new Kosik();
        pouzivatel.setKosik(kosik);

        pouzivatelDao.ulozPouzivatela(pouzivatel);
    }

    @Override
    public boolean jeVolneMeno(String meno) {
        Pouzivatel pouzivatel = pouzivatelDao.dajPouzivatela(meno);
        return pouzivatel == null;
    }

    @Override
    public void ulozPouzivatela(Pouzivatel pouzivatel) {
        pouzivatelDao.ulozPouzivatela(pouzivatel);
    }

    @Override
    public void zmenHeslo(Pouzivatel pouzivatel, String heslo) {
        aktivnyPouzivatel.setPassword(heslo);
        pouzivatelDao.ulozPouzivatela(pouzivatel);
    }

    @Override
    public void setAktivnyPouzivatel(Pouzivatel aktivnyPouzivatel) {
        this.aktivnyPouzivatel = aktivnyPouzivatel;
    }

    @Override
    public void setPrihlaseny(BooleanProperty prihlaseny) {
        this.prihlaseny = prihlaseny;
    }

    @Override
    public boolean maVyplneneFakturacneUdaje() {
        return aktivnyPouzivatel.getMeno() != null && aktivnyPouzivatel.getPriezvisko() != null
                && aktivnyPouzivatel.getMesto() != null && aktivnyPouzivatel.getUlica() != null
                && aktivnyPouzivatel.getPsc() > -1;
    }
}
