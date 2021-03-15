package com.bdcourse.library.UI.EditionUI;

import com.bdcourse.library.bookPosition.BookPosition;
import com.bdcourse.library.bookPosition.BookPositionService;
import com.bdcourse.library.edition.indoorEdition.IndoorEdition;
import com.bdcourse.library.edition.indoorEdition.IndoorEditionService;
import com.bdcourse.library.publication.Publication;
import com.bdcourse.library.publication.PublicationService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class IndoorEditionForm extends VerticalLayout {
    private TextField reason = new TextField("Reason For Indoor Usage Only");
    private IntegerField code = new IntegerField("Edition Code");
    private ComboBox<BookPosition> position = new ComboBox<>("Position");
    private ComboBox<Publication> publication = new ComboBox<>("Publication");
    private DatePicker dateArrived = new DatePicker("Date of Arrival");
    private DatePicker dateLeft = new DatePicker("Date of Leaving");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    private IndoorEditionService indoorEditionService;
    private IndoorEdition indoorEdition;
    Binder<IndoorEdition> indoorEditionBinder = new Binder<>(IndoorEdition.class);

    public IndoorEditionForm(PublicationService publicationService, BookPositionService bookPositionService){
        indoorEditionBinder.bindInstanceFields(this);
        indoorEditionBinder.forField(reason)
                .asRequired("Required Field")
                .bind(IndoorEdition::getReason, IndoorEdition::setReason);
        indoorEditionBinder.forField(code)
                .asRequired("Required Field")
                .bind(IndoorEdition::getCode, IndoorEdition::setCode);
        indoorEditionBinder.forField(position)
                .asRequired("Required Field")
                .bind(IndoorEdition::getPosition, IndoorEdition::setPosition);
        indoorEditionBinder.forField(publication)
                .asRequired("Required Field")
                .bind(IndoorEdition::getPublication, IndoorEdition::setPublication);
        indoorEditionBinder.forField(dateArrived)
                .asRequired("Required Field")
                .bind(IndoorEdition::getDateArrived, IndoorEdition::setDateArrived);
        publication.setItems(publicationService.findAll());
        position.setItems(bookPositionService.findAllFetch());
        add(createFieldsLayout(), createButtonsLayout());
    }

    private HorizontalLayout createFieldsLayout() {
        return new HorizontalLayout(code, publication, position, reason, dateArrived, dateLeft);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, indoorEdition)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        indoorEditionBinder.addStatusChangeListener(e -> save.setEnabled(indoorEditionBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            indoorEditionBinder.writeBean(indoorEdition);
            fireEvent(new saveEvent(this, indoorEdition));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setIndoorEdition(IndoorEdition indoorEdition) {
        this.indoorEdition = indoorEdition;
        indoorEditionBinder.readBean(indoorEdition);
    }

    public static abstract class IndoorEditionFormEvent extends ComponentEvent<IndoorEditionForm> {
        private IndoorEdition indoorEdition;

        protected IndoorEditionFormEvent(IndoorEditionForm source, IndoorEdition indoorEdition){
            super(source, false);
            this.indoorEdition = indoorEdition;
        }

        public IndoorEdition getIndoorEdition() {
            return indoorEdition;
        }
    }

    public static class saveEvent extends IndoorEditionFormEvent {
        saveEvent(IndoorEditionForm source, IndoorEdition indoorEdition) {
            super(source, indoorEdition);
        }
    }

    public static class deleteEvent extends IndoorEditionFormEvent {
        deleteEvent(IndoorEditionForm source, IndoorEdition indoorEdition) {
            super(source, indoorEdition);
        }
    }

    public static class closeEvent extends IndoorEditionFormEvent {
        closeEvent(IndoorEditionForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
