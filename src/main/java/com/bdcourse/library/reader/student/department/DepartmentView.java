package com.bdcourse.library.reader.student.department;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.klaudeta.PaginatedGrid;

@Route("departments")
public class DepartmentView extends VerticalLayout {
    private DepartmentService departmentService;
    private Grid<Department> grid = new Grid<>(Department.class);

    public DepartmentView(DepartmentService departmentService) {
        this.departmentService = departmentService;
        setSizeFull();
        configureGrid();
        add(grid);

        updateGrid();
    }
    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("faculty", "university");
        grid.setItems(departmentService.findAll());
    }

    private void updateGrid(){
        grid.setItems(departmentService.findAll());
    }
}
