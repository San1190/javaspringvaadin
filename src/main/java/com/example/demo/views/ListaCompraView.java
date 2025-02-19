package com.example.demo.views;

import com.example.demo.model.Articulo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.component.notification.Notification;

import java.util.ArrayList;
import java.util.List;

@Route("lista-compra")
@PageTitle("Lista de Compras")
public class ListaCompraView extends VerticalLayout {

    private List<Articulo> listaArticulos = new ArrayList<>();
    private VerticalLayout listaArticulosLayout = new VerticalLayout();

    public ListaCompraView() {
        // Crear elementos de UI
        TextField articuloInput = new TextField("Nuevo Artículo");
        Button agregarButton = new Button("Agregar");

        // Estilo
        setAlignItems(Alignment.CENTER);
        listaArticulosLayout.setWidth("400px");

        // Agrega evento
        agregarButton.addClickListener(event -> {
            String nombreArticulo = articuloInput.getValue();
            if (!nombreArticulo.isEmpty()) {
                Articulo nuevoArticulo = new Articulo(nombreArticulo);
                listaArticulos.add(nuevoArticulo);
                actualizarListaArticulos();
                articuloInput.clear();

            }
        });

        //Organiza el layout horizontalmente
        HorizontalLayout anadirLayout = new HorizontalLayout(articuloInput, agregarButton);
        anadirLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        add(anadirLayout, listaArticulosLayout);

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