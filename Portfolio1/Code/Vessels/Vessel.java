package Portfolio1.Code.Vessels;

import java.util.ArrayList;
import Portfolio1.Code.Cargo.Cargo;

public abstract class Vessel {
    protected static String name;
    protected static String type;
    public ArrayList<Cargo> cargoHold = new ArrayList<Cargo>();

    /* 
    public Vessel(String inName, String inType){
        name = inName;
        type = inType;
    }
    */
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Cargo> getCargo(){
        return cargoHold;
    }

    public void addCargo(Cargo cargo){
        cargoHold.add(cargo);
    };
}