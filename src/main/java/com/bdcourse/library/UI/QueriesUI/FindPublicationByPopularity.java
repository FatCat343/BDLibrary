package com.bdcourse.library.UI.QueriesUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.publication.Publication;
import com.bdcourse.library.publication.PublicationService;
import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.reader.ReaderService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.math.BigInteger;

@Route(value = "FindPublicationByPopularity", layout = MainView.class)
public class FindPublicationByPopularity extends VerticalLayout {
    private PublicationService publicationService;
    private Grid<Object[]> grid = new Grid<>();
    //private String name;
    //private TextField publicationNameField = new TextField("Publication Name");


    public FindPublicationByPopularity(PublicationService publicationService) {
        this.publicationService = publicationService;
        //addClassName("reader-layout");
        setSizeFull();
        configureGrid();
        add(grid);
        updateList();
    }

//    private TextField configureToolBar() {
//        publicationNameField.setPlaceholder("Enter Name of Publication");
//        publicationNameField.setClearButtonVisible(true);
//        publicationNameField.setValueChangeMode(ValueChangeMode.LAZY);
//        publicationNameField.addValueChangeListener(event -> updateList(event.getValue()));
//        return publicationNameField;
//    }

    private void configureGrid() {
        //grid.setClassName("query-grid");
        grid.setSizeFull();
        grid.addColumn(objects -> {
            String title = (String) objects[0];
            return title;
        }).setHeader("Publication Title").setSortProperty("title");
        grid.addColumn(objects -> {
            String firstName = (String) objects[1];
            String lastName = (String) objects[2];
            return firstName + " " + lastName;
        }).setHeader("Publication Author").setSortProperty("author");
        grid.addColumn(objects -> {
            BigInteger count = (BigInteger) objects[3];
            return count.toString();
        }).setHeader("Pick Count").setSortProperty("count");
        //grid.setItems(readerService.findReaderByPublication(""));

    }

    private void updateList() {
        grid.setItems(publicationService.findPublicationsByPopularity());
    }


}