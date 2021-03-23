package com.bdcourse.library.UI.WorkerUI;

import com.bdcourse.library.UI.WorkerUI.CompanyForm;
import com.bdcourse.library.UI.WorkerUI.ProfessionForm;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.library.LibraryService;
import com.bdcourse.library.reader.student.Student;
import com.bdcourse.library.reader.student.department.Department;
import com.bdcourse.library.reader.student.department.DepartmentService;
import com.bdcourse.library.reader.worker.Worker;
import com.bdcourse.library.reader.worker.WorkerService;
import com.bdcourse.library.reader.worker.company.Company;
import com.bdcourse.library.reader.worker.company.CompanyService;
import com.bdcourse.library.reader.worker.profession.Profession;
import com.bdcourse.library.reader.worker.profession.ProfessionService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;


public class WorkerForm extends VerticalLayout {
    TextField firstName = new TextField("FirstName");
    TextField lastName = new TextField("LastName");
    //IntegerField code = new IntegerField("Student Code");
    ComboBox<Company> company = new ComboBox<>("Company");
    ComboBox<Profession> profession = new ComboBox<>("Profession");
    ComboBox<Library> library = new ComboBox<>("Library");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    private CompanyForm companyForm;
    private CompanyService companyService;

    private ProfessionForm professionForm;
    private ProfessionService professionService;
    private WorkerService workerService;

    Binder<Worker> workerBinder = new Binder<>(Worker.class);
    private Worker worker;

    public WorkerForm(CompanyService companyService, ProfessionService professionService, LibraryService libraryService,
                      WorkerService workerService) {
        addClassName("worker-form");
        this.workerService = workerService;
        this.companyService = companyService;
        this.professionService = professionService;
        workerBinder.bindInstanceFields(this);
        workerBinder.forField(firstName)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Worker::getFirstName, Worker::setFirstName);
        workerBinder.forField(lastName)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Worker::getLastName, Worker::setLastName);

        profession.setItems(professionService.findAll());
        company.setItems(companyService.findAll());
        library.setItems(libraryService.findAll());

        professionForm = new ProfessionForm(professionService);
        professionForm.addListener(ProfessionForm.saveEvent.class,  this::saveProfession);
        professionForm.addListener(ProfessionForm.deleteEvent.class,  this::deleteProfession);
        professionForm.addListener(ProfessionForm.closeEvent.class, e -> closeProfessionEditor());

        companyForm = new CompanyForm(companyService);
        companyForm.addListener(CompanyForm.saveEvent.class,  this::saveCompany);
        companyForm.addListener(CompanyForm.deleteEvent.class,  this::deleteCompany);
        companyForm.addListener(CompanyForm.closeEvent.class, e -> closeCompanyEditor());

        add(createFieldsLayout(), createButtonsLayout(), professionForm, companyForm);

        closeProfessionEditor();
        closeCompanyEditor();
    }

    private void closeCompanyEditor() {
        companyForm.setCompany(null);
        companyForm.setVisible(false);
    }

    private void closeProfessionEditor() {
        professionForm.setProfession(null);
        professionForm.setVisible(false);
    }

    private void saveProfession(ProfessionForm.saveEvent event) {
        professionService.save(event.getProfession());
        this.profession.setItems(professionService.findAll());
        closeProfessionEditor();
    }

    private void deleteProfession(ProfessionForm.deleteEvent event) {
        professionService.delete(event.getProfession());
        this.profession.setItems(professionService.findAll());
        closeProfessionEditor();
    }

    private void saveCompany(CompanyForm.saveEvent event) {
        companyService.save(event.getCompany());
        this.company.setItems(companyService.findAll());
        closeCompanyEditor();
    }

    private void deleteCompany(CompanyForm.deleteEvent event) {
        companyService.delete(event.getCompany());
        this.company.setItems(companyService.findAll());
        closeCompanyEditor();
    }

    private HorizontalLayout createFieldsLayout() {
        Button addProfessionButton = new Button("Add Profession");
        addProfessionButton.addClickListener(click -> addProfession());

        Button addCompanyButton = new Button("Add Company");
        addCompanyButton.addClickListener(click -> addCompany());

        firstName.setRequired(true);
        lastName.setRequired(true);
        profession.setRequired(true);
        company.setRequired(true);
        library.setRequired(true);
        return new HorizontalLayout(firstName, lastName, profession, company, library, addProfessionButton, addCompanyButton);
    }

    private void addCompany() {
        companyForm.setCompany(new Company());
        companyForm.setVisible(true);
    }

    private void addProfession() {
        professionForm.setProfession(new Profession());
        professionForm.setVisible(true);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, worker)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        workerBinder.addStatusChangeListener(e -> save.setEnabled(workerBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            workerBinder.writeBean(worker);
            if (!workerService.exist(worker)){
                fireEvent(new saveEvent(this, worker));
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

    public void setWorker(Worker worker) {
        this.worker = new Worker(worker);
        workerBinder.readBean(worker);
    }

    public static abstract class WorkerFormEvent extends ComponentEvent<WorkerForm> {
        private Worker worker;

        protected WorkerFormEvent(WorkerForm source, Worker worker){
            super(source, false);
            this.worker = worker;
        }

        public Worker getWorker() {
            return worker;
        }
    }

    public static class saveEvent extends WorkerFormEvent {
        saveEvent(WorkerForm source, Worker worker) {
            super(source, worker);
        }
    }

    public static class deleteEvent extends WorkerFormEvent {
        deleteEvent(WorkerForm source, Worker worker) {
            super(source, worker);
        }
    }

    public static class closeEvent extends WorkerFormEvent {
        closeEvent(WorkerForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {

        return getEventBus().addListener(eventType, listener);
    }
}

