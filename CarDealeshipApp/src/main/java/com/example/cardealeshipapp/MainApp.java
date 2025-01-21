package com.example.cardealeshipapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private PieChart pieChart;
    private CarDAO carDAO;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        carDAO = new CarDAO();
        pieChart = new PieChart();

        TextField brandField = new TextField();
        brandField.setPromptText("Enter car brand");
        TextField modelField = new TextField();
        modelField.setPromptText("Enter car model");
        TextField typeField = new TextField();
        typeField.setPromptText("Enter car type");
        TextField engineField = new TextField();
        engineField.setPromptText("Enter engine type");
        TextField colorField = new TextField();
        colorField.setPromptText("Enter car color");
        TextField priceField = new TextField();
        priceField.setPromptText("Enter car price");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter car quantity");

        TextField updateIdField = new TextField();
        updateIdField.setPromptText("Enter car ID to update");
        TextField readIdField = new TextField();
        readIdField.setPromptText("Enter car ID to read/delete");

        Button insertButton = new Button("Insert Car");
        Button deleteButton = new Button("Delete Car");
        Button updateButton = new Button("Update Car");
        Button readButton = new Button("Read Car");

        Label feedbackLabel = new Label();
        Label readResultLabel = new Label();

        insertButton.setOnAction(e -> {
            String brand = brandField.getText();
            String model = modelField.getText();
            String type = typeField.getText();
            String engine = engineField.getText();
            String color = colorField.getText();
            String priceText = priceField.getText();
            String quantityText = quantityField.getText();

            if (!brand.isEmpty() && !model.isEmpty() && !type.isEmpty() && !engine.isEmpty() && !color.isEmpty() &&
                    !priceText.isEmpty() && !quantityText.isEmpty()) {
                try {
                    int price = Integer.parseInt(priceText);
                    int quantity = Integer.parseInt(quantityText);
                    carDAO.addCar(new Car(0, brand, model, type, engine, color, price, quantity));
                    feedbackLabel.setText("Car added successfully!");
                    clearFields(brandField, modelField, typeField, engineField, colorField, priceField, quantityField);
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
                    carDAO.deleteCar(id);
                    feedbackLabel.setText("Car deleted successfully!");
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
            String brand = brandField.getText();
            String model = modelField.getText();
            String type = typeField.getText();
            String engine = engineField.getText();
            String color = colorField.getText();
            String priceText = priceField.getText();
            String quantityText = quantityField.getText();

            if (!idText.isEmpty() && !brand.isEmpty() && !model.isEmpty() && !type.isEmpty() && !engine.isEmpty() &&
                    !color.isEmpty() && !priceText.isEmpty() && !quantityText.isEmpty()) {
                try {
                    int id = Integer.parseInt(idText);
                    int price = Integer.parseInt(priceText);
                    int quantity = Integer.parseInt(quantityText);
                    carDAO.updateCar(new Car(id, brand, model, type, engine, color, price, quantity));
                    feedbackLabel.setText("Car updated successfully!");
                    clearFields(brandField, modelField, typeField, engineField, colorField, priceField, quantityField, updateIdField);
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
                    Car car = carDAO.getCar(id);
                    if (car != null) {
                        readResultLabel.setText(
                                "ID: " + car.getId() +
                                        ", Brand: " + car.getBrand() +
                                        ", Model: " + car.getModel() +
                                        ", Type: " + car.getType() +
                                        ", Engine: " + car.getEngine() +
                                        ", Color: " + car.getColor() +
                                        ", Price: " + car.getPrice() +
                                        ", Quantity: " + car.getQuantity()
                        );
                    } else {
                        readResultLabel.setText("No car found with ID: " + id);
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
                new Label("Insert New Car:"),
                brandField, modelField, typeField, engineField, colorField, priceField, quantityField,
                insertButton,
                new Label("Update Car:"),
                updateIdField, updateButton,
                new Label("Delete or Read Car:"),
                readIdField, deleteButton, readButton,
                readResultLabel,
                feedbackLabel,
                pieChart
        );
        layout.setPadding(new Insets(20));

        brandField.setPrefWidth(300);
        modelField.setPrefWidth(300);
        typeField.setPrefWidth(300);
        engineField.setPrefWidth(300);
        colorField.setPrefWidth(300);
        priceField.setPrefWidth(300);
        quantityField.setPrefWidth(300);
        updateIdField.setPrefWidth(300);
        readIdField.setPrefWidth(300);

        Scene scene = new Scene(layout, 600, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Car Dealership with CRUD Operations");
        primaryStage.show();

        updatePieChart();
    }

    private void updatePieChart() {
        pieChart.getData().clear();

        int totalQuantity = carDAO.getAllCars().stream()
                .mapToInt(Car::getQuantity)
                .sum();

        if (totalQuantity > 0) {
            carDAO.getAllCars().forEach(car -> {
                double totalValue = car.getPrice() * car.getQuantity();
                double quantityPercentage = (double) car.getQuantity() / totalQuantity * 100;
                String label = car.getBrand() + " " + car.getModel() + " (ID: " + car.getId() + ") - "
                        + totalValue + " $ (" + String.format("%.2f", quantityPercentage) + "%)";
                pieChart.getData().add(new PieChart.Data(label, totalValue));
            });
        } else {
            pieChart.getData().add(new PieChart.Data("No cars available", 0));
        }
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
}
