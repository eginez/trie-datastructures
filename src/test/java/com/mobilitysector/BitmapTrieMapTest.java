package com.mobilitysector;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static java.util.stream.IntStream.of;
import static org.junit.Assert.*;

import static java.util.stream.IntStream.range;

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

    @Test
    @Ignore
    public void testAlot() {
        BitmapTrieMap m = new BitmapTrieMap();
        range(1, 1000).forEach(i -> m.put(i, i));
        assertEquals(999, m.get(999));
    }

    @Test
    @Ignore
    public void testSame() {
        BitmapTrieMap m = new BitmapTrieMap();
        range(1, 1000).forEach(i -> m.put(1, i));
        assertEquals(999, m.get(1));
    }


    @Test
    public void testRandom() {
        Random r = new Random();
        BitmapTrieMap m = new BitmapTrieMap();
        int[] keys = new int[1000];
        for (int i = 0; i < 1000 ; i++) {
            keys[i]  = r.nextInt(10000);
            m.put(keys[i], keys[i]);
        }

        Arrays.stream(keys).forEach(i -> assertEquals(i, m.get(i)));



    }

}
