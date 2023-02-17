//THIS PROGRAM IS COMPLETELY FUCKED
//IT REFUSES TO PRINT ANYTHING OTHER THAN NUMBERS AND I HAVE TRIED TO MANY
//DIFFERENT THINGS GIVING ME THE SAME SHIT AND I AM TIRED OF IT
//DO NOT LOOK OR TOUCH IT, IT'S FUCKED

/* import java.util.HashMap;

public class Main {
  static char[] charLetters = { 'c', 'a', 't' };

  static int token1 = 0;
  static int token2 = 1;
  static int token3 = 2;

  static HashMap<Integer, Character> letters = new HashMap<Integer, Character>();

  public static void setHashMap(char[] inLetters) {
    for (int i = 0; i < inLetters.length; i++) {
      letters.put(i, inLetters[i]);
    }
  }

  private static void flipSidesToSwap(String in) {
    switch (in) {
      case "front" -> in = "back";
      case "back" -> in = "front";
    }
  }

  private static void swapInt(int i1, int i2) {
    int temp = i1;
    i1 = i2;
    i2 = temp;
  }

  private static void printLetters() {
    String sidesToSwap = "front";

    for (int i = 0; i < 6; i++) {
      System.out.println(letters.get(token1) + letters.get(token2) + letters.get(token3));

      switch (sidesToSwap) {
        case "front" -> swapInt(token1, token2);
        case "back" -> swapInt(token2, token3);
      }

      flipSidesToSwap(sidesToSwap);
    }
  }

  /* public static void main(String[] args) {
    setHashMap(charLetters);
    // System.out.println(charLetters[0]);
    System.out.println(letters.get(0));
    printLetters();
  }
} */