package com.bdcourse.library.UI.QueriesUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.edition.Edition;
import com.bdcourse.library.edition.EditionService;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.library.LibraryService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "FindGivenEditionByPosition", layout = MainView.class)
public class FindGivenEditionByPosition extends VerticalLayout {
    private ComboBox<Library> libraries = new ComboBox<>("Select address:");
    private IntegerField roomField = new IntegerField("Set room number:");
    private IntegerField rackField = new IntegerField("Set rack number:");
    private IntegerField shelfField = new IntegerField("Set shelf number:");

    private String address = null;
    private Integer room = null;
    private Integer rack = null;
    private Integer shelf = null;

    private Grid<Edition> grid = new Grid<>(Edition.class);
    private EditionService editionService;

    public FindGivenEditionByPosition(EditionService editionService, LibraryService libraryService) {
        this.editionService = editionService;
        setSizeFull();
        configureGrid();
        add(configureToolBar(), grid);
        libraries.setItems(libraryService.findAll());
        updateList();
    }

    private HorizontalLayout configureToolBar() {
        roomField.setClearButtonVisible(true);
        roomField.setValueChangeMode(ValueChangeMode.LAZY);
        roomField.addValueChangeListener(event -> {
            room = event.getValue();
            updateList();
        });

        rackField.setClearButtonVisible(true);
        rackField.setValueChangeMode(ValueChangeMode.LAZY);
        rackField.addValueChangeListener(event -> {
            rack = event.getValue();
            updateList();
        });

        shelfField.setClearButtonVisible(true);
        shelfField.setValueChangeMode(ValueChangeMode.LAZY);
        shelfField.addValueChangeListener(event -> {
            shelf = event.getValue();
            updateList();
        });

        libraries.setClearButtonVisible(true);
        libraries.addValueChangeListener(event -> {
            if (event.getValue() != null) address = event.getValue().getAddress();
            updateList();
        });
        return new HorizontalLayout(libraries, roomField, rackField, shelfField);
    }

    private void configureGrid() {
        grid.setSizeFull();
//        grid.addColumn(objects -> {
//            String firstName = (String) objects[0];
//            String lastName = (String) objects[1];
//            return firstName + " " + lastName;
//        }).setHeader("Reader").setSortProperty("reader");
//        grid.addColumn(objects -> {
//            Integer code = (Integer) objects[2];
//            return code;
//        }).setHeader("Edition Code").setSortProperty("code");
        grid.setColumns("code", "dateArrived", "dateLeft");
        //grid.setItems(readerService.findReaderByPublication(""));
    }

    private void updateList() {
        if (address != null && room != null && shelf != null && rack != null) {
            grid.setItems(editionService.findGivenEditionByPosition(address, room, rack, shelf));
        }
    }
}
