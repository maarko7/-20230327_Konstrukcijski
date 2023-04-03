package dev.hivetech;

import dev.hivetech.exception.DuplicateVehicleException;
import dev.hivetech.exception.NoSuchVehicleException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    /**
     * kreirane su konstante za ponudu opcija programa
     */
    private static final int OPERATION_CREATE = 1;
    private static final int OPERATION_SEARCH = 2;
    private static final int OPERATION_PRINT = 3;
    private static final int OPERATION_DELETE = 4;
    private static final int OPERATION_QUIT = 5;

    private static final int SEARCH_OPERATION_VIN = 1;
    private static final int SEARCH_OPERATION_MF = 2;
    private static final int SEARCH_OPERATION_MODEL = 3;
    private static final int SEARCH_OPERATION_QUIT = 4;


    public static void main(String[] args) throws IOException {

        /**
         * LOGGER za bilježenje grešaka tokom rada programa
         */
        Logger LOGGER = Logger.getLogger(Main.class.getName());

        try{
            FileHandler fileHandler = new FileHandler("MainLog.txt");
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        }catch(IOException e) {
            LOGGER.warning("LOGGER file could not be created. " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        VehicleManager vehicleManager = new VehicleManagerImpl();

        final String operationSelectionLabel = new StringBuilder()
                .append("Choose operation:\n\t")
                .append(OPERATION_CREATE)
                .append(". Create vehicle\n\t")
                .append(OPERATION_SEARCH)
                .append(". Search for vehicle\n\t")
                .append(OPERATION_PRINT)
                .append(". Print all vehicles\n\t")
                .append(OPERATION_DELETE)
                .append(". Delete vehicle\n\t")
                .append(OPERATION_QUIT)
                .append(". Exit program\n")
                .append("Enter operation number: ")
                .toString();

        int selection;


        /**
         * petlja za odabir operacije
         */
        do {
            System.out.println("Vehicle fleet management system");
            System.out.println("----------------------------------");
            selection = getNumberInput(scanner, operationSelectionLabel, LOGGER);

            switch (selection) {

                case OPERATION_CREATE:
                    vehicleCreate(scanner, vehicleManager, LOGGER);
                    break;

                case OPERATION_SEARCH:
                    vehicleSearch(scanner, vehicleManager, LOGGER);
                    break;

                case OPERATION_PRINT:
                    vehiclePrint(vehicleManager);
                    break;

                case OPERATION_DELETE:
                    deleteVehicle(scanner, vehicleManager, LOGGER);
                    break;

                case OPERATION_QUIT:
                    System.out.println("End of program");
                    LOGGER.info("End of program");
                    break;

                default:
                    System.out.println("Wrong input. Please try again.");
                    LOGGER.warning("Wrong input. Please try again.");
            }
        } while (selection != OPERATION_QUIT);
    }


    /**
     * metoda za kreiranje vozila. Uzima podatke preko druge metode (vehicleDataForCreation), te ih obradjuje u drugoj klasi
     * @param scanner koristimo za uzimanje vrijednosti preko konzole
     * @param vehicleManager koristimo za pristup klasi u kojoj dodajemo vozila u listu
     * @param LOGGER koristimo za zapis grešaka
     */
    public static void vehicleCreate(Scanner scanner, VehicleManager vehicleManager, Logger LOGGER){

        Vehicle vehicle = vehicleDataForCreation(scanner, LOGGER);
        //definiramo vehicle tipa podatka Vehicle te pozivamo metodu kroz koje uzimamo podatke

        try {
            vehicleManager.addVehicle(vehicle, LOGGER);
        } catch (DuplicateVehicleException e) {
            LOGGER.warning("Failed to add vehicle - " + e.getMessage());
            System.out.println("Failed to add vehicle - " + e.getMessage());
            vehicleCreate(scanner, vehicleManager, LOGGER);
            //u slučaju da je unos pogrešan, ista metoda se ponovno poziva
        }
    }

    /**
     * metoda za prikupljanje podataka za kreiranje vozila
     * @return vrijednost spremamo kao vehicle. Moguća su dva načina dobivanja te vrijednosti. Jedan je preko klase Car, a druga preko klase Truck
     */
    public static Vehicle vehicleDataForCreation(Scanner scanner, Logger LOGGER) {

        Vehicle vehicle;

        String vehicleType;

        String make;
        String model;
        String yearOfManufacture;
        String colour;
        String vin;
        String fuelType;

        String numOfDoors;
        String carBodyStyle;

        String kapacitetTereta;

        do {
            System.out.println("Enter vehicle type (car or truck): ");
            vehicleType = scanner.nextLine();

            if (!vehicleType.equalsIgnoreCase("Car") && !vehicleType.equalsIgnoreCase("Truck")) {
                LOGGER.warning("Wrong input.");
                System.out.println("Wrong choice. Try again.");
            }
        } while (!vehicleType.equalsIgnoreCase("Car") && !vehicleType.equalsIgnoreCase("Truck"));

        System.out.println("Enter make: ");
        make = scanner.nextLine();

        System.out.println("Enter model: ");
        model = scanner.nextLine();

        System.out.println("Enter year of manufacturing: ");
        yearOfManufacture = scanner.nextLine();

        System.out.println("Enter colour: ");
        colour = scanner.nextLine();

        System.out.println("Enter VIN: ");
        vin = scanner.nextLine();

        System.out.println("Enter fuel type: ");
        fuelType = scanner.nextLine();

        if (vehicleType.equalsIgnoreCase("Car")) {
            System.out.println("Enter number of doors: ");
            numOfDoors = scanner.nextLine();

            System.out.println("Enter body style: ");
            carBodyStyle = scanner.nextLine();

            vehicle = new Car(make, model, yearOfManufacture,
                    colour, vin, fuelType, numOfDoors, carBodyStyle);

        } else {
            System.out.println("Enter towing capacity: ");
            kapacitetTereta = scanner.nextLine();

            vehicle = new Truck(make, model, yearOfManufacture,
                    colour, vin, fuelType, kapacitetTereta);

        }

        return vehicle;
    }


    /**
     * Metoda za pretragu liste na jedan od tri načina. Moguće je pretraživati preko VIN broja, proizvođača i preko modela vozila
     * @param scanner
     * @param vehicleManager koristimo za pristup metodama za pretragu iz druge klase (VehicleManagerImpl)
     * @param LOGGER
     */
    private static void vehicleSearch(Scanner scanner, VehicleManager vehicleManager, Logger LOGGER) {

        String searchTypeSelectionLabel = new StringBuilder()
                .append("Choose your operation: \n\t")
                .append(SEARCH_OPERATION_VIN)
                .append(". Search by VIN\n\t")
                .append(SEARCH_OPERATION_MF)
                .append(". Search by make\n\t")
                .append(SEARCH_OPERATION_MODEL)
                .append(". Search by model\n\t")
                .append(SEARCH_OPERATION_QUIT)
                .append(". Exit search\n")
                .append("Enter operation number: ")
                .toString();

        int selection;

        do {
            selection = getNumberInput(scanner, searchTypeSelectionLabel, LOGGER);

            switch (selection) {

                /**
                 * U slučaju da je pronađeno vozilo s određenim VIN brojem, lista foundVehicles poprima vrijednost s tim vozilom
                 * U suprotnom, lista ostaje prazna.
                 */
                case SEARCH_OPERATION_VIN: {
                    String vin = getStringInput(scanner, "Insert VIN you want to search by: ", LOGGER);
                    Vehicle vehicle = vehicleManager.searchVehicleByVIN(vin);
                    List<Vehicle> foundVehicles = vehicle != null ? List.of(vehicle) : new ArrayList<>();
                    presentSearchResults(vehicleManager, foundVehicles, "VIN", vin);
                    break;
                }
                case SEARCH_OPERATION_MF: {
                    String manufacturer = getStringInput(
                            scanner, "Insert vehicle manufacturer you want to search by: ", LOGGER);
                    List<Vehicle> foundVehicles = vehicleManager.searchVehicleByMake(manufacturer);
                    presentSearchResults(vehicleManager, foundVehicles, "make", manufacturer);
                    break;
                }
                case SEARCH_OPERATION_MODEL: {
                    String model = getStringInput(
                            scanner, "Insert vehicle model you want to search by: ", LOGGER);
                    List<Vehicle> foundVehicles = vehicleManager.searchVehicleByModel(model);
                    presentSearchResults(vehicleManager, foundVehicles, "model", model);
                    break;
                }
                case OPERATION_QUIT:
                    // Ne cini nikakvu operaciju, samo izlazi iz programa
                    break;
                default:
                    LOGGER.warning("Wrong input.");
                    System.out.println("Wrong input. Please enter number between 1 and 4.");
            }
        }while(selection != SEARCH_OPERATION_QUIT);

    }

    /**
     * Metoda koju koristimo za spremanje pronađenih vozila, te ispis istih
     * @param vehicleManager koristimo za pristup metodama iz klase VehicleManagerImpl, u ovom slučaju konkretno za printanje određene liste vozila
     * @param foundVehicles lista u koju spremamo vozila koja smo pronašli po odabranom kriteriju
     * @param criteriaName odabrana metoda traženja
     * @param criteriaValue vrijednost po kojoj tražimo
     */
    private static void presentSearchResults(VehicleManager vehicleManager, List<Vehicle> foundVehicles, String criteriaName, String criteriaValue) {

        if (!foundVehicles.isEmpty()) {
            System.out.println("Next vehicles are found by chosen criteria "
                    + criteriaName + " '" + criteriaValue + "': ");
            vehicleManager.printVehicles(foundVehicles);
        }
        else
            System.out.println("No single vehicle was found by criteria "
                    + criteriaName + " '" + criteriaValue + "'.");
    }

    /**
     * Metoda za uzimanje ulaznih String vrijednosti iz konzole te provjera da nije unesen prazan String
     * @param label služi za prosljeđivanje potrebnog teksta
     * @return unesena vrijednost ukoliko nije unesen prazan String
     */
    private static String getStringInput(Scanner scanner, String label, Logger LOGGER) {

        System.out.print(label);
        String selectedOperationInput = scanner.nextLine();
        if (selectedOperationInput == null || selectedOperationInput.trim().isBlank()) {
            LOGGER.warning("Empty input.");
            System.out.print("Input can not be empty. Please try again...");
            return getStringInput(scanner, label, LOGGER);
        }

        return selectedOperationInput;
    }


    /**
     * Metoda za uzimanje ulazne vrijednosti Integer, tako što poziva metodu getStringInput te String pretvara u Integer
     * @param label služi za prosljeđivanje potrebnog teksta
     * @return daje izlaznu vrijednost u Integeru
     */
    private static Integer getNumberInput(Scanner scanner, String label, Logger LOGGER) {

        String selectedOperationInput = getStringInput(scanner, label, LOGGER);

        try {
            return Integer.parseInt(selectedOperationInput);
        } catch (NumberFormatException e) {
            LOGGER.warning("Wrong input. " + e.getMessage());
            System.out.println("Wrong input. please try again, with one of offered operations...");
            return getNumberInput(scanner, label, LOGGER);
        }
    }


    private static void vehiclePrint(VehicleManager vehicleManager) {

        System.out.println("There are next vehicles currently in fleet:");
        vehicleManager.printAllVehicles();
    }

    private static void deleteVehicle(Scanner scanner, VehicleManager vehicleManager, Logger LOGGER) {

        String vin = getStringInput(scanner, "Unesite VIN vozila koje zelite izbrisati (-1 za izlazak): ", LOGGER);

        if (vin.equalsIgnoreCase("-1"))
            return;

        try {
            vehicleManager.deleteVehicle(vin, LOGGER);
        } catch (NoSuchVehicleException e) {
            LOGGER.warning("Vehicle deletion fail. " + e.getMessage());
            System.out.println("Vehicle deletion fail: " + e.getMessage());
            deleteVehicle(scanner, vehicleManager, LOGGER);
        }
    }


}
