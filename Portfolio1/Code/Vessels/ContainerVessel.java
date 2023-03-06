package Portfolio1.Code.Vessels;

public class ContainerVessel extends Vessel{
    public static int capacity;

    public ContainerVessel(String inName, int inCapacity) {
        super(inName, "Container Vessel");
        capacity = inCapacity;
    }

    @Override
    public void addCargo() {
       
    } 

    public boolean checkCapacity(){
        return getCapacity() > 0;
    }

    public int getCapacity(){
        return capacity - cargoHold.size();
    }
}