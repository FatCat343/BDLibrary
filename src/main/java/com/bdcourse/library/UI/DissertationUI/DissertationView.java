package com.bdcourse.library.UI.DissertationUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.publication.author.Author;
import com.bdcourse.library.publication.author.AuthorService;
import com.bdcourse.library.publication.dissertation.Dissertation;
import com.bdcourse.library.publication.dissertation.DissertationService;
import com.bdcourse.library.publication.dissertation.subject.Subject;
import com.bdcourse.library.publication.dissertation.subject.SubjectService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.SortOrder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.vaadin.gatanaso.MultiselectComboBox;
import org.vaadin.klaudeta.PaginatedGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Route(value = "dissertations", layout = MainView.class)
public class DissertationView extends VerticalLayout {
    private DissertationService dissertationService;
    private PaginatedGrid<Dissertation> grid = new PaginatedGrid<>(Dissertation.class);

    private TextField title = new TextField();
    private MultiselectComboBox<Author> author = new MultiselectComboBox<>();
    private MultiselectComboBox<Subject> subject = new MultiselectComboBox<>();

    private DissertationForm form;

    ConfigurableFilterDataProvider<Dissertation, Void, DissertationFilter> customDataProvider;
    DissertationFilter dissertationFilter;

    public DissertationView(DissertationService dissertationService, SubjectService subjectService,
                            AuthorService authorService) {
        this.dissertationService = dissertationService;
        dissertationFilter = new DissertationFilter();
        addClassName("dissertation-view");
        setSizeFull();
        DataProvider<Dissertation, DissertationFilter> dataProvider = createDataProvider(dissertationService);
        customDataProvider = dataProvider.withConfigurableFilter();
        customDataProvider.setFilter(dissertationFilter);
        author.setItems(authorService.findAll());
        subject.setItems(subjectService.findAll());
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new DissertationForm(authorService, subjectService);
        form.addListener(DissertationForm.saveEvent.class, this::saveDissertation);
        form.addListener(DissertationForm.deleteEvent.class, this::deleteDissertation);
        form.addListener(DissertationForm.closeEvent.class, e -> closeEditor());
        add(toolbar, form, grid);

        closeEditor();
    }

    private HorizontalLayout configureToolBar() {
        configureFilter(title, "Title filter");
        author.setPlaceholder("Choose Authors...");
        subject.setPlaceholder("Choose Subjects...");

        title.addValueChangeListener(event -> {
            if ((event.getValue() != null) && (!event.getValue().equals("")))
                dissertationFilter.setTitle(event.getValue());
            else dissertationFilter.setTitle(null);
            customDataProvider.refreshAll();
        });
        author.addValueChangeListener(event -> {
            if ((event.getValue() != null) && (!event.getValue().isEmpty())) dissertationFilter.setAuthors(event.getValue());
            else dissertationFilter.setAuthors(null);
            customDataProvider.refreshAll();
        });
        subject.addValueChangeListener(event -> {
            if ((event.getValue() != null) && (!event.getValue().isEmpty())) dissertationFilter.setSubjects(event.getValue());
            else dissertationFilter.setSubjects(null);

            customDataProvider.refreshAll();
        });

        Button addDissertationButton = new Button("Add Dissertation");
        addDissertationButton.addClickListener(click -> addDissertation());

        return new HorizontalLayout(title, author, subject, addDissertationButton);
    }

    void addDissertation() {
        grid.asSingleSelect().clear();
        editDissertation(new Dissertation());
    }

    private void configureFilter(TextField field, String name) {
        field.setPlaceholder(name);
        field.setClearButtonVisible(true);
        field.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void configureGrid(){
        grid.addClassName("dissertation-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Dissertation::getTitle).setHeader("Title").setSortProperty("title");
        grid.addColumn(dissertation -> {
            Author author = dissertation.getAuthor();
            return author.toString();
        }).setHeader("Author").setSortProperty("author");
        grid.addColumn(dissertation -> {
            Subject subject = dissertation.getSubject();
            return subject.toString();
        }).setHeader("Subject").setSortProperty("subject");
        grid.setPageSize(10);
        grid.setDataProvider(customDataProvider);

        grid.asSingleSelect().addValueChangeListener(event -> editDissertation(event.getValue()));
    }


    private void closeEditor() {
        form.setDissertation(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveDissertation(DissertationForm.saveEvent event) {
        Dissertation dissertation = dissertationService.save(event.getDissertation());
        customDataProvider.refreshItem(dissertation);
        closeEditor();
    }

    private void deleteDissertation(DissertationForm.deleteEvent event) {
        dissertationService.delete(event.getDissertation());
        customDataProvider.refreshAll();
        closeEditor();
    }


    public void editDissertation(Dissertation dissertation) {
        if (dissertation == null) closeEditor();
        else {
            form.setDissertation(dissertation);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private DataProvider<Dissertation, DissertationFilter> createDataProvider(DissertationService dissertationService) {
        DataProvider<Dissertation, DissertationFilter> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> {
                    Optional<DissertationFilter> filter = query.getFilter();
                    List<DissertationSort> sortOrders = new ArrayList<>();
                    for (SortOrder<String> queryOrder : query.getSortOrders()) {
                        DissertationSort sort = getDissertationService().createSort(queryOrder.getSorted(),
                                queryOrder.getDirection() == SortDirection.DESCENDING);
                        sortOrders.add(sort);
                    }

                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    int page = offset/limit;
                    List<Dissertation> books = getDissertationService().fetch(page, limit,
                            filter.map(f -> f.getTitle()).orElse(null),
                            filter.map(f -> f.getAuthors()).orElse(null),
                            filter.map(f -> f.getSubjects()).orElse(null),
                            sortOrders);
                    return books.stream();
                },

                query -> {
                    Optional<DissertationFilter> filter = query.getFilter();
                    return (int) getDissertationService().getDissertationCount(
                            filter.map(f -> f.getTitle()).orElse(null),
                            filter.map(f -> f.getAuthors()).orElse(null),
                            filter.map(f -> f.getSubjects()).orElse(null));
                });
        return dataProvider;
    }
    private DissertationService getDissertationService() {
        return dissertationService;
    }
}
