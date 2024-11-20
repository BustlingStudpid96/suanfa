package com.pku.sorts;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.filter.FilteringParserDelegate;
import com.fasterxml.jackson.databind.jsontype.impl.AsDeductionTypeDeserializer;

import java.util.Arrays;

public class ArraySort {

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {   // n-1轮即可
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[maxIndex]) {
                    maxIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[maxIndex];
            arr[maxIndex] = temp;
        }
    }

    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int numi = arr[i];
            for (int j = i - 1; j >= 0; j--) {
//                if (arr[j] > numi) {
//                    int temp = arr[j];
//                    arr[j] = arr[j + 1];
//                    arr[j + 1] = temp;
//                }
                if (arr[j] > numi) {
                    arr[j + 1] = arr[j];
                    if (j == 0) {
                        arr[j] = numi;
                    }
                } else {
                    arr[j + 1] = numi;
                    break;
                }
            }
        }
    }

    public static void shellSort(int[] arr) {
        int[] steps = {4, 2, 1};  // 间隔
        for (int step : steps) {
            for (int i = step; i < arr.length; i += step) {
                int numi = arr[i];
                for (int j = i - step; j >= 0; j -= step) {
//                    if (arr[j] > numi) {
//                        arr[j + step] = arr[j];
//                    } else {
//                        arr[j + step] = numi;
//                        if (j == 0) {
//                            arr[0] = numi;
//                        }
//                    }
                    if (arr[j] > numi) {
                        arr[j + step] = arr[j];
                        if (j == 0) {
                            arr[j] = numi;
                        }
                    } else {
                        arr[j + step] = numi;
                        break;
                    }
                }
            }
        }
    }

    /**
     * “并”
     *
     * @param arr   数组
     * @param left  左边界
     * @param mid   左边数组的最后一个索引
     * @param right 右边数组的最后一个索引
     */
    private static void merge(int[] arr, int left, int mid, int right) {
        int i = left, j = mid + 1;
        int k = 0;
        int[] merged = new int[right - left + 1];
        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                merged[k++] = arr[i++];
            } else {
                merged[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            merged[k++] = arr[i++];
        }
        while (j <= right) {
            merged[k++] = arr[j++];
        }
        System.arraycopy(merged, 0, arr, left, merged.length);
    }

    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[left];
        int i = left, j = right;
        while (i < j) {
            while (i < j && arr[j] >= pivot) {
                j--;
            }
            arr[i] = arr[j];
            while (i < j && arr[i] <= pivot) {
                i++;
            }
            arr[j] = arr[i];
        }
        // i=j
        arr[i] = pivot;
        return i;
    }

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = partition(arr, left, right);
        quickSort(arr, left, mid - 1);
        quickSort(arr, mid + 1, right);
    }

    private static void buildHeap(int[] arr, int lastIndex) {
        // 从最后一个非叶子节点开始
        int i = (lastIndex - 1) / 2;
        for (int j = i; j >= 0; j--) {
            // 寻找左右子节点的较大者
            int maxIndex;
            if (2 * j + 1 > lastIndex) {
                continue;
            }
            if (2 * j + 2 <= lastIndex) { // 如果右孩子存在
                maxIndex = arr[2 * j + 1] > arr[2 * j + 2] ? 2 * j + 1 : 2 * j + 2;
            } else { // 只有左孩子
                maxIndex = 2 * j + 1;
            }
            if (arr[maxIndex] > arr[j]) {
                int temp = arr[j];
                arr[j] = arr[maxIndex];
                arr[maxIndex] = temp;
                // 如果maxIndex为叶子节点，则结束
                if (2 * maxIndex + 1 > lastIndex) {
                    continue;
                }
                j = maxIndex + 1; // 进行调整子节点
            }
        }
    }

    private static void heapify(int[] arr, int index, int lastIndex) {
        int maxIndex = index;
        int leftIndex = 2 * index + 1;
        int rightIndex = 2 * index + 2;
        // 找到最大的索引
        if (leftIndex <= lastIndex && arr[leftIndex] > arr[maxIndex]) {
            maxIndex = leftIndex;
        }
        if (rightIndex <= lastIndex && arr[rightIndex] > arr[maxIndex]) {
            maxIndex = rightIndex;
        }
        if (arr[maxIndex] > arr[index]) {
            int temp = arr[maxIndex];
            arr[maxIndex] = arr[index];
            arr[index] = temp;
            heapify(arr, maxIndex, lastIndex);   // 递归调整子树
        }
    }

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            // 先建堆
            heapify(arr, 0, i);
            // 交换堆顶和最后的元素
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
        }
    }

    public static void countingSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int[] cnt = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] <= arr[i]) {
                    cnt[i]++;
                } else {
                    cnt[j]++;
                }
            }
        }
        int[] ans = new int[cnt.length];
        for (int i = 0; i < cnt.length; i++) {
            int idx = cnt[i];
            ans[idx] = arr[i];
        }
        System.arraycopy(ans, 0, arr, 0, arr.length);
    }

    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int[][] radixNum = new int[10][arr.length]; // 0-9
        int[] cnt = new int[10]; // 记录每个桶中有多少个数
        // 得到最大数来确定位数?
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        int bit = 0;
        while (max != 0) {
            max /= 10;
            bit++;
        }
//        System.out.println(bit);
        for (int b = 0; b < bit; b++) {
            for (int i = 0; i < arr.length; i++) {
                // 得到各位的数
                int wei = (int) (arr[i] / (Math.pow(10, b)) % 10);  // 开始时b=0，pow=1，相当于%10得到个位,第二轮循环则/10%10得到十位
                // 放到对应数组中
                radixNum[wei][cnt[wei]++] = arr[i]; // 比如个位是2，2的数组中放入这个arr[i],同时cnt[wei]自增，表示2这个位置的数多了一个
                // 从对应数组中取出
            }
            int k = 0;  // 用来写arr数组
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < cnt[i]; j++) {   // cnt[5]表示个位为5的数组中有多少个数
                    arr[k++] = radixNum[i][j];
                }
                cnt[i] = 0;  // 清空计数数组
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {400, 23, 3, 2, 12};
//        bubbleSort(arr);
//        selectionSort(arr);
//        insertionSort(arr);
//        shellSort(arr);
//        mergeSort(arr);
//        quickSort(arr);
//        heapSort(arr);
//        countingSort(arr);
        radixSort(arr);
        System.out.println(Arrays.toString(arr));

    }
}

