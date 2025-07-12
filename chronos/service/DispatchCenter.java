package chronos.service;
import chronos.model.Package;
import chronos.model.Rider;
import chronos.model.AuditLog;
import chronos.util.PackageComparator;  

import java.util.*;

public class DispatchCenter {
    private Map<String, Rider> riders = new HashMap<>();
    private Map<String, Package> packages = new HashMap<>();
    private PriorityQueue<Package> pendingPackages = new PriorityQueue<>(new PackageComparator());
    private List<AuditLog> auditLogs = new ArrayList<>();

    public void servicesByChronos(String input) {
        String[] userChoice = input.trim().split(" ");
        if (userChoice.length == 0 || userChoice[0].isEmpty()) return;

        switch (userChoice[0].toLowerCase()) {
            case "help":
                printHelp();
                break;
            case "place-order":
                placeOrder(userChoice);
                break;
            case "update-rider":
                updateRider(userChoice);
                break;
            case "assign-packages":
                assignPackages();
                break;
            case "process-new-package":
                assignPackages();
                break;
            case "complete-delivery":
                completeDelivery(userChoice);
                break;
            case "get-status":
                getStatus(userChoice);
                break;
            case "get-deliveries-by-rider":
                getDeliveriesByRider(userChoice);
                break;
            case "get-missed-express":
                getMissedExpressPackages();
                break;
            default:
                System.out.println("Unknown command. Type 'help' for a list of commands.");
        }
    }


     private void printHelp() {
        System.out.println("Commands:");
        System.out.println(" place-order EXPRESS|STANDARD [fragile]");
        System.out.println(" update-rider [riderId] online|offline");
        System.out.println(" assign-packages");
        System.out.println(" complete-delivery [packageId]");
        System.out.println(" get-status rider|package [id]");
        System.out.println(" get-deliveries-by-rider [riderId] [hours]");
        System.out.println(" get-missed-express");
        System.out.println(" exit");
    }

    private void placeOrder(String[] tokens) {
        if (tokens.length < 2) {
            System.out.println("Usage: place-order EXPRESS|STANDARD [fragile]");
            return;
        }
        String type = tokens[1];
        boolean fragile = tokens.length > 2 && tokens[2].equalsIgnoreCase("fragile");
        Package newPackage = new Package(type, fragile);
        packages.put(newPackage.getId(), newPackage);
        pendingPackages.add(newPackage);
        auditLogs.add(new AuditLog("Order Placed", newPackage.getId()));
        System.out.println("Order placed: " + newPackage.getId());
    }

    private void updateRider(String[] tokens) {
        if (tokens.length < 3) {
            System.out.println("Usage: update-rider [riderId] online|offline");
            return;
        }
        String id = tokens[1];
        String status = tokens[2];
        Rider rider = riders.getOrDefault(id, new Rider(id));
        rider.setOnline(status.equalsIgnoreCase("online"));
        riders.put(id, rider);
        System.out.println("Rider " + rider.getId() + " is now " + (rider.isOnline() ? "online" : "offline"));
        assignPackages();
    }

    private void assignPackages() {
        for (Rider rider : riders.values()) {
            if (!rider.isOnline() || rider.isBusy()) continue;
            Package pkg = pendingPackages.poll();
            if (pkg == null) break;
            pkg.assignTo(rider);
            rider.setBusy(true);
            auditLogs.add(new AuditLog("Package Assigned", pkg.getId(), rider.getId()));
            System.out.println("Assigned package " + pkg.getId() + " to rider " + rider.getId());
        }
    }

    private void completeDelivery(String[] tokens) {
        if (tokens.length < 2) {
            System.out.println("Usage: complete-delivery [packageId]");
            return;
        }
        String packageId = tokens[1];
        Package pkg = packages.get(packageId);
        if (pkg == null || pkg.getRider() == null) {
            System.out.println("Invalid package or package not assigned.");
            return;
        }
        Rider rider = pkg.getRider();
        rider.setBusy(false);
        pkg.markDelivered();
        auditLogs.add(new AuditLog("Delivery Completed", packageId, rider.getId()));
        System.out.println("Package " + packageId + " delivered by rider " + rider.getId());
    }

    private void getStatus(String[] tokens) {
        if (tokens.length < 3) {
            System.out.println("Usage: get-status rider|package [id]");
            return;
        }
        String type = tokens[1];
        String id = tokens[2];
        if (type.equalsIgnoreCase("rider")) {
            Rider rider = riders.get(id);
            if (rider == null) {
                System.out.println("Rider not found: " + id);
                return;
            }
            System.out.println(rider);
        } else if (type.equalsIgnoreCase("package")) {
            Package pkg = packages.get(id);
            if (pkg == null) {
                System.out.println("Package not found: " + id);
                return;
            }
            System.out.println(pkg);
        } else {
            System.out.println("Invalid type. Use 'rider' or 'package'.");
        }
    }

    private void getDeliveriesByRider(String[] tokens) {
        if (tokens.length < 3) {
            System.out.println("Usage: get-deliveries-by-rider [riderId] [hours]");
            return;
        }
        String riderId = tokens[1];
        long hours;
        try {
            hours = Long.parseLong(tokens[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid hours value.");
            return;
        }
        long now = System.currentTimeMillis();
        long window = now - hours * 3600 * 1000;
        boolean found = false;
        for (AuditLog log : auditLogs) {
            if ("Delivery Completed".equals(log.getEvent()) && riderId.equals(log.getRiderId())) {
                if (log.getTimestamp() >= window) {
                    System.out.println(log);
                    found = true;
                }
            }
        }
        if (!found) {
            System.out.println("No deliveries found for rider " + riderId + " in the last " + hours + " hours.");
        }
    }

    private void getMissedExpressPackages() {
        boolean missedAny = false;
        for (Package p : packages.values()) {
            if ("EXPRESS".equals(p.getType()) && "Delivered".equals(p.getStatus())) {
                if (p.getDeliveryTime() > p.getDeadline()) {
                    System.out.println("Missed: " + p);
                    missedAny = true;
                }
            }
        }
        if (!missedAny) {
            System.out.println("No missed EXPRESS deliveries.");
        }
    }
}