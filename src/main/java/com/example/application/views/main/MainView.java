package com.example.application.views.main;

import com.example.application.views.grid.ContainerGrid;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainView {

    private HorizontalLayout header;
    private RouterLink listView;

    public MainView() {
        header();
        drawer();
    }

    private void header() {
        H1 logo = new H1("Vaadin CRM");
        logo.addClassNames("text-l", "m-m");

        //DrawerToggle -> Menú que cambia la visibilidad de la barra lateral.
        header = new HorizontalLayout(new DrawerToggle(), logo);

        //Centramos los componentes de la cabecera en el eje vertical
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        //header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");
    }

    private void drawer() {
        //Navegacion entre formularios (Lista es la url y la clase es el formulario destino)
        listView = new RouterLink("Lista", ContainerGrid.class);
        //Evitar resaltar el enlace en caso de coincidencias parciales de ruta. (
        //Cada ruta comienza con una ruta vacía, por lo que sin esto, siempre se mostraría como activa, aunque el usuario no esté en la vista).
        listView.setHighlightCondition(HighlightConditions.sameLocation());
    }

    public HorizontalLayout getHeader() {
        return header;
    }

    public RouterLink getListView() {
        return listView;
    }
}
