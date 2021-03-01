package com.bdcourse.library.UI.StudentUI;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class StudentFilter extends HorizontalLayout {
    private TextField firstName = new TextField();
    private TextField lastName = new TextField();
    private TextField code = new TextField();
    private TextField library = new TextField();
    private TextField department = new TextField();

    public StudentFilter() {
        configureFilter(firstName, "FirstName filter");
        configureFilter(lastName, "LastName Filter");
        configureFilter(code, "Student Code Filter");
        configureFilter(library, "Library Address Filter");
        configureFilter(department, "Department Filter");
        add(firstName, lastName, code, library, department);
    }

    private void configureFilter(TextField field, String name) {
        field.setPlaceholder(name);
        field.setClearButtonVisible(true);
        field.setValueChangeMode(ValueChangeMode.LAZY);
        //field.addValueChangeListener()
    }
}
