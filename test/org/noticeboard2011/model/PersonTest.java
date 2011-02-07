package org.noticeboard2011.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slim3.tester.AppEngineTestCase;

/**
 * Personモデルのテスト
 * 
 * @author akiraabe
 *
 */
public class PersonTest extends AppEngineTestCase {

    private Person model = new Person();

    /**
     * Personモデルのテスト<br/>
     * <br/>
     * 合格条件<br/>
     * <li>セットした値が取り出せること</li>
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
        model.setFirstName("James");
        model.setLastName("Brown");
        model.setPlace("Absent");
        assertThat(model.getFirstName(), is("James"));
        assertThat(model.getLastName(), is("Brown"));
        assertThat(model.getPlace(), is("Absent"));
    }
}
