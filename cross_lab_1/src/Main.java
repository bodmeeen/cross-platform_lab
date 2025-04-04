import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.Comparator;
import java.util.Date;

import color.Color;
import static color.Color.*;

import com.google.gson.*;
import java.io.*;


class Plant{
    private String name;
    private String lightNeed;
    private int waterNeed;
    private Date addedDate;

    public Plant(String name, String lightNeed, int waterNeed){
        this.name = name;
        this.lightNeed = lightNeed;
        this.waterNeed = waterNeed;
        this.addedDate = new Date();
    }

    public String getName(){return name;}
    public String getLightNeed(){return lightNeed;}
    public int getWaterNeed(){return waterNeed;}
    public Date getAddedDate(){return addedDate;}

    public void setWaterNeed(int waterNeed){this.waterNeed = waterNeed;}
}


class PlantService {
    private final List<Plant> plants = new ArrayList<>();
    private static final String FILE_NAME = "plants.json";


    public void addPlant(String name, String lightNeed, int waterNeed) {
        plants.add(new Plant(name, lightNeed, waterNeed));
        System.out.println(Color.colorize(COLOR_GREEN, "Plant added"));
    }

    public void displayPlants() {
        if (plants.isEmpty()) {
            System.out.println(Color.colorize(COLOR_RED, "List is empty"));
            return;
        }
        for (Plant plant : plants) {
            System.out.println("Name: " + plant.getName() + ", Light: " + plant.getLightNeed()
                    + ", Watering: " + plant.getWaterNeed() + " Date of adding: " + plant.getAddedDate());
        }
    }


    public boolean updatePlants(String name, int newWaterNeed) {
        for (Plant plant : plants) {
            if (plant.getName().equalsIgnoreCase(name)) {
                plant.setWaterNeed(newWaterNeed);
                return true;
            }
        }
        return false;
    }


    public boolean deletePlant(String name) {
        return plants.removeIf(plant -> plant.getName().equalsIgnoreCase(name));
    }


    public void searchPlants(String query) {
        query = query.toLowerCase();
        for (Plant plant : plants) {
            if (plant.getName().toLowerCase().contains(query) || plant.getLightNeed().toLowerCase().contains(query)) {
                System.out.println("Found: " + plant.getName() + ", Light: " + plant.getLightNeed()
                        + ", Watering: " + plant.getWaterNeed() + " days");
            }
        }
    }


    public void sortPlants(int option) {
        if (option == 1) {
            plants.sort(Comparator.comparing(Plant::getName));
            System.out.println(Color.colorize(COLOR_GREEN, "Plants sorted by name"));
        } else if (option == 2) {
            plants.sort(Comparator.comparing(Plant::getAddedDate));
            System.out.println(Color.colorize(COLOR_GREEN, "Plants sorted by date"));
        } else {
            System.out.println(Color.colorize(COLOR_RED, "Wrong choice"));
        }
    }


    public void savePlantsToFile() {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            Gson gson = new Gson();
            gson.toJson(plants, writer);
            System.out.println(Color.colorize(COLOR_GREEN, "Plants saved to file"));
        } catch (IOException e) {
            System.out.println(Color.colorize(COLOR_RED, "Error saving plants to file"));
        }
    }


    public void loadPlantsFromFile() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            Gson gson = new Gson();
            Plant[] plantArray = gson.fromJson(reader, Plant[].class);
            plants.clear();
            plants.addAll(Arrays.asList(plantArray));
            System.out.println(Color.colorize(COLOR_GREEN, "Plants loaded from file"));
        } catch (FileNotFoundException e) {
            System.out.println(Color.colorize(COLOR_YELLOW, "No previous plant data found"));
        } catch (IOException e) {
            System.out.println(Color.colorize(COLOR_RED, "Error loading plants from file"));
        }
    }
}

class UserInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final PlantService plantService = new PlantService();

    public void run() {
        plantService.loadPlantsFromFile();

        while (true) {
            System.out.println(Color.colorize(COLOR_GREEN, "\n == Plant manager =="));
            System.out.println("1. Add plant");
            System.out.println("2. Show plants");
            System.out.println("3. Update plant");
            System.out.println("4. Delete plant");
            System.out.println("5. Search plant");
            System.out.println("6. Sort plants");
            System.out.println("7. Save plants to file");
            System.out.println(Color.colorize(COLOR_RED, "8. Exit"));

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addPlant();
                case 2 -> plantService.displayPlants();
                case 3 -> updatePlant();
                case 4 -> deletePlant();
                case 5 -> searchPlants();
                case 6 -> sortPlants();
                case 7 -> plantService.savePlantsToFile();
                case 8 -> {
                    System.out.println(Color.colorize(COLOR_RED, "Exit from the program"));
                    return;
                }
                default -> System.out.println(Color.colorize(COLOR_RED, "Wrong choice, try again"));
            }
        }
    }

    private void addPlant() {
        System.out.print("Enter the name of the plant: ");
        String name = scanner.nextLine();
        System.out.print("Enter the amount of light: ");
        String lightNeed = scanner.nextLine();
        System.out.print("Enter the frequency of watering (in days): ");
        int waterNeed = scanner.nextInt();
        scanner.nextLine();
        plantService.addPlant(name, lightNeed, waterNeed);
    }

    private void updatePlant() {
        System.out.print(Color.colorize(COLOR_PURPLE, "Enter the name of the plant you want to update: "));
        String name = scanner.nextLine();
        System.out.print("Enter the new watering frequency: ");
        int waterNeed = scanner.nextInt();
        scanner.nextLine();
        if (plantService.updatePlants(name, waterNeed)) {
            System.out.println(Color.colorize(COLOR_GREEN, "Plant is updated"));
        } else {
            System.out.println(Color.colorize(COLOR_RED, "Plant is not found"));
        }
    }

    private void deletePlant() {
        System.out.print(Color.colorize(COLOR_PURPLE, "Enter the name of the plant you want to remove: "));
        String name = scanner.nextLine();
        if (plantService.deletePlant(name)) {
            System.out.println(Color.colorize(COLOR_GREEN, "The plant is successfully removed"));
        } else {
            System.out.println(Color.colorize(COLOR_RED, "The plant is not found"));
        }
    }

    private void searchPlants() {
        System.out.println(Color.colorize(COLOR_PURPLE, "Enter the name or lightning to search for: "));
        String query = scanner.nextLine();
        plantService.searchPlants(query);
    }

    private void sortPlants() {
        System.out.println(Color.colorize(COLOR_PURPLE, "Sort by: 1 - name, 2 - date added: "));
        int option = scanner.nextInt();
        scanner.nextLine();
        plantService.sortPlants(option);
    }
}

public class Main {
    public static void main(String[] args) {
        new UserInterface().run();
    }
}
