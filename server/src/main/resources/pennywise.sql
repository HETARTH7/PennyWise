drop database if exists pennywiseDB;
create database pennywiseDB;

use pennywiseDB;

CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    budget DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Expense (
    expense_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    amount DECIMAL(10, 2) NOT NULL,
    category VARCHAR(50),
    mode_of_payment VARCHAR(50),
    reason VARCHAR(255),
    date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

