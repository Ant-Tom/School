-- Таблица "Person"
CREATE TABLE Person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT CHECK (age >= 0),
    has_license BOOLEAN DEFAULT FALSE
);

-- Таблица "Car"
CREATE TABLE Car (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) CHECK (price >= 0)
);

-- Связь между "Person" и "Car" (несколько людей могут пользоваться одной машиной)
ALTER TABLE Person
ADD COLUMN car_id INT,
ADD CONSTRAINT fk_car FOREIGN KEY (car_id) REFERENCES Car(id);
