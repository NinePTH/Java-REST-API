package com.example.demo.algorithm;

public class binSearch {
    public static int search(int[] list, int target) {
        int left = 0;
        int right = list.length - 1;

        while (left <= right){
            int middle = (left + right) / 2;
            if (list[middle] == target){ 
                return middle;
            } else if (list[middle] < target){ 
                left = middle + 1;
            } else { 
                right = middle - 1;
            }
        }
        return -1;
    }
}
