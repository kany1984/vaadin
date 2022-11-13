package com.example.application.views.grid;

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

public class GridForm extends VerticalLayout {

    private Div div;
    private TextField filtro;
    private Grid<Contact> grid;
    private ContactForm contactForm;
    private CrmService crmService;

    public GridForm(CrmService crmService) {
        this.crmService = crmService;
        configureForm();

        //Creamos div
        div();
        //Creamos grid
        grid();
        closeEditor();
    }

    private void configureForm() {
        //Paramos en el constructor la lista de compañias y estados
        contactForm = new ContactForm(crmService.findAllCompanies(), crmService.findAllStatuses());

        //Definimos los evemtos de los botones
        contactForm.addListener(ContactForm.SaveEvent.class, this::saveContact);
        contactForm.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        contactForm.addListener(ContactForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveContact(ContactForm.SaveEvent event){
        crmService.saveContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(ContactForm.DeleteEvent event){
        crmService.deleteContact(event.getContact());
        updateList();
        closeEditor();
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
        btn.addClickListener(e -> addContact());

        div = new Div();
        div.addClassName("div");
        //Añadimos componentes al div
        div.add(filtro, btn);
    }

    public void grid(){
        grid = new Grid<>(Contact.class);
        grid.addClassName("grid");
        //Ajustamos al ancho de la pagina
        grid.setWidthFull();
        //Ajsutao al 100% de la página
        //grid.setSizeFull();
        grid.setColumns("firstName", "lastName");
        grid.addColumn("email").setHeader("Correo");
        //Foreign keys
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Compañia").setSortable(true);
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Estado");
        //Ajuste de las columnas automatico
        grid.getColumns().forEach(c -> c.setAutoWidth(true));

        //Crea un evento en el grid que permite seleccionar un registro. Tambien puede ser multiple
        grid.asSingleSelect().addValueChangeListener(e -> editContact(e.getValue()));

        updateList();
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
        Notification.show("Nuevo contacto");
    }

    public void editContact(Contact contact){
        if (contact == null){
            closeEditor();
        }else{
            contactForm.setContact(contact);
            contactForm.setVisible(true);
            editing(true);
        }
    }

    public void editing(boolean edit){
        if (edit){
            grid.addClassName("editing");
            div.addClassName("editing");
        }else{
            grid.removeClassName("editing");
            div.removeClassName("editing");
        }
    }

    public Component form() {
        HorizontalLayout content = new HorizontalLayout(grid, contactForm);
        //Damos 2/3 del espacio para el grid y 1 para el formulario de 'nuevo'
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, contactForm);
        content.addClassName("contenedor-grid");
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
        editing(false);
    }
}
