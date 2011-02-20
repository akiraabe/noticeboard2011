package org.noticeboard2011.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slim3.tester.ControllerTestCase;

/**
 * IndexControllerのテスト
 * @author akiraabe
 *
 */
public class IndexControllerTest extends ControllerTestCase {
   
    @Test
    public void run() throws Exception {

        tester.start("/");
        IndexController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/index.jsp"));
    }
}
