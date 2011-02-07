package org.noticeboard2011.controller.person;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slim3.tester.ControllerTestCase;

/**
 * InsertControllerのテスト
 * 
 * @author akiraabe
 *
 */
public class InsertControllerTest extends ControllerTestCase {

    /**
     * 正常ケースのテスト<br/>
     * <br/>
     * 合格条件
     * <li>Validationに通り、結果画面に遷移</li>
     */
    @Test
    public void normalCase() throws Exception {
        tester.param("firstName", "James");
        tester.param("lastName", "Brown");
        tester.param("place", "Absent");
        tester.start("/person/insert");
        InsertController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(true));
        assertThat(tester.getDestinationPath(), is("/"));
    }
    
    /**
     * エラーケース<br/>
     * <br/>
     * 合格条件
     * <li>Validationでエラーとなり、入力画面に戻る</li>
     * @throws Exception
     */
    @Test
    public void errorCase() throws Exception {
        tester.param("firstName", ""); // 空文字
        tester.param("lastName", "Brown");
        tester.param("place", "Absent");
        tester.start("/person/insert");
        InsertController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/person/create.jsp"));
    }
}
