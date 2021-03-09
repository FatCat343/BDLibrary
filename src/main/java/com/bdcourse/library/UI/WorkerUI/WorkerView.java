package com.bdcourse.library.UI.WorkerUI;

import com.bdcourse.library.UI.MainView;

import com.bdcourse.library.UI.WorkerUI.WorkerForm;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.library.LibraryService;
import com.bdcourse.library.reader.worker.Worker;
import com.bdcourse.library.reader.worker.WorkerService;
import com.bdcourse.library.reader.worker.company.Company;
import com.bdcourse.library.reader.worker.company.CompanyService;
import com.bdcourse.library.reader.worker.profession.Profession;
import com.bdcourse.library.reader.worker.profession.ProfessionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.SortOrder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route(value = "workers", layout = MainView.class)
public class WorkerView extends VerticalLayout {
    private WorkerService workerService;
    private PaginatedGrid<Worker> grid = new PaginatedGrid<>(Worker.class);

    private TextField firstName = new TextField();
    private TextField lastName = new TextField();
    private TextField profession = new TextField();
    private TextField library = new TextField();
    private TextField company = new TextField();
    private WorkerForm form;

    ConfigurableFilterDataProvider<Worker, Void, WorkerFilter> customDataProvider;
    WorkerFilter workerFilter;

    public WorkerView(WorkerService workerService, LibraryService libraryService, CompanyService companyService, ProfessionService professionService) {
        this.workerService = workerService;
        workerFilter = new WorkerFilter();
        addClassName("worker-view");
        setSizeFull();
        DataProvider<Worker, WorkerFilter> dataProvider = createDataProvider(workerService);
        customDataProvider = dataProvider.withConfigurableFilter();
        customDataProvider.setFilter(workerFilter);
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new WorkerForm(companyService, professionService, libraryService);
        form.addListener(WorkerForm.saveEvent.class, this::saveWorker);
        form.addListener(WorkerForm.deleteEvent.class, this::deleteWorker);
        form.addListener(WorkerForm.closeEvent.class, e -> closeEditor());
        add(toolbar, form, grid);

        closeEditor();
    }

    private HorizontalLayout configureToolBar() {
        configureFilter(firstName, "FirstName filter");
        configureFilter(lastName, "LastName Filter");
        configureFilter(library, "Library Address Filter");
        configureFilter(profession, "Profession Filter");
        configureFilter(company, "Company Filter");

//        code.setPlaceholder("Code Filter");
//        code.setClearButtonVisible(true);
//        code.setValueChangeMode(ValueChangeMode.LAZY);

        firstName.addValueChangeListener(event -> {
            if ((event.getValue() != null) && (!event.getValue().equals(""))) workerFilter.setFirstName(event.getValue());
            else workerFilter.setFirstName(null);
            customDataProvider.refreshAll();
        });
        lastName.addValueChangeListener(event -> {
            if ((event.getValue() != null) && (!event.getValue().equals(""))) workerFilter.setLastName(event.getValue());
            else workerFilter.setLastName(null);
            customDataProvider.refreshAll();
        });
//        code.addValueChangeListener(event -> {
//            if (event.getValue() != null) studentFilter.setCode(String.valueOf(event.getValue()));
//            else studentFilter.setCode(null);
//            customDataProvider.refreshAll();
//        });
        library.addValueChangeListener(event -> {
            if ((event.getValue() != null) && (!event.getValue().equals(""))) workerFilter.setLibrary(event.getValue());
            else workerFilter.setLibrary(null);
            customDataProvider.refreshAll();
        });
        profession.addValueChangeListener(event -> {
            if ((event.getValue() != null) && (!event.getValue().equals(""))) workerFilter.setProfession(event.getValue());
            else workerFilter.setProfession(null);
            customDataProvider.refreshAll();
        });
        company.addValueChangeListener(event -> {
            if ((event.getValue() != null) && (!event.getValue().equals(""))) workerFilter.setCompany(event.getValue());
            else workerFilter.setCompany(null);
            customDataProvider.refreshAll();
        });

        Button addWorkerButton = new Button("Add Worker");
        addWorkerButton.addClickListener(click -> addWorker());

        return new HorizontalLayout(firstName, lastName, profession, company, library, addWorkerButton);
    }

    void addWorker() {
        grid.asSingleSelect().clear();
        editWorker(new Worker());
    }

    private void configureFilter(TextField field, String name) {
        field.setPlaceholder(name);
        field.setClearButtonVisible(true);
        field.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void configureGrid(){
        grid.addClassName("worker-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Worker::getFirstName).setHeader("FirstName").setSortProperty("firstName");
        grid.addColumn(Worker::getLastName).setHeader("LastName").setSortProperty("lastName");
        //grid.addColumn(Student::getCode).setHeader("Code").setSortProperty("code");
        grid.addColumn(worker -> {
            Profession p = worker.getProfession();
            return p.toString();
        }).setHeader("Profession").setSortProperty("profession");
        grid.addColumn(worker -> {
            Company c = worker.getCompany();
            return c.toString();
        }).setHeader("Company").setSortProperty("company");
        grid.addColumn(worker -> {
            Library l = worker.getLibrary();
            return l.getAddress();
        }).setHeader("Assigned Library").setSortProperty("library");
        grid.setPageSize(10);
        grid.setDataProvider(customDataProvider);

        grid.asSingleSelect().addValueChangeListener(event -> editWorker(event.getValue()));
    }


    private void closeEditor() {
        form.setWorker(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveWorker(WorkerForm.saveEvent event) {
        Worker worker = workerService.save(event.getWorker());
        customDataProvider.refreshItem(worker);
        closeEditor();
    }

    private void deleteWorker(WorkerForm.deleteEvent event) {
        workerService.delete(event.getWorker());
        customDataProvider.refreshAll();
        closeEditor();
    }


    public void editWorker(Worker worker) {
        if (worker == null) closeEditor();
        else {
            form.setWorker(worker);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private DataProvider<Worker, WorkerFilter> createDataProvider(WorkerService workerService) {
        DataProvider<Worker, WorkerFilter> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> {
                    Optional<WorkerFilter> filter = query.getFilter();
                    List<WorkerSort> sortOrders = new ArrayList<>();
                    for (SortOrder<String> queryOrder : query.getSortOrders()) {
                        WorkerSort sort = getWorkerService().createSort(queryOrder.getSorted(), queryOrder.getDirection() == SortDirection.DESCENDING);
                        sortOrders.add(sort);
                    }

                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    int page = offset/limit;
                    List<Worker> workers = getWorkerService().fetch(page, limit,
                            filter.map(f -> f.getFirstName()).orElse(null),
                            filter.map(f -> f.getLastName()).orElse(null),
                            filter.map(f -> f.getCode()).orElse(null),
                            filter.map(f -> f.getLibrary()).orElse(null),
                            filter.map(f -> f.getDepartment()).orElse(null) ,
                            sortOrders);
                    return workers.stream();
                },

                query -> {
                    Optional<WorkerFilter> filter = query.getFilter();
                    return (int) getWorkerService().getWorkerCount(
                            filter.map(f -> f.getFirstName()).orElse(null),
                            filter.map(f -> f.getLastName()).orElse(null),
                            filter.map(f -> f.getCode()).orElse(null),
                            filter.map(f -> f.getLibrary()).orElse(null),
                            filter.map(f -> f.getDepartment()).orElse(null));
                });
        return dataProvider;
    }
    private WorkerService getWorkerService() {
        return workerService;
    }
}
