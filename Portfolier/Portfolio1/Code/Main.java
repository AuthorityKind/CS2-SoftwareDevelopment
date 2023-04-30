import java.util.ArrayList;

public class Main {
  static ArrayList<Vessel> vessels = new ArrayList<>();
  public static void main(String[] args) {
    vessels.add(new RoRo("roro", 3, 50));
    vessels.add(new Tanker("tanker", 10));
    vessels.add(new ContainerVessel("container vessel", 100));

    vessels.get(0).loadingCargo(0, "Car");
    vessels.get(0).loadingCargo(1, "Car");
    vessels.get(0).loadingCargo(2, "Truck");
    System.out.println("RoRo fraction: "+vessels.get(0).loadingFraction());

    vessels.get(1).loadingCargo(0, "4");
    vessels.get(1).loadingCargo(3, "2");
    vessels.get(1).loadingCargo(6, "7");
    vessels.get(1).loadingCargo(8, "3");
    System.out.println("Tanker fraction: "+vessels.get(1).loadingFraction());

    vessels.get(2).loadingCargo(10, "TEU");
    vessels.get(2).loadingCargo(16, "TEU");
    vessels.get(2).loadingCargo(26, "TEU");
    System.out.println("Container Vessel fraction: "+vessels.get(2).loadingFraction());
  }
}