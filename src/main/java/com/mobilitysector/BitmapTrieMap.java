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


    public Object put(Object key, Object value)  {
        return insert(root, key, value, 0);
    }

    protected Object insert( Node node, Object key, Object value, int level) {
        if(node instanceof InnerNode){
            InnerNode innerNode = (InnerNode) node;
            int indexNew = calculateIndex(key.hashCode(), level);
            int posNew = getPositionForIndex(indexNew, innerNode.bitmap);
            if(innerNode.children != null && (innerNode.children[posNew] instanceof InnerNode )){
                return insert(innerNode.children[posNew], key, value, level + 1);
            }

            //Enable the bits in the bitmap
            innerNode.bitmap |= (1 << indexNew);
            int prevLen = innerNode.children != null ? innerNode.children.length : 0;
            Node[] newChildren = new Node[prevLen + 1];
            if(posNew != 0 && innerNode.children != null) {
                System.arraycopy(innerNode.children, 0, newChildren, 0, posNew - 1);
            }
            newChildren[posNew] = new ValueNode(innerNode, key, value);
            if(posNew == 0 || innerNode.children == null) {
                if(innerNode.children != null) {
                    System.arraycopy(innerNode.children, posNew+1, newChildren, posNew+1, innerNode.children.length-1);
                }
            } else {
                int newlen =  innerNode.children.length - posNew - 1;
                System.arraycopy(innerNode.children, posNew+1, newChildren, posNew+1, newlen);
            }

            logger.log(Level.INFO, "Inner bitmap at level is: {0}", Integer.toBinaryString( innerNode.bitmap));
            innerNode.children = newChildren;
            return key;
        }
        return null;
    }

    protected int calculateIndex(int key, int level) {
        return (key >>> (level * partitionSize)) & mask;
    }
    protected int getPositionForIndex(int index, int bitmap) {
        int bits = ((1 << index) & bitmap) | bitmap;
        return Integer.bitCount(bits);
    }

    protected Node getRoot() {
        return root;
    }

    protected int calculateMask(int partitionSize){
        return IntStream.range(0, partitionSize).reduce(0, (acc, val) -> acc + (1<<val));
    }

}
