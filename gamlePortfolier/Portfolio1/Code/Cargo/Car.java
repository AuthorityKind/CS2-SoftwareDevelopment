package Portfolio1.Code.Cargo;

public class Car extends Cargo{
    public static short lane;

    public Car(String inName, short inLane) {
        super(inName, "Car", "Meters", 8);
        lane = inLane;
    }

    public short getLane(){
        return lane;
    }

    public static void setLane(short newLane){
        lane = newLane;
    }
}