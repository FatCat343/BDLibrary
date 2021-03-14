package com.bdcourse.library.UI.StaffUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.staff.Staff;
import com.bdcourse.library.staff.StaffService;
import com.bdcourse.library.storage.StorageService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "staff", layout = MainView.class)
public class StaffView extends VerticalLayout {
    private StaffService staffService;
    private Grid<Staff> grid = new Grid<>(Staff.class);

    private StaffForm form;

    public StaffView(StaffService staffService, StorageService storageService) {
        this.staffService = staffService;
        setSizeFull();
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new StaffForm(storageService, staffService);
        form.addListener(StaffForm.saveEvent.class, this::saveStaff);
        form.addListener(StaffForm.deleteEvent.class, this::deleteStaff);
        form.addListener(StaffForm.closeEvent.class, e -> closeEditor());
        add(toolbar, form, grid);

        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Staff::getFirstName).setHeader("FirstName").setSortProperty("firstname");
        grid.addColumn(Staff::getLastName).setHeader("LastName").setSortProperty("lastname");
        //grid.addColumn(staff -> staff.initi)
        //grid.setSelectionMode(Sele)
        grid.asSingleSelect().addValueChangeListener(event -> editStaff(event.getValue()));
        //grid.addListener(event -> event.isDoubleClick() ? );
        grid.setItemDetailsRenderer(TemplateRenderer.<Staff>of(
                "<div class='custom-details' style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box;'>"
                        + "<div>Assigned to storage : <b>[[item.storage]]!</b></div>"
                        + "</div>")
                .withProperty("storage", staff -> {
                    return staffService.findStaffByIdFetch(staff).getStorage().toString();
                })
                // This is now how we open the details
                .withEventHandler("handleClick", staff -> {
                    grid.getDataProvider().refreshItem(staff);
                }));
        grid.setDetailsVisibleOnClick(false);

        grid.addColumn(new NativeButtonRenderer<>("Details", item -> grid
                .setDetailsVisible(item, !grid.isDetailsVisible(item))));

    }

    private HorizontalLayout configureToolBar() {
        Button addStaffButton = new Button("Add Staff");
        addStaffButton.addClickListener(click -> addStaff());
        return new HorizontalLayout(addStaffButton);
    }

    private void addStaff() {
        grid.asSingleSelect().clear();
        //editStaff(new Staff());
        form.setStaffNotFetched(new Staff());
        form.setVisible(true);
    }

    private void updateList(){
        grid.setItems(staffService.findAll());
    }

    private void saveStaff(StaffForm.saveEvent event) {
        Staff staff = staffService.save(event.getStaff());
        updateList();
        closeEditor();
    }

    private void deleteStaff(StaffForm.deleteEvent event) {
        staffService.delete(event.getStaff());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setStaff(null);
        form.setVisible(false);

    }

    private void editStaff(Staff staff) {
        if (staff == null) closeEditor();
        else {
            form.setStaff(staff);
            form.setVisible(true);
        }
    }

//    private void configureFilter(TextField field, String name) {
//        field.setPlaceholder(name);
//        field.setClearButtonVisible(true);
//        field.setValueChangeMode(ValueChangeMode.LAZY);
//    }
}
