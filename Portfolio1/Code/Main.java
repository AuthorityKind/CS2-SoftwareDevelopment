package Portfolio1.Code;

import Portfolio1.Code.Vessels.ContainerVessel;
import Portfolio1.Code.Vessels.RoRo;
import Portfolio1.Code.Vessels.Vessel;

import java.util.ArrayList;

import Portfolio1.Code.Cargo.Car;
import Portfolio1.Code.Cargo.Cargo;
import Portfolio1.Code.Cargo.Container;

public class Main {
    static ArrayList<Vessel> vessels = new ArrayList<Vessel>();

    final static ContainerVessel conVes1 = new ContainerVessel("Container Vessel 1", 40);
    static ContainerVessel conVes2 = new ContainerVessel("Container Vessel 2", 20);
    
    final static RoRo roro1 = new RoRo("Roro 1", (byte) 12, (short) 100);

    public static void main(String[] args) {
        vessels.add(conVes1);
        vessels.add(conVes2);
        vessels.add(roro1);

        Car car1 = roro1.createVehicle("car1");
        roro1.addCargo(car1);    
        
        Container con1 = conVes1.createContainer("con1");
        conVes1.addCargo(con1);

        Container con2 = conVes2.createContainer("con2");
        conVes2.addCargo(con2);

        printVessels();
    }

    public static void printVessels() {
        for (Vessel i : vessels) {
            System.out.println(i.getName() + ", " + i.getType());
            for (Cargo j : i.cargoHold) {
                System.out.println("  " + j.getName() + ", " + j.getType() + ", " + j.getSize() + " " + j.getMeasurement());
            }
        }
    }
}