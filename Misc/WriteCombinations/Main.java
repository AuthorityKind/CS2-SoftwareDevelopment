/* 
import java.util.Scanner;

public class Main {
  static char[][] output;

  public static void main(String[] args) {
    String input = getInput();
    char[][] output = new char[calDigits(input)][input.length()];

    for(int i = 0; i < input.length(); i++){
      if(input.length() > 3){
        // output
      }
    }
  }

  private static String getInput() {
    System.out.println("Write your word or combination of characters of choice:");
    Scanner in = new Scanner(System.in);
    String input;

    input = in.next();
    in.close();

    //char[] output = input.toCharArray();

    return input;
  }

  private static int calDigits(String in){
    int out = 1;
    for(int i = 1; i <= in.length(); i++){
      out = out * i;
    }

    private void printCombinations(){

    }
}
*/
