package com.bdcourse.library.UI.QueriesUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.reader.ReaderService;
import com.bdcourse.library.staff.Staff;
import com.bdcourse.library.staff.StaffService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

@Route(value = "FindReaderByStaffAndDate", layout = MainView.class)
public class FindReaderByStaffAndDate extends VerticalLayout {
    private DatePicker startDate = new DatePicker("From:");
    private DatePicker finishDate = new DatePicker("To:");
    private ComboBox<Staff> staffComboBox = new ComboBox<>("Select Staff:");

    private Integer staffId = null;
    private LocalDate start = null;
    private LocalDate finish = null;

    private ReaderService readerService;
    private Grid<Reader> grid = new Grid<>(Reader.class);


    public FindReaderByStaffAndDate(ReaderService readerService, StaffService staffService) {
        this.readerService = readerService;
        //addClassName("reader-layout");
        setSizeFull();
        configureGrid();
        add(configureToolBar(), grid);
        staffComboBox.setItems(staffService.findAll());
        updateList();
    }

    private HorizontalLayout configureToolBar() {
        staffComboBox.setClearButtonVisible(true);
        staffComboBox.addValueChangeListener(event -> {
            if (event.getValue() != null) staffId = event.getValue().getId();
            updateList();
        });

        startDate.addValueChangeListener(event -> {
            start = event.getValue();
            updateList();
        });
        finishDate.addValueChangeListener(event -> {
            finish = event.getValue();
            updateList();
        });
        return new HorizontalLayout(staffComboBox, startDate, finishDate);
    }

    private void configureGrid() {
        //grid.setClassName("query-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName");
        //grid.setItems(readerService.findReaderByPublication(""));
    }

    private void updateList() {
        if (staffId != null && start != null && finish != null) {
            grid.setItems(readerService.findReaderByStaffAndDate(staffId, start, finish));
        }
    }
}
