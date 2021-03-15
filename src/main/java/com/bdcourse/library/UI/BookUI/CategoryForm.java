package com.bdcourse.library.UI.BookUI;


import com.bdcourse.library.publication.book.category.Category;
import com.bdcourse.library.publication.book.category.CategoryService;
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

public class CategoryForm extends VerticalLayout {
    TextField name = new TextField("New Category Name");

    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");

    Binder<Category> categoryBinder = new Binder<>(Category.class);
    private Category category;

    public CategoryForm(CategoryService categoryService) {
        addClassName("category-form");
        //studentBinder.forField(code).withConverter(new DoubleToIntegerConverter()).bind(Student::getCode, Student::setCode);
        categoryBinder.bindInstanceFields(this);
        categoryBinder.forField(name)
                .withValidator(min -> min.length() >= 1, "Minimum 1 letter")
                .withValidator(max -> max.length() <= 20, "Maximum 20 letters")
                .bind(Category::getName, Category::setName);

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
        delete.addClickListener(event -> fireEvent(new deleteEvent(this, category)));
        close.addClickListener(event -> fireEvent(new closeEvent(this)));

        delete.setEnabled(false);

        categoryBinder.addStatusChangeListener(e -> save.setEnabled(categoryBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            categoryBinder.writeBean(category);
            fireEvent(new saveEvent(this, category));
        }
        catch (ValidationException err) {
            err.printStackTrace();
        }
    }

    public void setCategory(Category category) {
        this.category = category;
        categoryBinder.readBean(category);
    }

    public static abstract class CategoryFormEvent extends ComponentEvent<CategoryForm> {
        private Category category;

        protected CategoryFormEvent(CategoryForm source, Category category){
            super(source, false);
            this.category = category;
        }

        public Category getCategory() {
            return category;
        }
    }

    public static class saveEvent extends CategoryFormEvent {
        saveEvent(CategoryForm source, Category category) {
            super(source, category);
        }
    }

    public static class deleteEvent extends CategoryFormEvent {
        deleteEvent(CategoryForm source, Category category) {
            super(source, category);
        }
    }

    public static class closeEvent extends CategoryFormEvent {
        closeEvent(CategoryForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {

        return getEventBus().addListener(eventType, listener);
    }
}
