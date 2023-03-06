package Portfolio1.Code.Vessels;

import Portfolio1.Code.Cargo.Car;
import Portfolio1.Code.Cargo.Truck;

public class RoRo extends Vessel{
    public short[] lanes;
    public short laneLength;
    
    public RoRo(String inName, byte lanesNum, short inLaneLength) {
        super(inName, "RoRo");
        lanes = new short[lanesNum];
        laneLength = inLaneLength;
    }

    public Car createVehicle(String inName) {
        Car output;
        
        short tempLane = -1;
        for(short i:lanes){
            if(checkLaneSpace(i, Car.getSize())){
                tempLane = i;
                break;
            }
        }
        output = new Car(inName, tempLane);

        return output;
    }

    //method overloading
    public Truck createVehicle(Truck truck, String inName) {
        short tempLane = -1;
        for(short i:lanes){
            if(checkLaneSpace(i, Car.getSize())){
                tempLane = i;
                break;
            }
        }
        truck = new Truck(inName, tempLane);

        return truck;
    }

    private boolean checkLaneSpace(short lane, int newVehicleLength){
        return getRemainingLaneSpace(lane) >= newVehicleLength;
    }

    private int getRemainingLaneSpace(short lane){
        return laneLength - lanes[lane];
    }
}