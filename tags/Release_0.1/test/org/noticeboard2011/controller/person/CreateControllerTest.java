package org.noticeboard2011.controller.person;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slim3.tester.ControllerTestCase;

/**
 * CreateControllerのテスト
 * 
 * @author akiraabe
 *
 */
public class CreateControllerTest extends ControllerTestCase {

    /**
     * ノーマルケース<br/>
     * <br/>
     * 合格条件
     * <li>入力画面へ遷移すること</li>
     * @throws Exception
     */
    @Test
    public void run() throws Exception {
        tester.start("/person/create");
        CreateController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/person/create.jsp"));
    }
}
