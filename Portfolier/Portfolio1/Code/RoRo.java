import java.util.ArrayList;

public class RoRo extends Vessel {
  int lanes;
  int laneLength;
  ArrayList<Vehicle> cargo = new ArrayList<>();

  public RoRo(String name, int lanes, int laneLength) {
    super(name);
    this.lanes = lanes;
    this.laneLength = laneLength;
  }

  @Override
  public void loadingCargo(int lane, String vehicle) {
    if (vehicle.equals("Car") && getCapacity(lane) >= 8) {
      cargo.add(new Vehicle(vehicle, lane));
    } else if (vehicle.equals("Truck") && getCapacity(lane) >= 30) {
      cargo.add(new Vehicle(vehicle, lane));
    } else{
      System.err.println("Lane " + lane + " is full, cannot add vehicle");
    }
  }

  @Override
  public double loadingFraction() {
    int totalLaneCap = 0;
    for (int i = 0; i < lanes; i++)
      totalLaneCap += getCapacity(i + 1);

    // wacky work-around for it to return the proper value
    // needs to have a modifier that it is first multiplied by then divided by
    final int mod = 100;
    double out = totalLaneCap * mod / (lanes * laneLength);
    return out / mod;
  }

  private int getCapacity(int lane) {
    int laneCapacity = 0;
    for (Vehicle vehicle : cargo)
      if (vehicle.lane == lane)
        laneCapacity += vehicle.length;
    return laneLength - laneCapacity;
  }
}

class Vehicle {
  public String type;
  public int lane; // lane used to know which lane of the RoRo the vehicle is in
  public int length;

  public Vehicle(String type, int lane) {
    this.type = type;
    this.lane = lane;

    switch (type) {
      case "Car" -> this.length = 8;
      case "Truck" -> this.length = 30;
    }
  }
}