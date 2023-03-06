//Assignment: 
// create an ArrayList <Integer> and add the numbers from 1 to 10 using a loop, 
// reverse the list using a for or while loop, and print the elements from last to first.

package Misc.ReverseArrPrint;

import java.util.ArrayList;

public class Main {

    static final int num = 10;
    static ArrayList<Integer> list;

    public static void main(String[] args) {
        list = initializeList(list, num);
        printReverse(list);
    }

    private static ArrayList<Integer> initializeList(ArrayList<Integer> inList, int listLength) {
        inList = new ArrayList<Integer>(listLength);
        for (int i = 0; i < listLength; i++) {
            inList.add(i + 1);
        }
        return inList;
    }

    private static void printReverse(ArrayList<Integer> inList) {
        for (int i = inList.size() - 1; i >= 0; i--) {
            System.out.println(inList.get(i));
        }
    }
}