package com.bdcourse.library.UI.QueriesUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.edition.EditionService;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

@Route(value = "FindEditionByArrivedDate", layout = MainView.class)
public class FindEditionByArrivedDate extends VerticalLayout {
    private DatePicker startDate = new DatePicker("From:");
    private DatePicker finishDate = new DatePicker("To:");

    private LocalDate start = null;
    private LocalDate finish = null;

    private EditionService editionService;
    private Grid<Object[]> grid = new Grid<>();

    public FindEditionByArrivedDate(EditionService editionService) {
        this.editionService = editionService;
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
        grid.setSizeFull();
        grid.addColumn(objects -> objects[0]).setHeader("Edition Code").setSortProperty("code");
        grid.addColumn(objects -> objects[1]).setHeader("Edition Title").setSortProperty("title");
        grid.addColumn(objects -> objects[2] + " " + objects[3]).setHeader("Publication Author").setSortProperty("author");
    }

    private void updateList() {
        if (start != null && finish != null) {
            grid.setItems(editionService.findEditionByArrivedDate(start, finish));
        }
    }
}
