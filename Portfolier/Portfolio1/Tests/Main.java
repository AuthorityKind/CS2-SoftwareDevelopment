import org.junit.Test;

public class Main {

  @Test(expected = IllegalArgumentException.class)
  public void testTankerLoadingCargo(){
    TankerTest tankerTest = new TankerTest();
    tankerTest.loadingCargoTest(null, null);
  }
  public static void main(String[] args) {
  }
}