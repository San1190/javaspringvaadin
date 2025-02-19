package com.example.demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.component.notification.Notification;

@Route("calculadora")
@PageTitle("Calculadora")
public class CalculadoraView extends VerticalLayout {

    private TextField numero1Field = new TextField("Número 1");
    private TextField numero2Field = new TextField("Número 2");
    private Label resultadoLabel = new Label("Resultado: ");

    public CalculadoraView() {
        //Botones para las operaciones
        Button sumarButton = new Button("Sumar", event -> calcular('+'));
        Button restarButton = new Button("Restar", event -> calcular('-'));
        Button multiplicarButton = new Button("Multiplicar", event -> calcular('*'));
        Button dividirButton = new Button("Dividir", event -> calcular('/'));

        //Estilos
        setAlignItems(Alignment.CENTER);

        //Agregar los elementos

        add(numero1Field, numero2Field,
            sumarButton, restarButton, multiplicarButton, dividirButton,
            resultadoLabel);
    }

    private void calcular(char operacion) {
        try{
            double num1 = Double.parseDouble(numero1Field.getValue());
            double num2 = Double.parseDouble(numero2Field.getValue());
            double resultado = 0;

            switch (operacion) {
                case '+':
                    resultado = num1 + num2;
                    break;
                case '-':
                    resultado = num1 - num2;
                    break;
                case '*':
                    resultado = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        Notification.show("¡No se puede dividir por cero!");
                        return;
                    }
                    resultado = num1 / num2;
                    break;
            }

            resultadoLabel.setText("Resultado: " + resultado);
        } catch (NumberFormatException e) {
            Notification.show("Por favor, ingresa números válidos.");
        }

    }
}