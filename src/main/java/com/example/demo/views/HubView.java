package com.example.demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route("") // This is now the root route
@PageTitle("Application Hub")
public class HubView extends VerticalLayout {

    public HubView() {
        // App Title
        H1 title = new H1("Welcome to the Application Hub");
        title.getStyle().set("text-align", "center");

        // Buttons for Navigation
        Button goToAhorcadoButton = new Button("Go to Hangman (Ahorcado)", event ->
                event.getSource().getUI().ifPresent(ui ->
                        ui.navigate("ahorcado")
                )
        );

        Button goToTresEnRayaButton = new Button("Go to Tic-Tac-Toe (Tres en Raya)", event ->
                event.getSource().getUI().ifPresent(ui ->
                        ui.navigate("tresenraya")
                )
        );

        Button goToTaskViewButton = new Button("Go to Task Manager", event ->
                event.getSource().getUI().ifPresent(ui ->
                        ui.navigate("task") // Important: Route must match the @Route in TaskView
                )
        );

        Button goToNameButton = new Button("Go to Name", event ->
                event.getSource().getUI().ifPresent(ui ->
                        ui.navigate("name") // Important: Route must match the @Route in NameView
                )
        );

        // Styling (Optional - you can use CSS if you prefer)
        goToAhorcadoButton.addThemeName("primary");
        goToTresEnRayaButton.addThemeName("primary");
        goToTaskViewButton.addThemeName("primary");
        goToNameButton.addThemeName("primary");

        // Layout
        setAlignItems(Alignment.CENTER);
        add(title, goToAhorcadoButton, goToTresEnRayaButton, goToTaskViewButton, goToNameButton);
    }
}