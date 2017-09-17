package info.knigoed.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SearchSphinxParamTest {

    @Test
    public void testQuery1() {
        assertEquals("и", new SearchParam("и", "", null, 0, 0).getKey());
    }

    @Test
    public void testQuery2() {
        assertEquals("и", new SearchParam("\"и\"", "", null,0, 0).getKey());
    }

    @Test
    public void testQuery3() {
        assertEquals("и", new SearchParam("(и)", "", null,0, 0).getKey());
    }
}