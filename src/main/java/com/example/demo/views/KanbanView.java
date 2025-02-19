package com.example.demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.Component; // Import component

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Route("kanban")
@PageTitle("Kanban Board")

public class KanbanView extends VerticalLayout {

    private static final String TODO = "To Do";
    private static final String IN_PROGRESS = "In Progress";
    private static final String DONE = "Done";

    private VerticalLayout todoColumn, inProgressColumn, doneColumn;
    private List<Task> tasks = new ArrayList<>();

    private Div draggedTaskCard;  // To track the currently dragged card
    private VerticalLayout sourceColumn; // Track the source column

    public KanbanView() {
        // Header
        H3 title = new H3("Kanban Board");
        add(title);

        // Create columns
        todoColumn = createColumn(TODO);
        inProgressColumn = createColumn(IN_PROGRESS);
        doneColumn = createColumn(DONE);

        //Sample task for test
        addTask(TODO, "Sample task To do column");

        //Horizontal layout for the column view
        HorizontalLayout board = new HorizontalLayout(todoColumn, inProgressColumn, doneColumn);
        board.setSizeFull();
        add(board);

        //Add initial styles
        setHeight("100%");
        setSizeFull();
        getStyle().set("overflow", "auto");
    }

    //Creates the new Task
    private void addTask(String column, String description) {
        Task task = new Task(UUID.randomUUID().toString(), description, column);
        tasks.add(task);
        Div taskCard = createTaskCard(task);

        switch (column) {
            case TODO:
                todoColumn.add(taskCard);
                break;
            case IN_PROGRESS:
                inProgressColumn.add(taskCard);
                break;
            case DONE:
                doneColumn.add(taskCard);
                break;
        }
    }

    //Creates column of Kanban
    private VerticalLayout createColumn(String columnName) {
        VerticalLayout column = new VerticalLayout();
        column.setWidth("300px");
        column.getStyle().set("flex-shrink", "0"); //Prevent column to reduce
        column.getStyle().set("padding", "10px"); // Add spacing
        column.getStyle().set("background-color", "var(--lumo-contrast-5pct)"); //Set different color than the page
        column.setClassName("kanban-column"); // CSS

        H3 columnTitle = new H3(columnName);

        TextField taskInput = new TextField();
        taskInput.setPlaceholder("Add a new task");
        taskInput.setWidthFull();

        Button addButton = new Button(new Icon(VaadinIcon.PLUS), e -> {
            if (!taskInput.getValue().isEmpty()) {
                addTask(columnName, taskInput.getValue());
                taskInput.clear();
            } else {
                Notification.show("Task description cannot be empty");
            }
        });
        addButton.setClassName("add-button");

        HorizontalLayout addLayout = new HorizontalLayout(taskInput, addButton);
        addLayout.setWidthFull();

        column.add(columnTitle, addLayout);

        return column;
    }

    //Drag and Drop card item
    private Div createTaskCard(Task task) {
        Div card = new Div();
        card.setText(task.getDescription());
        card.setClassName("task-card");
        card.setId(task.getId()); // Unique ID

        // Event Listeners - Drag-and-Drop Simulation
        card.addMouseDownListener(e -> {
            if (draggedTaskCard == null) {
                draggedTaskCard = card; // Set dragged

                sourceColumn = (VerticalLayout) card.getParent().orElse(null); // Source Column

                if (sourceColumn != null) {
                    sourceColumn.remove(card); // Remove from the source column
                }

                card.getStyle().set("position", "absolute");  //Fix the element
                card.getStyle().set("cursor", "grabbing"); //Change cursor while dragging

                //Bring the dragged card to front
                card.getElement().executeJs("this.style.zIndex = '1000';");

                Notification.show("Task: "+task.getDescription()+ " is being dragged.");

                card.addMouseMoveListener(moveEvent -> {
                   //Set the location while the card is being dragged
                    card.getStyle().set("left", moveEvent.getClientX() + "px");
                    card.getStyle().set("top", moveEvent.getClientY() + "px");
                });

                });

        card.addMouseUpListener(e -> {
            if (draggedTaskCard != null) {
                    // Find the column under the mouse
                    Component target = e.getSource(); // the value is null

                    VerticalLayout targetColumn = findTargetColumn(e.getClientX(), e.getClientY());

                    if (targetColumn != null) {
                        targetColumn.add(card); // Add to new target column

                        Task movedTask = findTaskById(task.getId()); // Find the task that the user moved
                        movedTask.setColumn(targetColumn.getId()); //set the new column for the task
                        Notification.show("Task: " + task.getDescription() + " moved to column: "+ targetColumn.getId());

                    } else {
                         // If no valid column is found, return to the source column
                        if (sourceColumn != null) {
                            sourceColumn.add(card);
                            Notification.show("Task: " + task.getDescription() + " has returned to original column");
                        }
                    }

                    draggedTaskCard = null;
                    sourceColumn = null;

                    card.getStyle().clear(); //Clear after is dropped

                }
                e.getSource().getElement().executeJs("this.style.zIndex = 'auto';");
                System.out.println("Release Mouse");
        });

        return card;
    }

    //Finds the correct ID, and the reassign it after is moved.
    private Task findTaskById(String taskId) {
        for (Task task : tasks) {
            if (task.getId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    // Find the Target
    private VerticalLayout findTargetColumn(int x, int y) {
        List<VerticalLayout> columns = List.of(todoColumn, inProgressColumn, doneColumn);
        for (VerticalLayout column : columns) {
            com.vaadin.flow.component.ComponentUtil.getData(column, "element");

            Style columnStyle = column.getElement().getStyle();

            //Get location of each item on the list
            double columnLeft = column.getElement().getOffsetLeft();
            double columnTop = column.getElement().getOffsetTop();
            double columnWidth = column.getElement().getOffsetWidth();
            double columnHeight = column.getElement().getOffsetHeight();

            if (x >= columnLeft && x <= columnLeft + columnWidth &&
                y >= columnTop && y <= columnTop + columnHeight) {
                return column;
            }
        }
        return null;
    }

    public static class Task {
        private String id;
        private String description;
        private String column; // To Do, In Progress, Done

        public Task() {}

        public Task(String id, String description, String column) {
            this.id = id;
            this.description = description;
            this.column = column;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }
    }
}