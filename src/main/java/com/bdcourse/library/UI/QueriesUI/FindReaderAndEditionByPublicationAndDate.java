package com.bdcourse.library.UI.QueriesUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.reader.ReaderService;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
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
    private LocalDate start = null;
    private LocalDate finish = null;

    private ReaderService readerService;
    private Grid<Object[]> grid = new Grid<>();


    public FindReaderAndEditionByPublicationAndDate(ReaderService readerService) {
        this.readerService = readerService;
        //addClassName("reader-layout");
        setSizeFull();
        configureGrid();
        add(configureToolBar(), grid);
        updateList();
    }

    private HorizontalLayout configureToolBar() {
        titleTextField.setPlaceholder("Enter Title of Publication");
        titleTextField.setClearButtonVisible(true);
        titleTextField.setValueChangeMode(ValueChangeMode.LAZY);
        titleTextField.addValueChangeListener(event -> {
            title = event.getValue();
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
        return new HorizontalLayout(titleTextField, startDate, finishDate);
    }

    private void configureGrid() {
        //grid.setClassName("query-grid");
        grid.setSizeFull();
        grid.addColumn(objects -> {
            String firstName = (String) objects[0];
            String lastName = (String) objects[1];
            return firstName + " " + lastName;
        }).setHeader("Reader").setSortProperty("reader");
        grid.addColumn(objects -> {
            Integer code = (Integer) objects[2];
            return code;
        }).setHeader("Edition Code").setSortProperty("code");
        //grid.setItems(readerService.findReaderByPublication(""));
    }

    private void updateList() {
        if (title != null && start != null && finish != null) {
            grid.setItems(readerService.findReaderAndEditionByPublicationAndDate(title, start, finish));
        }
    }
}
