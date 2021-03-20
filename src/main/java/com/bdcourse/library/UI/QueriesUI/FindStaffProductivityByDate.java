package com.bdcourse.library.UI.QueriesUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.staff.StaffService;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.math.BigInteger;
import java.time.LocalDate;

@Route(value = "FindStaffProductivityByDate", layout = MainView.class)
public class FindStaffProductivityByDate extends VerticalLayout {
    private DatePicker startDate = new DatePicker("From:");
    private DatePicker finishDate = new DatePicker("To:");

    private LocalDate start = null;
    private LocalDate finish = null;

    private StaffService staffService;
    private Grid<Object[]> grid = new Grid<>();


    public FindStaffProductivityByDate(StaffService staffService) {
        this.staffService = staffService;
        setSizeFull();
        configureGrid();
        add(configureToolBar(), grid);
        updateList();
    }

    private HorizontalLayout configureToolBar() {
        startDate.addValueChangeListener(event -> {
            start = event.getValue();
            updateList();
        });
        finishDate.addValueChangeListener(event -> {
            finish = event.getValue();
            updateList();
        });
        return new HorizontalLayout(startDate, finishDate);
    }

    private void configureGrid() {
        //grid.setClassName("query-grid");
        grid.setSizeFull();
        grid.addColumn(objects -> {
            String firstName = (String) objects[0];
            String lastName = (String) objects[1];
            return firstName + " " + lastName;
        }).setHeader("Staff Name").setSortProperty("name");
        grid.addColumn(objects -> {
            BigInteger amount = (BigInteger) objects[2];
            return amount;
        }).setHeader("Amount of Served Readers").setSortProperty("amount");
        //grid.setItems(readerService.findReaderByPublication(""));
    }

    private void updateList() {
        if (start != null && finish != null) {
            grid.setItems(staffService.findStaffProductivityByDate(start, finish));
        }
    }
}
