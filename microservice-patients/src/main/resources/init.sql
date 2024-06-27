CREATE DATABASE IF NOT EXISTS `patients_db`;

USE `patients_db`;

CREATE TABLE IF NOT EXISTS patients (
  patient_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  firstname VARCHAR(100) NOT NULL,
  lastname VARCHAR(100) NOT NULL,
  birthdate TIMESTAMP,
  gender VARCHAR(25),
  address VARCHAR(150),
  phone_number VARCHAR(120)
);

INSERT INTO patients (firstname, lastname, birthdate, gender, address, phone_number) VALUES
('Test', 'TestNone', '1945-06-24 00:00:00', 'F', '1 Brookside St', '100-222-3333'),
('Test', 'TestBorderline', '1945-06-24 00:00:00', 'M', '2 High St', '200-333-4444'),
('Test', 'TestInDanger', '2004-06-18 00:00:00', 'M', '3 Club Road', '300-444-5555'),
('Test', 'TestEarlyOnset', '2002-06-28 00:00:00', 'F', '4 Valley Dr', '400-555-6666');

