package com.mobilitysector;

import org.junit.Test;
import static org.junit.Assert.*;

public class BitmapTrieMapTest {

    @Test
    public void testPostForIndex() {
        BitmapTrieMap m = new BitmapTrieMap();
        assertEquals(0, m.getPositionForIndex(0, 0));
        assertEquals(3, m.getPositionForIndex(5, 19));
    }
    @Test
    public void testCalculateIndex() {
        BitmapTrieMap m = new BitmapTrieMap();
        assertEquals(0, m.calculateIndex(0, 0));
        assertEquals(1, m.calculateIndex(1, 0));
        assertEquals(15, m.calculateIndex(15, 0));
        assertEquals(0, m.calculateIndex(15, 1));
        assertEquals(0, m.calculateIndex(16, 0));
        assertEquals(1, m.calculateIndex(16, 1));
    }

    @Test
    public void testSimple(){
        BitmapTrieMap m = new BitmapTrieMap();
        m.put(1, "1");
        BitmapTrieMap.InnerNode root = (BitmapTrieMap.InnerNode) m.getRoot();
        assertEquals(1, root.children.length);
    }
}
