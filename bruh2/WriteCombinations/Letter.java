public class Letter {
    char letter;
    int priority;

    public Letter(char letter, int prio){
        this.letter = letter;
        this.priority = prio;
    }

    public char getChar(){
        return letter;
    }

    public int getPrio(){
        return priority;
    }
}
