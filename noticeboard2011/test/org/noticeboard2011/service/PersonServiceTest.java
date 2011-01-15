package org.noticeboard2011.service;

import org.slim3.tester.AppEngineTestCase;
import org.junit.Test;
import org.noticeboard2011.service.PersonService;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PersonServiceTest extends AppEngineTestCase {

    private PersonService service = new PersonService();

    @Test
    public void test() throws Exception {
        assertThat(service, is(notNullValue()));
    }
}
