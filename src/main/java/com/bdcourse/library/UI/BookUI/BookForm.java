package com.bdcourse.library.UI.BookUI;

import com.bdcourse.library.publication.PublicationService;
import com.bdcourse.library.publication.author.Author;
import com.bdcourse.library.publication.author.AuthorService;
import com.bdcourse.library.publication.book.Book;
import com.bdcourse.library.publication.book.category.Category;
import com.bdcourse.library.publication.book.category.CategoryService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class BookForm extends VerticalLayout {
    TextField title = new TextField("Title");
    ComboBox<Author> author = new ComboBox<>("Author");
    ComboBox<Category> category = new ComboBox<>("Category");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    private CategoryForm categoryForm;
    private CategoryService categoryService;
    private PublicationService publicationService;

    Binder<Book> bookBinder = new Binder<>(Book.class);
    private Book book;

    public BookForm(AuthorService authorService, CategoryService categoryService, PublicationService publicationService) {
        this.publicationService = publicationService;
        this.categoryService = categoryService;
        bookBinder.bindInstanceFields(this);
        bookBinder.forField(title)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 100, "Maximum 100 letters")
                .bind(Book::getTitle, Book::setTitle);
        author.setItems(authorService.findAll());
        category.setItems(categoryService.findAll());

        title.setRequired(true);
        author.setRequired(true);
        category.setRequired(true);

        categoryForm = new CategoryForm(categoryService);
        categoryForm.addListener(CategoryForm.saveEvent.class,  this::saveCategory);
        categoryForm.addListener(CategoryForm.deleteEvent.class,  this::deleteCategory);
        categoryForm.addListener(CategoryForm.closeEvent.class, e -> closeEditor());

        add(createFieldsLayout(), createButtonsLayout(), categoryForm);

        closeEditor();
    }

    private void closeEditor() {
        categoryForm.setCategory(null);
        categoryForm.setVisible(false);
    }

    private void saveCategory(CategoryForm.saveEvent event) {
        categoryService.save(event.getCategory());
        category.setItems(categoryService.findAll());
        closeEditor();
    }

    private void deleteCategory(CategoryForm.deleteEvent event) {
        categoryService.delete(event.getCategory());
        category.setItems(categoryService.findAll());
        closeEditor();
    }

    private HorizontalLayout createFieldsLayout() {
        Button addCategoryButton = new Button("Add Category");
        addCategoryButton.addClickListener(click -> addCategory());
        return new HorizontalLayout(author, title, category, addCategoryButton);
    }

    private void addCategory() {
        categoryForm.setCategory(new Category());
        categoryForm.setVisible(true);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, book)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        bookBinder.addStatusChangeListener(e -> save.setEnabled(bookBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            bookBinder.writeBean(book);
            if (!publicationService.exist(book)) {
                fireEvent(new saveEvent(this, book));
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

    public void setBook(Book book) {
        this.book = new Book(book);
        bookBinder.readBean(book);
    }

    public static abstract class BookFormEvent extends ComponentEvent<BookForm> {
        private Book book;

        protected BookFormEvent(BookForm source, Book book){
            super(source, false);
            this.book = book;
        }

        public Book getBook() {
            return book;
        }
    }

    public static class saveEvent extends BookFormEvent {
        saveEvent(BookForm source, Book book) {
            super(source, book);
        }
    }

    public static class deleteEvent extends BookFormEvent {
        deleteEvent(BookForm source, Book book) {
            super(source, book);
        }
    }

    public static class closeEvent extends BookFormEvent {
        closeEvent(BookForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {

        return getEventBus().addListener(eventType, listener);
    }
}
