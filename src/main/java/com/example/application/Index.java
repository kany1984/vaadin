package com.example.application;

import com.example.application.service.CrmService;
import com.example.application.views.Formulario;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("list")
@Route(value = "")
public class Index extends VerticalLayout {

    private Formulario formulario;
    private CrmService crmService;

    public Index(CrmService crmService) {
        this.crmService = crmService;
        //Iniciamos el formulario inicial
        init();
        add(formulario.form());
    }

    //Creamos un div con
    public void init() {
        addClassName("cotenedor");
        //El contenedor ocupa el 100% del espacio
        setSizeFull();
        //Iniciamos componentes
        formulario = new Formulario(crmService);
    }

}
