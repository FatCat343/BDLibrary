package com.bdcourse.library.UI.BookUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.edition.EditionService;
import com.bdcourse.library.publication.PublicationService;
import com.bdcourse.library.publication.author.Author;
import com.bdcourse.library.publication.author.AuthorService;
import com.bdcourse.library.publication.book.Book;
import com.bdcourse.library.publication.book.BookService;
import com.bdcourse.library.publication.book.category.Category;
import com.bdcourse.library.publication.book.category.CategoryService;
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


@Route(value = "books", layout = MainView.class)
public class BookView extends VerticalLayout {
    private BookService bookService;
    private PaginatedGrid<Book> grid = new PaginatedGrid<>(Book.class);

    private TextField title = new TextField();
    private MultiselectComboBox<Author> author = new MultiselectComboBox<>();
    private MultiselectComboBox<Category> category = new MultiselectComboBox<>();

    private BookForm form;

    ConfigurableFilterDataProvider<Book, Void, BookFilter> customDataProvider;
    BookFilter bookFilter;

    public BookView(BookService bookService, CategoryService categoryService, AuthorService authorService,
                    PublicationService publicationService) {
        this.bookService = bookService;
        bookFilter = new BookFilter();
        addClassName("book-view");
        setSizeFull();
        DataProvider<Book, BookFilter> dataProvider = createDataProvider(bookService);
        customDataProvider = dataProvider.withConfigurableFilter();
        customDataProvider.setFilter(bookFilter);
        author.setItems(authorService.findAll());
        category.setItems(categoryService.findAll());
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new BookForm(authorService, categoryService, publicationService);
        form.addListener(BookForm.saveEvent.class, this::saveBook);
        form.addListener(BookForm.deleteEvent.class, this::deleteBook);
        form.addListener(BookForm.closeEvent.class, e -> closeEditor());
        add(toolbar, form, grid);

        closeEditor();
    }

    private HorizontalLayout configureToolBar() {
        configureFilter(title);
        author.setPlaceholder("Choose Authors...");
        category.setPlaceholder("Choose Categories...");

        title.addValueChangeListener(event -> {
            if ((event.getValue() != null) && (!event.getValue().equals(""))) bookFilter.setTitle(event.getValue());
            else bookFilter.setTitle(null);
            customDataProvider.refreshAll();
        });
        author.addValueChangeListener(event -> {
            if ((event.getValue() != null) && (!event.getValue().isEmpty())) bookFilter.setAuthors(event.getValue());
            else bookFilter.setAuthors(null);
            customDataProvider.refreshAll();
        });
        category.addValueChangeListener(event -> {
            if ((event.getValue() != null) && (!event.getValue().isEmpty())) bookFilter.setCategories(event.getValue());
            else bookFilter.setCategories(null);

            customDataProvider.refreshAll();
        });

        Button addBookButton = new Button("Add Book");
        addBookButton.addClickListener(click -> addBook());

        return new HorizontalLayout(title, author, category, addBookButton);
    }

    void addBook() {
        grid.asSingleSelect().clear();
        editBook(new Book());
    }

    private void configureFilter(TextField field) {
        field.setPlaceholder("Title filter");
        field.setClearButtonVisible(true);
        field.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void configureGrid(){
        grid.addClassName("student-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Book::getTitle).setHeader("Title").setSortProperty("title");
        grid.addColumn(book -> {
            Author author = book.getAuthor();
            return author.toString();
        }).setHeader("Author").setSortProperty("author");
        grid.addColumn(book -> {
            Category category = book.getCategory();
            return category.toString();
        }).setHeader("Category").setSortProperty("category");
        grid.setPageSize(10);
        grid.setDataProvider(customDataProvider);

        grid.asSingleSelect().addValueChangeListener(event -> editBook(event.getValue()));
    }


    private void closeEditor() {
        grid.asSingleSelect().clear();
        form.setBook(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveBook(BookForm.saveEvent event) {
        Book book = bookService.save(event.getBook());
        customDataProvider.refreshItem(book);
        closeEditor();
    }

    private void deleteBook(BookForm.deleteEvent event) {
        bookService.delete(event.getBook());
        customDataProvider.refreshAll();
        closeEditor();
    }


    public void editBook(Book book) {
        if (book == null) closeEditor();
        else {
            form.setBook(book);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private DataProvider<Book, BookFilter> createDataProvider(BookService bookService) {
        DataProvider<Book, BookFilter> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> {
                    Optional<BookFilter> filter = query.getFilter();
                    List<BookSort> sortOrders = new ArrayList<>();
                    for (SortOrder<String> queryOrder : query.getSortOrders()) {
                        BookSort sort = getBookService().createSort(queryOrder.getSorted(),
                                queryOrder.getDirection() == SortDirection.DESCENDING);
                        sortOrders.add(sort);
                    }

                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    int page = offset/limit;
                    List<Book> books = getBookService().fetch(page, limit,
                            filter.map(f -> f.getTitle()).orElse(null),
                            filter.map(f -> f.getAuthors()).orElse(null),
                            filter.map(f -> f.getCategories()).orElse(null),
                            sortOrders);
                    return books.stream();
                },

                query -> {
                    Optional<BookFilter> filter = query.getFilter();
                    return (int) getBookService().getBookCount(
                            filter.map(f -> f.getTitle()).orElse(null),
                            filter.map(f -> f.getAuthors()).orElse(null),
                            filter.map(f -> f.getCategories()).orElse(null));
                });
        return dataProvider;
    }
    private BookService getBookService() {
        return bookService;
    }
}
