package engine.comparators;

import engine.entity.JSONEntitiy.JSONContentQuestion;

import java.util.Comparator;

public class SortById implements Comparator<JSONContentQuestion> {

    @Override
    public int compare(JSONContentQuestion o1, JSONContentQuestion o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }
}
