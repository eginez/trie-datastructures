package com.mobilitysector;

import org.junit.Test;
import static org.junit.Assert.*;

public class BitmapTrieMapTest {

    @Test
    public void testPostForIndex() {
        BitmapTrieMap m = new BitmapTrieMap();
        assertEquals(0, m.getPositionForIndex(0, 0));
        assertEquals(0, m.getPositionForIndex(1, 2));
        assertEquals(3, m.getPositionForIndex(5, 19));
        assertEquals(3, m.getPositionForIndex(5, 51));
        assertEquals(1, m.getPositionForIndex(1, 1));
        assertEquals(1, m.getPositionForIndex(1, 1));
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
    public void testMask() {
        BitmapTrieMap m = new BitmapTrieMap();
        assertEquals(0, m.calculateMask(0));
        assertEquals(1, m.calculateMask(1));
        assertEquals(3, m.calculateMask(2));
        assertEquals(7, m.calculateMask(3));
        assertEquals(15, m.calculateMask(4));
    }


    @Test
    public void testSimple(){
        BitmapTrieMap m = new BitmapTrieMap();
        BitmapTrieMap.InnerNode root = (BitmapTrieMap.InnerNode) m.getRoot();
        m.put(1, "1");
        assertEquals(1, root.children.length);
        assertEquals(2, root.bitmap);
        assertEquals("1", m.get(1));
        m.put(3, "3");
        assertEquals(2, root.children.length);
        assertEquals("3", m.get(3));
        m.put(2, "2");
        assertEquals(3, root.children.length);
        assertEquals("2", m.get(2));
        m.put(0, "0");
        assertEquals(4, root.children.length);
        assertEquals("0", m.get(0));
    }

    @Test
    public void testSimple2() {
        BitmapTrieMap m = new BitmapTrieMap();
        BitmapTrieMap.InnerNode root = (BitmapTrieMap.InnerNode) m.getRoot();

        m.put(1, "1");
        assertEquals(1, root.children.length);
        assertEquals("1", m.get(1));

        m.put(17, "17");
        assertEquals(1, root.children.length);
        assertEquals("17", m.get(17));
        assertEquals("1", m.get(1));
    }


    @Test
    public void insertSimple3() {
        Object key = new Object();
        BitmapTrieMap m = new BitmapTrieMap();
        m.put(key, "dos");
        assertEquals("dos", m.get(key));
    }
}
