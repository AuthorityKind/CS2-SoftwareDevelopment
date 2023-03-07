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

    final static ContainerVessel test2 = new ContainerVessel("Test2", 40);
    final static RoRo test1 = new RoRo("Test1", (byte) 12, (short) 100);

    public static void main(String[] args) {
        vessels.add(test2);
        vessels.add(test1);

        Car car1 = test1.createVehicle("car1");
        test1.addCargo(car1);    
        
        Container con1 = test2.createContainer("con1");
        test2.addCargo(con1);

        System.out.println(test1.getName());
        System.out.println(test2.getName());

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