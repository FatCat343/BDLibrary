package com.bdcourse.library.UI.DissertationUI;

import com.bdcourse.library.edition.EditionService;
import com.bdcourse.library.publication.author.Author;
import com.bdcourse.library.publication.author.AuthorService;
import com.bdcourse.library.publication.dissertation.Dissertation;
import com.bdcourse.library.publication.dissertation.subject.Subject;
import com.bdcourse.library.publication.dissertation.subject.SubjectService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class DissertationForm extends VerticalLayout {
    TextField title = new TextField("Title");
    ComboBox<Author> author = new ComboBox<>("Author");
    ComboBox<Subject> subject = new ComboBox<>("Subject");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    private SubjectForm subjectForm;
    private SubjectService subjectService;

    Binder<Dissertation> dissertationBinder = new Binder<>(Dissertation.class);
    private Dissertation dissertation;

    public DissertationForm(AuthorService authorService, SubjectService subjectService) {
        this.subjectService = subjectService;
        dissertationBinder.bindInstanceFields(this);
        dissertationBinder.forField(title)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Dissertation::getTitle, Dissertation::setTitle);
        author.setItems(authorService.findAll());
        subject.setItems(subjectService.findAll());

        subjectForm = new SubjectForm(subjectService);
        subjectForm.addListener(SubjectForm.saveEvent.class,  this::saveSubject);
        subjectForm.addListener(SubjectForm.deleteEvent.class,  this::deleteSubject);
        subjectForm.addListener(SubjectForm.closeEvent.class, e -> closeEditor());

        add(createFieldsLayout(), createButtonsLayout(), subjectForm);

        closeEditor();
    }

    private void closeEditor() {
        subjectForm.setSubject(null);
        subjectForm.setVisible(false);
    }

    private void saveSubject(SubjectForm.saveEvent event) {
        subjectService.save(event.getSubject());
        subject.setItems(subjectService.findAll());
        closeEditor();
    }

    private void deleteSubject(SubjectForm.deleteEvent event) {
        subjectService.delete(event.getSubject());
        subject.setItems(subjectService.findAll());
        closeEditor();
    }

    private HorizontalLayout createFieldsLayout() {
        Button addSubjectButton = new Button("Add Subject");
        addSubjectButton.addClickListener(click -> addCategory());
        return new HorizontalLayout(author, title, subject, addSubjectButton);
    }

    private void addCategory() {
        subjectForm.setSubject(new Subject());
        subjectForm.setVisible(true);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, dissertation)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        dissertationBinder.addStatusChangeListener(e -> save.setEnabled(dissertationBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            dissertationBinder.writeBean(dissertation);
            fireEvent(new saveEvent(this, dissertation));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setDissertation(Dissertation dissertation) {
        this.dissertation = dissertation;
        dissertationBinder.readBean(dissertation);
    }

    public static abstract class DissertationFormEvent extends ComponentEvent<DissertationForm> {
        private Dissertation dissertation;

        protected DissertationFormEvent(DissertationForm source, Dissertation dissertation){
            super(source, false);
            this.dissertation = dissertation;
        }

        public Dissertation getDissertation() {
            return dissertation;
        }
    }

    public static class saveEvent extends DissertationFormEvent {
        saveEvent(DissertationForm source, Dissertation dissertation) {
            super(source, dissertation);
        }
    }

    public static class deleteEvent extends DissertationFormEvent {
        deleteEvent(DissertationForm source, Dissertation dissertation) {
            super(source, dissertation);
        }
    }

    public static class closeEvent extends DissertationFormEvent {
        closeEvent(DissertationForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {

        return getEventBus().addListener(eventType, listener);
    }
}
