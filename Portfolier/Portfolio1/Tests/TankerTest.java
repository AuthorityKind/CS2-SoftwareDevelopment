import org.junit.Test;

public class TankerTest {
    int capacity;
    TankerCargo[] cargo = new TankerCargo[10];

    public TankerTest() {
        Integer capacity = 20;
        if (capacity instanceof Integer) {
            this.capacity = (int) capacity;
        } else {
            throw new IllegalArgumentException("Argument was null!");
        }
    }

    public void loadingCargoTest(Object compartment, Object volume) {
        if ((compartment instanceof Integer) && (volume instanceof String)) {
            if (cargo[(int) compartment] == null) {
                cargo[(int) compartment] = new TankerCargo((int) compartment, volume.toString());
            } else {
                System.err.println("Compartment " + compartment + " is full, cannot add tanker cargo");
            }
        } else {
            throw new IllegalArgumentException("Argument was invalid!");
        }
    }

    @Test
    public void loadingFractionTest() {
        int totalCapacity = 0;
        for (TankerCargo tankerCargo : cargo)
            if (tankerCargo == null)
                totalCapacity++;
        final int mod = 100;
        double out = totalCapacity * mod / capacity;
        System.out.println(out / mod);
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