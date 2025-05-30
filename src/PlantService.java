import color.Color;
import com.google.gson.Gson;

import java.io.*;
import java.util.*;
import static color.Color.*;

class PlantService {
    private final List<Plant> plants = new ArrayList<>();
    private static final String fileName = "plants.json";


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
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new Gson();
            gson.toJson(plants, writer);
            System.out.println(Color.colorize(COLOR_GREEN, "Plants saved to file"));
        } catch (IOException e) {
            System.out.println(Color.colorize(COLOR_RED, "Error saving plants to file"));
        }
    }


    public void loadPlantsFromFile() {
        try (Reader reader = new FileReader(fileName)) {
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