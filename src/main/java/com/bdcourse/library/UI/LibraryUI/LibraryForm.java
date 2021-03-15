package com.bdcourse.library.UI.LibraryUI;

import com.bdcourse.library.library.Library;
import com.bdcourse.library.library.LibraryService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;


public class LibraryForm extends VerticalLayout {
    private TextField address = new TextField("Library Address");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    private LibraryService libraryService;
    private Library library;
    Binder<Library> libraryBinder = new Binder<>(Library.class);

    public LibraryForm(){
        libraryBinder.bindInstanceFields(this);
        libraryBinder.forField(address)
                .asRequired("Required Field")
                .bind(Library::getAddress, Library::setAddress);
        add(createFieldsLayout(), createButtonsLayout());
    }

    private HorizontalLayout createFieldsLayout() {
        return new HorizontalLayout(address);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, library)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        libraryBinder.addStatusChangeListener(e -> save.setEnabled(libraryBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            libraryBinder.writeBean(library);
            fireEvent(new saveEvent(this, library));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setLibrary(Library library) {
        this.library = library;
        libraryBinder.readBean(library);
    }

    public static abstract class LibraryFormEvent extends ComponentEvent<LibraryForm> {
        private Library library;

        protected LibraryFormEvent(LibraryForm source, Library library){
            super(source, false);
            this.library = library;
        }

        public Library getLibrary() {
            return library;
        }
    }

    public static class saveEvent extends LibraryFormEvent {
        saveEvent(LibraryForm source, Library library) {
            super(source, library);
        }
    }

    public static class deleteEvent extends LibraryFormEvent {
        deleteEvent(LibraryForm source, Library library) {
            super(source, library);
        }
    }

    public static class closeEvent extends LibraryFormEvent {
        closeEvent(LibraryForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}