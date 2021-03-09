package com.bdcourse.library.UI.WorkerUI;

public class WorkerFilter {
    String profession;
    String company;
    String firstName;
    String lastName;
    String library;

    public WorkerFilter() {
    }

    public WorkerFilter(String profession, String company, String firstName, String lastName, String library) {
        this.profession = profession;
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.library = library;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
}
