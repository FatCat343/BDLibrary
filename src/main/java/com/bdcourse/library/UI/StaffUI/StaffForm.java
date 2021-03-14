package com.bdcourse.library.UI.StaffUI;

import com.bdcourse.library.UI.StudentUI.StudentForm;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.staff.Staff;
import com.bdcourse.library.staff.StaffService;
import com.bdcourse.library.storage.Storage;
import com.bdcourse.library.storage.StorageService;
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

public class StaffForm extends VerticalLayout {
    TextField firstName = new TextField("FirstName");
    TextField lastName = new TextField("LastName");
    ComboBox<Storage> storage = new ComboBox<>("Storage");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    private StorageService storageService;
    private StaffService staffService;

    Binder<Staff> staffBinder = new Binder<>(Staff.class);
    private Staff staff;

    public StaffForm(StorageService storageService, StaffService staffService) {
        //this.storage
        this.staffService = staffService;
        staffBinder.bindInstanceFields(this);
        staffBinder.forField(firstName)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Staff::getFirstName, Staff::setFirstName);
        staffBinder.forField(lastName)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Staff::getLastName, Staff::setLastName);
        storage.setItems(storageService.findAll());
        add(createFieldsLayout(), createButtonsLayout());


    }
    private HorizontalLayout createFieldsLayout(){
        return new HorizontalLayout(firstName, lastName, storage);
    }
    private HorizontalLayout createButtonsLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, staff)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        staffBinder.addStatusChangeListener(e -> save.setEnabled(staffBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try{
            staffBinder.writeBean(staff);
            fireEvent(new saveEvent(this, staff));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setStaff(Staff staff) {
        Staff fetchedStaff = staffService.findStaffByIdFetch(staff);
        this.staff = fetchedStaff;
        staffBinder.readBean(fetchedStaff);
    }

    public void setStaffNotFetched(Staff staff) {
        //Staff fetchedStaff = staffService.findStaffByIdFetch(staff);
        this.staff = staff;
        staffBinder.readBean(staff);
    }

    public static abstract class StaffFormEvent extends ComponentEvent<StaffForm> {
        private Staff staff;

        protected StaffFormEvent(StaffForm source, Staff staff){
            super(source, false);
            this.staff = staff;
        }

        public Staff getStaff() {
            return staff;
        }
    }

    public static class saveEvent extends StaffFormEvent {
        saveEvent(StaffForm source, Staff staff) {
            super(source, staff);
        }
    }

    public static class deleteEvent extends StaffFormEvent {
        deleteEvent(StaffForm source, Staff staff) {
            super(source, staff);
        }
    }

    public static class closeEvent extends StaffFormEvent {
        closeEvent(StaffForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>>Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
