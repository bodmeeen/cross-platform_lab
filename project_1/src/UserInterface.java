import color.Color;

import java.util.Scanner;

import static color.Color.*;

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