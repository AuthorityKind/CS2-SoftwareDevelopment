package Portfolio1.Code.Cargo;

public class Truck extends Cargo{
    public static byte lane;

    public Truck(String inName, byte inLane) {
        super(inName, "Truck", "Meters", 30);
        lane = inLane;
    }
    
    public byte getLane(){
        return lane;
    }

    public static void setLane(byte newLane){
        lane = newLane;
    }
}