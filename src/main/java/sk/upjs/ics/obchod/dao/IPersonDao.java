package sk.upjs.ics.obchod.dao;

import sk.upjs.ics.obchod.entity.Person;

public interface IPersonDao extends IEntityDao<Person> {
           
    Person findByName(String name);          
}
