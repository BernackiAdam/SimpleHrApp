DROP SCHEMA hrapp;
CREATE SCHEMA hrapp;
USE hrapp;

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS projects;

CREATE TABLE employee
(
	id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(40) NOT NULL,
    tel_nr VARCHAR(9) NOT NULL,
    join_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
)AUTO_INCREMENT=1;

CREATE TABLE projects
(
	id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    project_type VARCHAR(20) NOT NULL,
    description BLOB NOT NULL,
    PRIMARY KEY (id)
)AUTO_INCREMENT=1;

CREATE TABLE projects_users
(
	employee_id INT NOT NULL,
    project_id INT NOT NULL,
    PRIMARY KEY(employee_id, project_id),
    KEY fk_employee_id (employee_id),
    KEY fk_project_id (project_id),
    CONSTRAINT fk_employee_id FOREIGN KEY(employee_id)
    REFERENCES employee(id),
    CONSTRAINT fk_project_id FOREIGN KEY(project_id)
    REFERENCES projects(id)
);

INSERT INTO employee(first_name, last_name, email, tel_nr) VALUES
('Adam', 'Bernacki', 'ab@email.com', '123123123'),
('Grzegorz', 'Bernacki', 'gb@email.com', '234234234'),
('Magda', 'Siergiejuk', 'ms@email.com', '345345345');

INSERT INTO projects(title, project_type,description) VALUES
	('Project1', 'WEB_APP','Description for project one'),
	('Project2', 'MOBILE_APP','Unique description for the second project'),
	('Project2', 'DEV_OPS','Another fancy description for the project to make a lot of money');

INSERT INTO projects_users VALUES
(1,1), (2,1), 
(2,2), (3,2), 
(1,3), (3,3);
