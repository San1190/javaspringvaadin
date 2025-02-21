package com.example.demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.dependency.StyleSheet; // Import para CSS externo

@Route("prueba")
@PageTitle("Inicio de Sesión")
@StyleSheet("styles.css")
public class PruebaMiaView extends VerticalLayout {

    private TextField usuarioTextField;
    private TextField contrasenaTextField;
    private Button botonInicioSesiButton;
    private Button botonRegistroButton;

    public PruebaMiaView() {
        // Título principal
        H1 title = new H1("Bienvenido");
        title.addClassName("main-title"); // Clase para estilos CSS

        // Campos de texto con iconos (requiere Vaadin Icon component)
        usuarioTextField = new TextField("Usuario");
        usuarioTextField.setPlaceholder("Introduce tu nombre de usuario");
       // usuarioTextField.setPrefixComponent(VaadinIcon.USER.create()); // Icono de usuario
        usuarioTextField.addClassName("input-field");

        contrasenaTextField = new TextField("Contraseña");
        contrasenaTextField.setPlaceholder("Introduce tu contraseña");
        //contrasenaTextField.setPasswordField(true); // Oculta la contraseña
        //contrasenaTextField.setPrefixComponent(VaadinIcon.LOCK.create()); // Icono de candado
        contrasenaTextField.addClassName("input-field");

        // Botones con estilos modernos y animaciones
        botonInicioSesiButton = new Button("Iniciar Sesión");
        botonInicioSesiButton.addClassName("primary-button");
        botonInicioSesiButton.addClickShortcut(Key.ENTER); // Permite iniciar sesión con la tecla Enter
        botonInicioSesiButton.addClickListener(event -> {
            Notification.show("¡Inicio de sesión exitoso! (Simulado)");
        });

        botonRegistroButton = new Button("Registrarse");
        botonRegistroButton.addClassName("secondary-button");

        // Estructura y diseño principal
        VerticalLayout formLayout = new VerticalLayout(title, usuarioTextField, contrasenaTextField, botonInicioSesiButton, botonRegistroButton);
        formLayout.addClassName("form-container"); // Contenedor para estilos CSS
        formLayout.setAlignItems(Alignment.CENTER);

        // Añade el formulario al layout principal
        add(formLayout);
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull(); // Ocupa toda la pantalla
        addClassName("main-view"); // Clase para estilos CSS generales
    }
}