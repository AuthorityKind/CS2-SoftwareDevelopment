package Portfolio1.Code.Cargo;

public class TankerCargo extends Cargo {
    public static byte compartment;

    public TankerCargo(String inName, int inSize, byte inCompartment) {
        super(inName, "Tanker Cargo", "Cubic Meters", inSize);
        compartment = inCompartment;
    }

    public byte getCompartment(){
        return compartment;
    }

    public static void setCompartment(byte newCompartment){
        compartment = newCompartment;
    }
}