package com.bdcourse.library.UI.WorkerUI;

import com.bdcourse.library.reader.student.department.Department;
import com.bdcourse.library.reader.student.department.DepartmentService;
import com.bdcourse.library.reader.worker.company.Company;
import com.bdcourse.library.reader.worker.company.CompanyService;
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

public class CompanyForm extends VerticalLayout {
    TextField companyName = new TextField("Company");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    Binder<Company> companyBinder = new Binder<>(Company.class);
    private Company company;

    public CompanyForm(CompanyService companyService) {
        addClassName("company-form");
        //studentBinder.forField(code).withConverter(new DoubleToIntegerConverter()).bind(Student::getCode, Student::setCode);
        companyBinder.bindInstanceFields(this);
        companyBinder.forField(companyName)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Company::getName, Company::setName);
//        companyBinder.forField(university)
//                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
//                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
//                .bind(Department::getUniversity, Department::setUniversity);

        //department.setItems(departmentService.findAll());
        //library.setItems(libraryService.findAll());

        add(createFieldsLayout(), createButtonsLayout());

    }

    private HorizontalLayout createFieldsLayout() {
        return new HorizontalLayout(companyName);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, company)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        delete.setEnabled(false);

        companyBinder.addStatusChangeListener(e -> save.setEnabled(companyBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            companyBinder.writeBean(company);
            fireEvent(new saveEvent(this, company));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setCompany(Company company) {
        this.company = company;
        companyBinder.readBean(company);
    }

    public static abstract class CompanyFormEvent extends ComponentEvent<CompanyForm> {
        private Company company;

        protected CompanyFormEvent(CompanyForm source, Company company){
            super(source, false);
            this.company = company;
        }

        public Company getCompany() {
            return company;
        }
    }

    public static class saveEvent extends CompanyForm.CompanyFormEvent {
        saveEvent(CompanyForm source, Company company) {
            super(source, company);
        }
    }

    public static class deleteEvent extends CompanyForm.CompanyFormEvent {
        deleteEvent(CompanyForm source, Company company) {
            super(source, company);
        }
    }

    public static class closeEvent extends CompanyForm.CompanyFormEvent {
        closeEvent(CompanyForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {

        return getEventBus().addListener(eventType, listener);
    }
}
