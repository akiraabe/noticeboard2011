package org.noticeboard2011.controller.person;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.noticeboard2011.model.Person;
import org.noticeboard2011.service.PersonService;
import org.slim3.tester.ControllerTestCase;

/**
 * ListControllerのテスト
 * @author akiraabe
 *
 */
public class ListControllerTest extends ControllerTestCase {

    private PersonService service = new PersonService();
    private Person person1 = null;
    private Person person2 = null;
    private Person person3 = null;
    private Person person4 = null;
    private Person person5 = null;
    
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
    
    @Test
    public void run() throws Exception {
        tester.param("page", "1");
        tester.param("rp", "3");
        tester.param("sortorder", "");
        tester.param("qtype", "firstName");
        tester.param("query", "B");
        tester.start("/person/list");
        ListController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
