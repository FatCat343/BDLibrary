package com.bdcourse.library.UI.QueriesUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.reader.ReaderService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "FindReaderExpired", layout = MainView.class)
public class FindReaderExpired extends VerticalLayout {
    private ReaderService readerService;
    private Grid<Reader> grid = new Grid<>(Reader.class);

    public FindReaderExpired(ReaderService readerService) {
        this.readerService = readerService;
        setSizeFull();
        configureGrid();
        add(grid);
        updateList();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName");
        grid.addColumn(reader -> {
            Library library = reader.getLibrary();
            return library.getAddress();
        }).setHeader("Assigned Library").setSortProperty("library");
    }

    private void updateList() {
        grid.setItems(readerService.findReaderExpired());
    }

}
