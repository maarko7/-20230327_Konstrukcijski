package dev.hivetech;

public class Truck extends Vehicle {

    private String towingCapacity;

    public Truck(String make, String model, String yearOfManufacture, String colour,
                 String vin, String fuelType, String towingCapacity) {
        super(make, model, yearOfManufacture, colour, vin, fuelType);
        this.towingCapacity = towingCapacity;
    }

    public String getTowingCapacity() {
        return towingCapacity;
    }

    public void setTowingCapacity(String towingCapacity) {
        this.towingCapacity = towingCapacity;
    }

    @Override
    public String toString() {
        return "Truck{" +
                super.toString() +
                "towingCapacity='" + towingCapacity + '\'' +
                "} ";
    }
}
