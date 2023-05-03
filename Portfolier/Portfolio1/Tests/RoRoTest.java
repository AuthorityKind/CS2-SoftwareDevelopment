import java.util.ArrayList;

import org.junit.Test;

public class RoRoTest{
  int lanes;
  int laneLength;
  ArrayList<Vehicle> cargo = new ArrayList<>();

  public RoRoTest() {
    lanes = 10;
    laneLength = 50;
  }

  @Test
  public void loadingCargoTest() {
    int lane = 2;
    String vehicle = "Car";
    if(!checkLaneCap(lane)){
      cargo.add(new Vehicle(vehicle, lane));
    } else{
      System.err.println("Lane " + lane + " is full, cannot add vehicle");
    }
  }

  @Test
  public void loadingFractionTest() {
    int totalLaneCap = 0;
    for (int i = 0; i < lanes; i++)
      totalLaneCap += getCapacity(i + 1);
    
    //wacky work-around for it to return the proper value 
    final int mod = 100;
    double out = totalLaneCap * mod / (lanes * laneLength);
    System.out.println(out/mod);
  }

  @Test
  public void checkLaneCap(){
    int lane = 6;
    System.out.println(getCapacity(lane) < laneLength);
  }

  private boolean checkLaneCap(int lane){
    return getCapacity(lane) < laneLength;
  }

  @Test
  public void getCapacity() {
    int lane = 6;
    int laneCap = 0;
    for (Vehicle vehicle : cargo)
      if (vehicle.lane == lane)
        laneCap += vehicle.length;
    System.out.println(laneLength - laneCap);
  }

  //method overloading to be able to use it in other methods
  private int getCapacity(int lane) {
    int laneCap = 0;
    for (Vehicle vehicle : cargo)
      if (vehicle.lane == lane)
        laneCap += vehicle.length;
    return laneLength - laneCap;
  }

}

class Vehicle{
  public String type;
  public int lane;
  public int length;

  public Vehicle(String type, int lane){
    this.type = type;
    this.lane = lane;

    switch (type){
      case "Car" -> this.length = 8;
      case "Truck" -> this.length = 30;
    }
  }
}