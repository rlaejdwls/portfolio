package com.example.hellomvpworld.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Hwang on 2018-01-05.
 *
 * Description :
 */
public class StringUtilUnitTest {
    private StringUtil util;

    @Before
    public void setup() {
        util = new StringUtil();
    }

    @Test
    public void toArray() {
        String[] result = util.toArray("d1,d2,d3", ",");
        Assert.assertEquals(3, result.length);
    }
}
