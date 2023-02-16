public class Letters {
    static Letter[] letterList;

    public Letters(char[] letters) {
        Letters.letterList = new Letter[letters.length];

        for (int i = 0; i < letterList.length; i++) {
            letterList[i] = new Letter(letters[i], i);
            //System.out.print(letterList[i].getChar() + " ");
            //System.out.println(letterList[i].getPrio());
        }
    }

    public void printLetters() {
        int token1 = 0;
        int token2 = 1;
        int token3 = 2;

        String sidesToSwap = "front";

        for(int i = 0; i < 6; i++){
            System.out.println(
                letterList[token1].getChar() + 
                letterList[token2].getChar() + 
                letterList[token3].getChar()
            );

            switch (sidesToSwap){
                case "front" -> swapInt(token1, token2);
                case "back" -> swapInt(token2, token3); 
            }

            flipSidesToSwap(sidesToSwap);
        }
    }

    public void flipSidesToSwap(String in) {
        switch (in){
            case "front" -> in = "back";
            case "back" -> in = "front"; 
        }
    }

    public static void swapInt(int i1, int i2) {
        int temp = i1;
        i1 = i2;
        i2 = temp;
    }
}
