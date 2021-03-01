package com.bdcourse.library.UI;

import com.bdcourse.library.UI.StudentUI.StudentView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.RouterLink;


public class MainView extends AppLayout {
    private MenuBar menuBar = new MenuBar();

    public MainView() {
        MenuItem readers = menuBar.addItem("readers");
        MenuItem editions = menuBar.addItem("editions");
        readers.getSubMenu().addItem(new RouterLink("students", StudentView.class));
        readers.getSubMenu().addItem(new RouterLink("workers", WorkerView.class));
        //addToDrawer(menuBar);
        addToNavbar(menuBar);
    }


//    @Override
//    public void beforeEnter(BeforeEnterEvent event) {
//        //tabs.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
//    }

}
