package org.noticeboard2011.vo;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.noticeboard2011.model.Person;
import org.slim3.tester.AppEngineTestCase;

public class ResultVoTest extends AppEngineTestCase {

    @Test
    public void test() throws Exception {

        // generate test data.
        ResultVo vo = new ResultVo();
        vo.setPage("3");
        vo.setTotal(5);

        List array = new ArrayList();

        List<Person> people = new ArrayList<Person>();
        Person person1 = new Person();
        person1.setFirstName("Aretha");
        person1.setLastName("Franklin");
        person1.setPlace("Present");
        Person person2 = new Person();
        person2.setFirstName("James");
        person2.setLastName("Brown");
        person2.setPlace("Absent");
        Person person3 = new Person();
        person3.setFirstName("Otis");
        person3.setLastName("Redding");
        person3.setPlace("Absent");
        Person person4 = new Person();
        person4.setFirstName("B.B.");
        person4.setLastName("King");
        person4.setPlace("Present");
        Person person5 = new Person();
        person5.setFirstName("Ben E.");
        person5.setLastName("King");
        person5.setPlace("Present");

        people.add(person1);
        people.add(person2);
        people.add(person3);
        people.add(person4);
        people.add(person5);

        for (Person person : people) {
            String[] strArray = new String[5];
            strArray[0] = "0";
            strArray[1] = "1";
            strArray[2] = person.getFirstName();
            strArray[3] = person.getLastName();
            strArray[4] = person.getPlace();
            array.add(strArray);
        }

        for (int i = 0; i < array.size(); i++) {
            vo.addRow(i, (String[]) array.get(i));
        }
        
        //get & assert...
        assertThat(vo.getPage(), is("3"));
        assertThat(vo.getTotal(), is(5));
        assertThat(vo.getRows().size(), is(5));
    }
}
