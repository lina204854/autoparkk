
CREATE DATABASE IF NOT EXISTS autopark_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;


USE autopark_db;

CREATE TABLE IF NOT EXISTS cars (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    release_year INT NOT NULL,
    registration_date DATE,
    owner_name VARCHAR(255)
);

INSERT INTO cars (brand, release_year, registration_date, owner_name) VALUES
('Lada Vesta', 2021, '2021-05-20', 'Иванов Петр Сергеевич'),
('Toyota Camry', 2022, '2022-01-15', 'Сидорова Анна Викторовна'),
('BMW X5', 2020, '2020-11-11', 'Петров Иван Максимович'),
('Kia Rio', 2023, '2023-03-10', 'Кузнецова Ольга Олеговна'),
('Lada Granta', 2022, '2022-08-01', NULL);
