package org.noticeboard2011.controller.cron;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TaskQueueSubControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/cron/taskQueueSub");
        TaskQueueSubController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
