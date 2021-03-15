package com.bdcourse.library.UI.StorageUI;

import com.bdcourse.library.UI.BookUI.CategoryForm;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.library.LibraryService;
import com.bdcourse.library.storage.Storage;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class StorageForm extends VerticalLayout {

    private ComboBox<Library> library = new ComboBox<Library>("Library");
    private IntegerField roomNumber = new IntegerField("Room Number");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    Binder<Storage> storageBinder = new Binder<>(Storage.class);
    private Storage storage;

    public StorageForm(LibraryService libraryService) {
        storageBinder.bindInstanceFields(this);
        storageBinder.forField(roomNumber)
                .asRequired("required field")
                .bind(Storage::getRoomNumber, Storage::setRoomNumber);
        storageBinder.forField(library)
                .asRequired("required field")
                .bind(Storage::getLibrary, Storage::setLibrary);
        library.setItems(libraryService.findAll());
        add(createFieldsLayout(), createButtonsLayout());
    }

    private HorizontalLayout createFieldsLayout() {
        return new HorizontalLayout(roomNumber, library);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, storage)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        storageBinder.addStatusChangeListener(e -> save.setEnabled(storageBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            storageBinder.writeBean(storage);
            fireEvent(new saveEvent(this, storage));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
        storageBinder.readBean(storage);
    }

    public static abstract class StorageFormEvent extends ComponentEvent<StorageForm> {
        private Storage storage;

        protected StorageFormEvent(StorageForm source, Storage storage){
            super(source, false);
            this.storage = storage;
        }

        public Storage getStorage() {
            return storage;
        }
    }

    public static class saveEvent extends StorageFormEvent {
        saveEvent(StorageForm source, Storage storage) {
            super(source, storage);
        }
    }

    public static class deleteEvent extends StorageFormEvent {
        deleteEvent(StorageForm source, Storage storage) {
            super(source, storage);
        }
    }

    public static class closeEvent extends StorageFormEvent {
        closeEvent(StorageForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {

        return getEventBus().addListener(eventType, listener);
    }
}
