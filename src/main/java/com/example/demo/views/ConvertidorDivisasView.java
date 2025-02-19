package com.example.demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Route("divisa")
@PageTitle("Convertidor de Divisas")
public class ConvertidorDivisasView extends VerticalLayout {

    private ComboBox<String> divisaOrigenComboBox = new ComboBox<>("Divisa Origen");
    private ComboBox<String> divisaDestinoComboBox = new ComboBox<>("Divisa Destino");
    private TextField cantidadTextField = new TextField("Cantidad");
    private Label resultadoLabel = new Label("Resultado: ");
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private final String API_KEY = "fca_live_your_api_key_here";
    private final String API_URL = "https://api.freecurrencyapi.com/v1/latest?apikey=" + API_KEY + "¤cies=";

    private final String [] divisas = {"EUR", "USD", "GBP", "JPY", "CAD"};

    public ConvertidorDivisasView() {
        divisaOrigenComboBox.setItems(divisas);
        divisaDestinoComboBox.setItems(divisas);

        Button convertirButton = new Button("Convertir", event -> convertirDivisa());

        setAlignItems(Alignment.CENTER);

        add(divisaOrigenComboBox, divisaDestinoComboBox, cantidadTextField, convertirButton, resultadoLabel);
    }

    private void convertirDivisa() {
        String divisaOrigen = divisaOrigenComboBox.getValue();
        String divisaDestino = divisaDestinoComboBox.getValue();

        if(divisaOrigen == null || divisaDestino == null || cantidadTextField.getValue().isEmpty()){
            resultadoLabel.setText("Por favor, selecciona las divisas y la cantidad");
            return;
        }

        try{
            double cantidad = Double.parseDouble(cantidadTextField.getValue());

            WebClient client = WebClient.create();

            Mono<Map> response = client.get()
                    .uri(API_URL + divisaDestino + "&base_currency=" + divisaOrigen)
                    .retrieve()
                    .bodyToMono(Map.class);

            response.subscribe(data -> {
                    HashMap results = (HashMap) data.get("data");
                    Double rate = (Double) results.get(divisaDestino);
                    double resultado = cantidad * rate;
                    resultadoLabel.setText("Resultado: " + df.format(resultado) + " " + divisaDestino);
                },
                error -> {
                    resultadoLabel.setText("Error al obtener la tasa de cambio");
                }
            );

        } catch (NumberFormatException e) {
            resultadoLabel.setText("Por favor, ingresa una cantidad válida");
        }
    }
}