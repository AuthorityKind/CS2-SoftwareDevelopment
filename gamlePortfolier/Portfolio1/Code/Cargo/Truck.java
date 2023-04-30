package Portfolio1.Code.Cargo;

public class Truck extends Cargo{
    public static short lane;

    public Truck(String inName, short inLane) {
        super(inName, "Truck", "Meters", 30);
        lane = inLane;
    }
    
    public short getLane(){
        return lane;
    }

    public static void setLane(short newLane){
        lane = newLane;
    }
}