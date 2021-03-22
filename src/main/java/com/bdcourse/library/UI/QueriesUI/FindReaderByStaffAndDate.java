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
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

@Route(value = "FindReaderByStaffAndDate", layout = MainView.class)
public class FindReaderByStaffAndDate extends VerticalLayout {
    private DatePicker startDate = new DatePicker("From:");
    private DatePicker finishDate = new DatePicker("To:");
    private ComboBox<Staff> staffComboBox = new ComboBox<>("Select Staff:");

    private Integer staffId = null;

    private ReaderService readerService;
    private Grid<Reader> grid = new Grid<>(Reader.class);


    public FindReaderByStaffAndDate(ReaderService readerService, StaffService staffService) {
        this.readerService = readerService;
        setSizeFull();
        configureGrid();
        add(configureToolBar(), grid);
        staffComboBox.setItems(staffService.findAll());
        updateList();
    }

    private HorizontalLayout configureToolBar() {
        staffComboBox.setClearButtonVisible(true);
        staffComboBox.setRequired(true);
        staffComboBox.addValueChangeListener(event -> {
            if (event.getValue() != null) staffId = event.getValue().getId();
            updateList();
        });

        startDate.addValueChangeListener(event -> {
            LocalDate selected = event.getValue();
            if (selected != null) {
                finishDate.setMin(selected.plusDays(1));
            } else {
                finishDate.setMin(null);
            }
            updateList();
        });
        finishDate.addValueChangeListener(event -> {
            LocalDate selected = event.getValue();
            if (selected != null) {
                startDate.setMax(selected.minusDays(1));
            } else {
                startDate.setMax(null);
            }
            updateList();
        });
        return new HorizontalLayout(staffComboBox, startDate, finishDate);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName");
    }

    private void updateList() {
        if (staffId != null) {
            grid.setItems(readerService.findReaderByStaffAndDate(staffId, startDate.getValue(),
                    finishDate.getValue()));
        }
    }
}
