package sk.upjs.ics.obchod.managers;

import sk.upjs.ics.obchod.entity.Person;

public interface IPersonManager {
            
    boolean hasFilledBillingAddress(Person person);
}
