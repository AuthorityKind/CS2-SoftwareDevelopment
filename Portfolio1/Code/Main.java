package Portfolio1.Code;

import Portfolio1.Code.Vessels.RoRo;
import Portfolio1.Code.Vessels.Vessel;
import Portfolio1.Code.Cargo.Car;
import Portfolio1.Code.Cargo.Cargo;

public class Main{
    public static void main(String[] args) {
        RoRo test = new RoRo("Test", (byte) 12, (short) 100);
        Car car1 = test.createVehicle("car1");
        test.addCargo(car1);

        printVessel(test);
    }

    public static void printVessel(Vessel vessel){
        System.out.println(vessel.getName() + ", " + vessel.getType());
        for(Cargo i:vessel.cargoHold){
            System.out.println("  " + i.getName() + ", " + i.getType() + ", " + i.getSize() + " " + i.getMeasurement());
        }
    }
}