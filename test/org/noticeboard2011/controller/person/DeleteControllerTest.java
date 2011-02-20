package org.noticeboard2011.controller.person;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.slim3.tester.ControllerTestCase;

/**
 * DeleteControllerのテスト
 * @author akiraabe
 *
 */
public class DeleteControllerTest extends ControllerTestCase {

    /**
     * ノーマルケース<br/>
     * <br/>
     * 合格条件
     * <li>画面遷移はnullであること</li>
     * <li>responseのContentTypeのアサートに成功すること</li>
     * @throws Exception
     */
    @Test
    public void run() throws Exception {
        tester.start("/person/delete");
        DeleteController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
        
        HttpServletResponse response = tester.response;
        assertThat(response.getContentType(),is("application/json; charset=UTF-8"));
    }
}
