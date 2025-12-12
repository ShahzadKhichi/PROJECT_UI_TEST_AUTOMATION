package com.sqe.framework.utils;

import com.sqe.framework.config.ConfigManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {
    
    private Connection connection;
    
    public DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Get credentials from ConfigManager
            String host = ConfigManager.getInstance().getProperty("db.host");
            String port = ConfigManager.getInstance().getProperty("db.port");
            String database = ConfigManager.getInstance().getProperty("db.name");
            String username = ConfigManager.getInstance().getProperty("db.username");
            String password = ConfigManager.getInstance().getProperty("db.password");
            
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
            
            System.out.println("Attempting to connect to database: " + url);
            System.out.println("Username: " + username);
            
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Database connection successful!");
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
            throw new RuntimeException("Failed to load MySQL JDBC Driver");
        } catch (SQLException e) {
            System.err.println("Failed to connect to database!");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to database: " + e.getMessage());
        }
    }
    
    public List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = rs.getObject(i);
                    row.put(columnName, columnValue);
                }
                resultList.add(row);
            }
            
        } catch (SQLException e) {
            System.err.println("Error executing query: " + query);
            e.printStackTrace();
        }
        
        return resultList;
    }
    
    public int executeUpdate(String query) {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Error executing update: " + query);
            e.printStackTrace();
            return -1;
        }
    }
    
    public void createTestDatabase() {
        try {
            // Create database if it doesn't exist
            String createDbQuery = "CREATE DATABASE IF NOT EXISTS test_automation";
            executeUpdate(createDbQuery);
            System.out.println("✅ Database 'test_automation' created or already exists");
            
            // Use the database
            String useDbQuery = "USE test_automation";
            executeUpdate(useDbQuery);
            
            // Create users table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "username VARCHAR(50) NOT NULL UNIQUE," +
                    "password VARCHAR(100) NOT NULL," +
                    "first_name VARCHAR(50)," +
                    "last_name VARCHAR(50)," +
                    "email VARCHAR(100)," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            executeUpdate(createUsersTable);
            System.out.println("✅ Users table created or already exists");
            
            // Create products table
            String createProductsTable = "CREATE TABLE IF NOT EXISTS products (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(100) NOT NULL," +
                    "description TEXT," +
                    "price DECIMAL(10,2) NOT NULL," +
                    "category VARCHAR(50)," +
                    "in_stock BOOLEAN DEFAULT true," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
            executeUpdate(createProductsTable);
            System.out.println("✅ Products table created or already exists");
            
            // Insert test data
            insertTestData();
            
        } catch (Exception e) {
            System.err.println("Error creating test database: " + e.getMessage());
        }
    }
    
    private void insertTestData() {
        // Insert users if table is empty
        String checkUsers = "SELECT COUNT(*) as count FROM users";
        List<Map<String, Object>> usersCount = executeQuery(checkUsers);
        long userCount = (Long) usersCount.get(0).get("count");
        
        if (userCount == 0) {
            String insertUsers = "INSERT INTO users (username, password, first_name, last_name, email) VALUES " +
                    "('standard_user', 'secret_sauce', 'Standard', 'User', 'standard@test.com')," +
                    "('locked_out_user', 'secret_sauce', 'Locked', 'Out', 'locked@test.com')," +
                    "('problem_user', 'secret_sauce', 'Problem', 'User', 'problem@test.com')," +
                    "('performance_glitch_user', 'secret_sauce', 'Performance', 'Glitch', 'performance@test.com')";
            executeUpdate(insertUsers);
            System.out.println("✅ Test users inserted");
        }
        
        // Insert products if table is empty
        String checkProducts = "SELECT COUNT(*) as count FROM products";
        List<Map<String, Object>> productsCount = executeQuery(checkProducts);
        long productCount = (Long) productsCount.get(0).get("count");
        
        if (productCount == 0) {
            String insertProducts = "INSERT INTO products (name, description, price, category) VALUES " +
                    "('Sauce Labs Backpack', 'carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.', 29.99, 'backpack')," +
                    "('Sauce Labs Bike Light', 'A red light isn''t the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included.', 9.99, 'accessory')," +
                    "('Sauce Labs Bolt T-Shirt', 'Get your testing superhero on with the Sauce Labs bolt T-shirt. From American Apparel, 100% ringspun combed cotton, heather gray with red bolt.', 15.99, 'clothing')," +
                    "('Sauce Labs Fleece Jacket', 'It''s not every day that you come across a midweight quarter-zip fleece jacket capable of handling everything from a relaxing day outdoors to a busy day at the office.', 49.99, 'clothing')," +
                    "('Sauce Labs Onesie', 'Rib snap infant onesie for the junior automation engineer in development. Reinforced 3-snap bottom closure, two-needle hemmed sleeved and bottom won''t unravel.', 7.99, 'clothing')," +
                    "('Test.allTheThings() T-Shirt (Red)', 'This classic Sauce Labs t-shirt is perfect to wear when cozying up to your keyboard to automate a few tests. Super-soft and comfy ringspun combed cotton.', 15.99, 'clothing')";
            executeUpdate(insertProducts);
            System.out.println("✅ Test products inserted");
        }
    }
    
    public List<Map<String, Object>> getUsers() {
        String query = "SELECT * FROM users";
        return executeQuery(query);
    }
    
    public List<Map<String, Object>> getProducts() {
        String query = "SELECT * FROM products";
        return executeQuery(query);
    }
    
    public Map<String, Object> getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = '" + username + "'";
        List<Map<String, Object>> users = executeQuery(query);
        return users.isEmpty() ? null : users.get(0);
    }
    
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
