package com.bdcourse.library.UI.StudentUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.library.LibraryService;
import com.bdcourse.library.reader.student.Student;
import com.bdcourse.library.reader.student.StudentService;
import com.bdcourse.library.reader.student.department.Department;
import com.bdcourse.library.reader.student.department.DepartmentService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import org.vaadin.klaudeta.PaginatedGrid;

@Route(value = "students", layout = MainView.class)
public class StudentView extends VerticalLayout {
    private StudentService studentService;
    private PaginatedGrid<Student> grid = new PaginatedGrid<>(Student.class);
    private StudentFilter filter = new StudentFilter();
    private StudentForm form;

    DataProvider<Student, Void> dataProvider;

    public StudentView(StudentService studentService, LibraryService libraryService, DepartmentService departmentService) {
        this.studentService = studentService;
        addClassName("student-view");
        setSizeFull();
        //dataProvider = createDataProvider(studentService);

        configureGrid();
        form = new StudentForm(departmentService, libraryService);
        form.addListener(StudentForm.saveEvent.class, this::saveStudent);
        form.addListener(StudentForm.deleteEvent.class, this::deleteStudent);
        form.addListener(StudentForm.closeEvent.class, e -> closeEditor());

        add(filter, form, grid);
        updateList();

        closeEditor();
    }

    private void configureGrid(){
        grid.addClassName("student-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(student -> {
            Department d = student.getDepartment();
            return d.getUniversity() + ", " + d.getFaculty();
        }).setHeader("Department").setSortProperty("department");
        grid.setColumns("firstName", "lastName", "code");
        grid.addColumn(student -> {
            Library l = student.getLibrary();
            return l.getAddress();
        }).setHeader("Assigned Library").setSortProperty("library");
        grid.setPageSize(10);
        //grid.setDataProvider(dataProvider);
        grid.setItems(studentService.findAll());
        grid.asSingleSelect().addValueChangeListener(event -> editStudent(event.getValue()));
    }

    private void updateList(){
        grid.setItems(studentService.findAll());
    }

    private void closeEditor() {
        form.setStudent(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveStudent(StudentForm.saveEvent event) {
        Student student = studentService.save(event.getStudent());
        updateList();
        dataProvider.refreshItem(student);
        closeEditor();
    }

    private void deleteStudent(StudentForm.deleteEvent event) {
        studentService.delete(event.getStudent());
        updateList();
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
//    private DataProvider<Student, Void> createDataProvider(StudentService studentService) {
//        DataProvider<Student, Void> dataProvider = DataProvider.fromCallbacks(
//                query -> {
//
//                }
//        );
//        return dataProvider;
//    }
}
