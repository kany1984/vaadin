package com.example.application.views.grid;

import com.example.application.entity.Company;
import com.example.application.entity.Contact;
import com.example.application.entity.Status;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

//FormLayout -> Diseño adaptable que muestra los campos de formulario en 1 o 2 columnas según el ancho
public class ContactForm extends FormLayout {

    private Contact contact;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Correo");

    ComboBox<Status> status = new ComboBox<>("Estado");
    ComboBox<Company> company = new ComboBox<>("Compañia");

    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");
    Button cancel = new Button("Cancelar");

    /*  Es necesario crear un 'Binder' para poder poder crear y validar los datos del formulario
        Establece una relacion entre las entidades y los componentes del formulario
        Documentacion -> https://vaadin.com/docs/latest/binding-data/components-binder-validation
        BeanValidationBinder permite validar las anotaciones de las entidades */
    Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    //En el constructor recibimos las listas de los combos para rellenar
    public ContactForm(List<Company> companies, List<Status> statuses) {
        addClassName("formulario-contacto");
        setWidth("25em");

        company.setItems(companies);
        //Mosteamos el nombre de la compañia
        company.setItemLabelGenerator(Company::getName);
        status.setItems(statuses);
        status.setItemLabelGenerator(Status::getName);

        add(firstName, lastName, email, company, status, createButtonsLayout());

        //Llamada al 'binder' desde el formulario
        binder.bindInstanceFields(this);
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        //Permite al 'binder' operar sobre el contacto actual (seleccionado). Copia los valores a un modelo interno
        binder.readBean(contact);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, contact)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        //Habilita el boton de guardar solo si el binder es correcto. Si todos los validadores se cumplen
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            //Escribe/actualiza los datos del contacto en el formulario original
            binder.writeBean(contact);
            fireEvent(new SaveEvent(this, contact));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    //Sistema de gestion de eventos
    public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
        private Contact contact;

        protected ContactFormEvent(ContactForm source, Contact contact) {
            super(source, false);
            this.contact = contact;
        }

        public Contact getContact() {
            return contact;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ContactForm source) {
            super(source, null);
        }
    }

    //Registrar tipos de eventos personalizados de vaadin.
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}