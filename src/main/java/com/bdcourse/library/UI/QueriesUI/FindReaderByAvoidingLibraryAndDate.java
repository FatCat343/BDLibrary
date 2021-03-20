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

@Route(value = "FindReaderByAvoidingLibraryAndDate", layout = MainView.class)
public class FindReaderByAvoidingLibraryAndDate extends VerticalLayout {
    private DatePicker startDate = new DatePicker("From:");
    private DatePicker finishDate = new DatePicker("To:");

    private LocalDate start = null;
    private LocalDate finish = null;

    private ReaderService readerService;
    private Grid<Reader> grid = new Grid<>(Reader.class);


    public FindReaderByAvoidingLibraryAndDate(ReaderService readerService) {
        this.readerService = readerService;
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
        grid.setColumns("firstName", "lastName");
        //grid.setItems(readerService.findReaderByPublication(""));
    }

    private void updateList() {
        if (start != null && finish != null) {
            grid.setItems(readerService.findReaderByAvoidingLibraryAndDate(start, finish));
        }
    }
}
