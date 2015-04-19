package com.mobilitysector;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class BitTrieMap implements Map<Object, Object> {

    /**
     * The size in bits of the key
     */
    public static final int KEY_BIT_SIZE = 32;
    /**
     * The number of bits for the partition
     */
    public static final int BIT_PARTITION_SIZE = 4;
    /**
     * The number of children any level will have, more is better
     */
    public static final int BRANCHING_FACTOR = 1 << BIT_PARTITION_SIZE;
    /**
     * The number of levels the tree will have, less is better
     */
    public static final int DEPTH_OF_TREE = KEY_BIT_SIZE / BIT_PARTITION_SIZE;


    Object[] root;
    private int MASK;

    public BitTrieMap(){
        MASK = calculateMask(BIT_PARTITION_SIZE);
        root = new Object[BRANCHING_FACTOR];
    }

    /**
     * Mask has all the bits enabled for the width of the partition size
     * eg: partition size: 4 bits then mask -> 1111
     * @param partitionSize the size of the bit partition
     * @return the mask
     */
    private int calculateMask(int partitionSize){
        return IntStream.range(0, partitionSize).reduce(0, (acc, val) -> acc + (1<<val));
    }
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        int numberKey = key.hashCode();
        int level = 0;
        Object[] node = root;
        int index = 0;
        while(level < DEPTH_OF_TREE ) {
            index = numberKey & MASK;
            if(node[index] == null) {
                return false;
            }
            node = (Object[])node[index];
            numberKey = numberKey >>> BIT_PARTITION_SIZE;
            level++;
        }
        return node[index] != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        int numberKey = key.hashCode();
        int level = 0;
        Object[] node = root;
        int index = 0;
        while(level < DEPTH_OF_TREE ) {
            index = numberKey & MASK;
            if(node[index] == null) {
                return null;
            }
            node = (Object[])node[index];
            numberKey = numberKey >> BIT_PARTITION_SIZE;
            level++;
        }
        return node[index];

    }

    @Override
    public Object put(Object key, Object value) {
        int numberKey = key.hashCode();
        int level = 0;
        Object[] node = root;
        int index = 0;
        while(level < DEPTH_OF_TREE  ) {
            index = numberKey & MASK;
            if(node[index] == null) {
                node[index] = new Object[BRANCHING_FACTOR];
            }
            node = (Object[])node[index];
            numberKey = numberKey >> BIT_PARTITION_SIZE;
            level++;
        }
        node[index] = value;
        return node[index];
    }

    @Override
    public String remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {
        root = new Object[BRANCHING_FACTOR];
    }

    @Override
    public Set<Object> keySet() {
        return null;
    }

    @Override
    public Collection<Object> values() {
        return null;
    }

    @Override
    public Set<Entry<Object, Object>> entrySet() {
        return null;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BitTrieMap{");
        sb.append('}');
        return sb.toString();
    }
}
