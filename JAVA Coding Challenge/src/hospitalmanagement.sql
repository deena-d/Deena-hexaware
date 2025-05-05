eate database hospitalmanagement;
use hospitalmanagement;
create table patient (
    patient_id int primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    date_of_birth date not null,
    gender varchar(10),
    contact_number varchar(15) unique,
    address varchar(255)
);
create table doctor (
    doctor_id int primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    specialization varchar(100),
    contact_number varchar(15) unique
);
create table appointment (
    appointment_id int primary key auto_increment,
    patient_id int,
    doctor_id int,
    appointment_date datetime not null,
    description text,
    foreign key (patient_id) references patient(patient_id) on delete cascade,
    foreign key (doctor_id) references doctor(doctor_id) on delete set null
);

INSERT INTO patient (first_name, last_name, date_of_birth, gender, contact_number, address) VALUES
('John', 'Doe', '1985-06-15', 'Male', '1234567890', '123 Main St, Springfield'),
('Jane', 'Smith', '1990-03-22', 'Female', '2345678901', '456 Oak St, Riverside'),
('Alice', 'Brown', '1975-12-05', 'Female', '3456789012', '789 Pine St, Hillside'),
('Robert', 'Wilson', '2000-08-30', 'Male', '4567890123', '101 Maple St, Greenfield');

INSERT INTO doctor (first_name, last_name, specialization, contact_number) VALUES
('Dr. Emily', 'Clark', 'Cardiologist', '9876543210'),
('Dr. Michael', 'Adams', 'Pediatrician', '8765432109'),
('Dr. Sarah', 'Lee', 'Dermatologist', '7654321098');

INSERT INTO appointment (patient_id, doctor_id, appointment_date, description) VALUES
(1, 1, '2025-05-10 10:00:00', 'Routine heart check-up'),
(2, 2, '2025-05-11 14:30:00', 'Pediatric consultation for flu symptoms'),
(3, 3, '2025-05-12 09:00:00', 'Skin allergy examination'),
(4, 1, '2025-05-13 11:15:00', 'Blood pressure follow-up');
