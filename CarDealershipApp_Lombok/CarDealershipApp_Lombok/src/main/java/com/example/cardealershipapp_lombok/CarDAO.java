package com.example.cardealershipapp_lombok;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    private Connection connection;

    public CarDAO() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_dealership", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCar(Car car) {
        String query = "INSERT INTO cars (brand, model, type, engine, color, price, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getType());
            ps.setString(4, car.getEngine());
            ps.setString(5, car.getColor());
            ps.setInt(6, car.getPrice());
            ps.setInt(7, car.getQuantity());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Car getCar(int carId) {
        String query = "SELECT * FROM cars WHERE id = ?";
        Car car = null;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, carId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                car = new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("type"),
                        rs.getString("engine"),
                        rs.getString("color"),
                        rs.getInt("price"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    public List<Car> getAllCars() {
        String query = "SELECT * FROM cars";
        List<Car> cars = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("id"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("type"),
                        rs.getString("engine"),
                        rs.getString("color"),
                        rs.getInt("price"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public void updateCar(Car car) {
        String query = "UPDATE cars SET brand = ?, model = ?, type = ?, engine = ?, color = ?, price = ?, quantity = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getType());
            ps.setString(4, car.getEngine());
            ps.setString(5, car.getColor());
            ps.setInt(6, car.getPrice());
            ps.setInt(7, car.getQuantity());
            ps.setInt(8, car.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCar(int carId) {
        String query = "DELETE FROM cars WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, carId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
