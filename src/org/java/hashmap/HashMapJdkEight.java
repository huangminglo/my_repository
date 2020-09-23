package org.java.hashmap;

/**
 * jdk8实现hashMap
 *
 * @param <K> 添加的key
 * @param <V> 添加的value
 */
public class HashMapJdkEight<K, V> implements MyMap<K, V> {


    //hashMap默认的初始化容量 16
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    //hashMap最大容量
    static final int MAXIMUM_CAPACITY = 1 << 30;

    //默认的负载因子
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    transient Node<K,V> tabls = null;

    //负载因子
    final float loadFactor;

    //记录数组的长度
    int size;

    //表示hashMap修改的次数
    transient int modCount;

    //阈值, 一旦数组的长度超过了这个阈值就会进行扩容操作
    int threshold;

    //转为红黑树阈值,当链表长度大于8为的时候转换为红黑树
    static final int TREEIFY_THRESHOLD = 8;

    //转为链表阈值,当红黑树节点小于6时转换为链表
    static final int UNTREEIFY_THRESHOLD = 6;





    public HashMapJdkEight() {
        //设置默认的负载因子
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }


    /**
     * 具体存放每一个元素的node节点
     *
     * @param <K> key
     * @param <V> value
     */
    static class Node<K, V> implements Entry<K, V> {

        private K key;
        private V value;
        private int hash;
        private Node<K, V> next;

        public Node(K key, V value, int hash, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
    }

    /**
     * 计算key的hash值
     * @param key
     * @return
     */
    public int hash(K key) {
        int h;
        //判断key是否为空,为空hash值为0,否则计算hash值
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }




    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        putVal(hash(key),key,value,false,true);

        return null;
    }

    /**
     *  hashMap新增元素
     * @param hash key的hash值
     * @param key 键
     * @param value 值
     * @param onlyIfAbsent
     * @param evict
     */
    public void putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict){






    }


}
