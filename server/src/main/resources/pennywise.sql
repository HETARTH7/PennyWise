create database pennywiseDB;

use pennywiseDB;

CREATE TABLE User (
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
    reason VARCHAR(255),
    saved BOOLEAN DEFAULT FALSE,
    date DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);
