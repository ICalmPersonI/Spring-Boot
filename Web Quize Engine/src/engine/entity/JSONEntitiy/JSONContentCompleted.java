package engine.entity.JSONEntitiy;

import java.util.Comparator;

public class JSONContentCompleted {
    private int id;
    private String completedAt;

    public JSONContentCompleted(int id, String completedAt) {
        this.id = id;
        this.completedAt = completedAt;
    }

    public int getId() {
        return id;
    }

    public String getCompletedAt() {
        return completedAt;
    }
}

