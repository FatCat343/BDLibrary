package com.bdcourse.library.UI.StudentUI;

import com.bdcourse.library.library.Library;
import com.bdcourse.library.library.LibraryService;
import com.bdcourse.library.reader.student.Student;
import com.bdcourse.library.reader.student.StudentService;
import com.bdcourse.library.reader.student.department.Department;
import com.bdcourse.library.reader.student.department.DepartmentService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;


public class StudentForm extends VerticalLayout {
    TextField firstName = new TextField("FirstName");
    TextField lastName = new TextField("LastName");
    IntegerField code = new IntegerField("Student Code");
    ComboBox<Department> department = new ComboBox<>("Department");
    ComboBox<Library> library = new ComboBox<>("Library");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    private DepartmentForm departmentForm;
    private DepartmentService departmentService;
    private StudentService studentService;

    Binder<Student> studentBinder = new Binder<>(Student.class);
    private Student student;

    public StudentForm(DepartmentService departmentService, LibraryService libraryService, StudentService studentService) {
        addClassName("student-form");
        this.departmentService = departmentService;
        this.studentService = studentService;
        studentBinder.bindInstanceFields(this);
        studentBinder.forField(code)
                .withValidator(min -> min != null && min >= 100000000 && min <= 999999999, "Require 9 characters")
                .bind(Student::getCode, Student::setCode);
        studentBinder.forField(firstName)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Student::getFirstName, Student::setFirstName);
        studentBinder.forField(lastName)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Student::getLastName, Student::setLastName);
        department.setItems(departmentService.findAll());
        library.setItems(libraryService.findAll());

        departmentForm = new DepartmentForm(departmentService);
        departmentForm.addListener(DepartmentForm.saveEvent.class,  this::saveDepartment);
        departmentForm.addListener(DepartmentForm.deleteEvent.class,  this::deleteDepartment);
        departmentForm.addListener(DepartmentForm.closeEvent.class, e -> closeEditor());

        add(createFieldsLayout(), createButtonsLayout(), departmentForm);

        closeEditor();
    }

    private void closeEditor() {
        departmentForm.setDepartment(null);
        departmentForm.setVisible(false);
    }

    private void saveDepartment(DepartmentForm.saveEvent event) {
        departmentService.save(event.getDepartment());
        this.department.setItems(departmentService.findAll());
        closeEditor();
    }

    private void deleteDepartment(DepartmentForm.deleteEvent event) {
        departmentService.delete(event.getDepartment());
        this.department.setItems(departmentService.findAll());
        closeEditor();
    }

    private HorizontalLayout createFieldsLayout() {
        Button addDepartmentButton = new Button("Add Department");
        addDepartmentButton.addClickListener(click -> addDepartment());
        department.setRequired(true);
        library.setRequired(true);
        lastName.setRequired(true);
        firstName.setRequired(true);
        return new HorizontalLayout(firstName, lastName, code, department, library, addDepartmentButton);
    }

    private void addDepartment() {
        departmentForm.setDepartment(new Department());
        departmentForm.setVisible(true);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, student)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        studentBinder.addStatusChangeListener(e -> save.setEnabled(studentBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            studentBinder.writeBean(student);
            if (!studentService.exist(student)) {
                fireEvent(new saveEvent(this, student));
            }
            else {
                Notification.show("Save error: "+ "This item already exists").
                        setPosition(Notification.Position.TOP_START);
            }
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setStudent(Student student) {
        this.student = new Student(student);
        studentBinder.readBean(student);
    }

    public static abstract class StudentFormEvent extends ComponentEvent<StudentForm> {
        private Student student;

        protected StudentFormEvent(StudentForm source, Student student){
            super(source, false);
            this.student = student;
        }

        public Student getStudent() {
            return student;
        }
    }

    public static class saveEvent extends StudentFormEvent {
        saveEvent(StudentForm source, Student student) {
            super(source, student);
        }
    }

    public static class deleteEvent extends StudentFormEvent {
        deleteEvent(StudentForm source, Student student) {
            super(source, student);
        }
    }

    public static class closeEvent extends StudentFormEvent {
        closeEvent(StudentForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {

        return getEventBus().addListener(eventType, listener);
    }
}
