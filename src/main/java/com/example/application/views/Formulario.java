package com.example.application.views;

import com.example.application.entity.Contact;
import com.example.application.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class Formulario extends VerticalLayout {

    private Div div;
    private TextField filtro;
    private Grid<Contact> grid;
    private ContactForm contactForm;
    private CrmService crmService;

    public Formulario(CrmService crmService) {
        this.crmService = crmService;
        //Creamos div
        div();
        //Creamos grid
        grid();
    }

    public void div() {
        filtro = new TextField("Filtro");
        filtro.setPlaceholder("Texto a buscar");
        //Boton 'X' para vaciar cuadro de texto
        filtro.setClearButtonVisible(true);
        //Evita lanzar 'valueChangeListener' mienstras se esta escribiendo
        filtro.setValueChangeMode(ValueChangeMode.LAZY);
        filtro.addValueChangeListener(e -> updateList());

        Button btn = new Button("Añadir");
        btn.addClickListener(click-> Notification.show("Hola" + filtro.getValue()));

        div = new Div();
        div.addClassName("div-inicial");
        //Añadimos componentes al div
        div.add(filtro, btn);
    }

    public void grid(){
        grid = new Grid<>(Contact.class);
        grid.addClassName("grid");
        //Ajustamos al ancho de la pagina
        grid.setWidthFull();
        grid.setColumns("firstName", "lastName");
        grid.addColumn("email").setHeader("Correo");
        //Foreign keys
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Compañia").setSortable(true);
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Estado");
        //Ajuste de las columnas automatico
        grid.getColumns().forEach(c -> c.setAutoWidth(true));

        //Crea un evento en el grid que permite seleccionar un registro. Tambien puede ser multiple
        grid.asSingleSelect()
    }

    public Component form() {
        contactForm = new ContactForm(crmService.findAllCompanies(), crmService.findAllStatuses());

        HorizontalLayout content = new HorizontalLayout(grid, contactForm);
        //Damos 2/3 del espacio para el grid y 1 para el formulario de 'nuevo'
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, contactForm);
        content.addClassName("contenedor-grid-form");
        content.setSizeFull();

        closeEditor();

        return new VerticalLayout(div, content);
    }

    public void updateList(){
        grid.setItems(crmService.findAllContacts(filtro.getValue()));
    }

    public void closeEditor(){
        contactForm.setContact(null);
        contactForm.setVisible(false);
        removeClassName("edicion");
    }
}
