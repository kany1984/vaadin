package com.example.application.views.grid;

import com.example.application.service.CrmService;
import com.example.application.views.main.MainContainer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

//Asociamos la vista a la pagina principal (Route), para poder navegar desde el menu lateral
@Route(value="", layout = MainContainer.class)
//@Route(value="") // -> Sin menu
@PageTitle("Contacts | Vaadin CRM")
public class ContainerGrid extends VerticalLayout {

    private GridForm formulario;
    private CrmService crmService;

    //Inyectamos el servicio en el constructor de la vista
    public ContainerGrid(CrmService crmService) {
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
        formulario = new GridForm(crmService);
    }

}
