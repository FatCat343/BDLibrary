package com.bdcourse.library.UI;

import com.bdcourse.library.UI.QueriesUI.FindPublicationByPopularity;
import com.bdcourse.library.UI.QueriesUI.FindReadersByEdition;
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
        MenuItem editions = menuBar.addItem("editions");
        MenuItem queries = menuBar.addItem("queries");
        readers.getSubMenu().addItem(new RouterLink("students", StudentView.class));
        readers.getSubMenu().addItem(new RouterLink("workers", WorkerView.class));
        queries.getSubMenu().addItem(new RouterLink("Find Reader by Publication Name", FindReadersByEdition.class));
        queries.getSubMenu().addItem(new RouterLink("Get Publications by Popularity", FindPublicationByPopularity.class));
        //addToDrawer(menuBar);
        addToNavbar(menuBar);
    }


//    @Override
//    public void beforeEnter(BeforeEnterEvent event) {
//        //tabs.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
//    }

}
