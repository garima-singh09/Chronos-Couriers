package chronos.util;

import chronos.model.Package;
import java.util.Comparator;

public class PackageComparator implements Comparator<Package> {
    @Override
    public int compare(Package p1, Package p2) {
        // Prioritize EXPRESS packages over STANDARD
        if (!p1.getType().equalsIgnoreCase(p2.getType())) {
            return p1.getType().equalsIgnoreCase("EXPRESS") ? -1 : 1;
        }

        // Deadline Priority
        int deadlineComparison = Long.compare(p1.getDeadline(), p2.getDeadline());
        if (deadlineComparison != 0) {
            return deadlineComparison;
        }

        // Compare by order time if deadlines are the same
        return Long.compare(p1.getOrderTime(), p2.getOrderTime());
    }
}