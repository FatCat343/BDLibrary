package com.bdcourse.library.UI.DistributionUI;

import com.bdcourse.library.UI.StaffUI.StaffForm;
import com.bdcourse.library.distribution.Distribution;
import com.bdcourse.library.distribution.DistributionService;
import com.bdcourse.library.edition.Edition;
import com.bdcourse.library.edition.EditionService;
import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.reader.ReaderService;
import com.bdcourse.library.staff.Staff;
import com.bdcourse.library.staff.StaffService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class DistributionForm extends VerticalLayout {
    DatePicker dateGive = new DatePicker("Give Date");
    DatePicker dateReturn = new DatePicker("Return Date");

    ComboBox<Reader> reader = new ComboBox<>("Reader");
    ComboBox<Edition> edition = new ComboBox<>("Edition");
    ComboBox<Staff> staff = new ComboBox<>("Staff");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    Binder<Distribution> distributionBinder = new Binder<>(Distribution.class);
    DistributionService distributionService;
    private Distribution distribution;

    public DistributionForm(ReaderService readerService, EditionService editionService,
                            StaffService staffService, DistributionService distributionService) {
        this.distributionService = distributionService;
        distributionBinder.bindInstanceFields(this);
        reader.setItems(readerService.findAll());
        reader.setRequired(true);
        edition.setItems(editionService.findAll());
        edition.setRequired(true);
        staff.setItems(staffService.findAll());
        staff.setRequired(true);
        dateGive.setRequired(true);

        add(createFieldsLayout(), createButtonsLayout());
    }

    private HorizontalLayout createFieldsLayout() {
        return new HorizontalLayout(edition, reader, staff, dateGive, dateReturn);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, distribution)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        distributionBinder.addStatusChangeListener(e -> save.setEnabled(distributionBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try{
            distributionBinder.writeBean(distribution);
            if (!distributionService.exists(distribution)) {
                fireEvent(new saveEvent(this, distribution));
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

    public void setDistributionNotFetched(Distribution distribution) {
        this.distribution = distribution;
        distributionBinder.readBean(distribution);
    }

    public void setDistribution(Distribution distribution) {
        Distribution fetchedDistribution = distributionService.findDistributionFetch(distribution);
        this.distribution = fetchedDistribution;
        distributionBinder.readBean(fetchedDistribution);
    }

    public static abstract class DistributionFormEvent extends ComponentEvent<DistributionForm> {
        private Distribution distribution;

        protected DistributionFormEvent(DistributionForm source, Distribution distribution){
            super(source, false);
            this.distribution = distribution;
        }

        public Distribution getDistribution() {
            return distribution;
        }
    }

    public static class saveEvent extends DistributionFormEvent {
        saveEvent(DistributionForm source, Distribution distribution) {
            super(source, distribution);
        }
    }

    public static class deleteEvent extends DistributionFormEvent {
        deleteEvent(DistributionForm source, Distribution distribution) {
            super(source, distribution);
        }
    }

    public static class closeEvent extends DistributionFormEvent {
        closeEvent(DistributionForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
