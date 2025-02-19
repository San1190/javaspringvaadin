package com.example.demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route("register")
@PageTitle("Register")
public class RegisterView extends VerticalLayout {

    private TextField usernameField;
    private PasswordField passwordField;
    private Button registerButton;

    public RegisterView() {
        // Create UI components
        H1 title = new H1("Register");
        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");
        registerButton = new Button("Register");

        // Set basic styling
        setAlignItems(Alignment.CENTER); // Center content

        // Add click listener to register button
        registerButton.addClickListener(event -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();

            // Basic validation (you'd want more robust validation in a real app)
            if (username.isEmpty() || password.isEmpty()) {
                Notification.show("Please enter a username and password.");
                return;
            }

            // Simulate registration (in-memory) - In a real app, you would save to a database
            //For simiplicity, we're going to just show a notification

            Notification.show("Registration Successful for " + username + "!");

            // Navigate to the main view after successful registration
            registerButton.getUI().ifPresent(ui -> ui.navigate("main"));
        });

        // Add components to the layout
        add(title, usernameField, passwordField, registerButton);
    }
}