package com.mobilitysector;

import java.util.Stack;

public class DigitTrie {

    class Node<T> {
        Node<T>[] children;

        public  Node(int size) {
            this.children = new Node[size];
        }
    }
    private static final int BASE = 5;
    private Node<Integer> root;

    public DigitTrie() {
        root = new Node<>(BASE);
    }

    public void insert(int element){
        Integer[] digits = convertToKeys(element);
        Node<Integer> node = root;

        for(Integer digit : digits) {
            if(node.children[digit] == null) {
                node.children[digit]  =  new Node(BASE);
            }
            node = node.children[digit];
        }
    }

    public boolean exists(int value) {
        Integer[] digits = convertToKeys(value);
        Node<Integer> node = root;

        for(Integer digit : digits) {
            if(node.children[digit] == null) {
                return false;
            }
            node = node.children[digit];
        }
        return (node != null);
    }

    protected Integer[] convertToKeys(int value){
        Stack<Integer> keys = new Stack<>();
        int num = value;
        while( num >= BASE) {
           keys.push(num % BASE);
            num = num/BASE;
        }
        keys.push(num);
        Integer[]res = new Integer[keys.size()];
        keys.toArray(res);
        return res;
    }
}
