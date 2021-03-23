package com.bdcourse.library.UI.LibraryUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.library.LibraryService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "library", layout = MainView.class)
public class LibraryView extends VerticalLayout {
    private LibraryService libraryService;
    private Grid<Library> grid = new Grid<>(Library.class);

    private LibraryForm form;

    public LibraryView(LibraryService libraryService) {
        this.libraryService = libraryService;
        setSizeFull();
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new LibraryForm(libraryService);
        form.addListener(LibraryForm.saveEvent.class, this::saveLibrary);
        form.addListener(LibraryForm.deleteEvent.class, this::deleteLibrary);
        form.addListener(LibraryForm.closeEvent.class, e -> closeEditor());

        add(toolbar, form, grid);

        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();;
        grid.addColumn(Library::getAddress).setHeader("Address").setSortProperty("address");
        grid.asSingleSelect().addValueChangeListener(event -> editLibrary(event.getValue()));
    }

    private HorizontalLayout configureToolBar() {
        Button addLibraryButton = new Button("Add Storage");
        addLibraryButton.addClickListener(click -> addLibrary());
        return new HorizontalLayout(addLibraryButton);
    }

    private void addLibrary() {
        grid.asSingleSelect().clear();
        form.setLibrary(new Library());
        form.setVisible(true);
    }

    private void updateList() {
        grid.setItems(libraryService.findAll());
    }

    private void saveLibrary(LibraryForm.saveEvent event) {
        Library library = libraryService.save(event.getLibrary());
        updateList();
        closeEditor();
    }

    private void deleteLibrary(LibraryForm.deleteEvent event) {
        libraryService.delete(event.getLibrary());
        updateList();
        closeEditor();
    }

    private void closeEditor(){
        grid.asSingleSelect().clear();
        form.setLibrary(null);
        form.setVisible(false);
    }

    private void editLibrary(Library library) {
        if (library == null) closeEditor();
        else {
            form.setLibrary(library);
            form.setVisible(true);
        }
    }

}
