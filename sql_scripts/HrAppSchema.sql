DROP SCHEMA hrapp;
CREATE SCHEMA hrapp;
USE hrapp;

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS projects_users;
DROP TABLE IF EXISTS project_phase;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS project_consultant;

CREATE TABLE employee
(
	id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(40) NOT NULL,
    tel_nr VARCHAR(9) NOT NULL,
    position VARCHAR(40) NOT NULL,
    join_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
)AUTO_INCREMENT=1;

CREATE TABLE clients
(
	id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(255) NOT NULL,
    date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
)AUTO_INCREMENT=1;


CREATE TABLE projects
(
	id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    project_type VARCHAR(20) NOT NULL,
    date_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description BLOB NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    client_id INT NOT NULL,
    PRIMARY KEY (id),
    KEY fk_client_proj_id(client_id),
    CONSTRAINT fk_client_proj_id FOREIGN KEY(client_id)
    REFERENCES clients(id) 
    ON DELETE NO ACTION ON UPDATE NO ACTION
)AUTO_INCREMENT=1;

CREATE TABLE project_consultant
(
	id INT NOT NULL AUTO_INCREMENT,
    project_id INT NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(40) NOT NULL,
    tel_nr VARCHAR(9) NOT NULL,
    PRIMARY KEY(id),
    KEY fk_project_consultant_project(project_id),
    CONSTRAINT fk_project_consultant_project FOREIGN KEY(project_id)
    REFERENCES projects(id) ON UPDATE NO ACTION ON DELETE NO ACTION
)AUTO_INCREMENT=1;


CREATE TABLE project_phase
(
	id INT NOT NULL AUTO_INCREMENT,
    project_id INT NOT NULL,
    phase VARCHAR(30) NOT NULL,
    date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    KEY fk_phase_proj_id (project_id),
    CONSTRAINT fk_phase_proj_id FOREIGN KEY(project_id)
    REFERENCES projects(id) ON UPDATE NO ACTION ON DELETE NO ACTION
)AUTO_INCREMENT=1;

CREATE TABLE projects_users
(
	employee_id INT NOT NULL,
    project_id INT NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY(employee_id, project_id),
    KEY fk_employee_id (employee_id),
    KEY fk_project_id (project_id),
    CONSTRAINT fk_employee_id FOREIGN KEY(employee_id)
    REFERENCES employee(id)
    ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_project_id FOREIGN KEY(project_id)
    REFERENCES projects(id)
    ON UPDATE NO ACTION ON DELETE NO ACTION
);




INSERT INTO employee(first_name, last_name, email, tel_nr, position) VALUES
('Adam', 'Bernacki', 'ab@email.com', '123123123', 'Junior Frontend Developer'),
('Grzegorz', 'Bernacki', 'gb@email.com', '234234234', 'Senior Backend Developer'),
('Pawel', 'Jumper', 'jp@email.com', '654654654', 'Mid QA Engineer'),
('Magda', 'Siergiejuk', 'ms@email.com', '345345345', 'Mid Project Manager');

INSERT INTO clients(name, address) VALUES
('Company1', 'bialystok ul.strazacka 25'),
('Company2', 'Warszawa ul.gwiazkowa 32'),
('Company3', 'gdansk ul.chyża 232');

INSERT INTO projects(title, project_type,description, client_id) VALUES
('Project1', 'WEB_APP','Description for project one',3),
('Project2', 'MOBILE_APP','Unique description for the second project',2),
('Project3', 'DEV_OPS','Another fancy description for the project to make a lot of money',1);
    
INSERT INTO projects_users VALUES
(1,1, 'Frontend Developer'), (2,1, 'Backend developer'), 
(2,2, 'Consultant'), (3,2, 'QA'),
(1,3, 'Frontend Developer'), (3,3, 'QA consultant');

INSERT INTO project_phase(project_id,phase) VALUES
(1,'DEVELOPMENT'),
(2,'SECURITY'),
(3,'INTEGRATION');

INSERT INTO project_consultant(project_id, first_name, last_name, email, tel_nr) VALUES
(1, 'Marek', 'Mostowiak', 'mm@email.com', '123456789'),
(2, 'Halina', 'Jakas', 'hj@email.com', '234234234'),
(3, 'Stachu', 'Staszynski', 'st@email.com', '456456456');



