package com.bdcourse.library.UI.StudentUI;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class StudentToolBar extends HorizontalLayout {
    private TextField firstName = new TextField();
    private TextField lastName = new TextField();
    private TextField code = new TextField();
    private TextField library = new TextField();
    private TextField department = new TextField();
    private StudentFilter studentFilter;

    public StudentToolBar(StudentFilter studentFilter) {
        this.studentFilter = studentFilter;
        configureFilter(firstName, "FirstName filter");
        configureFilter(lastName, "LastName Filter");
        configureFilter(code, "Student Code Filter");
        configureFilter(library, "Library Address Filter");
        configureFilter(department, "Department Filter");
        firstName.addValueChangeListener(event -> {
            studentFilter.setFirstName(event.getValue());

        });
        add(firstName, lastName, code, library, department);
    }

    private void configureFilter(TextField field, String name) {
        field.setPlaceholder(name);
        field.setClearButtonVisible(true);
        field.setValueChangeMode(ValueChangeMode.LAZY);
    }
}
