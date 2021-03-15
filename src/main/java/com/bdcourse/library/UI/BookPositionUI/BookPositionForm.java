package com.bdcourse.library.UI.BookPositionUI;

import com.bdcourse.library.UI.StaffUI.StaffForm;
import com.bdcourse.library.bookPosition.BookPosition;
import com.bdcourse.library.staff.Staff;
import com.bdcourse.library.storage.Storage;
import com.bdcourse.library.storage.StorageService;
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


public class BookPositionForm extends VerticalLayout {
    IntegerField rackNumber = new IntegerField("Rack Number");
    IntegerField shelfNumber = new IntegerField("Shelf Number");
    ComboBox<Storage> storage = new ComboBox<>("Storage");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    Binder<BookPosition> bookPositionBinder = new Binder<>(BookPosition.class);
    private BookPosition bookPosition;

    public BookPositionForm(StorageService storageService) {
        bookPositionBinder.bindInstanceFields(this);
        storage.setItems(storageService.findAll());
        add(createFieldsLayout(), createButtonsLayout());
    }

    private HorizontalLayout createFieldsLayout(){
        return new HorizontalLayout(shelfNumber, rackNumber, storage);
    }

    private HorizontalLayout createButtonsLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, bookPosition)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        bookPositionBinder.addStatusChangeListener(e -> save.setEnabled(bookPositionBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try{
            bookPositionBinder.writeBean(bookPosition);
            fireEvent(new saveEvent(this, bookPosition));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setBookPosition(BookPosition bookPosition) {
        this.bookPosition = bookPosition;
        bookPositionBinder.readBean(bookPosition);
    }

    public static abstract class BookPositionFormEvent extends ComponentEvent<BookPositionForm> {
        private BookPosition bookPosition;

        protected BookPositionFormEvent(BookPositionForm source, BookPosition bookPosition){
            super(source, false);
            this.bookPosition = bookPosition;
        }

        public BookPosition getBookPosition() {
            return bookPosition;
        }
    }

    public static class saveEvent extends BookPositionFormEvent {
        saveEvent(BookPositionForm source, BookPosition bookPosition) {
            super(source, bookPosition);
        }
    }

    public static class deleteEvent extends BookPositionFormEvent {
        deleteEvent(BookPositionForm source, BookPosition bookPosition) {
            super(source, bookPosition);
        }
    }

    public static class closeEvent extends BookPositionFormEvent {
        closeEvent(BookPositionForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}