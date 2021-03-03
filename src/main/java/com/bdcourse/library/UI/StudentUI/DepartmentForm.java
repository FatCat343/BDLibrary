package com.bdcourse.library.UI.StudentUI;

import com.bdcourse.library.library.Library;
import com.bdcourse.library.library.LibraryService;
import com.bdcourse.library.reader.student.Student;
import com.bdcourse.library.reader.student.department.Department;
import com.bdcourse.library.reader.student.department.DepartmentService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class DepartmentForm extends VerticalLayout {
    TextField faculty = new TextField("Faculty");
    TextField university = new TextField("University");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    Binder<Department> departmentBinder = new Binder<>(Department.class);
    private Department department;

    public DepartmentForm(DepartmentService departmentService) {
        addClassName("department-form");
        //studentBinder.forField(code).withConverter(new DoubleToIntegerConverter()).bind(Student::getCode, Student::setCode);
        departmentBinder.bindInstanceFields(this);
        departmentBinder.forField(faculty)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Department::getFaculty, Department::setFaculty);
        departmentBinder.forField(university)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Department::getUniversity, Department::setUniversity);

        //department.setItems(departmentService.findAll());
        //library.setItems(libraryService.findAll());

        add(createFieldsLayout(), createButtonsLayout());

    }

    private HorizontalLayout createFieldsLayout() {
        return new HorizontalLayout(faculty, university);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, department)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        delete.setEnabled(false);

        departmentBinder.addStatusChangeListener(e -> save.setEnabled(departmentBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            departmentBinder.writeBean(department);
            fireEvent(new saveEvent(this, department));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setDepartment(Department department) {
        this.department = department;
        departmentBinder.readBean(department);
    }

    public static abstract class DepartmentFormEvent extends ComponentEvent<DepartmentForm> {
        private Department department;

        protected DepartmentFormEvent(DepartmentForm source, Department department){
            super(source, false);
            this.department = department;
        }

        public Department getDepartment() {
            return department;
        }
    }

    public static class saveEvent extends DepartmentFormEvent {
        saveEvent(DepartmentForm source, Department department) {
            super(source, department);
        }
    }

    public static class deleteEvent extends DepartmentFormEvent {
        deleteEvent(DepartmentForm source, Department department) {
            super(source, department);
        }
    }

    public static class closeEvent extends DepartmentFormEvent {
        closeEvent(DepartmentForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {

        return getEventBus().addListener(eventType, listener);
    }
}
