//Assignment: 
//Calculate the powers of 2 table from 1 to 10 using a nested for loop where you
// multiply 2 with itself the given number of times. 

package bruh2.RaiseToPower;

import java.lang.Math;

public class Main {

    static int[] list1, list2;
    static final int num = 10;

    public static void main(String[] args) {
        list1 = initializeList(list1, num);
        list2 = initializeList(list2, num);
        printPow(list1, list2, num);
    }

    private static int[] initializeList(int[] inList, int listLength) {
        inList = new int[listLength];
        for (int i = 0; i < listLength; i++) {
            inList[i] = i + 1;
        }
        return inList;
    }

    private static void printPow(int[] inList1, int[] inList2, int length) {
        for (int i = 0; i < length; i++) {
            System.out.println(Math.pow(inList1[i], inList2[i]));
        }
    }
}
