package com.bdcourse.library.UI;

import com.bdcourse.library.staff.StaffService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainView.class)
public class DefaultView extends VerticalLayout {

    //private IntegerField field = new IntegerField()
    private StaffService staffService;
    private Header header = new Header();
    public DefaultView(StaffService staffService) {
        this.staffService = staffService;
        header.setText("Welcome");
        add(header, new Span("Worker Of The Century: " + staffService.findMostProductiveStaff()));
    }
}
