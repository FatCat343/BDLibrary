package com.bdcourse.library.UI.EditionUI;

import com.bdcourse.library.bookPosition.BookPosition;
import com.bdcourse.library.bookPosition.BookPositionService;
import com.bdcourse.library.edition.EditionService;
import com.bdcourse.library.edition.indoorEdition.IndoorEdition;
import com.bdcourse.library.edition.outdoorEdition.OutdoorEdition;
import com.bdcourse.library.edition.outdoorEdition.OutdoorEditionService;
import com.bdcourse.library.publication.Publication;
import com.bdcourse.library.publication.PublicationService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class OutdoorEditionForm extends VerticalLayout {
    private IntegerField rentalPeriod = new IntegerField("Rental Period in Days");
    private IntegerField code = new IntegerField("Edition Code");
    private ComboBox<BookPosition> position = new ComboBox<>("Position");
    private ComboBox<Publication> publication = new ComboBox<>("Publication");
    private DatePicker dateArrived = new DatePicker("Date of Arrival");
    private DatePicker dateLeft = new DatePicker("Date of Leaving");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    private EditionService editionService;
    private OutdoorEdition outdoorEdition;
    Binder<OutdoorEdition> outdoorEditionBinder = new Binder<>(OutdoorEdition.class);

    public OutdoorEditionForm(PublicationService publicationService, BookPositionService bookPositionService,
                              EditionService editionService){
        this.editionService = editionService;
        outdoorEditionBinder.bindInstanceFields(this);
        outdoorEditionBinder.forField(rentalPeriod)
                .asRequired("Required Field")
                .bind(OutdoorEdition::getRentalPeriod, OutdoorEdition::setRentalPeriod);
        outdoorEditionBinder.forField(code)
                .asRequired("Required Field")
                .bind(OutdoorEdition::getCode, OutdoorEdition::setCode);
        outdoorEditionBinder.forField(position)
                .asRequired("Required Field")
                .bind(OutdoorEdition::getPosition, OutdoorEdition::setPosition);
        outdoorEditionBinder.forField(publication)
                .asRequired("Required Field")
                .bind(OutdoorEdition::getPublication, OutdoorEdition::setPublication);
        outdoorEditionBinder.forField(dateArrived)
                .asRequired("Required Field")
                .bind(OutdoorEdition::getDateArrived, OutdoorEdition::setDateArrived);
        publication.setItems(publicationService.findAll());
        position.setItems(bookPositionService.findAllFetch());
        add(createFieldsLayout(), createButtonsLayout());
    }

    private HorizontalLayout createFieldsLayout() {
        return new HorizontalLayout(code, publication, position, rentalPeriod, dateArrived, dateLeft);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(
                new deleteEvent(this, outdoorEdition)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        outdoorEditionBinder.addStatusChangeListener(e -> save.setEnabled(outdoorEditionBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            outdoorEditionBinder.writeBean(outdoorEdition);
            if (!editionService.exist(outdoorEdition)) {
                fireEvent(new saveEvent(this, outdoorEdition));
            }
            else {
                Notification.show("Save error: "+ "This item already exists").
                        setPosition(Notification.Position.TOP_START);
            }
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setOutdoorEdition(OutdoorEdition outdoorEdition) {
        this.outdoorEdition = outdoorEdition;
        outdoorEditionBinder.readBean(outdoorEdition);
    }

    public static abstract class OutdoorEditionFormEvent extends ComponentEvent<OutdoorEditionForm> {
        private OutdoorEdition outdoorEdition;

        protected OutdoorEditionFormEvent(OutdoorEditionForm source, OutdoorEdition outdoorEdition){
            super(source, false);
            this.outdoorEdition = outdoorEdition;
        }

        public OutdoorEdition getOutdoorEdition() {
            return outdoorEdition;
        }
    }

    public static class saveEvent extends OutdoorEditionFormEvent {
        saveEvent(OutdoorEditionForm source, OutdoorEdition outdoorEdition) {
            super(source, outdoorEdition);
        }
    }

    public static class deleteEvent extends OutdoorEditionFormEvent {
        deleteEvent(OutdoorEditionForm source, OutdoorEdition outdoorEdition) {
            super(source, outdoorEdition);
        }
    }

    public static class closeEvent extends OutdoorEditionFormEvent {
        closeEvent(OutdoorEditionForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
