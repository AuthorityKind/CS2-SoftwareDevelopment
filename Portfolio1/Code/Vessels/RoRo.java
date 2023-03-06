package Portfolio1.Code.Vessels;

public class RoRo extends Vessel{
    public byte[] lanes;
    public short laneLength;
    
    public RoRo(String inName, byte lanesNum, short inLaneLength) {
        super(inName, "RoRo");
        lanes = new byte[lanesNum];
        laneLength = inLaneLength;
    }

    @Override
    public void addCargo() {
       
    } 
}