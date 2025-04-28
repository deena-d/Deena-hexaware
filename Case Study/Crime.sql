create database Crime;
use Crime;

create table victims (
    victim_id int primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    date_of_birth date,
    gender enum('male', 'female', 'other'),
    victim_address text,
    victim_phonenumber varchar(15)
);


create table suspects (
    suspect_id int primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    date_of_birth date,
    gender enum('male', 'female', 'other'),
    suspects_address text,
    suspects_phonenumber varchar(15)
);


create table law_enforcement_agencies (
    agency_id int primary key,
    agency_name varchar(100) not null,
    jurisdiction varchar(100),
    agency_address text,
    agency_phonenumber varchar(15)
);


create table officers (
    officer_id int primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    badge_number int,
    officers_rank varchar(50),
    officer_address text,
    officer_phonenumber varchar(15),
    agency_id int,
    foreign key (agency_id) references law_enforcement_agencies(agency_id)
);


create table incidents (
    incident_id int primary key,
    incident_type varchar(50) not null,
    incident_date date not null,
    description text,
    status enum('open', 'closed', 'under investigation') not null,
    officer_id int,
    victim_id int,
    suspect_id int,
    latitude decimal(9,6),
    longitude decimal(9,6),
    foreign key (victim_id) references victims(victim_id),
    foreign key (suspect_id) references suspects(suspect_id),
    foreign key (officer_id) references officers(officer_id)
);


create table evidence (
    evidence_id int primary key,
    description text,
    location_found text,
    incident_id int,
    foreign key (incident_id) references incidents(incident_id)
);


create table reports (
    report_id int primary key,
    incident_id int,
    reporting_officer int,
    report_date date not null,
    report_details text,
    status enum('draft', 'finalized') not null,
    foreign key (incident_id) references incidents(incident_id) on delete cascade,
    foreign key (reporting_officer) references officers(officer_id)
);

insert into victims (victim_id, first_name, last_name, date_of_birth, gender, victim_address, victim_phonenumber) values
(1, 'Ravi', 'Kumar', '1990-05-14', 'male', 'Bangalore, Karnataka', '9876543210'),
(2, 'Sita', 'Iyer', '1985-08-22', 'female', 'Chennai, Tamil Nadu', '9823456789'),
(3, 'Arjun', 'Singh', '1992-12-11', 'male', 'Delhi', '9845123678'),
(4, 'Pooja', 'Sharma', '1988-07-19', 'female', 'Mumbai, Maharashtra', '9867452310'),
(5, 'Rohan', 'Das', '1995-03-25', 'male', 'Kolkata, West Bengal', '9988776655'),
(6, 'Meera', 'Patel', '1983-11-02', 'female', 'Ahmedabad, Gujarat', '9776655443'),
(7, 'Vikram', 'Joshi', '1991-06-17', 'male', 'Hyderabad, Telangana', '9556677889'),
(8, 'Ananya', 'Reddy', '1997-09-28', 'female', 'Pune, Maharashtra', '9445566778'),
(9, 'Suresh', 'Naik', '1994-04-05', 'male', 'Goa', '9334455667'),
(10, 'Neha', 'Verma', '1990-01-30', 'female', 'Lucknow, Uttar Pradesh', '9123344556');

insert into suspects (suspect_id, first_name, last_name, date_of_birth, gender, suspects_address, suspects_phonenumber) values
(1, 'Rahul', 'Mishra', '1985-09-21', 'male', 'Patna, Bihar', '9811122334'),
(2, 'Kavita', 'Shah', '1990-02-15', 'female', 'Indore, Madhya Pradesh', '9844332211'),
(3, 'Amit', 'Bose', '1987-06-10', 'male', 'Howrah, West Bengal', '9765544321'),
(4, 'Sunita', 'Pillai', '1984-03-08', 'female', 'Thiruvananthapuram, Kerala', '9899776655'),
(5, 'Rajesh', 'Gupta', '1981-12-02', 'male', 'Jaipur, Rajasthan', '9873322110'),
(6, 'Manish', 'Yadav', '1989-07-23', 'male', 'Varanasi, Uttar Pradesh', '9955443322'),
(7, 'Swati', 'Chopra', '1993-10-29', 'female', 'Shimla, Himachal Pradesh', '9899556677'),
(8, 'Arvind', 'Thakur', '1996-05-14', 'male', 'Bhopal, Madhya Pradesh', '9445566778'),
(9, 'Prakash', 'Shetty', '1982-08-30', 'male', 'Mangalore, Karnataka', '9776655443'),
(10, 'Neeraj', 'Kapoor', '1990-11-11', 'male', 'Dehradun, Uttarakhand', '9112233445');

insert into law_enforcement_agencies (agency_id, agency_name, jurisdiction, agency_address, agency_phonenumber) values
(1, 'Bangalore Police Department', 'Bangalore', 'MG Road, Bangalore', '9876543210'),
(2, 'Mumbai Crime Branch', 'Mumbai', 'Colaba, Mumbai', '9823456789'),
(3, 'Delhi Special Task Force', 'Delhi', 'Connaught Place, Delhi', '9845123678'),
(4, 'Chennai Cyber Cell', 'Chennai', 'T. Nagar, Chennai', '9867452310'),
(5, 'Kolkata Traffic Police', 'Kolkata', 'Park Street, Kolkata', '9988776655');

insert into officers (officer_id, first_name, last_name, badge_number, officers_rank, officer_address, officer_phonenumber, agency_id) values
(1, 'Vikas', 'Raj', 101, 'Inspector', 'Koramangala, Bangalore', '9876543211', 1),
(2, 'Akhil', 'Pawar', 102, 'Sub-Inspector', 'Bandra, Mumbai', '9823456799', 2),
(3, 'Anand', 'Singh', 103, 'DSP', 'Rohini, Delhi', '9845123688', 3),
(4, 'Shankar', 'Nair', 104, 'ACP', 'Anna Nagar, Chennai', '9867452320', 4),
(5, 'Rajeev', 'Sen', 105, 'Constable', 'Salt Lake, Kolkata', '9988776677', 5);

insert into incidents (incident_id, incident_type, incident_date, description, status, victim_id, suspect_id, latitude, longitude, officer_id) values
(1, 'Robbery', '2024-03-01', 'Robbery at a jewelry store', 'open', 1, 2, 12.971599, 77.594566, 1),
(2, 'Assault', '2024-02-15', 'Physical assault near metro station', 'under investigation', 3, 4, 28.704060, 77.102493, 2),
(3, 'Fraud', '2024-01-10', 'Bank fraud reported', 'closed', 5, 6, 19.076090, 72.877426, 3),
(4, 'Burglary', '2024-03-12', 'House burglary at midnight', 'open', 7, 8, 13.082680, 80.270721, 4),
(5, 'Hit and Run', '2024-02-28', 'Hit and run case near bypass road', 'under investigation', 9, 10, 22.572645, 88.363892, 5);

insert into evidence (evidence_id, description, location_found, incident_id) values
(1, 'Blood-stained knife', 'Near victim\'s residence', 1),
(2, 'CCTV footage', 'Bank ATM', 2);


insert into reports (report_id, incident_id, reporting_officer, report_date, report_details, status) values
(1, 1, 1, '2024-02-16', 'Initial report filed, awaiting forensic analysis', 'draft'),
(2, 2, 2, '2024-03-06', 'Case handed over to crime branch', 'finalized');

