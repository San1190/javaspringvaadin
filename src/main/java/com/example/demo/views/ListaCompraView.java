package com.example.demo.views;

import com.example.demo.model.Articulo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
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

@Route("lista-compra")
@PageTitle("Lista de Compras")
@StyleSheet("comprastyle.css")
public class ListaCompraView extends VerticalLayout {

    private List<Articulo> listaArticulos = new ArrayList<>();
    private VerticalLayout listaArticulosLayout = new VerticalLayout();
    private TextField articuloInput = new TextField("Nuevo Artículo"); //Made it a class member

    public ListaCompraView() {
        // Crear elementos de UI

        Button agregarButton = new Button("Agregar");

        // Estilo
        setAlignItems(Alignment.CENTER);
        listaArticulosLayout.setWidth("400px");

        // Agrega evento al button
        agregarButton.addClickListener(event -> agregarArticulo());

        // Agrega evento al TextField (Enter key)
        articuloInput.addKeyDownListener(Key.ENTER, event -> agregarArticulo());

        //Organiza el layout horizontalmente
        HorizontalLayout anadirLayout = new HorizontalLayout(articuloInput, agregarButton);
        anadirLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        add(anadirLayout, listaArticulosLayout);

        // Add enter click listener to the layout
        articuloInput.addKeyDownListener(Key.ENTER, event -> agregarArticulo());
    }

    //Extract method
    private void agregarArticulo() {
        String nombreArticulo = articuloInput.getValue();
        if (!nombreArticulo.isEmpty()) {
            Articulo nuevoArticulo = new Articulo(nombreArticulo);
            listaArticulos.add(nuevoArticulo);
            actualizarListaArticulos();
            articuloInput.clear();

        }
    }

    private void actualizarListaArticulos() {
        listaArticulosLayout.removeAll(); // Limpia para actualizar
        for (Articulo articulo : listaArticulos) {
            HorizontalLayout articuloLayout = new HorizontalLayout();
            Checkbox compradoCheckbox = new Checkbox();
            compradoCheckbox.setValue(articulo.isComprado());
            compradoCheckbox.addValueChangeListener(event -> {
                articulo.setComprado(event.getValue());
            });

            Button eliminarButton = new Button("Eliminar");
            eliminarButton.addClickListener(event -> {
                listaArticulos.remove(articulo);
                actualizarListaArticulos();
            });
            articuloLayout.add(compradoCheckbox, new Div(){{setText(articulo.getNombre());}}, eliminarButton);
            articuloLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

            listaArticulosLayout.add(articuloLayout);
        }
    }
}