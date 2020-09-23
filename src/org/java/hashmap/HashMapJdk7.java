package org.java.hashmap;


/**
 * jdk1.7 实现hashMap
 *
 * @param <K> key
 * @param <V> value
 */
public class HashMapJdk7<K, V> implements MyMap<K, V> {


    /**
     * hashMap默认的初始化容量 16
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    /**
     * hashMap默认的负载因子 0.75f
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;


    /**
     * 阈值,用来做hashmap扩容
     * 扩容机制:调整下一次大小值(容量*负载因子)
     */
    int threshold;


    /**
     * hashMap的最大初始化容量1073741824
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;


    /**
     * 负载因子
     */
    final float loadFactor;


    /**
     * hash种子
     */
    int hashSeed;


    /**
     * HashMap中存放的键值对的个数
     */
    transient int size;


    /**
     * hashmap中存放元素的数组
     */
    transient Entry<K, V>[] tables = null;


    public HashMapJdk7() {
        //初始化hashmap, 如果未定义初始化容量和负载因子,则使用默认值
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * hashmap初始化构造方法
     *
     * @param initialCapacity 初始化容量
     * @param loadFactor      负载因子
     */
    public HashMapJdk7(int initialCapacity, float loadFactor) {
        //判断如果初始化容量小于0则抛出异常,效验数据
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        //判断初始化容量是否大于最大初始化容量,如果大于则设置最大初始化容量
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        //效验数据 负载因子
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        //设置负载因子
        this.loadFactor = loadFactor;
        //设置初始化容量阈值
        threshold = initialCapacity;
        init();
    }

    //空方法用作用户子类重写
    protected void init() {
    }


    /**
     * 实现Map接口中的size方法
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 实现Map接口中的Get方法
     *
     * @param key
     * @return
     */
    public V get(K key) {
        //如果key为空则走查找nullkey的value
        if (key == null)
            return getForNullKey();
        //根据key取得Entry对象,并返回Entry对象value
        Entry<K, V> entry = getEntry(key);
        return null == entry ? null : entry.getValue();
    }

    final Entry<K, V> getEntry(Object key) {
        if (size == 0)
            return null;
        //根据key取得hash值
        int hash = hash(key);
        //根据hash值和数组的长度计算出 元素存放的桶索引位置
        int index = indexFor(hash, tables.length);
        //根据计算出的桶索引循环遍历tables, (1.7通过链表需要一个个遍历如果链表过长查询效率会降低 复杂度O(n))
        for (Entry<K, V> e = tables[index]; e != null; e = e.next) {
            Object k;
            //如果hash值相同 key也相同则返回Entry对象
            if (e.hash == hash && ((k = e.getKey()) == key || key.equals(k))) {
                return e;
            }
        }
        return null;
    }


    private V getForNullKey() {
        //如果size为0表示没有存储数据,直接返回null
        if (size == 0)
            return null;
        //由于nullkey 存放在数组的0下标位置,循环遍历tables[0]
        for (Entry<K, V> e = tables[0]; e != null; e = e.next) {
            //key为空则返回value
            if (e.getKey() == null) {
                return e.getValue();
            }
        }
        return null;
    }


    /**
     * 实现Map接口中的put方法
     *
     * @param key   添加的键
     * @param value 添加的值
     * @return
     */
    public V put(K key, V value) {
        //判断该数组是否为空,如果为空则初始化该数组
        if (tables == null) {
            inflateTable(threshold);
        }
        //如果传入的key为null
        if (key == null) {
            return putForNullKey(value);
        }
        //根据key来计算出hash值
        int hash = hash(key);
        //通过hash值和数组的长度计算出存放在数组中的下标位置
        int index = indexFor(hash, tables.length);
        //通过index下标在数组中查出是否存在相同的元素
        for (Entry<K, V> e = tables[index]; e != null; e = e.next) {
            //判断元素key的hashcode是否相同, e.getKey() == key用于判断基本元素的键是否相同
            //e.getKey().equals(key) 判断引用类型元素是否相同,如果相同这表示该key是同一个,需要覆盖元素值
            Object k;
            //优先将e.key的值赋给k判断基本类型
            if (e.hashCode() == hash && ((k = e.getKey()) == key || key.equals(k))) {
                V oldValue = e.getValue();
                e.setValue(value);
                return oldValue;
            }
        }
        addEntry(hash, key, value, index);
        return null;
    }

    /**
     * 用于新增为空的键
     *
     * @param value
     * @return
     */
    private V putForNullKey(V value) {
        //判断之前的数组中是否存有null的key
        for (Entry<K, V> e = tables[0]; e != null; e = e.next) {
            //如果存在并且键为null 则进行修改value操作
            if (e.getKey() == null) {
                V oldValue = e.getValue();
                e.setValue(value);
                return oldValue;
            }
        }
        //将null键添加至Entry对象中 hashcode为0,桶索引为0 （null key一般添加在数组中的第一个或者第一个链表中的）
        addEntry(0, null, value, 0);
        return null;
    }

    /**
     * 新增Entry
     *
     * @param hash        key对应的hashcode
     * @param key         key键
     * @param value       value值
     * @param bucketIndex 存放在数组中的下标(桶索引)
     */
    void addEntry(int hash, K key, V value, int bucketIndex) {
        //如果size大于等于阈值,并且通过桶索引取得tables元素不为空 则需要扩容
        if ((size >= threshold) && (null != tables[bucketIndex])) {
            //每次扩容2倍
            resize(2 * tables.length);
            //扩容后bucketIndex下标的位置可能会发生改变,需要重新计算hash,和bucketIndex索引
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, tables.length);
        }
        createEntry(hash, key, value, bucketIndex);
    }

    //创建Entry对象
    void createEntry(int hash, K key, V value, int bucketIndex) {
        //通过桶索引取得原来的元素
        Entry<K, V> next = tables[bucketIndex];
        //将原来的元素放入到新创建的元素中(next),在将新创建的放入当前桶索引中
        tables[bucketIndex] = new Entry<>(hash, key, value, next);
        //每次新增一个Entry对象size都+1
        size++;
    }

    /**
     * 根据key(键)删除元素
     *
     * @param key
     * @return
     */
    public V remove(Object key) {
        Entry<K, V> e = removeEntryForKey(key);
        return (e == null ? null : e.value);
    }

    //删除hashmap中的元素
    public Entry<K, V> removeEntryForKey(Object key) {
        if (size == 0)
            return null;
        //通过hash,tables.length取得该key存放在数组中的index
        int hash = hash(key);
        int index = indexFor(hash, tables.length);
        Entry<K, V> prev = tables[index];//用户记录被删除元素的上一个节点
        Entry<K, V> e = prev; //被删除元素
        //开始循环
        while (e != null) {
            Entry<K, V> next = e.next; //取得当前删除元素的下一个节点
            Object k;
            //判断该key与hash是否相等
            if (e.hash == hash && ((k = e.getKey()) == key) || key.equals(e.getKey())) {
                size--; //hashmap存储的键值对长度-1
                if (prev == e) { //如果prev==e 表示该元素没有上一个节点,在链表头部
                    tables[index] = next; //通过覆盖来进行删除,将下一个节点的元素覆盖当前index节点元素
                } else {
                    prev.next = next;  //如果不是头节点, 则修改上一个节点的next节点 为e节点的下一个节点元素
                }
                return e;
            }
            //下一次循环
            prev = e;
            e = next;
        }
        return e;
    }


    /**
     * 通过hash值和数组的长度计算出存放在数组中的下标位置
     *
     * @param h      hashcode
     * @param length 数组的长度
     * @return
     */
    static int indexFor(int h, int length) {
        // assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
        return h & (length - 1);
    }

    /**
     * 通过传入的key计算出hashcode
     *
     * @param k key
     * @return
     */
    final int hash(Object k) {
        int h = 0;
        if (0 != h && k instanceof String) {
            return 0;
            //return sun.misc.Hashing.stringHash32((String) k);
        }

        h ^= k.hashCode();
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * hashmap扩容
     *
     * @param newCapacity 新的数组容量
     */
    void resize(int newCapacity) {
        //先取到旧的数组容量长度
        Entry<K, V>[] oldTables = tables;
        int oldCapacity = tables.length;
        //判断旧的数组容量是否大于hashmap最大初始化容量,如果大于则将阈值设置为Integer.MAX_VALUE
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        //创建一个新的长度为newCapacity的链表数组
        Entry<K, V>[] newTable = new Entry[newCapacity];
        //将原数组中的元素迁移到扩容后的数组中
        transfer(newTable, initHashSeedAsNeeded(newCapacity));
        //将新的Table赋值到tables中
        tables = newTable;
        //重新计算阈值
        threshold = (int) Math.min(newCapacity * loadFactor, MAXIMUM_CAPACITY + 1);
    }

    /**
     * 将旧数组里面的值移值 newTable中
     *
     * @param newTable 新的Entry数组
     * @param rehash   是否需要重新计算hash值
     */
    void transfer(Entry[] newTable, boolean rehash) {
        //取得新数组的容量长度
        int newCapacity = newTable.length;
        //循环oldTable
        for (Entry<K, V> e : tables) {
            //当桶不为空
            while (null != e) {
                Entry<K, V> next = e.next;
                if (rehash) { //通过传入的rehash值判断是否需要重新计算hashcode,主要是index下标发生了改变  一般不会重新计算
                    e.hash = null == e.key ? 0 : hash(e.key);
                }
                int i = indexFor(e.hash, newCapacity);
                //使用链表头插入法,将后建立的元素插在头部 (与之前实际建立的链表顺序相反)
                e.next = newTable[i];
                newTable[i] = e;
                //下一轮循环
                e = next;
            }
        }
    }


    /**
     * 判断是否需要重新计算hash
     *
     * @param capacity 数组的容量
     * @return
     */
    final boolean initHashSeedAsNeeded(int capacity) {
        // hashSeed默认为0    所以currentAltHashing为false
        boolean currentAltHashing = hashSeed != 0;
        // sun.misc.VM.isBooted()启动时赋值为true 所以主要关注capacity是否大于Integer.MAX_VALUE   useAltHashing该值一般为false
        boolean useAltHashing = sun.misc.VM.isBooted() &&
                (capacity >= Integer.MAX_VALUE);
        boolean switching = currentAltHashing ^ useAltHashing; //false ^ false = false
        //通过switching判断useAltHashing是否为true hashSeed是否需要重新rehash 一般不会
        if (switching) {
//            hashSeed = useAltHashing
//                    ? sun.misc.Hashing.randomHashSeed(this)
//                    : 0;
            hashSeed = 0;
        }
        return switching; //该值一般返回false
    }


    /**
     * 第一次数组为空初始化数组
     *
     * @param toSize
     */
    private void inflateTable(int toSize) {
        //取得数组实际容量
        int capacity = roundUpToPowerOf2(toSize);
        //通过数组容量来计算数组扩容的阈值, 传入数组容量*负载因子
        //在Math.min方法中 与数组初始化最大容量+1比较 取得最小的值
        threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
        //init tables
        tables = new Entry[capacity];
    }


    private static int roundUpToPowerOf2(int number) {
        // assert number >= 0 : "number must be non-negative";
        return number >= MAXIMUM_CAPACITY
                ? MAXIMUM_CAPACITY
                : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
    }


    static class Entry<K, V> implements MyMap.Entry<K, V> {

        /**
         * 存放每个Entry对象key的具体hash值
         */
        private int hash;

        /**
         * 存放的Entry对象具体key
         */
        private K key;

        /**
         * 存放Entry对象具体的value
         */
        private V value;

        /**
         * 存放下个一个Entry对象
         */
        private Entry<K, V> next;


        public Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
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
            V oldValue = newValue;
            value = newValue;
            return oldValue;
        }
    }

}
