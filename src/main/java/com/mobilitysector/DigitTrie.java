package com.mobilitysector;

import java.util.Stack;

public class DigitTrie {

    private static final int BASE = 5;
    private Object[] root;

    public void insert(int element){
        Integer[] digits = convertToKeys(element);
        Object [] node = root;

        for(Integer digit : digits) {
            if(node[digit] == null) {
                node[digit] = new Object[BASE];
            }
            node = (Object[]) node[digit];
        }
    }

    protected Integer[] convertToKeys(int value){
        Stack<Integer> keys = new Stack<>();
        int num = value;
        while( num/BASE > BASE) {
           keys.push(num % BASE);
            num = num/BASE;
        }
        keys.push(num);
        Integer[]res = new Integer[keys.size()];
        keys.toArray(res);
        return res;
    }




}
