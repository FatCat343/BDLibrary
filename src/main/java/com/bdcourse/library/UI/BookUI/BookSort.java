package com.bdcourse.library.UI.BookUI;

public class BookSort {
    String propertyName;
    boolean descending;

    public BookSort() {
    }

    public BookSort(String propertyName, boolean descending) {
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
