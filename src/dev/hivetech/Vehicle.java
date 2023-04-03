package dev.hivetech;

public class Vehicle {

    private String make;
    private String model;
    private String yearOfManufacture;
    private String colour;
    private String vin;
    private String fuelType;


    public Vehicle(String make, String model, String yearOfManufacture,
                   String colour, String vin, String fuelType) {
        this.make = make;
        this.model = model;
        this.yearOfManufacture = yearOfManufacture;
        this.colour = colour;
        this.vin = vin;
        this.fuelType = fuelType;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(String yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public String toString() {
        return "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", yearOfManufacture='" + yearOfManufacture + '\'' +
                ", colour='" + colour + '\'' +
                ", vin='" + vin + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", ";
    }
}

