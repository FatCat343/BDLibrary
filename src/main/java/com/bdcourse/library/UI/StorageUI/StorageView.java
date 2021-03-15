package com.bdcourse.library.UI.StorageUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.UI.StaffUI.StaffForm;
import com.bdcourse.library.library.LibraryService;
import com.bdcourse.library.staff.Staff;
import com.bdcourse.library.storage.Storage;
import com.bdcourse.library.storage.StorageService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "storage", layout = MainView.class)
public class StorageView extends VerticalLayout {
    private StorageService storageService;
    private Grid<Storage> grid = new Grid<>(Storage.class);

    private StorageForm form;

    public StorageView(StorageService storageService, LibraryService libraryService) {
        this.storageService = storageService;
        setSizeFull();
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new StorageForm(libraryService);
        form.addListener(StorageForm.saveEvent.class, this::saveStorage);
        form.addListener(StorageForm.deleteEvent.class, this::deleteStorage);
        form.addListener(StorageForm.closeEvent.class, e -> closeEditor());

        add(toolbar, form, grid);

        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Storage::getRoomNumber).setHeader("Room Number").setSortProperty("room");
        grid.addColumn(Storage::getLibrary).setHeader("Library").setSortProperty("library");
        grid.asSingleSelect().addValueChangeListener(event -> editStorage(event.getValue()));
    }

    private HorizontalLayout configureToolBar() {
        Button addStorageButton = new Button("Add Storage");
        addStorageButton.addClickListener(click -> addStorage());
        return new HorizontalLayout(addStorageButton);
    }

    private void addStorage() {
        grid.asSingleSelect().clear();
        form.setStorage(new Storage());
        form.setVisible(true);
    }

    private void updateList() {
        grid.setItems(storageService.findAll());
    }

    private void saveStorage(StorageForm.saveEvent event) {
        Storage storage = storageService.save(event.getStorage());
        updateList();
        closeEditor();
    }

    private void deleteStorage(StorageForm.deleteEvent event) {
        storageService.delete(event.getStorage());
        updateList();
        closeEditor();
    }

    private void closeEditor(){
        form.setStorage(null);
        form.setVisible(false);
    }

    private void editStorage(Storage storage) {
        if (storage == null) closeEditor();
        else {
            form.setStorage(storage);
            form.setVisible(true);
        }
    }



}
