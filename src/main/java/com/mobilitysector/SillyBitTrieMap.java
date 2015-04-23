package com.mobilitysector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Silly since it uses empty space for null keys
 */
public class SillyBitTrieMap implements Map<Object, Object> {

    /**
     * The size in bits of the key
     */
    public static final int KEY_BIT_SIZE = 32;
    public static final int DEFAULT_PARTITION_SIZE = 4;


    Object[] root;
    private int mask;
    /**
     * The number of bits for the partition
     */
    private int partitionSize;
    /**
     * The number of children any level will have, more is better
     */
    private int branchingFactor;
    /**
     * The number of levels the tree will have, less is better
     */
    private int depth;
    /**
     * number of elements
     */
    private int size = 0;


    public SillyBitTrieMap(){
        this(DEFAULT_PARTITION_SIZE);
    }

    public SillyBitTrieMap(int partitionSize) {
        this.partitionSize = partitionSize;
        this.branchingFactor = 1 << this.partitionSize;
        this.depth = KEY_BIT_SIZE / partitionSize;
        mask = calculateMask(partitionSize);
        root = new Object[branchingFactor];
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
        return size;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty(root);
    }

    private boolean isEmpty(Object node) {
        boolean res = true;
        if( node instanceof Object[]) {
            Object[] parent = (Object[]) node;
            for (Object o : parent) {
                res = res && isEmpty(o);
            }
            return res;
        }
        return node == null;
    }

    protected Object getLeafNode(Object key){
        int numberKey = key.hashCode();
        int level = 0;
        Object[] node = root;
        int index = 0;
        while(level < depth ) {
            index = numberKey & mask;
            if(node[index] == null) {
                return null;
            }
            node = (Object[])node[index];
            numberKey = numberKey >>> partitionSize;
            level++;
        }
        return node[index];
    }

    @Override
    public boolean containsKey(Object key) {
        Object node = getLeafNode(key);
        return node != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return getLeafNode(key);
    }

    @Override
    public Object put(Object key, Object value) {
        int numberKey = key.hashCode();
        int level = 0;
        Object[] node = root;
        int index = 0;
        while(level < depth  ) {
            index = numberKey & mask;
            if(node[index] == null) {
                node[index] = new Object[branchingFactor];
            }
            node = (Object[])node[index];
            numberKey = numberKey >> partitionSize;
            level++;
        }
        node[index] = value;
        size++;
        return node[index];
    }

    @Override
    public Object remove(Object key) {
        int numberKey = key.hashCode();
        int level = 0;
        Object[] node = root;
        int index = 0;
        while(level < depth  ) {
            index = numberKey & mask;
            if(node[index] == null) {
                return null;
            }
            node = (Object[])node[index];
            numberKey = numberKey >> partitionSize;
            level++;
        }
        Object value =  node[index];
        size--;
        node[index] = null;
        return value;
    }

    @Override
    public void putAll(Map<?, ?> m) {
        m.forEach(this::put);
    }


    @Override
    public void clear() {
        root = new Object[branchingFactor];
        size = 0;
    }

    @Override
    public Set<Object> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Object> values() {
        return values(root, 0);
    }

    private Collection<Object> values (Object node, int level) {
        Object[] newRoot = (Object[])node;
        if(node == null) {
            return Collections.emptyList();
        }
        List<Object> allValues = new ArrayList<>();
        if(level == depth - 1) {
            allValues.add(Arrays.stream(newRoot).filter(it -> it != null).collect(Collectors.toList()));
        } else {
            level++;
           for(Object obj : newRoot){
              allValues.addAll(values(obj, level));
           }
        }
        return allValues;
    }

    @Override
    public Set<Entry<Object, Object>> entrySet() {
        throw new UnsupportedOperationException();
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BitTrieMap{");
        sb.append('}');
        return sb.toString();
    }
}
