package org.java.hashmap;

import java.util.ArrayList;

public class MyArrayListHashMap<K, V> implements MyMap<K, V> {

    /**
     * 用于存储map中存放的元素
     */
    ArrayList<Entry<K, V>> myList = new ArrayList<>();

    @Override
    public int size() {
        return myList.size();
    }

    @Override
    public V get(K key) {
        //return myList.stream().filter(v -> v.getKey().equals(key.toString())).findAny().orElse(null).getValue();
        return null;
    }

    @Override
    public V put(K key, V value) {
        //根据key,value创建一个entry对象,并且存放到集合当中
        MyEntry<K, V> kvMyEntry = new MyEntry<>(key, value);
        myList.add(kvMyEntry);
        return value;
    }

    /**
     * 具体实现类实现的MyEntry对象
     *
     * @param <K>
     * @param <V>
     */
    static class MyEntry<K, V> implements Entry<K, V> {

        public MyEntry() {

        }

        public MyEntry(K k, V v) {
            this.k = k;
            this.v = v;
        }

        /**
         * entry对象中具体的key
         */
        private K k;

        /**
         * entry对象中具体的value
         */
        private V v;


        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        @Override
        public V setValue(V value) {
            this.v = value;
            return null;
        }
    }


}
