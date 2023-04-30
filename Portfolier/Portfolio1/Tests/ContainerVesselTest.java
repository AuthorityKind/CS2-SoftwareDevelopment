import java.util.ArrayList;

import org.junit.Test;

public class ContainerVesselTest extends VesselTest {
  int capacity;
  ArrayList<Container> cargo = new ArrayList<>();

  public ContainerVesselTest() {
    capacity = 100;
  }

  @Test
  @Override
  public void loadingCargo() {
    int numOfContainers = 10;
    if ((numOfContainers + getCapacity(true)) <= capacity) {
      cargo.add(new Container(numOfContainers, "TEU"));
    }
  }

  @Test
  @Override
  public void loadingFraction() {
    final int mod = 1000;
    double out = getCapacity(true) * mod / capacity;
    System.out.println(out / mod);
    ;
  }

  @Test
  public void getCapacity() {
    int totalCapacity = 0;
    if (!cargo.isEmpty()) {
      for (Container container : cargo) {
        totalCapacity += container.numOfContainers;
      }
    }
    System.out.println(totalCapacity);
  }

  //method overloading to be able to use it in other methods
  private int getCapacity(boolean bool){
    if(!cargo.isEmpty()){
      int totalCapacity = 0;
      for (Container container: cargo){
        totalCapacity += container.numOfContainers;
      }
      return totalCapacity;
    }
    return 0;
  }
}

class Container {
  public int numOfContainers;
  String str;

  public Container(int numOfContainers, String str) {
    this.numOfContainers = numOfContainers;
    this.str = str;
  }
}
