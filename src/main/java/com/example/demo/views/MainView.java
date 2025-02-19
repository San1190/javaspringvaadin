package com.example.demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.notification.Notification;

@Route("main") // Maps this view to the root URL ("/")
@PageTitle("My First Vaadin App") // Sets the browser tab title
public class MainView extends VerticalLayout {

    public MainView() {
        // Create components
        TextField textField = new TextField("Your Name"); // Label added
        Button button = new Button("Say Hello");

        // Add a click listener to the button
        button.addClickListener(event -> {
            String name = textField.getValue();  // Get the name from the text field
            if (name.isEmpty()) {
                Notification.show("Please enter your name!"); // Show a notification if the name is empty
            } else {
                Notification.show("Hello, " + name + "!"); // Show a personalized greeting
            }

        });

        // Add components to the layout
        add(textField, button);

        // Optional: Center the content
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }
}