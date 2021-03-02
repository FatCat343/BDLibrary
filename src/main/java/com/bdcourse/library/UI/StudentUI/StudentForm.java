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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;


public class StudentForm extends VerticalLayout {
    TextField firstName = new TextField("FirstName");
    TextField lastName = new TextField("LastName");
    NumberField code = new NumberField("Student Code");
    ComboBox<Department> department = new ComboBox<>("Department");
    ComboBox<Library> library = new ComboBox<>("Library");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    Binder<Student> studentBinder = new Binder<>(Student.class);
    private Student student;

    public StudentForm(DepartmentService departmentService, LibraryService libraryService) {
        addClassName("student-form");
        studentBinder.forField(code).withConverter(new DoubleToIntegerConverter()).bind(Student::getCode, Student::setCode);
        studentBinder.bindInstanceFields(this);

        department.setItems(departmentService.findAll());
        library.setItems(libraryService.findAll());

        add(createFieldsLayout(), createButtonsLayout());

    }

    private HorizontalLayout createFieldsLayout() {
        return new HorizontalLayout(firstName, lastName, code, department, library);
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
            fireEvent(new saveEvent(this, student));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setStudent(Student student) {
        this.student = student;
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
