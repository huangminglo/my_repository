package org.java.hashmap;

public interface MyMap<K, V> {

    /**
     * 用于获取map的长度
     *
     * @return
     */
    int size();

    /**
     * 通过key获得map值
     *
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 先map里面添加value
     *
     * @param key
     * @param value
     * @return
     */
    V put(K key, V value);


    /**
     * 用于存储map里面key,value对象
     *
     * @param <K>
     * @param <V>
     */
    interface Entry<K, V> {

        /**
         * 获得key
         *
         * @return
         */
        K getKey();

        /**
         * 获得value
         *
         * @return
         */
        V getValue();

        /**
         * 设置value
         *
         * @param value
         * @return
         */
        V setValue(V value);
    }
}
