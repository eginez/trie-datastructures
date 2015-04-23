package com.mobilitysector;

import org.junit.Ignore;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class SillyBitTrieMapTest {


    @Test
    public void testSimpleInsert() {
        SillyBitTrieMap map = new SillyBitTrieMap();
        map.put(0, "hi");
        map.put(24, "hell0");
        map.put(2530, "h2");
        map.put(-1, "gola");
        assertEquals(map.get(0), "hi");
        assertEquals(map.get(24), "hell0");
        assertEquals(map.get(2530), "h2");
        assertEquals(map.get(-1), "gola");
    }

    @Test
    public void testBooleanMethods() {
        SillyBitTrieMap p = new SillyBitTrieMap();
        SillyBitTrieMap p2 = new SillyBitTrieMap();
        Object obj = new Object();
        p.put(obj, "hola");
        assertEquals(false, p.isEmpty());
        assertEquals(true, p2.isEmpty());
        assertEquals(true, p.containsKey(obj));
    }

    @Test
    public void testGetValues() {
        SillyBitTrieMap p = new SillyBitTrieMap();
        p.put(new Object(), "hola");
        p.put(new Object(), "chao");
        assertEquals(2, p.values().size());
    }


    @Test
    public void testSameObject() {
        SillyBitTrieMap p = new SillyBitTrieMap();
        Object obj = new Object();
        IntStream.range(1, 1000).forEach(it -> p.put(obj, it));
        assertEquals(999, p.get(obj));

    }
    @Test
    public void testIsEmpty() {
        SillyBitTrieMap p = new SillyBitTrieMap();
        assertEquals(true, p.isEmpty());
        p.put(new Object(), "this is");
        assertEquals(false, p.isEmpty());

    }

    @Test
    public void testDelete() {
        SillyBitTrieMap map = new SillyBitTrieMap();
        Object obj = new Object();
        map.put(obj, 1);
        assertEquals(1, map.remove(obj));
        assertEquals(true, map.isEmpty());
    }

    @Test
    public void testInsetLimits(){
        SillyBitTrieMap map = new SillyBitTrieMap();
        map.put(Integer.MAX_VALUE, "test");
        map.put(Integer.MIN_VALUE, "test2");
        assertEquals(map.get(Integer.MAX_VALUE), "test");
        assertEquals(map.get(Integer.MIN_VALUE), "test2");
    }

    @Test
    public void testInsertIntOverflow() {
        SillyBitTrieMap map = new SillyBitTrieMap();
        map.put(Double.MAX_VALUE, "test");
        map.put(Double.MIN_VALUE, "test2");
        assertEquals(map.get(Double.MAX_VALUE), "test");
        assertEquals(map.get(Double.MIN_VALUE), "test2");
    }

    @Test
    public void testDoublePut() {
        SillyBitTrieMap map = new SillyBitTrieMap();
        map.put(Double.MAX_VALUE, "test");
        map.put(Double.MAX_VALUE, "test2");
        assertEquals(map.get(Double.MAX_VALUE), "test2");
    }

    @Test
    public void testObjectPut() {
        SillyBitTrieMap map = new SillyBitTrieMap();
        IntStream.range(0, 100).forEach(it -> map.put(String.format("key%d", it), it));
        assertEquals(map.get("key2"), 2);
    }

    /**
     * The current data structure works well for less than 10e6,
     * after that it start to choke :/
     */
    @Test
    @Ignore
    public void testInsertLotsOfObjects() {
        SillyBitTrieMap map = new SillyBitTrieMap();
        IntStream.range(0, 5 * (int)Math.pow(10,6)).forEach(it -> map.put(it, it));
    }
}
