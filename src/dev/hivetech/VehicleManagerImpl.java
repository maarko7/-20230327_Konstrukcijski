package dev.hivetech;

import dev.hivetech.exception.DuplicateVehicleException;
import dev.hivetech.exception.NoSuchVehicleException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VehicleManagerImpl implements VehicleManager {

    Logger LOGGER = Logger.getLogger(VehicleManagerImpl.class.getName());

    List<Vehicle> list = new ArrayList<>();

    /**
     * Metoda za dodavanje vozila u flotu, ukoliko isti već ne postoje u floti
     * @param vehicle varijabla u koju prosljeđujemo vozilo za koje smo uzeli datoteke u Main klasi preko vehicleDataForCreation metode
     * @throws DuplicateVehicleException exception u slučaju da vozilo s istom VIN oznakom već postoji
     */
    @Override
    public void addVehicle (Vehicle vehicle, Logger LOGGER) throws DuplicateVehicleException {
        Vehicle alreadyExistingVehicleWithSameVin = this.searchVehicleByVIN(vehicle.getVin());
        if (alreadyExistingVehicleWithSameVin != null){
            LOGGER.warning("Vehicle you are trying to add already exists.");
            throw new DuplicateVehicleException("Vehicle you are trying to add already exists. VIN: " + vehicle.getVin());
        }
        list.add(vehicle);
        System.out.println("[ADD] Vehicle with VIN " + vehicle.getVin() + " succesfully added. ");
        }

    /**
     * Metoda za prolazak kroz cijelu listu te pretragu vozila po proizvođaču preko getter metode
     * @param manufacturer varijabla za odabranog proizvođača
     * @return vraća listu pronađenih vozila koje su pod istim proizvođačem
     */
    @Override
    public List<Vehicle> searchVehicleByMake(String manufacturer) {
        List<Vehicle> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : list){
            if(vehicle.getMake().equalsIgnoreCase(manufacturer)) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }


    /**
     * Metoda za prolazak kroz cijelu listu te pretragu vozila po modelu preko getter metode
     * @param model varijabla za odabrani model vozila
     * @return vraća listu pronađenih vozila koje su pod istim modelom vozila
     */
    @Override
    public List<Vehicle> searchVehicleByModel(String model) {
        List<Vehicle> matchingVehicles = new ArrayList<>();
        for (Vehicle vehicle : list){
            if(vehicle.getModel().equalsIgnoreCase(model)) {
                matchingVehicles.add(vehicle);
            }
        }
        return matchingVehicles;
    }


    /**
     * Metoda za pretragu vozila s određenom VIN oznakom
     * @param vin varijabla za odabrani VIN vozila
     * @return vraća pronađeno vozilo u listi
     */
    @Override
    public Vehicle searchVehicleByVIN(String vin) {
        for (Vehicle vehicle : list) {
            if (vehicle.getVin().equalsIgnoreCase(vin)) {
                return vehicle;
            }
        }
        return null;
    }

    /**
     * Prolazi kroz listu te redom ispisuje vozila po metodi koju smo odabrali da ispiše
     * @param vehiclesToPrint varijabla za odabir vozila za printanje
     */
    public void printVehicles(List<Vehicle> vehiclesToPrint) {

        for (Vehicle vehicle : vehiclesToPrint){
            System.out.println("\t - " + vehicle.toString());
        }
    }

    /**
     * Prolazi kroz listu te redom ispisuje sva vozila
     */
    public void printAllVehicles() {
        if (!list.isEmpty())
            printVehicles(list);
        else
            System.out.println("- There are no vehicles in fleet.");
    }

    /**
     * Briše odabrano vozilo
     * @param vin oznaka vozila koje želimo obrisati
     * @return boolean kojim brišemo vozilo iz liste
     * @throws NoSuchVehicleException exception koji se pojavljuje ukoliko vozilo s odabranom VIN oznakom ne postoji
     */
    @Override
    public boolean deleteVehicle(String vin, Logger LOGGER) throws NoSuchVehicleException {

        Vehicle vehicleToDelete = searchVehicleByVIN(vin);

        if (vehicleToDelete == null){
            LOGGER.warning("Vehicle you're trying to delete doesn't exist.");
            throw new NoSuchVehicleException("Vehicle you're trying to delete doesn't exist. VIN: " + vin);
        }

        boolean deleteVehicle = list.remove(vehicleToDelete);
        System.out.println("[DELETE] Vehicle with VIN " + vin + " succesfully deleted.");

        return deleteVehicle;
    }
}
