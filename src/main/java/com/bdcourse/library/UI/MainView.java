package com.bdcourse.library.UI;

import com.bdcourse.library.UI.AuthorUI.AuthorView;
import com.bdcourse.library.UI.BookPositionUI.BookPositionView;
import com.bdcourse.library.UI.BookUI.BookView;
import com.bdcourse.library.UI.DissertationUI.DissertationView;
import com.bdcourse.library.UI.DistributionUI.DistributionView;
import com.bdcourse.library.UI.QueriesUI.FindEditionByAuthorOrPublication;
import com.bdcourse.library.UI.QueriesUI.FindPublicationByPopularity;
import com.bdcourse.library.UI.QueriesUI.FindReadersByEdition;
import com.bdcourse.library.UI.StaffUI.StaffView;
import com.bdcourse.library.UI.StudentUI.StudentView;
import com.bdcourse.library.UI.WorkerUI.WorkerView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.RouterLink;


public class MainView extends AppLayout {
    private MenuBar menuBar = new MenuBar();

    public MainView() {
        MenuItem readers = menuBar.addItem("readers");
        MenuItem publications = menuBar.addItem("publications");
        MenuItem editions = menuBar.addItem("editions");
        MenuItem queries = menuBar.addItem("queries");
        MenuItem staff = menuBar.addItem("staff");
        MenuItem author = menuBar.addItem("authors");
        MenuItem bookPosition = menuBar.addItem("BookPositions");
        MenuItem distribution = menuBar.addItem("Distribution");
        author.getSubMenu().addItem(new RouterLink("Authors", AuthorView.class));
        readers.getSubMenu().addItem(new RouterLink("Students", StudentView.class));
        readers.getSubMenu().addItem(new RouterLink("Workers", WorkerView.class));
        queries.getSubMenu().addItem(new RouterLink("Find Reader by Publication Name", FindReadersByEdition.class));
        queries.getSubMenu().addItem(new RouterLink("Get Publications by Popularity", FindPublicationByPopularity.class));
        queries.getSubMenu().addItem(new RouterLink("Find Edition By Publication Or Author", FindEditionByAuthorOrPublication.class));
        staff.getSubMenu().addItem(new RouterLink("Staff", StaffView.class));
        bookPosition.getSubMenu().addItem(new RouterLink("BookPositions", BookPositionView.class));
        distribution.getSubMenu().addItem(new RouterLink("Distribution", DistributionView.class));
        publications.getSubMenu().addItem(new RouterLink("Books", BookView.class));
        publications.getSubMenu().addItem(new RouterLink("Dissertations", DissertationView.class));
        //addToDrawer(menuBar);
        addToNavbar(menuBar);
    }


//    @Override
//    public void beforeEnter(BeforeEnterEvent event) {
//        //tabs.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
//    }

}
