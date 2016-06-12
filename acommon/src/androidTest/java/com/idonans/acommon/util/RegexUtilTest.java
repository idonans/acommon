package com.idonans.acommon.util;

import junit.framework.TestCase;

/**
 * Created by idonans on 16-6-12.
 */
public class RegexUtilTest extends TestCase {

    public void testIsPhoneNumber() throws Exception {
        assertFalse(RegexUtil.isPhoneNumber("123"));
        assertTrue(RegexUtil.isPhoneNumber("15201019526"));
        assertFalse(RegexUtil.isPhoneNumber("abc"));
        assertFalse(RegexUtil.isPhoneNumber("11001019526"));
    }

    public void testIsEmail() throws Exception {
        assertTrue(RegexUtil.isEmail("idonans@126.com"));
        assertFalse(RegexUtil.isEmail("abc@1@2.com"));
    }

    public void testIsJsonp() throws Exception {
        assertTrue(RegexUtil.isJsonp("call('123')"));
        assertTrue(RegexUtil.isJsonp("print([1,2,'3']);"));
        assertTrue(RegexUtil.isJsonp("draw ({'id':1, 'name':'idonans'} );"));
        assertFalse(RegexUtil.isJsonp("({123})"));
        assertFalse(RegexUtil.isJsonp("1"));
    }

    public void testJsonp2json() throws Exception {
        String jsonp = "call(1);";
        String json = "1";
        assertEquals("not equals", RegexUtil.jsonp2json(jsonp), json);
    }

}