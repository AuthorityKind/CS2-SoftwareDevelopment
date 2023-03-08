package Portfolio1.Code.Vessels;

import java.util.ArrayList;
import Portfolio1.Code.Cargo.Cargo;

public abstract class Vessel {
    protected String name;
    protected String type;
    public ArrayList<Cargo> cargoHold = new ArrayList<Cargo>();

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