package org.noticeboard2011.service;

import java.util.List;
import java.util.Map;

import org.noticeboard2011.meta.PersonMeta;
import org.noticeboard2011.model.Person;
import org.slim3.datastore.Datastore;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

public class PersonService {

    private PersonMeta personMeta = PersonMeta.get();

    public Person get(Key key, Long version) {
        return Datastore.get(personMeta, key, version);
    }

    public List<Person> getPersonList(int offset, int limit) {
        return Datastore
            .query(personMeta)
            .sort(personMeta.firstName.asc)
            .offset(offset)
            .limit(limit)
            .asList();
    }

    public List<Person> getPersonListDesc(int offset, int limit) {
        return Datastore
            .query(personMeta)
            .sort(personMeta.firstName.desc)
            .offset(offset)
            .limit(limit)
            .asList();
    }

    public Integer count() {
        return Datastore.query(personMeta).count();
    }

    public Person insert(Map<String, Object> input) {
        Person person = new Person();
        BeanUtil.copy(input, person);

        Transaction tx = Datastore.beginTransaction();
        Datastore.put(tx, person);
        tx.commit();

        return person;
    }

    public void update(Long id, String place) {
        Person person = Datastore.get(Person.class, KeyFactory.createKey("Person", id));
        person.setPlace(place);
        Datastore.put(person);
    }
}
