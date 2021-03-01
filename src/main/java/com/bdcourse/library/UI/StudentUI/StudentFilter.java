package com.bdcourse.library.UI.StudentUI;

public class StudentFilter {
    private String firstName = null;
    private String lastName = null;
    private String library = null;
    private String department = null;
    private String code = null;

    public StudentFilter() {
    }

    public StudentFilter(String firstName, String lastName, String library, String department, String code) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.library = library;
        this.department = department;
        this.code = code;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLibrary() {
        return library;
    }

    public void setLibrary(String library) {
        this.library = library;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
