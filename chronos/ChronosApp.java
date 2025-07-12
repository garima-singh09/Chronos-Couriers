package chronos;

import java.util.Scanner;

import chronos.service.DispatchCenter;

public class ChronosApp {
    public static void main(String[] args) {
        DispatchCenter dispatchCenter = new DispatchCenter();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Chronos Couriers System! We are Happy To seve You Please Type 'help' to see available commands.");

        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Thank You For Using Our Services!");
                break;
            }

            try {
                dispatchCenter.servicesByChronos(input);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        // Close the scanner to avoid resource leaks
        scanner.close();
    }
}