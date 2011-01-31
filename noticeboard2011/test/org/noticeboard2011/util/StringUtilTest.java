package org.noticeboard2011.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.slim3.tester.AppEngineTestCase;

public class StringUtilTest extends AppEngineTestCase {
    
    @Test
    public void test() throws Exception {
        String val1 = StringUtil.htmlEscape("&<script />");
        assertThat(val1, is("&amp;&lt;script /&gt;"));
    }

}
