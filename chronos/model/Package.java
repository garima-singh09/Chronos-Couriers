package chronos.model;

import java.util.UUID;

public class Package {
    private String id;
    private String type; // EXPRESS or STANDARD
    private boolean fragile;
    private long orderTime;
    private long deadline;
    private String status;
    private Rider rider;
    private long deliveryTime = -1; // Track when delivered

    public Package(String type, boolean fragile) {
        this.id = "P" + UUID.randomUUID().toString().substring(0, 6);
        this.type = type;
        this.fragile = fragile;
        this.orderTime = System.currentTimeMillis();
        this.deadline = orderTime + (type.equalsIgnoreCase("EXPRESS") ? 3600000 : 7200000);
        this.status = "Pending";
    }

    public String getId() {
        return id;
    }

    public long getDeadline() {
        return deadline;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public String getType() {
        return type;
    }

    public Rider getRider() {
        return rider;
    }

    public void assignTo(Rider rider) {
        this.rider = rider;
        this.status = "Assigned";
    }

    public void markDelivered() {
        this.status = "Delivered";
        this.deliveryTime = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return id + " [" + type + ", " + status + ", fragile=" + fragile + "]";
    }

    public String getStatus() {
        return status;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }
}