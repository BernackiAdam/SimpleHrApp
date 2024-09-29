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
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(40) NOT NULL,
    email VARCHAR(60) NOT NULL,
    tel_nr VARCHAR(9) NOT NULL,
    seniority VARCHAR(12),
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
    active BOOLEAN DEFAULT 1,
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
    first_name VARCHAR(40) NOT NULL,
    last_name VARCHAR(40) NOT NULL,
    email VARCHAR(60) NOT NULL,
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

CREATE TABLE projects_employees
(
	employee_id INT NOT NULL,
    project_id INT NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY(employee_id, project_id),
    KEY fk_employee_id (employee_id),
    KEY fk_project_id (project_id),
    CONSTRAINT fk_employee_id FOREIGN KEY(employee_id)
    REFERENCES employee(id)
    ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT fk_project_id FOREIGN KEY(project_id)
    REFERENCES projects(id)
    ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE employee_activity
(
	id INT NOT NULL AUTO_INCREMENT,
	employee_id INT NOT NULL,
    active BOOLEAN DEFAULT true,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    reactivation_date DATETIME DEFAULT NULL,
    deactivation_reason VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY(id),
    KEY fk_activiti_employee_id(employee_id),
    CONSTRAINT fk_activity_employee_id FOREIGN KEY(employee_id)
    REFERENCES employee(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION
)AUTO_INCREMENT=1;





