package com.example.demo.views;

import com.example.demo.model.Articulo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.Key; //Import key

import java.util.ArrayList;
import java.util.List;

@Route("prueba")
@PageTitle("Mi Prueba")
public class PruebaMiaView extends VerticalLayout{

    private Button botonInicioSesiButton;
    private Button botonRegistroButton;
    private TextField usuarioTextField;
    private TextField contrasenaTextField;


    public PruebaMiaView() {
        H1 title = new H1("Inicio de Sesión");
        usuarioTextField = new TextField("Usuario");
        contrasenaTextField = new TextField("Contraseña");
        botonInicioSesiButton = new Button("Iniciar Sesión");
        botonRegistroButton = new Button("Registrarse");

        //Estilos y alineación básica
        setAlignItems(Alignment.CENTER); // Alinea el contenido verticalmente al centro
        setJustifyContentMode(JustifyContentMode.CENTER); // Alinea el contenido horizontalmente al centro
        setBoxSizing(null); // Elimina el relleno predeterminado
        //colores de la pagina 
        getStyle().set("background-color", "#f0f0f0");
        getStyle().set("color", "#333");
        //colores de los botones
        botonInicioSesiButton.getStyle().set("background-color", "#4CAF50");
        botonInicioSesiButton.getStyle().set("color", "white");
        botonInicioSesiButton.getStyle().set("border", "none");
        botonInicioSesiButton.getStyle().set("padding", "10px 24px");
        botonInicioSesiButton.getStyle().set("cursor", "pointer");
        botonInicioSesiButton.getStyle().set("border-radius", "5px");
        botonInicioSesiButton.getStyle().set("text-align", "center");
        botonInicioSesiButton.getStyle().set("text-decoration", "none");
        botonInicioSesiButton.getStyle().set("display", "inline-block");
        botonInicioSesiButton.getStyle().set("font-size", "16px");
        






        add(title, usuarioTextField, contrasenaTextField, botonInicioSesiButton, botonRegistroButton);

    }
    
}
