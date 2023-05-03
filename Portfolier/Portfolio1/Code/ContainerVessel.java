import java.util.ArrayList;

public class ContainerVessel extends Vessel{
  int capacity;
  ArrayList<Container> cargo = new ArrayList<>();

  public ContainerVessel(String name, int capacity) {
    super(name);
    this.capacity = capacity;
  }

  @Override
  public void loadingCargo(int numOfContainers, String teu) {
    if((numOfContainers + getCapacity())<= capacity)
      cargo.add(new Container(numOfContainers, "TEU"));
  }

  @Override
  public double loadingFraction() {
    final int mod = 1000;
    double out = getCapacity() * mod / capacity;
    return out/mod;
  }

  //returns the number of containers it currently holds.
  private int getCapacity(){
    if(!cargo.isEmpty()){
      int totalCapacity = 0;
      for (Container container: cargo)
        totalCapacity += container.numOfContainers;
      return totalCapacity;
    }
    return 0;
  }  
}

class Container{
  public int numOfContainers;
  String str;

  public Container(int numOfContainers, String str){
    this.numOfContainers = numOfContainers;
    this.str = str;
  }
}