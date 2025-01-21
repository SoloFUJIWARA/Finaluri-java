package com.example.java_nikoloz_sologashvili;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private PieChart pieChart;
    private ProductDAO productDAO;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        productDAO = new ProductDAO();
        pieChart = new PieChart();

        TextField nameField = new TextField();
        nameField.setPromptText("Enter product name");
        TextField priceField = new TextField();
        priceField.setPromptText("Enter product price");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter product quantity");
        TextField updateIdField = new TextField();
        updateIdField.setPromptText("Enter product ID to update");
        TextField readIdField = new TextField();
        readIdField.setPromptText("Enter product ID to read/delete");

        Button insertButton = new Button("Insert Product");
        Button deleteButton = new Button("Delete Product");
        Button updateButton = new Button("Update Product");
        Button readButton = new Button("Read Product");

        Label feedbackLabel = new Label();
        Label readResultLabel = new Label();

        insertButton.setOnAction(e -> {
            String name = nameField.getText();
            String priceText = priceField.getText();
            String quantityText = quantityField.getText();
            if (!name.isEmpty() && !priceText.isEmpty() && !quantityText.isEmpty()) {
                try {
                    int price = Integer.parseInt(priceText);
                    int quantity = Integer.parseInt(quantityText);
                    productDAO.addProduct(new Product(0, name, price, quantity));
                    feedbackLabel.setText("Product added successfully!");
                    nameField.clear();
                    priceField.clear();
                    quantityField.clear();
                    updatePieChart();
                } catch (NumberFormatException ex) {
                    feedbackLabel.setText("Please enter valid price and quantity!");
                }
            } else {
                feedbackLabel.setText("All fields are required!");
            }
        });

        deleteButton.setOnAction(e -> {
            String idText = readIdField.getText();
            if (!idText.isEmpty()) {
                try {
                    int id = Integer.parseInt(idText);
                    productDAO.deleteProduct(id);
                    feedbackLabel.setText("Product deleted successfully!");
                    readIdField.clear();
                    updatePieChart();
                } catch (NumberFormatException ex) {
                    feedbackLabel.setText("Please enter a valid ID!");
                }
            } else {
                feedbackLabel.setText("ID field is required!");
            }
        });

        updateButton.setOnAction(e -> {
            String idText = updateIdField.getText();
            String name = nameField.getText();
            String priceText = priceField.getText();
            String quantityText = quantityField.getText();
            if (!idText.isEmpty() && !name.isEmpty() && !priceText.isEmpty() && !quantityText.isEmpty()) {
                try {
                    int id = Integer.parseInt(idText);
                    int price = Integer.parseInt(priceText);
                    int quantity = Integer.parseInt(quantityText);
                    productDAO.updateProduct(new Product(id, name, price, quantity));
                    feedbackLabel.setText("Product updated successfully!");
                    updateIdField.clear();
                    nameField.clear();
                    priceField.clear();
                    quantityField.clear();
                    updatePieChart();
                } catch (NumberFormatException ex) {
                    feedbackLabel.setText("Please enter valid values!");
                }
            } else {
                feedbackLabel.setText("All fields are required for update!");
            }
        });

        readButton.setOnAction(e -> {
            String idText = readIdField.getText();
            if (!idText.isEmpty()) {
                try {
                    int id = Integer.parseInt(idText);
                    Product product = productDAO.getProduct(id);
                    if (product != null) {
                        readResultLabel.setText(
                                "ID: " + product.getId() +
                                        ", Name: " + product.getName() +
                                        ", Price: " + product.getPrice() +
                                        ", Quantity: " + product.getQuantity()
                        );
                    } else {
                        readResultLabel.setText("No product found with ID: " + id);
                    }
                } catch (NumberFormatException ex) {
                    readResultLabel.setText("Please enter a valid ID!");
                }
            } else {
                readResultLabel.setText("ID field is required for reading!");
            }
        });

        VBox layout = new VBox(
                15,
                new Label("Insert New Product:"),
                nameField,
                priceField,
                quantityField,
                insertButton,
                new Label("Update Product:"),
                updateIdField,
                updateButton,
                new Label("Delete or Read Product:"),
                readIdField,
                deleteButton,
                readButton,
                readResultLabel,
                feedbackLabel,
                pieChart
        );
        layout.setPadding(new Insets(20));

        nameField.setPrefWidth(300);
        priceField.setPrefWidth(300);
        quantityField.setPrefWidth(300);
        updateIdField.setPrefWidth(300);
        readIdField.setPrefWidth(300);

        Scene scene = new Scene(layout, 500, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Product Management with CRUD Operations");
        primaryStage.show();

        updatePieChart();
    }

    private void updatePieChart() {
        pieChart.getData().clear();
        productDAO.getAllProducts().forEach(product -> {
            double totalValue = product.getPrice() * product.getQuantity();
            String label = product.getName() + " (ID: " + product.getId() + ") - " + totalValue+ " $ ";
            pieChart.getData().add(new PieChart.Data(label, totalValue));
        });
    }

}
