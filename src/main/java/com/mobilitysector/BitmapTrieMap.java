package com.mobilitysector;


import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class BitmapTrieMap {
    private  final Node EMPTY_NODE = new InnerNode(null);
    private static final Logger logger = Logger.getLogger(BitmapTrieMap.class.getName());
    private static final int KEY_BIT_SIZE = 32 ;
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
    private int size;

    /**
     * The root
     */
    Node root;

    interface Node{}
    class ValueNode implements Node {
        Object key;
        Object value;
        Node parent;

        public ValueNode(Node parent, Object key, Object value) {
            this.parent = parent;
            this.key = key;
            this.value = value;
        }
    }

    class InnerNode implements Node{
        int bitmap;
        Node[] children;
        Node parent;

        @Override
        public String toString() {
            return "InnerNode{" +
                    "parent=" + parent +
                    ", children=" + Arrays.toString(children) +
                    ", bitmap=" + Integer.toBinaryString(bitmap) +
                    '}';
        }

        public InnerNode(Node parent){
            bitmap = 0;
            children = null;
            this.parent = parent;
        }


    }

    public BitmapTrieMap() {
        this(4);
    }

    public BitmapTrieMap(int partitionSize) {
        this.partitionSize = partitionSize;
        this.branchingFactor = 1 << this.partitionSize;
        this.depth = KEY_BIT_SIZE / partitionSize;
        this.mask = calculateMask(partitionSize);
        this.root = EMPTY_NODE;
    }


    public Object get(Object key) {
        return get(root, key, 0);
    }

    public Object get(Node node, Object key, int level) {
        if(node instanceof ValueNode) {
            ValueNode valueNode = (ValueNode)node;
            return valueNode.value;
        }

        if(node instanceof InnerNode) {
            InnerNode innerNode = (InnerNode) node;
            int indexNew = calculateIndex(key.hashCode(), level);
            int posNew = getPositionForIndex(indexNew, innerNode.bitmap);
            return get(innerNode.children[posNew], key, level + 1);
        }

        return null;
    }


    public Object put(Object key, Object value)  {
        return insert(root, key, value, 0);
    }

    protected Object insert( Node node, Object key, Object value, int level) {
        if(node instanceof InnerNode){
            InnerNode parentNode = (InnerNode) node;
            int indexNew = calculateIndex(key.hashCode(), level);
            int posNew = getPositionForIndex(indexNew, parentNode.bitmap);
            //Is the node already occupied
            if(((1<<indexNew) & parentNode.bitmap) >= 1){
                //Push both nodes down a level
                ValueNode vNode = (ValueNode)parentNode.children[posNew];
                parentNode.children[posNew] = new InnerNode(parentNode);
                insert(parentNode.children[posNew], vNode.key, vNode.value, level + 1);
                return insert(parentNode.children[posNew], key, value, level + 1);
            }

            //Enable the bits in the bitmap
            parentNode.bitmap |= (1 << indexNew);
            int prevLen = parentNode.children != null ? parentNode.children.length : 0;
            Node[] newChildren = new Node[prevLen + 1];
            if(posNew != 0 && parentNode.children != null) {
                System.arraycopy(parentNode.children, 0, newChildren, 0, Math.max(posNew - 1, 1));
            }
            newChildren[posNew] = new ValueNode(parentNode, key, value);
            if(posNew == 0 || parentNode.children == null) {
                if(parentNode.children != null) {
                    System.arraycopy(parentNode.children, posNew+1, newChildren, posNew+1, parentNode.children.length-1);
                }
            } else {
                System.arraycopy(parentNode.children, 0, newChildren, posNew - 1, parentNode.children.length-1);
            }

            logger.log(Level.INFO, "Inner bitmap at level={0} is= {1}", asArray(level, Integer.toBinaryString(parentNode.bitmap)));
            parentNode.children = newChildren;
            return key;
        }
        return null;
    }


    protected int calculateIndex(int key, int level) {
        return (key >>> (level * partitionSize)) & mask;
    }
    protected int getPositionForIndex(int index, int bitmap) {
        //bug here index = 1, bitmap = 1
        int bits = (calculateMask(index + 1) & bitmap) | (1<<index);
        //int bits = bitmap & (calculateMask(index));
        return Math.max(0, Integer.bitCount(bits) - 1);
    }

    protected Node getRoot() {
        return root;
    }

    protected int calculateMask(int partitionSize){
        //It would be better to shift right all a byte full of 1's
        //eg 11111 >> 2
        return IntStream.range(0, partitionSize).reduce(0, (acc, val) -> acc + (1<<val));
    }

    private Object[] asArray(Object... args){
        return args;
    }

}
