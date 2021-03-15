package com.bdcourse.library.UI.AuthorUI;

import com.bdcourse.library.UI.StaffUI.StaffForm;
import com.bdcourse.library.publication.author.Author;

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

public class AuthorForm extends VerticalLayout {
    TextField firstName = new TextField("FirstName");
    TextField lastName = new TextField("LastName");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    Binder<Author> authorBinder = new Binder<>(Author.class);
    private Author author;

    public AuthorForm() {
        authorBinder.bindInstanceFields(this);
        authorBinder.forField(firstName)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Author::getFirstName, Author::setFirstName);
        authorBinder.forField(lastName)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Author::getLastName, Author::setLastName);
        add(createFieldsLayout(), createButtonsLayout());
    }

    private HorizontalLayout createFieldsLayout() {
        return new HorizontalLayout(firstName, lastName);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, author)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        authorBinder.addStatusChangeListener(e -> save.setEnabled(authorBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try{
            authorBinder.writeBean(author);
            fireEvent(new saveEvent(this, author));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setAuthor(Author author) {
        this.author = author;
        authorBinder.readBean(author);
    }

    public static abstract class AuthorFormEvent extends ComponentEvent<AuthorForm> {
        private Author author;

        protected AuthorFormEvent(AuthorForm source, Author author){
            super(source, false);
            this.author = author;
        }

        public Author getAuthor() {
            return author;
        }
    }

    public static class saveEvent extends AuthorFormEvent {
        saveEvent(AuthorForm source, Author author) {
            super(source, author);
        }
    }

    public static class deleteEvent extends AuthorFormEvent {
        deleteEvent(AuthorForm source, Author author) {
            super(source, author);
        }
    }

    public static class closeEvent extends AuthorFormEvent {
        closeEvent(AuthorForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}