package org.noticeboard2011.service;

import java.util.List;
import java.util.Map;

import org.noticeboard2011.meta.PersonMeta;
import org.noticeboard2011.model.Person;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

public class PersonService {

    private PersonMeta personMeta = PersonMeta.get();

    public Person get(Key key, Long version) {
        return Datastore.get(personMeta, key, version);
    }

    public List<Person> getPersonList(int offset, int limit, String query, String sortorder) {
        ModelQuery<Person> modelQuery = 
        Datastore
            .query(personMeta)
            .offset(offset)
            .limit(limit);
        if (!"".equals(query)) {
            modelQuery = modelQuery.filter(personMeta.firstName.startsWith(query));
        }
        if ("desc".equals(sortorder)) {
            modelQuery = modelQuery.sort(personMeta.firstName.desc);
        } else {
            modelQuery = modelQuery.sort(personMeta.firstName.asc);
        }
        return modelQuery.asList();
    }

    public Integer count(String query) {
        if (!"".equals(query)) {
            return Datastore.query(personMeta).filter(personMeta.firstName.startsWith(query)).count();
        } else {
            return Datastore.query(personMeta).count();
        }
        
    }

    public Person insert(Map<String, Object> input) {
        Person person = new Person();
        BeanUtil.copy(input, person);

        Transaction tx = Datastore.beginTransaction();
        Datastore.put(tx, person);
        tx.commit();

        return person;
    }

    public void updatePlace(Long id, String place) {
        Person person = Datastore.get(Person.class, KeyFactory.createKey("Person", id));
        person.setPlace(place);
        Datastore.put(person);
    }

    public void updateProperty(Long id, String firstName, String lastName) {
        Person person = Datastore.get(Person.class, KeyFactory.createKey("Person", id));
        person.setFirstName(firstName);
        person.setLastName(lastName);
        Datastore.put(person);
    }
}
