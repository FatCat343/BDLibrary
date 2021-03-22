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
import com.vaadin.flow.router.Route;

import java.time.LocalDate;

@Route(value = "FindEditionByReaderInLibrary", layout = MainView.class)
public class FindEditionByReaderInLibrary extends VerticalLayout {
    private DatePicker startDate = new DatePicker("From:");
    private DatePicker finishDate = new DatePicker("To:");
    private ComboBox<Reader> readerComboBox = new ComboBox<>("Select Reader");
    private ComboBox<String> assignedComboBox = new ComboBox<>("Choose case:");

    private Reader reader = null;
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
        startDate.addValueChangeListener(event -> {
            LocalDate selected = event.getValue();
            LocalDate finish = finishDate.getValue();
            if (selected != null) {
                finishDate.setMin(selected.plusDays(1));
            } else {
                finishDate.setMin(null);
            }
            updateList();
        });
        finishDate.addValueChangeListener(event -> {
            LocalDate selected = event.getValue();
            LocalDate start = startDate.getValue();
            if (selected != null) {
                startDate.setMax(selected.minusDays(1));
            } else {
                startDate.setMax(null);
            }
            updateList();
        });
        return new HorizontalLayout(readerComboBox, startDate, finishDate, assignedComboBox);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("code", "dateArrived", "dateLeft");
    }

    private void updateList() {
        if (reader != null && inAssigned != null) {
            grid.setItems(editionService.findEditionByReaderInLibrary(reader.getId(), startDate.getValue(),
                    finishDate.getValue(), inAssigned));
        }
    }
}
