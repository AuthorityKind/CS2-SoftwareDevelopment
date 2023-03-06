package Portfolio1.Code.Cargo;

public class Car extends Cargo{
    public static byte lane;

    public Car(String inName, byte inLane) {
        super(inName, "Car", "Meters", 8);
        lane = inLane;
    }

    public byte getLane(){
        return lane;
    }

    public static void setLane(byte newLane){
        lane = newLane;
    }
}