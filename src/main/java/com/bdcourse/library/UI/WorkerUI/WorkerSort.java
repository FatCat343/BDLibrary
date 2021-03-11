package com.bdcourse.library.UI.WorkerUI;

public class WorkerSort {
    String property;
    boolean descending;

    public WorkerSort(String property, boolean descending) {
        this.property = property;
        this.descending = descending;
    }

    public String getPropertyName() {
        return property;
    }

    public void setPropertyName(String property) {
        this.property = property;
    }

    public boolean isDescending() {
        return descending;
    }

    public void setDescending(boolean descending) {
        this.descending = descending;
    }
}
