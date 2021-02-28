package com.bdcourse.library.UI;

import com.bdcourse.library.reader.student.Student;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.HashMap;
import java.util.Map;


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
