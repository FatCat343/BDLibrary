package com.bdcourse.library.UI;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route(value = "reader", layout = MainView.class)
public class ReaderView extends Div {
    public ReaderView(){
        add(new Span("Readers grid"));
    }
}
