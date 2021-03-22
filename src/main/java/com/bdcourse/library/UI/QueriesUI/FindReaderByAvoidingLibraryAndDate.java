package com.bdcourse.library.UI.QueriesUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.reader.ReaderService;
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
        return new HorizontalLayout(startDate, finishDate);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName");
    }

    private void updateList() {
        grid.setItems(readerService.findReaderByAvoidingLibraryAndDate(startDate.getValue(), finishDate.getValue()));
    }
}
