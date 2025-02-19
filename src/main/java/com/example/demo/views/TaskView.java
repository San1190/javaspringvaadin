package com.example.demo.views;

import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div; // Import Div for layout
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.dom.Style;  // Import Style
import com.vaadin.flow.component.dependency.CssImport; // Import CssImport
import com.vaadin.flow.router.RouterLink;

import javax.swing.ButtonModel;

import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@PageTitle("Task Manager")
@CssImport("./styles/task-view.css") // Import custom CSS
public class TaskView extends VerticalLayout {

    private final TaskService taskService;
    private final Grid<Task> taskGrid = new Grid<>(Task.class);
    private final TextField taskDescription = new TextField("Task Description");
    private final Button addButton = new Button("Add Task");

    @Autowired  // Injects TaskService
    public TaskView(TaskService taskService) {
        this.taskService = taskService;

        // Apply general styles to the layout
        getStyle().set("padding", "var(--lumo-space-m)");
        getStyle().set("max-width", "800px"); // Limit width for readability
        getStyle().set("margin", "0 auto");   // Center the layout

        // Task Description Field Styling
        taskDescription.setWidth("100%"); // Make it fill the available space
        taskDescription.getStyle().set("margin-bottom", "var(--lumo-space-s)"); // Add a little space below

        // Add Button Styling (Using Vaadin's Lumo theme variables)
        addButton.addThemeName("primary"); // Use the primary color from Lumo
        addButton.getStyle().set("margin-left", "var(--lumo-space-s)");

        // Configure the grid
        taskGrid.setColumns("id", "description");  // Define the columns (don't show "completed" directly)
        taskGrid.addComponentColumn(task -> { // Add column for checkbox
                    Checkbox completed = new Checkbox();
                    completed.setValue(task.isCompleted());
                    completed.addValueChangeListener(event -> {
                        taskService.markCompleted(task.getId());
                        refreshTasks();
                    });
                    return completed;
                }
        ).setHeader("Completed").setWidth("100px");  // Make the checkbox column narrower

        taskGrid.setAllRowsVisible(true); // Display all tasks in the grid

        // Configure the add task form
        addButton.addClickListener(event -> {
            if (!taskDescription.getValue().isEmpty()) {
                taskService.addTask(taskDescription.getValue());
                taskDescription.clear();
                refreshTasks();
            }
        });

        // Navigation Button Styling
        Button goToMainButton = new Button("Go to Main View");
        goToMainButton.addClickListener(event ->
                goToMainButton.getUI().ifPresent(ui ->
                        ui.navigate("main") // Navigate to the "main" route
                )
        );
        goToMainButton.addThemeName("secondary"); // A less prominent theme
        goToMainButton.getStyle().set("margin-top", "var(--lumo-space-2)");

        Button goToTikTakToeButton = new Button("Go to Tik Tak Toe");
        goToTikTakToeButton.addClickListener(event ->
                goToTikTakToeButton.getUI().ifPresent(ui ->
                        ui.navigate("tresenraya") // Navigate to the "tresenraya" route
                )
        );
        goToTikTakToeButton.addThemeName("secondary"); // A less prominent theme
        goToTikTakToeButton.getStyle().set("margin-top", "var(--lumo-space-l)");
        // Layout using Div for a bit more structure
        Div content = new Div();
        content.setWidthFull(); // Make the content fill the available space
        content.getStyle().set("padding", "var(--lumo-space-m)");
        content.getStyle().set("border", "1px solid var(--lumo-shade-20pct)");
        content.getStyle().set("border-radius", "var(--lumo-border-radius)");
        content.getStyle().set("background-color", "var(--lumo-base-color)"); // A light background

        HorizontalLayout formLayout = new HorizontalLayout(taskDescription, addButton);
        formLayout.setWidthFull(); // Make the form fill the available width
        formLayout.setFlexGrow(1, taskDescription); // Let the description field grow to fill space
        formLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE); // Align components

        content.add(formLayout, taskGrid, goToMainButton);

        add(content); // Add content to the main vertical layout

        // Initial data load
        refreshTasks();

        setSizeFull(); // Make the layout take up the full screen
    }

    private void refreshTasks() {
        taskGrid.setItems(taskService.getAllTasks());
    }
}