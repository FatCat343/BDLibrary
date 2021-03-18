package com.bdcourse.library.UI.QueriesUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.edition.Edition;
import com.bdcourse.library.edition.EditionService;
import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.reader.ReaderService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

@Route(value = "FindEditionByReaderInLibrary", layout = MainView.class)
public class FindEditionByReaderInLibrary extends VerticalLayout {

    private DatePicker startDate = new DatePicker("From:");
    private DatePicker finishDate = new DatePicker("To:");
    private ComboBox<Reader> readerComboBox = new ComboBox<>("Select Reader");
    private ComboBox<String> assignedComboBox = new ComboBox<>("Choose case:");

    private Reader reader = null;
    private LocalDate start = null;
    private LocalDate finish = null;
    private Boolean inAssigned = null;

    private Grid<Edition> grid = new Grid<>(Edition.class);
    private EditionService editionService;

    public FindEditionByReaderInLibrary(EditionService editionService, ReaderService readerService) {
        this.editionService = editionService;
        setSizeFull();
        configureGrid();
        add(configureToolBar(), grid);
        readerComboBox.setItems(readerService.findAll());
        updateList();
    }

    private HorizontalLayout configureToolBar() {
        readerComboBox.setClearButtonVisible(true);
        readerComboBox.addValueChangeListener(event -> {
            reader = event.getValue();
            updateList();
        });
        assignedComboBox.setItems("Took In Assigned Library", "Took In Not Assigned Library");
        assignedComboBox.addValueChangeListener(event -> {
            String res = event.getValue();
            if (res.equals("Took In Assigned Library")) inAssigned = true;
            if (res.equals("Took In Not Assigned Library")) inAssigned = false;
            updateList();
        });
        //assignedComboBox.setValue("Took In Assigned Library");
        startDate.addValueChangeListener(event -> {
            start = event.getValue();
            updateList();
        });
        finishDate.addValueChangeListener(event -> {
            finish = event.getValue();
            updateList();
        });
        return new HorizontalLayout(readerComboBox, startDate, finishDate, assignedComboBox);
    }

    private void configureGrid() {
        //grid.setClassName("query-grid");
        grid.setSizeFull();
        grid.setColumns("code", "dateArrived", "dateLeft");
//        grid.addColumn(objects -> {
//            String firstName = (String) objects[0];
//            String lastName = (String) objects[1];
//            return firstName + " " + lastName;
//        }).setHeader("Reader").setSortProperty("reader");
//        grid.addColumn(objects -> {
//            Integer code = (Integer) objects[2];
//            return code;
//        }).setHeader("Edition Code").setSortProperty("code");
        //grid.setItems(readerService.findReaderByPublication(""));
    }

    private void updateList() {
        if (reader != null && start != null && finish != null && inAssigned != null) {
            if (inAssigned) grid.setItems(editionService.findEditionByReaderInAssignedLibrary(reader.getFirstName(), reader.getLastName(), start, finish));
            else grid.setItems(editionService.findEditionByReaderInNotAssignedLibrary(reader.getFirstName(), reader.getLastName(), start, finish));
        }
    }
}
