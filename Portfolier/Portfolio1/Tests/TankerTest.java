import org.junit.Test;

public class TankerTest extends VesselTest {
    int capacity;
    TankerCargo[] cargo = new TankerCargo[10];

    public TankerTest() {
        capacity = 20;
    }

    @Test
    @Override
    public void loadingCargo() {
        int compartment = 4;
        String volume = "6";
        if (cargo[compartment] == null) {
            cargo[compartment] = new TankerCargo(compartment, volume);
        } else {
            System.err.println("Compartment " + compartment + " is full, cannot add tanker cargo");
        }
    }

    @Test
    @Override
    public void loadingFraction() {
        int totalCapacity = 0;
        for (TankerCargo tankerCargo : cargo)
            if (tankerCargo == null)
                totalCapacity++;
        final int mod = 100;
        double out = totalCapacity * mod / capacity;
        System.out.println(out / mod);
    }
}

class TankerCargo{
	public int compartment;
	public String volume;

	public TankerCargo(int compartment, String volume){
		this.compartment = compartment;
		this.volume = volume;
	}
}