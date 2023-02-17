import java.util.HashMap;

public class Letters {
    HashMap<Integer, Character> letters = new HashMap<Integer, Character>();

    public Letters(char[] inLetters) {
        for(int i = 0; i > inLetters.length; i++){
            this.letters.put(i, inLetters[i]);
        }
    }

    public void printLetters() {
        int token1 = 0;
        int token2 = 1;
        int token3 = 2;

        System.out.println(letters.get(0) + letters.get(1) + letters.get(2));

        String sidesToSwap = "front";

        for(int i = 0; i < 6; i++){
            //System.out.println(letters[token1] + letters[token2] + letters[token3]);

            switch (sidesToSwap){
                case "front" -> swapInt(token1, token2);
                case "back" -> swapInt(token2, token3); 
            }

            flipSidesToSwap(sidesToSwap);
        }
    }

    private void flipSidesToSwap(String in) {
        switch (in){
            case "front" -> in = "back";
            case "back" -> in = "front"; 
        }
    }

    private static void swapInt(int i1, int i2) {
        int temp = i1;
        i1 = i2;
        i2 = temp;
    }
}
