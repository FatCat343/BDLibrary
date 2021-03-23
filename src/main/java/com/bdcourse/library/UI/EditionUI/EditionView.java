package com.bdcourse.library.UI.EditionUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.bookPosition.BookPosition;
import com.bdcourse.library.bookPosition.BookPositionService;
import com.bdcourse.library.edition.Edition;
import com.bdcourse.library.edition.EditionService;
import com.bdcourse.library.edition.indoorEdition.IndoorEdition;
import com.bdcourse.library.edition.indoorEdition.IndoorEditionService;
import com.bdcourse.library.edition.outdoorEdition.OutdoorEdition;
import com.bdcourse.library.edition.outdoorEdition.OutdoorEditionService;
import com.bdcourse.library.publication.PublicationService;
import com.bdcourse.library.staff.Staff;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;


@Route(value = "edition", layout = MainView.class)
public class EditionView extends VerticalLayout {
    private Grid<Edition> grid = new Grid<>(Edition.class);
    private EditionService editionService;
    private IndoorEditionService indoorEditionService;
    private OutdoorEditionService outdoorEditionService;
    private IndoorEditionForm indoorEditionForm;
    private OutdoorEditionForm outdoorEditionForm;

    private ComboBox<String> filterVariants = new ComboBox<>("Filter By:");
    private DatePicker startDate = new DatePicker("From:");
    private DatePicker finishDate = new DatePicker("To:");
    private Boolean isArrived = null;

    public EditionView(IndoorEditionService indoorEditionService, OutdoorEditionService outdoorEditionService,
                       PublicationService publicationService, BookPositionService bookPositionService,
                       EditionService editionService) {
        this.editionService = editionService;
        this.outdoorEditionService = outdoorEditionService;
        this.indoorEditionService = indoorEditionService;
        setSizeFull();
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        indoorEditionForm = new IndoorEditionForm(publicationService, bookPositionService);
        indoorEditionForm.addListener(IndoorEditionForm.saveEvent.class, this::saveIndoorEdition);
        indoorEditionForm.addListener(IndoorEditionForm.deleteEvent.class, this::deleteIndoorEdition);
        indoorEditionForm.addListener(IndoorEditionForm.closeEvent.class, e -> closeIndoorEditionEditor());

        outdoorEditionForm = new OutdoorEditionForm(publicationService, bookPositionService);
        outdoorEditionForm.addListener(OutdoorEditionForm.saveEvent.class, this::saveOutdoorEdition);
        outdoorEditionForm.addListener(OutdoorEditionForm.deleteEvent.class, this::deleteOutdoorEdition);
        outdoorEditionForm.addListener(OutdoorEditionForm.closeEvent.class, e -> closeOutdoorEditionEditor());

        add(toolbar, indoorEditionForm, outdoorEditionForm, grid);

        updateList();
        closeOutdoorEditionEditor();
        closeIndoorEditionEditor();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();;
        grid.addColumn(Edition::getCode).setHeader("Edition Code").setSortProperty("code");
        grid.addColumn(Edition::getPublication).setHeader("Publication").setSortProperty("publication");
        //grid.addColumn(Edition::getPosition).setHeader("Edition Placement").setSortProperty("position");
        grid.addColumn(Edition::getDateArrived).setHeader("Edition Date of Arrival").setSortProperty("dateArrived");
        grid.addColumn(Edition::getDateLeft).setHeader("Edition Date of Leaving").setSortProperty("dateLeft");
        grid.setItemDetailsRenderer(TemplateRenderer.<Edition>of(
                "<div class='custom-details' style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box;'>"
                        + "<div>Assigned to storage : <b>[[item.storage]]</b></div>"
                        + "<div><b>[[item.info]]</b></div>"
                        + "</div>")
                .withProperty("storage", edition -> edition.getPosition().toString())
                .withProperty("info", edition -> {
                    if (indoorEditionService.find(edition) != null) return "Reason for indoor usage only: " +
                            indoorEditionService.findFetch(edition).getReason();
                    else {
                        if (outdoorEditionService.find(edition) != null) return "Rental period: " +
                                outdoorEditionService.findFetch(edition).getRentalPeriod() + " days";
                    }
                    return "";
                })
                // This is now how we open the details
                .withEventHandler("handleClick", edition -> {
                    grid.getDataProvider().refreshItem(edition);
                }));
        grid.setDetailsVisibleOnClick(false);

        grid.addColumn(new NativeButtonRenderer<>("Details", item -> grid
                .setDetailsVisible(item, !grid.isDetailsVisible(item))));
        grid.asSingleSelect().addValueChangeListener(event -> {
            Edition edition = event.getValue();
            if (edition == null) {
                closeIndoorEditionEditor();
                closeOutdoorEditionEditor();
            }
            else {
                if (indoorEditionService.find(edition) != null)
                    editIndoorEdition(indoorEditionService.findFetch(edition));
                if (outdoorEditionService.find(edition) != null)
                    editOutdoorEdition(outdoorEditionService.findFetch(edition));
            }
        });
    }

    private HorizontalLayout configureToolBar() {
        Button addIndoorEditionButton = new Button("Add Indoor Edition");
        addIndoorEditionButton.addClickListener(click -> addIndoorEdition());
        Button addOutdoorEditionButton = new Button("Add Outdoor Edition");
        addOutdoorEditionButton.addClickListener(click -> addOutdoorEdition());

        filterVariants.setItems("Arrival Date", "Leaving Date");
        filterVariants.setClearButtonVisible(true);
        filterVariants.addValueChangeListener(event -> {
            if (event.getValue() == null) isArrived = null;
            else {
                if (event.getValue().equals("Arrival Date")) isArrived = true;
                else {
                    if (event.getValue().equals("Arrival Date")) isArrived = false;
                }
            }
            updateList();
        });

        startDate.addValueChangeListener(event -> {
            LocalDate selected = event.getValue();
            LocalDate finish = finishDate.getValue();
            if (selected != null) {
                finishDate.setMin(selected.plusDays(1));
            } else {
                finishDate.setMin(null);
            }
            updateList();
        });
        finishDate.addValueChangeListener(event -> {
            LocalDate selected = event.getValue();
            LocalDate start = startDate.getValue();
            if (selected != null) {
                startDate.setMax(selected.minusDays(1));
            } else {
                startDate.setMax(null);
            }
            updateList();
        });

        return new HorizontalLayout(addIndoorEditionButton, addOutdoorEditionButton, filterVariants, startDate, finishDate);
    }

    private void addIndoorEdition() {
        grid.asSingleSelect().clear();
        indoorEditionForm.setIndoorEdition(new IndoorEdition());
        indoorEditionForm.setVisible(true);
    }

    private void addOutdoorEdition() {
        grid.asSingleSelect().clear();
        outdoorEditionForm.setOutdoorEdition(new OutdoorEdition());
        outdoorEditionForm.setVisible(true);
    }

    private void updateList() {
        grid.setItems(editionService.findAllByDateFetchAll(isArrived, startDate.getValue(), finishDate.getValue()));
    }

    private void saveIndoorEdition(IndoorEditionForm.saveEvent event) {
        IndoorEdition edition = indoorEditionService.save(event.getIndoorEdition());
        updateList();
        closeIndoorEditionEditor();
    }

    private void saveOutdoorEdition(OutdoorEditionForm.saveEvent event) {
        OutdoorEdition edition = outdoorEditionService.save(event.getOutdoorEdition());
        updateList();
        closeOutdoorEditionEditor();
    }

    private void deleteIndoorEdition(IndoorEditionForm.deleteEvent event) {
        indoorEditionService.delete(event.getIndoorEdition());
        updateList();
        closeIndoorEditionEditor();
    }

    private void deleteOutdoorEdition(OutdoorEditionForm.deleteEvent event) {
        outdoorEditionService.delete(event.getOutdoorEdition());
        updateList();
        closeOutdoorEditionEditor();
    }

    private void closeIndoorEditionEditor(){
        indoorEditionForm.setIndoorEdition(null);
        indoorEditionForm.setVisible(false);
    }

    private void closeOutdoorEditionEditor(){
        outdoorEditionForm.setOutdoorEdition(null);
        outdoorEditionForm.setVisible(false);
    }


    private void editOutdoorEdition(OutdoorEdition edition) {
        if (edition == null) closeOutdoorEditionEditor();
        else {
            outdoorEditionForm.setOutdoorEdition(edition);
            outdoorEditionForm.setVisible(true);
        }
    }

    private void editIndoorEdition(IndoorEdition edition) {
        if (edition == null) closeIndoorEditionEditor();
        else {
            indoorEditionForm.setIndoorEdition(edition);
            indoorEditionForm.setVisible(true);
        }
    }

}
