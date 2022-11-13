package com.example.application.views.pages;

import com.example.application.views.main.MainContainer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="Page2", layout = MainContainer.class)
@PageTitle("Page2 | Vaadin CRM")
public class Page2 extends VerticalLayout {

    public Page2(){
        H2 h2 = new H2("Pagina 2");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(h2);
    }

}
