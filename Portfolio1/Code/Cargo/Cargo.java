package Portfolio1.Code.Cargo;

public abstract class Cargo {
    public String measurement;
    public int size;
    public String name;
    public String type;

    public Cargo(String inName, String inType, String inMeas, int inSize){
        name = inName;
        type = inType;
        measurement = inMeas;
        size = inSize;
    }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public int getSize(){
        return size;
    }

    public String getMeasurement(){
        return measurement;
    }
}