package com.bdcourse.library.UI.StudentUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.library.LibraryService;
import com.bdcourse.library.reader.student.Student;
import com.bdcourse.library.reader.student.StudentService;
import com.bdcourse.library.reader.student.department.Department;
import com.bdcourse.library.reader.student.department.DepartmentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.SortOrder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route(value = "students", layout = MainView.class)
    public class StudentView extends VerticalLayout {
        private StudentService studentService;
        private PaginatedGrid<Student> grid = new PaginatedGrid<>(Student.class);

        private TextField firstName = new TextField();
        private TextField lastName = new TextField();
        private IntegerField code = new IntegerField();
        private TextField library = new TextField();
        private TextField department = new TextField();
        private StudentForm form;

        ConfigurableFilterDataProvider<Student, Void, StudentFilter> customDataProvider;
        StudentFilter studentFilter;

        public StudentView(StudentService studentService, LibraryService libraryService, DepartmentService departmentService) {
            this.studentService = studentService;
            studentFilter = new StudentFilter();
            addClassName("student-view");
            setSizeFull();
            DataProvider<Student, StudentFilter> dataProvider = createDataProvider(studentService);
            customDataProvider = dataProvider.withConfigurableFilter();
            customDataProvider.setFilter(studentFilter);
            HorizontalLayout toolbar = configureToolBar();
            configureGrid();
            form = new StudentForm(departmentService, libraryService);
            form.addListener(StudentForm.saveEvent.class, this::saveStudent);
            form.addListener(StudentForm.deleteEvent.class, this::deleteStudent);
            form.addListener(StudentForm.closeEvent.class, e -> closeEditor());
            add(toolbar, form, grid);

            closeEditor();
        }

        private HorizontalLayout configureToolBar() {
            configureFilter(firstName, "FirstName filter");
            configureFilter(lastName, "LastName Filter");
            configureFilter(library, "Library Address Filter");
            configureFilter(department, "Department Filter");

            code.setPlaceholder("Code Filter");
            code.setClearButtonVisible(true);
            code.setValueChangeMode(ValueChangeMode.LAZY);

            firstName.addValueChangeListener(event -> {
                if ((event.getValue() != null) && (!event.getValue().equals(""))) studentFilter.setFirstName(event.getValue());
                else studentFilter.setFirstName(null);
                customDataProvider.refreshAll();
            });
            lastName.addValueChangeListener(event -> {
                if ((event.getValue() != null) && (!event.getValue().equals(""))) studentFilter.setLastName(event.getValue());
                else studentFilter.setLastName(null);
                customDataProvider.refreshAll();
            });
            code.addValueChangeListener(event -> {
                if (event.getValue() != null) studentFilter.setCode(String.valueOf(event.getValue()));
                else studentFilter.setCode(null);
                customDataProvider.refreshAll();
            });
            library.addValueChangeListener(event -> {
                if ((event.getValue() != null) && (!event.getValue().equals(""))) studentFilter.setLibrary(event.getValue());
                else studentFilter.setLibrary(null);
                customDataProvider.refreshAll();
            });
            department.addValueChangeListener(event -> {
                if ((event.getValue() != null) && (!event.getValue().equals(""))) studentFilter.setDepartment(event.getValue());
                else studentFilter.setDepartment(null);

                customDataProvider.refreshAll();
            });

            Button addStudentButton = new Button("Add Student");
            addStudentButton.addClickListener(click -> addStudent());

            return new HorizontalLayout(firstName, lastName, code, department, library, addStudentButton);
        }

        void addStudent() {
            grid.asSingleSelect().clear();
            editStudent(new Student());
        }

        private void configureFilter(TextField field, String name) {
            field.setPlaceholder(name);
            field.setClearButtonVisible(true);
            field.setValueChangeMode(ValueChangeMode.LAZY);
        }

        private void configureGrid(){
            grid.addClassName("student-grid");
            grid.setSizeFull();
            grid.removeAllColumns();
            grid.addColumn(Student::getFirstName).setHeader("FirstName").setSortProperty("firstName");
            grid.addColumn(Student::getLastName).setHeader("LastName").setSortProperty("lastName");
            grid.addColumn(Student::getCode).setHeader("Code").setSortProperty("code");
            grid.addColumn(student -> {
                Department d = student.getDepartment();
                return d.getUniversity() + ", " + d.getFaculty();
            }).setHeader("Department").setSortProperty("department");
            grid.addColumn(student -> {
                Library l = student.getLibrary();
                return l.getAddress();
            }).setHeader("Assigned Library").setSortProperty("library");
            grid.setPageSize(10);
            grid.setDataProvider(customDataProvider);

            grid.asSingleSelect().addValueChangeListener(event -> editStudent(event.getValue()));
        }


        private void closeEditor() {
            form.setStudent(null);
            form.setVisible(false);
            removeClassName("editing");
        }

        private void saveStudent(StudentForm.saveEvent event) {
            Student student = studentService.save(event.getStudent());
            customDataProvider.refreshItem(student);
            closeEditor();
        }

        private void deleteStudent(StudentForm.deleteEvent event) {
            studentService.delete(event.getStudent());
            customDataProvider.refreshAll();
            closeEditor();
        }


    public void editStudent(Student student) {
        if (student == null) closeEditor();
        else {
            form.setStudent(student);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private DataProvider<Student, StudentFilter> createDataProvider(StudentService studentService) {
        DataProvider<Student, StudentFilter> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> {
                    Optional<StudentFilter> filter = query.getFilter();
                    List<StudentSort> sortOrders = new ArrayList<>();
                    for (SortOrder<String> queryOrder : query.getSortOrders()) {
                        StudentSort sort = getStudentService().createSort(queryOrder.getSorted(), queryOrder.getDirection() == SortDirection.DESCENDING);
                        sortOrders.add(sort);
                    }

                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    int page = offset/limit;
                    List<Student> students = getStudentService().fetch(page, limit,
                            filter.map(f -> f.getFirstName()).orElse(null),
                            filter.map(f -> f.getLastName()).orElse(null),
                            filter.map(f -> f.getCode()).orElse(null),
                            filter.map(f -> f.getLibrary()).orElse(null),
                            filter.map(f -> f.getDepartment()).orElse(null) ,
                            sortOrders);
                    return students.stream();
                },

                query -> {
                    Optional<StudentFilter> filter = query.getFilter();
                    return (int) getStudentService().getStudentCount(
                            filter.map(f -> f.getFirstName()).orElse(null),
                            filter.map(f -> f.getLastName()).orElse(null),
                            filter.map(f -> f.getCode()).orElse(null),
                            filter.map(f -> f.getLibrary()).orElse(null),
                            filter.map(f -> f.getDepartment()).orElse(null));
                });
        return dataProvider;
    }
    private StudentService getStudentService() {
        return studentService;
    }
}
