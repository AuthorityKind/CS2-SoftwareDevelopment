
public class Tanker extends Vessel {
    int capacity;
    TankerCargo[] cargo = new TankerCargo[10];

    public Tanker(String name, int capacity) {
        super(name);
        this.capacity = capacity;
    }

    @Override
    public void loadingCargo(int compartment, String volume) {
        if (cargo[compartment] == null) {
            cargo[compartment] = new TankerCargo(compartment, volume);
        } else {
            System.err.println("Compartment " + compartment + " is full, cannot add tanker cargo");
        }
    }

    @Override
    public double loadingFraction() {
        int totalCapacity = 0;
        for (TankerCargo tankerCargo : cargo)
            if (tankerCargo == null)
                totalCapacity++;
        final int mod = 100;
        double out = totalCapacity * mod / cargo.length;
        return out / mod;
    }
}

class TankerCargo {
    public int compartment;
    public String volume;

    public TankerCargo(int compartment, String volume) {
        this.compartment = compartment;
        this.volume = volume;
    }
}