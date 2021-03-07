package com.bdcourse.library.UI.QueriesUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.reader.ReaderService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.vaadin.klaudeta.PaginatedGrid;

@Route(value = "FindReadersByEdition", layout = MainView.class)
public class FindReadersByEdition extends VerticalLayout {
    private ReaderService readerService;
    private Grid<Reader> grid = new Grid<>(Reader.class);
    //private String name;
    private TextField publicationNameField = new TextField("Publication Name");


    public FindReadersByEdition(ReaderService readerService) {
        this.readerService = readerService;
        //addClassName("reader-layout");
        setSizeFull();
        configureGrid();
        add(configureToolBar(), grid);
        updateList("");
    }

    private TextField configureToolBar() {
        publicationNameField.setPlaceholder("Enter Name of Publication");
        publicationNameField.setClearButtonVisible(true);
        publicationNameField.setValueChangeMode(ValueChangeMode.LAZY);
        publicationNameField.addValueChangeListener(event -> updateList(event.getValue()));
        return publicationNameField;
    }

    private void configureGrid() {
        //grid.setClassName("query-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName");
        grid.addColumn(reader -> {
            Library library = reader.getLibrary();
            return library.getAddress();
        }).setHeader("Assigned Library").setSortProperty("library");
        //grid.setItems(readerService.findReaderByPublication(""));

    }

    private void updateList(String name) {
        grid.setItems(readerService.findReaderByPublication(name));
    }


}
