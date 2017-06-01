package sk.upjs.ics.obchod.managers;

import sk.upjs.ics.obchod.entity.Person;
import sk.upjs.ics.obchod.dao.IPersonDao;

public class PersonManager implements IPersonManager {

    private final IPersonDao userDao;  

    public PersonManager(IPersonDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean hasFilledBillingAddress(Person person) {
        return person.getName() != null && person.getSurname() != null
                && person.getCity() != null && person.getStreet() != null
                && person.getPostalCode() > -1;
    }

}
