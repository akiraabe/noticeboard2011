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
 * UpdateControllerのテスト
 * 
 * @author akiraabe
 *
 */
public class UpdateControllerTest extends ControllerTestCase {

    private Person person = null;
    
    /**
     * テストデータのセットアップ
     */
    @Before
    public void init() {
        PersonService service = new PersonService();
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("firstName", "James");
        input.put("lastName", "Brown");
        input.put("place", "Absent");
        person = service.insert(input);
    }
    
    
    /**
     * 場所の更新の正常ケース<br/>
     * <br/>
     * 合格条件
     * <li>Validationに通り、遷移先画面にnullが指定される</li>
     */
    @Test
    public void normalCaseByCommandPlace() throws Exception {
        tester.param("firstName", "James");
        tester.param("lastName", "Brown");
        tester.param("place", "Absent");
        tester.param("command", "PLACE");
//        System.out.println(person.getKey().getId());
        tester.param("id", person.getKey().getId());
        tester.start("/person/update");

        UpdateController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
    
    /**
     * 属性の更新の正常ケース<br/>
     * <br/>
     * 合格条件
     * <li>Validationに通り、遷移先画面にnullが指定される</li>
     */
    @Test
    public void normalCaseByCommandProperty() throws Exception {
        tester.param("firstName", "James");
        tester.param("lastName", "Brown");
        tester.param("place", "Absent");
        tester.param("command", "PROPERTY");
//        System.out.println(person.getKey().getId());
        tester.param("id", person.getKey().getId());
        tester.start("/person/update");

        UpdateController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
    
    /**
     * 属性の更新のエラーケース<br/>
     * <br/>
     * 合格条件
     * <li>Validationに通り、遷移先画面にnullが指定される</li>
     */
    @Test
    public void errorCaseByCommandProperty() throws Exception {
        tester.param("firstName", "");
        tester.param("lastName", "Brown");
        tester.param("place", "Absent");
        tester.param("command", "PROPERTY");
//        System.out.println(person.getKey().getId());
        tester.param("id", person.getKey().getId());
        tester.start("/person/update");

        UpdateController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
