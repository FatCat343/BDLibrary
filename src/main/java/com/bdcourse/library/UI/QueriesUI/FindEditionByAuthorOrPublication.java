package com.bdcourse.library.UI.QueriesUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.edition.Edition;
import com.bdcourse.library.edition.EditionService;
import com.bdcourse.library.library.Library;
import com.bdcourse.library.publication.Publication;
import com.bdcourse.library.publication.PublicationService;
import com.bdcourse.library.publication.author.Author;
import com.bdcourse.library.publication.author.AuthorService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "FindEditionByAuthorOrPublication", layout = MainView.class)
public class FindEditionByAuthorOrPublication extends VerticalLayout {
    EditionService editionService;
    ComboBox<Author> authorComboBox = new ComboBox<>("Choose Author");
    ComboBox<Publication> publicationComboBox = new ComboBox<>("Choose Publication");
    Grid<Edition> grid = new Grid<>(Edition.class);

    public FindEditionByAuthorOrPublication(EditionService editionService, AuthorService authorService,
                                            PublicationService publicationService) {
        this.editionService = editionService;
        setSizeFull();
        configureGrid();
        authorComboBox.setItems(authorService.findAll());
        publicationComboBox.setItems(publicationService.findAll());
        add(configureToolBar(), grid);

    }

    private HorizontalLayout configureToolBar() {
        authorComboBox.setClearButtonVisible(true);
        authorComboBox.addValueChangeListener(event -> {
            updateListAuthor(event.getValue());
            publicationComboBox.clear();
        });
        publicationComboBox.setClearButtonVisible(true);
        publicationComboBox.addValueChangeListener(event -> {
            updateListPublication(event.getValue());
            authorComboBox.clear();
        });
        return new HorizontalLayout(authorComboBox, publicationComboBox);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("code");

    }

    private void updateListAuthor(Author author) {
        grid.setItems(editionService.findEditionByAuthor(author));
    }
    private void updateListPublication(Publication publication) {
        grid.setItems(editionService.findEditionByPublication(publication));
    }

}
