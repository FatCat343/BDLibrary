package com.bdcourse.library.UI.StudentUI;

public class StudentSort {
    String propertyName;
    boolean descending;

    public StudentSort(String propertyName, boolean descending) {
        this.propertyName = propertyName;
        this.descending = descending;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public boolean isDescending() {
        return descending;
    }

    public void setDescending(boolean descending) {
        this.descending = descending;
    }
}
