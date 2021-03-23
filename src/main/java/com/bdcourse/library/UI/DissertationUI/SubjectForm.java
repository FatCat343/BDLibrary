package com.bdcourse.library.UI.DissertationUI;

import com.bdcourse.library.publication.dissertation.subject.Subject;
import com.bdcourse.library.publication.dissertation.subject.SubjectService;
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

public class SubjectForm extends VerticalLayout {
    TextField name = new TextField("New Subject Name");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    Binder<Subject> subjectBinder = new Binder<>(Subject.class);
    private SubjectService subjectService;
    private Subject subject;

    public SubjectForm(SubjectService subjectService) {
        this.subjectService = subjectService;
        addClassName("subject-form");
        subjectBinder.bindInstanceFields(this);
        subjectBinder.forField(name)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Subject::getName, Subject::setName);
        name.setRequired(true);
        add(createFieldsLayout(), createButtonsLayout());
    }

    private HorizontalLayout createFieldsLayout() {
        return new HorizontalLayout(name);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, subject)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        delete.setEnabled(false);

        subjectBinder.addStatusChangeListener(e -> save.setEnabled(subjectBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            subjectBinder.writeBean(subject);
            if (!subjectService.exist(subject)) {
                fireEvent(new saveEvent(this, subject));
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

    public void setSubject(Subject subject) {
        this.subject = subject;
        subjectBinder.readBean(subject);
    }

    public static abstract class SubjectFormEvent extends ComponentEvent<SubjectForm> {
        private Subject subject;

        protected SubjectFormEvent(SubjectForm source, Subject subject){
            super(source, false);
            this.subject = subject;
        }

        public Subject getSubject() {
            return subject;
        }
    }

    public static class saveEvent extends SubjectFormEvent {
        saveEvent(SubjectForm source, Subject subject) {
            super(source, subject);
        }
    }

    public static class deleteEvent extends SubjectFormEvent {
        deleteEvent(SubjectForm source, Subject subject) {
            super(source, subject);
        }
    }

    public static class closeEvent extends SubjectFormEvent {
        closeEvent(SubjectForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {

        return getEventBus().addListener(eventType, listener);
    }
}
