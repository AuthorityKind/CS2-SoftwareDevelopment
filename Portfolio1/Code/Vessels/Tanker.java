package Portfolio1.Code.Vessels;

import Portfolio1.Code.Cargo.TankerCargo;

public class Tanker extends Vessel{

    int[] compartments = new int[10];
    int capacity;

    public Tanker(String inName, int inCapacity) {
        name = inName;
        type = "Tanker";
        capacity = inCapacity;
    }

    public int getCapacity(int compartment){
        return capacity - compartments[compartment];
    }

    public TankerCargo createTankerCargo(String inName, int inSize){
        int inCompartment = -1;
        
        for(int i:compartments){
            if(compartmentAvailable(i)){
                inCompartment = i;
                break;
            }
        }

        TankerCargo tankerCargo = new TankerCargo(inName, inSize, (byte) inCompartment);
        return tankerCargo;
    }

    public boolean compartmentAvailable(int compartment){
        return getCapacity(compartment) < capacity;
    }
}