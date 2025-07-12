package chronos.model;

public class Rider {
    private String id;
    private boolean online;
    private boolean busy;

    public Rider(String id) {
        this.id = id;
        this.online = true; // Riders are online by default
        this.busy = false;  // Riders start as not busy
    }

    public String getId() {
        return id;
    }

    public boolean isOnline() {
        return online;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    @Override
    public String toString() {
        return "Rider ID: " + id + " [Online: " + online + ", Busy: " + busy + "]";
    }
}