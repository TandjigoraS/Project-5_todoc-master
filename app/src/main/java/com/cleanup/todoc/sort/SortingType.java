package com.cleanup.todoc.sort;

import androidx.annotation.NonNull;

import com.cleanup.todoc.pojo.Task;

import java.util.Comparator;

public enum SortingType {
    /**
     * Sort alphabetical by name
     */
    ALPHABETICAL(
            (o1, o2) -> {
                return o1.getProject().getName().compareTo(o2.getProject().getName());
            }
    ),
    /**
     * Inverted sort alphabetical by name
     */
    ALPHABETICAL_INVERTED(
            (o1, o2) -> {
                return o2.getProject().getName().compareTo(o1.getProject().getName());
            }
    ),

    /**
     * Lastly created first
     */
    RECENT_FIRST(
            (o1, o2) ->  (int) (o2.getLocalDateTime().compareTo(o1.getLocalDateTime()))
            ),
    /**
     * First created first
     */
    OLD_FIRST(
            (o1, o2) -> (int) (o1.getLocalDateTime().compareTo(o2.getLocalDateTime()))
            );



    private final Comparator<Task> mTaskComparator;

    SortingType(Comparator<Task> taskComparator) {
        mTaskComparator = taskComparator;
    }

    @NonNull
    public Comparator<Task> getTaskComparator() {
        return mTaskComparator;
    }
}
