package dev.hivetech;

import dev.hivetech.exception.DuplicateVehicleException;
import dev.hivetech.exception.NoSuchVehicleException;

import java.util.List;
import java.util.logging.Logger;

public interface VehicleManager {


    void addVehicle(Vehicle vehicle, Logger LOGGER) throws DuplicateVehicleException;
    Vehicle searchVehicleByVIN(String vin);
    List<Vehicle> searchVehicleByMake(String manufacturer);
    List<Vehicle> searchVehicleByModel(String model);
    void printAllVehicles ();
    void printVehicles(List<Vehicle> vehiclesToPrint);
    boolean deleteVehicle (String vin, Logger LOGGER) throws NoSuchVehicleException;

}
