package com.bdcourse.library.UI.DistributionUI;

import com.bdcourse.library.UI.MainView;
import com.bdcourse.library.distribution.Distribution;
import com.bdcourse.library.distribution.DistributionService;
import com.bdcourse.library.edition.Edition;
import com.bdcourse.library.edition.EditionService;
import com.bdcourse.library.reader.Reader;
import com.bdcourse.library.reader.ReaderService;
import com.bdcourse.library.staff.Staff;
import com.bdcourse.library.staff.StaffService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.Route;

@Route(value = "distribution", layout = MainView.class)
public class DistributionView extends VerticalLayout {
    private DistributionService distributionService;
    private Grid<Distribution> grid = new Grid<>(Distribution.class);

    private DistributionForm form;

    public DistributionView(DistributionService distributionService, ReaderService readerService,
                            EditionService editionService, StaffService staffService) {
        this.distributionService = distributionService;
        setSizeFull();
        HorizontalLayout toolbar = configureToolBar();
        configureGrid();
        form = new DistributionForm(readerService, editionService, staffService, distributionService);
        form.addListener(DistributionForm.saveEvent.class, this::saveDistribution);
        form.addListener(DistributionForm.deleteEvent.class, this::deleteDistribution);
        form.addListener(DistributionForm.closeEvent.class, e -> closeEditor());
        add(toolbar, form, grid);

        updateList();
        closeEditor();
    }

    private HorizontalLayout configureToolBar() {
        Button addDistributionButton = new Button("Add Distribution");
        addDistributionButton.addClickListener(click -> addDistribution());
        return new HorizontalLayout(addDistributionButton);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Distribution::getDateGive).setHeader("Give Date").setSortProperty("dateGive");
        grid.addColumn(Distribution::getDateReturn).setHeader("Return Date").setSortProperty("dateReturn");
        grid.asSingleSelect().addValueChangeListener(event -> editDistribution(event.getValue()));

        grid.setItemDetailsRenderer(TemplateRenderer.<Distribution>of(
                "<div class='custom-details' style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box;'>"
                        + "<div>Edition : <b>[[item.edition]]</b></div>"
                       // + "<div>Edition : <b>[[item.publication]]!</b></div>"
                        + "<div>Reader : <b>[[item.reader]]</b></div>"
                        + "<div>Staff : <b>[[item.staff]]</b></div>"
                        + "<div>Storage : <b>[[item.storage]]</b></div>"
                        + "</div>")
                .withProperty("edition", distribution -> {
                    Edition edition = distributionService.findDistributionFetchEdition(distribution).getEdition();
                    return edition.getCode();
                })
                .withProperty("reader", distribution -> {
                    Reader reader = distributionService.findDistributionFetchReader(distribution).getReader();
                    return reader.toString();
                })
                .withProperty("staff", distribution -> {
                    Staff staff = distributionService.findDistributionFetchStaff(distribution).getStaff();
                    return staff.toString();
                })
                .withProperty("storage", distribution -> {
                    Reader reader = distributionService.findDistributionFetchReader(distribution).getReader();
                    return reader.getLibrary().toString();
                })
                // This is now how we open the details
                .withEventHandler("handleClick", distribution -> {
                    grid.getDataProvider().refreshItem(distribution);
                }));

        grid.setDetailsVisibleOnClick(false);
        grid.addColumn(new NativeButtonRenderer<>("Details", item -> grid
                .setDetailsVisible(item, !grid.isDetailsVisible(item))));
    }

    private void addDistribution() {
        grid.asSingleSelect().clear();
        form.setDistributionNotFetched(new Distribution());
        form.setVisible(true);
    }

    private void updateList() {
        grid.setItems(distributionService.findAll());
    }

    private void saveDistribution(DistributionForm.saveEvent event) {
        Distribution distribution = distributionService.save(event.getDistribution());
        updateList();
        closeEditor();
    }

    private void deleteDistribution(DistributionForm.deleteEvent event) {
        distributionService.delete(event.getDistribution());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        grid.asSingleSelect().clear();
        form.setDistribution(null);
        form.setVisible(false);
    }

    private void editDistribution(Distribution distribution) {
        if (distribution == null) closeEditor();
        else {
            form.setDistribution(distribution);
            form.setVisible(true);
        }
    }
}
