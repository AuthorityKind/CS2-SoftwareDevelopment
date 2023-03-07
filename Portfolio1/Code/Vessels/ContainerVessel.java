package Portfolio1.Code.Vessels;

import Portfolio1.Code.Cargo.Container;

public class ContainerVessel extends Vessel{
    public static int capacity;

    public ContainerVessel(String inName, int inCapacity) {
        name = inName;
        type = "Container Vessel";
        capacity = inCapacity;
    }

    public Container createContainer(String inName) {
        Container container = new Container(inName);
        return container;
    }

    public boolean checkCapacity(){
        return getCapacity() > 0;
    }

    public int getCapacity(){
        return capacity - cargoHold.size();
    }
}