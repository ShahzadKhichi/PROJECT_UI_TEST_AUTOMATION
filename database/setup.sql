-- Test Automation Database Setup
CREATE DATABASE IF NOT EXISTS test_automation;
USE test_automation;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(50),
    in_stock BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Test data insertion
INSERT INTO users (username, password, first_name, last_name, email) VALUES
('standard_user', 'secret_sauce', 'Standard', 'User', 'standard@test.com'),
('locked_out_user', 'secret_sauce', 'Locked', 'Out', 'locked@test.com'),
('problem_user', 'secret_sauce', 'Problem', 'User', 'problem@test.com'),
('performance_glitch_user', 'secret_sauce', 'Performance', 'Glitch', 'performance@test.com');

INSERT INTO products (name, description, price, category) VALUES
('Sauce Labs Backpack', 'carry.allTheThings() with the sleek, streamlined Sly Pack', 29.99, 'backpack'),
('Sauce Labs Bike Light', 'A red light for riding your bike at night', 9.99, 'accessory'),
('Sauce Labs Bolt T-Shirt', 'Get your testing superhero on with the Sauce Labs bolt T-shirt', 15.99, 'clothing'),
('Sauce Labs Fleece Jacket', 'Midweight quarter-zip fleece jacket', 49.99, 'clothing'),
('Sauce Labs Onesie', 'Rib snap infant onesie for the junior automation engineer', 7.99, 'clothing'),
('Test.allTheThings() T-Shirt (Red)', 'Classic Sauce Labs t-shirt', 15.99, 'clothing');

-- Create test results table
CREATE TABLE IF NOT EXISTS test_results (
    id INT PRIMARY KEY AUTO_INCREMENT,
    test_name VARCHAR(100),
    status VARCHAR(20),
    execution_time INT,
    executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
