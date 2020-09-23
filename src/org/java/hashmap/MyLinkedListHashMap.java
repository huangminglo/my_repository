package org.java.hashmap;

import java.util.LinkedList;

public class MyLinkedListHashMap<K, V> implements MyMap<K, V> {

    LinkedList[] linkedLists = new LinkedList[100]; //用于存放元素

    @Override
    public int size() {
        return linkedLists.length;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        //计算key的hashcode
        int hash = hash(key);
        LinkedList<MyEntry> linkedList = linkedLists[hash];
        for (MyEntry entry : linkedList) {
            if (entry.getKey().equals(key)) {
                return (V) entry.getValue();
            }
        }
        return null;
    }

    /**
     * put方法
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        //通过key拿到hashcode
        int hash = hash(key);
        MyEntry myEntry = new MyEntry(key, value);
        //1.LinkedList数组里不存在该元素
        //通过hashcode下标取到数组对应下标的元素,判断该元素是否存在,不存在则新增
        LinkedList<MyEntry> list = linkedLists[hash];
        if (list == null) {
            //没有发生hash冲突
            list = new LinkedList();
            list.add(myEntry);
            linkedLists[hash] = list;
            return value;
        }
        //2.LinkedList数组里存在该元素key相同 需要修改value值
        for (MyEntry entry : list) {
            //如果key值相同则 hashcode一定相同 修改value
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return value;
            }
        }
        //3.LinkedList数组hashcode相同,key不相同,往尾部追加
        list.add(myEntry);
        linkedLists[hash] = list;
        return value;
    }

    private int hash(K key) {
        //拿到key的hashCode
        int hashCode = key.hashCode();
        //通过hashCode % 数组长度得到存放的下标位置
        int hash = hashCode % linkedLists.length;
        return hash;
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
