package com.example.demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route("main")
@PageTitle("Main")
public class MainView extends VerticalLayout {

    public MainView() {
        H1 title = new H1("Welcome to the Main View!");
        Button logoutButton = new Button("Logout");
        logoutButton.addClickListener(event -> {
            // In a real application, you would invalidate the session here
            // For this example, we simply navigate back to the register view
            logoutButton.getUI().ifPresent(ui -> ui.navigate("register"));
        });

        setAlignItems(Alignment.CENTER);
        add(title, logoutButton);
    }
}