package com.mobilitysector;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.logging.Logger;

public class DigitTrieTest {
    Logger logger  = Logger.getLogger(DigitTrieTest.class.getName());

    @Test
    public void testInsert_1() {
        DigitTrie d = new DigitTrie();
        d.insert(2);
        d.insert(32);
        d.insert(4302);
        assertTrue(d.exists(2));
        assertTrue(d.exists(32));
        assertTrue(d.exists(4302));

        assertFalse(d.exists(1));
        assertFalse(d.exists(4301));
    }
}
