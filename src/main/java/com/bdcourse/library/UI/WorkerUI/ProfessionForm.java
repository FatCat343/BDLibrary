package com.bdcourse.library.UI.WorkerUI;

import com.bdcourse.library.reader.worker.profession.Profession;
import com.bdcourse.library.reader.worker.profession.ProfessionService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ProfessionForm extends VerticalLayout {
    TextField name = new TextField("Profession");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    Binder<Profession> professionBinder = new Binder<>(Profession.class);
    private ProfessionService professionService;
    private Profession profession;

    public ProfessionForm(ProfessionService professionService) {
        addClassName("profession-form");
        this.professionService = professionService;
        professionBinder.bindInstanceFields(this);
        professionBinder.forField(name)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Profession::getName, Profession::setName);
        add(createFieldsLayout(), createButtonsLayout());
    }

    private HorizontalLayout createFieldsLayout() {
        name.setRequired(true);
        return new HorizontalLayout(name);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, profession)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        delete.setEnabled(false);

        professionBinder.addStatusChangeListener(e -> save.setEnabled(professionBinder.isValid()));
        return new HorizontalLayout(save, close);
    }

    private void validateAndSave() {
        try {
            professionBinder.writeBean(profession);
            if (!professionService.exist(profession)) {
                fireEvent(new saveEvent(this, profession));
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

    public void setProfession(Profession profession) {
        this.profession = profession;
        professionBinder.readBean(profession);
    }

    public static abstract class ProfessionFormEvent extends ComponentEvent<ProfessionForm> {
        private Profession profession;

        protected ProfessionFormEvent(ProfessionForm source, Profession profession){
            super(source, false);
            this.profession = profession;
        }

        public Profession getProfession() {
            return profession;
        }
    }

    public static class saveEvent extends ProfessionFormEvent {
        saveEvent(ProfessionForm source, Profession profession) {
            super(source, profession);
        }
    }

    public static class deleteEvent extends ProfessionFormEvent {
        deleteEvent(ProfessionForm source, Profession profession) {
            super(source, profession);
        }
    }

    public static class closeEvent extends ProfessionFormEvent {
        closeEvent(ProfessionForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {

        return getEventBus().addListener(eventType, listener);
    }
}
