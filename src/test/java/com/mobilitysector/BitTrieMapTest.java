package com.mobilitysector;

import org.junit.Ignore;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class BitTrieMapTest {


    @Test
    public void testSimpleInsert() {
        BitTrieMap map = new BitTrieMap();
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
        BitTrieMap p = new BitTrieMap();
        BitTrieMap p2 = new BitTrieMap();
        Object obj = new Object();
        p.put(obj, "hola");
        assertEquals(false, p.isEmpty());
        assertEquals(true, p2.isEmpty());
        assertEquals(true, p.containsKey(obj));
    }


    @Test
    public void testSameObject() {
        BitTrieMap p = new BitTrieMap();
        Object obj = new Object();
        IntStream.range(1, 1000).forEach(it -> p.put(obj, it));
        assertEquals(999, p.get(obj));

    }
    @Test
    public void testIsEmpty() {
        BitTrieMap p = new BitTrieMap();
        assertEquals(true, p.isEmpty());
        p.put(new Object(), "this is");
        assertEquals(false, p.isEmpty());

    }

    @Test
    public void testInsetLimits(){
        BitTrieMap map = new BitTrieMap();
        map.put(Integer.MAX_VALUE, "test");
        map.put(Integer.MIN_VALUE, "test2");
        assertEquals(map.get(Integer.MAX_VALUE), "test");
        assertEquals(map.get(Integer.MIN_VALUE), "test2");
    }

    @Test
    public void testInsertIntOverflow() {
        BitTrieMap map = new BitTrieMap();
        map.put(Double.MAX_VALUE, "test");
        map.put(Double.MIN_VALUE, "test2");
        assertEquals(map.get(Double.MAX_VALUE), "test");
        assertEquals(map.get(Double.MIN_VALUE), "test2");
    }

    @Test
    public void testDoublePut() {
        BitTrieMap map = new BitTrieMap();
        map.put(Double.MAX_VALUE, "test");
        map.put(Double.MAX_VALUE, "test2");
        assertEquals(map.get(Double.MAX_VALUE), "test2");
    }

    @Test
    public void testObjectPut() {
        BitTrieMap map = new BitTrieMap();
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
        BitTrieMap map = new BitTrieMap();
        IntStream.range(0, 5 * (int)Math.pow(10,6)).forEach(it -> map.put(it, it));
    }
}
