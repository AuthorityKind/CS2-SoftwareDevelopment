public abstract class Vessel {
  public String name;

  public Vessel(String name){
    this.name = name;
  }

  public abstract void loadingCargo(int num, String str);

  public abstract double loadingFraction();
}