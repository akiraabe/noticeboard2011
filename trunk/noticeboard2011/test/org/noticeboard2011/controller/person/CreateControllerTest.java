package org.noticeboard2011.controller.person;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import org.noticeboard2011.controller.person.CreateController;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class CreateControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/person/create");
        CreateController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/person/create.jsp"));
    }
}