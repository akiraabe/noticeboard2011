package org.noticeboard2011.controller.person;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import org.noticeboard2011.controller.person.InsertController;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class InsertControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/person/insert");
        InsertController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
