package engine.comparators;

import engine.entity.JSONEntitiy.JSONContentCompleted;

import java.util.Comparator;

public class SortByCompletedAt implements Comparator<JSONContentCompleted> {

    @Override
    public int compare(JSONContentCompleted o1, JSONContentCompleted o2) {
        return o1.getCompletedAt().compareTo(o2.getCompletedAt());
    }
}
