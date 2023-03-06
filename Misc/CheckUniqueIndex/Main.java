package Misc.CheckUniqueIndex;
//Assignment:
//Write a method that takes an array of int and returns true if the numbers are all
// different and false if a number appears more than once.

public class Main {
    static int[] list;
    static int[] susList;

    public static void main(String[] args) {
        list = initializeList(list, 10);
        susList = initializeList(list, 10);
        susList[3] = 3;

        checkSus(list);
        checkSus(susList);
    }

    private static int[] initializeList(int[] inList, int listLength) {
        inList = new int[listLength];
        for (int i = 0; i < listLength; i++) {
            inList[i] = i + 1;
        }
        return inList;
    }

    private static void checkSus(int[] inList) {
        for(int i = 1; i < inList.length; i++){
            if(!checkList(inList, 0, i, inList[i])){
                System.out.println("Failure: there are multiple instances of " + inList[i]);
                break;
            }
        }
        System.out.println("Complete: this list have no duplicates");
    }

    private static boolean checkList(int[] inList, int startPoint, int checkPoint, int inContent) {
        for(int i = startPoint; i < checkPoint; i++){
            if(inList[i] == inContent){
                return false;
            }    
        }
        return true;
    }
}