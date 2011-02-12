package org.noticeboard2011.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.noticeboard2011.model.Person;
import org.slim3.datastore.Datastore;
import org.slim3.tester.AppEngineTestCase;

/**
 * PersonServiceのテスト
 * 
 * @author akiraabe
 *
 */
public class PersonServiceTest extends AppEngineTestCase {

    private PersonService service = new PersonService();
    private Person person1 = null;
    private Person person2 = null;
    private Person person3 = null;
    private Person person4 = null;
    private Person person5 = null;
    
    /**
     * serviceが利用可能であることをテストする（スモークテスト）
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
    
    /**
     * テストデータのセットアップ
     */
    @Before
    public void init() {
        Map<String, Object> input1 = new HashMap<String, Object>();
        input1.put("firstName", "James");
        input1.put("lastName", "Brown");
        input1.put("place", "Absent");
        person1 = service.insert(input1);
        Map<String, Object> input2 = new HashMap<String, Object>();
        input2.put("firstName", "Otis");
        input2.put("lastName", "Redding");
        input2.put("place", "Absent");
        person2 = service.insert(input2);
        Map<String, Object> input3 = new HashMap<String, Object>();
        input3.put("firstName", "Aretha");
        input3.put("lastName", "Franklin");
        input3.put("place", "Present");
        person3 = service.insert(input3);
        Map<String, Object> input4 = new HashMap<String, Object>();
        input4.put("firstName", "B.B.");
        input4.put("lastName", "King");
        input4.put("place", "Present");
        person4 = service.insert(input4);
        Map<String, Object> input5 = new HashMap<String, Object>();
        input5.put("firstName", "Ben E.");
        input5.put("lastName", "King");
        input5.put("place", "Present");
        person5 = service.insert(input5);
    }
    
    /**
     * insert処理のテスト
     * 
     * @throws Exception
     */
    @Test
    public void insert() throws Exception {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("firstName", "Aretha");
        input.put("lastName", "Franklin");
        input.put("place", "Present");
        Person person = service.insert(input);
        assertThat(person, is(notNullValue()));
        Person stored = Datastore.get(Person.class, person.getKey());
        assertThat(stored.getFirstName(), is("Aretha"));
        assertThat(stored.getLastName(), is("Franklin"));
        assertThat(stored.getPlace(), is("Present"));
    }
    
    /**
     * get処理のテスト
     * 
     * @throws Exception
     */
    @Test
    public void get() throws Exception {
        Person person = service.get(person1.getKey(), person1.getVersion());
        assertThat(person, is(notNullValue()));
        assertThat(person.getFirstName(), is("James"));
        assertThat(person.getLastName(), is("Brown"));
        assertThat(person.getPlace(), is("Absent"));
    }
    
    /**
     * getPersonListのテスト
     * 検索条件あり
     * 
     * @throws Exception
     */
    @Test
    public void getPersonListWithQuery() throws Exception {
        List<Person> people = service.getPersonList(0, 3, "B", "");
        assertThat(people.size(), is(2));
        assertThat(people.get(0).getFirstName(), is("B.B."));
        assertThat(people.get(0).getLastName(), is("King"));
        assertThat(people.get(0).getPlace(), is("Present"));
        assertThat(people.get(1).getFirstName(), is("Ben E."));
        assertThat(people.get(1).getLastName(), is("King"));
        assertThat(people.get(1).getPlace(), is("Present"));
    }
    
    /**
     * getPersonListのテスト
     * 検索条件なし
     * 
     * @throws Exception
     */
    @Test
    public void getPersonListWithoutQuery() throws Exception {
        List<Person> people = service.getPersonList(0, 3, "", "");
        assertThat(people.size(), is(3));
        assertThat(people.get(0).getFirstName(), is("Aretha"));
        assertThat(people.get(0).getLastName(), is("Franklin"));
        assertThat(people.get(0).getPlace(), is("Present"));
        assertThat(people.get(1).getFirstName(), is("B.B."));
        assertThat(people.get(1).getLastName(), is("King"));
        assertThat(people.get(1).getPlace(), is("Present"));
        assertThat(people.get(2).getFirstName(), is("Ben E."));
        assertThat(people.get(2).getLastName(), is("King"));
        assertThat(people.get(2).getPlace(), is("Present"));
    }
    
    /**
     * getPersonListのテスト
     * 検索条件なし(Page2)
     * 
     * @throws Exception
     */
    @Test
    public void getPersonListWithoutQueryPage2() throws Exception {
        List<Person> people = service.getPersonList(3, 3, "", "");
        assertThat(people.size(), is(2));
        assertThat(people.get(0).getFirstName(), is("James"));
        assertThat(people.get(0).getLastName(), is("Brown"));
        assertThat(people.get(0).getPlace(), is("Absent"));
        assertThat(people.get(1).getFirstName(), is("Otis"));
        assertThat(people.get(1).getLastName(), is("Redding"));
        assertThat(people.get(1).getPlace(), is("Absent"));
    }
    
    /**
     * getPersonListのテスト
     * 検索条件なし(DescSort)
     * 
     * @throws Exception
     */
    @Test
    public void getPersonListWithoutQueryDescSort() throws Exception {
        List<Person> people = service.getPersonList(0, 3, "", "desc");
        assertThat(people.size(), is(3));
        assertThat(people.get(0).getFirstName(), is("Otis"));
        assertThat(people.get(0).getLastName(), is("Redding"));
        assertThat(people.get(0).getPlace(), is("Absent"));
        assertThat(people.get(1).getFirstName(), is("James"));
        assertThat(people.get(1).getLastName(), is("Brown"));
        assertThat(people.get(1).getPlace(), is("Absent"));
        assertThat(people.get(2).getFirstName(), is("Ben E."));
        assertThat(people.get(2).getLastName(), is("King"));
        assertThat(people.get(2).getPlace(), is("Present"));
    }
    
    /**
     * countのテスト
     * 
     * @throws Exception
     */
    @Test
    public void count() throws Exception {
        assertThat(service.count("B"), is(2));
        assertThat(service.count(""), is(5));
    }
    
    @Test
    public void initialize() throws Exception {
        service.initialize();
        List<Person> people = service.getPersonList(0,5,"","");
        for (Person person : people) {
            assertThat(person.getPlace(), is("undefined"));
        }
    }
    
    @Test
    public void updatePlace() throws Exception {
        service.updatePlace(person1.getKey().getId(), "Somewhere", "test@gmail.com");
        Person stored = Datastore.get(Person.class, person1.getKey());
        assertThat(stored.getFirstName(), is("James"));
        assertThat(stored.getLastName(), is("Brown"));
        assertThat(stored.getPlace(), is("Somewhere"));
    }
    
    @Test
    public void updateProperty() throws Exception {
        service.updateProperty(person1.getKey().getId(), "アレサ", "フランクリン");
        Person stored = Datastore.get(Person.class, person1.getKey());
        assertThat(stored.getFirstName(), is("アレサ"));
        assertThat(stored.getLastName(), is("フランクリン"));
        assertThat(stored.getPlace(), is("Absent"));
    }
    
    @Test
    public void parseText() throws Exception {
        Map<String,String> map = service.parseText("今日は休みです！！！");
        System.out.println(map.get("place"));
        System.out.println(map.get("memo"));
        
    }
}
