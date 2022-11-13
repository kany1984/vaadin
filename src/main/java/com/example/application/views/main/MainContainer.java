package com.example.application.views.main;

import com.example.application.views.pages.Page2;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

//Applayout es un tipo de vista con cabecera y un contenedor responsivo
public class MainContainer extends AppLayout {

    public MainContainer() {
        MainView mainLayout = new MainView();
        //Añadimos la cabecera a la barra de navegacion (la seccion de la parte superior de la pantalla).
        addToNavbar(mainLayout.getHeader());
        //Añadimos el verticalLayout al contenedor (drawer)
        addToDrawer(new VerticalLayout(
            mainLayout.getListView(),
            new RouterLink("Pagina 2", Page2.class)
        ));
    }
}
