package dev.hivetech;

public class Car extends Vehicle {

    private String doorNum;
    private String bodyStyle;

    public Car(String proizvodac, String model, String godinaProizvodnje, String boja,
               String vin, String tipGoriva, String doorNum, String bodyStyle) {
        super(proizvodac, model, godinaProizvodnje, boja, vin, tipGoriva);
        this.doorNum = doorNum;
        this.bodyStyle = bodyStyle;
    }

    public void setDoorNum(String doorNum) {
        this.doorNum = doorNum;
    }

    public String getDoorNum() {
        return doorNum;
    }

    public String getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle(String bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    @Override
    public String toString() {
        return "Car{" +
                super.toString() +
                "doorNum='" + doorNum + '\'' +
                ", bodyStyle='" + bodyStyle + '\'' +
                "} ";
    }
}
