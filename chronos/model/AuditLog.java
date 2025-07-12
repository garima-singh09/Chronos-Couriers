package chronos.model;


public class AuditLog {
    private String event;
    private String packageId;
    private String riderId;
    private long timestamp;

    public AuditLog(String event, String packageId) {
        this.event = event;
        this.packageId = packageId;
        this.riderId = null;
        this.timestamp = System.currentTimeMillis();
    }

      public AuditLog(String event, String packageId, String riderId) {
        this.event = event;
        this.packageId = packageId;
        this.riderId = riderId;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        StringBuilder logEntry = new StringBuilder();
        logEntry.append(timestamp).append(": ").append(event).append(" - Package: ").append(packageId);
        if (riderId != null) {
            logEntry.append(", Rider: ").append(riderId);
        }
        return logEntry.toString();
    }

    public String getEvent() {
    return event;
}

public String getPackageId() {
    return packageId;
}

public String getRiderId() {
    return riderId;
}

public long getTimestamp() {
    return timestamp;
}
}