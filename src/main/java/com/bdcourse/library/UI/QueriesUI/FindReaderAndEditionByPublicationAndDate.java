package com.bdcourse.library.UI.QueriesUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.distribution.Distribution;
import com.bdcourse.library.distribution.DistributionService;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

@Route(value = "FindReaderAndEditionByPublicationAndDate", layout = MainView.class)
public class FindReaderAndEditionByPublicationAndDate extends VerticalLayout {
    private DatePicker startDate = new DatePicker("From:");
    private DatePicker finishDate = new DatePicker("To:");
    private TextField titleTextField = new TextField("Publication Title");

    private String title = null;

    private DistributionService distributionService;
    private Grid<Distribution> grid = new Grid<>();


    public FindReaderAndEditionByPublicationAndDate(DistributionService distributionService) {
        this.distributionService = distributionService;
        setSizeFull();
        configureGrid();
        add(configureToolBar(), grid);
        updateList();
    }

    private HorizontalLayout configureToolBar() {
        titleTextField.setPlaceholder("Enter Title of Publication");
        titleTextField.setClearButtonVisible(true);
        titleTextField.setRequired(true);
        titleTextField.setValueChangeMode(ValueChangeMode.LAZY);
        titleTextField.addValueChangeListener(event -> {
            title = event.getValue();
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
        return new HorizontalLayout(titleTextField, startDate, finishDate);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.addColumn(distribution -> distribution.getReader().toString()).setHeader("Reader").setSortProperty("reader");
        grid.addColumn(distribution -> distribution.getEdition().getCode()).setHeader("Edition Code").setSortProperty("code");
    }

    private void updateList() {
        if (title != null) {
            grid.setItems(distributionService.findReaderAndEditionByPublicationAndDate(title, startDate.getValue(),
                    finishDate.getValue()));
        }
    }
}
