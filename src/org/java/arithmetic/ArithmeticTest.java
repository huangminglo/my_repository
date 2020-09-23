package org.java.arithmetic;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author huangming
 * @date 2020/9/6 16:44
 */
public class ArithmeticTest {

    public static void main(String[] args) {
        int[] ints = new int[100];
        for (int i = 0; i < 100; i++) {
            ints[i] = i + 1;
        }
        System.out.println(binarySearch(ints, 63));
    }


    /**
     * 二分查找
     *
     * @param array  原数组
     * @param target 目标值
     * @return target 所在数组下标位置
     */
    public static int binarySearch(int[] array, int target) {
        int start = 0; //下标从0开始
        int end = array.length - 1; //下标结束处
        int mid = 0; //中间值
        while (start <= end) {
            mid = (start + end) / 2; //得出中间值下标
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

    @Test
    public void method2() {
        int nums[] = {6, 1, 2, 7, 9, 3, 4, 5, 10, 8};
        quickSort(nums, 0, nums.length - 1);
        Arrays.stream(nums).forEach(System.out::println);

    }


//    /**
//     * 快速排序
//     *
//     * @param arrs  排序的数组
//     * @param start 左边开始的起点
//     * @param end   右边开始的起点
//     */
//    public void quickSort(int[] arrs, int start, int end) {
//        int temp = arrs[start]; //平衡点 左边大值小于该数,右边值大于该数,一般为数组的第一个数
//        int i = start; //左边查找开始的下标
//        int j = end; //右边查找开始的下标
//        while (i < j) {
//            //如果右边的数小于平衡值得话 j-- 6, 1, 2, 7, 9, 3, 4, 5, 10, 8
//            while (temp <= arrs[j] && i < j) j--;
//            while (temp >= arrs[i] && i < j) i++;
//            if (i < j) {
//                int z = arrs[i];
//                int y = arrs[j];
//                arrs[i] = y;
//                arrs[j] = z;
//            }
//        }
//        //此时如果跳出循环这表示i==j
//        arrs[start] = arrs[i];
//        arrs[i] = temp;
//
//        //
//
//
//    }


    public static void quickSort(int[] arr, int low, int high) {
        int i, j, temp;
        if (low > high) {
            return;
        }
        i = low;
        j = high;
        //temp就是基准位
        temp = arr[low];
        while (i < j) {
            //先看右边，依次往左递减
            while (temp <= arr[j] && i < j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp >= arr[i] && i < j) {
                i++;
            }
            //如果满足条件则交换
            if (i < j) {
                int z = arr[i];
                int y = arr[j];
                arr[i] = y;
                arr[j] = z;
            }
        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSort(arr, low, j - 1);
        //递归调用右半数组
        quickSort(arr, j +  1, high);
    }
}
