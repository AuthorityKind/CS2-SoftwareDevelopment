package Portfolio1.Code.Vessels;

import java.util.ArrayList;
import Portfolio1.Code.Cargo.Cargo;

public abstract class Vessel {
    public String name;
    public static String type;
    public ArrayList<Cargo> cargoHold;

    public Vessel(String inName, String inType){
        name = inName;
        type = inType;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Cargo> getCargo(){
        return cargoHold;
    }

    abstract public void addCargo();
}