package com.bdcourse.library.UI;

import com.bdcourse.library.UI.AuthorUI.AuthorView;
import com.bdcourse.library.UI.BookPositionUI.BookPositionView;
import com.bdcourse.library.UI.BookUI.BookView;
import com.bdcourse.library.UI.DissertationUI.DissertationView;
import com.bdcourse.library.UI.DistributionUI.DistributionView;
import com.bdcourse.library.UI.EditionUI.EditionView;
import com.bdcourse.library.UI.LibraryUI.LibraryView;
import com.bdcourse.library.UI.QueriesUI.*;
import com.bdcourse.library.UI.StaffUI.StaffView;
import com.bdcourse.library.UI.StorageUI.StorageView;
import com.bdcourse.library.UI.StudentUI.StudentView;
import com.bdcourse.library.UI.WorkerUI.WorkerView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.RouterLink;


public class MainView extends AppLayout {
    private MenuBar menuBar = new MenuBar();

    public MainView() {
        MenuItem readers = menuBar.addItem("Readers");
        MenuItem publications = menuBar.addItem("Publications");
        MenuItem editions = menuBar.addItem("Editions");
        MenuItem queries = menuBar.addItem("Queries");
        MenuItem staff = menuBar.addItem("Staff");
        MenuItem author = menuBar.addItem("Authors");
        MenuItem bookPosition = menuBar.addItem("BookPositions");
        MenuItem storages = menuBar.addItem("Storages");
        MenuItem libraries = menuBar.addItem("Libraries");
        MenuItem distribution = menuBar.addItem("Distribution");
        author.getSubMenu().addItem(new RouterLink("Authors", AuthorView.class));
        readers.getSubMenu().addItem(new RouterLink("Students", StudentView.class));
        readers.getSubMenu().addItem(new RouterLink("Workers", WorkerView.class));
        queries.getSubMenu().addItem(
                new RouterLink("Find Reader by Publication Name", FindReadersByPublication.class));
        queries.getSubMenu().addItem(
                new RouterLink("Find Reader by Edition Code", FindReadersByEdition.class));
        queries.getSubMenu().addItem(
                new RouterLink("Get Publications by Popularity", FindPublicationByPopularity.class));
        queries.getSubMenu().addItem(
                new RouterLink("Find Edition By Publication Or Author", FindEditionByAuthorOrPublication.class));
        queries.getSubMenu().addItem(
                new RouterLink("Find Reader And Edition By Publication And Date", FindReaderAndEditionByPublicationAndDate.class));
        queries.getSubMenu().addItem(
                new RouterLink("Find Edition By Reader In Library", FindEditionByReaderInLibrary.class));
        queries.getSubMenu().addItem(
                new RouterLink("Find Given Edition By Position", FindGivenEditionByPosition.class));
        queries.getSubMenu().addItem(
                new RouterLink("Find Reader By Staff And Date", FindReaderByStaffAndDate.class));
        staff.getSubMenu().addItem(new RouterLink("Staff", StaffView.class));
        bookPosition.getSubMenu().addItem(new RouterLink("BookPositions", BookPositionView.class));
        distribution.getSubMenu().addItem(new RouterLink("Distribution", DistributionView.class));
        publications.getSubMenu().addItem(new RouterLink("Books", BookView.class));
        publications.getSubMenu().addItem(new RouterLink("Dissertations", DissertationView.class));
        storages.getSubMenu().addItem(new RouterLink("Storages", StorageView.class));
        libraries.getSubMenu().addItem(new RouterLink("Libraries", LibraryView.class));
        editions.getSubMenu().addItem(new RouterLink("Editions", EditionView.class));
        //addToDrawer(menuBar);
        addToNavbar(menuBar);
    }


//    @Override
//    public void beforeEnter(BeforeEnterEvent event) {
//        //tabs.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
//    }

}
