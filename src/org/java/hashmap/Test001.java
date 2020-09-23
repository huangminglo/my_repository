package org.java.hashmap;

import org.junit.Test;


public class Test001 {


    @Test
    public void test() {
        String a = "a";
        Integer b = new Integer(97);
        /**
         * 实现hashmap思路
         * 1.构造方法
         * 2.put方法
         */
        HashMapJdk7<Object, String> hashMapJdk7 = new HashMapJdk7<>();
        hashMapJdk7.put(a, "jack");
        hashMapJdk7.put(b, "send");
        hashMapJdk7.put("c", "andy");
        hashMapJdk7.put("d", "DDD");
        hashMapJdk7.put("e", "EEE");
        hashMapJdk7.put("f", "FFF");
        hashMapJdk7.put(null, "hashMap null key value");
        System.out.println(hashMapJdk7.size());
        System.out.println(hashMapJdk7.get(null));
        String remove = hashMapJdk7.remove(a);
        System.out.println(remove);
        hashMapJdk7.remove("c");
        System.out.println(hashMapJdk7.size());

//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("key", "value");
//        hashMap.size();
//        hashMap.remove(null);
//        Iterator<Map.Entry<String, String>> iterator = hashMap.entrySet().iterator();
//        iterator.next();

    }


    @Test
    public void method() {
        /**
         *  LinkedList实现hashmap
         *  1.通过计算key的hashcode 如果hashcode不冲突则直接存放至数组中
         *  2.如果hashcode相同，且key值也相同着修改元素值
         *  3.如果hashcode相同(碰撞) 则添加至当前碰撞的linkedlist后面，在将linkedlist添加至数组中
         */
//        MyLinkedListHashMap map = new MyLinkedListHashMap();
//        map.put("a", "123");
//        map.put(97, "123");
//
//        System.out.println(map.get("a"));
//        System.out.println(map.get(97));
//
//        HashSet<String> hashSet = new HashSet<>();
        System.out.println(65 & 7);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(true && false);
        System.out.println(true && true);
        System.out.println(false ^ false);

    }

    @Test
    public void method02() {

        Object[] objects = new Object[10];
        for (int i = 0; i < 8; i++) {
            objects[i] = i + "value";
        }
        Object[] objects1 = new Object[15];
        for (int i = 0; i < objects.length; i++) {
            objects1[i] = objects[i];
        }

        objects = objects1;
        System.out.println(objects.length);
        for (Object s : objects) {
            System.out.println(s);
        }
    }

}
