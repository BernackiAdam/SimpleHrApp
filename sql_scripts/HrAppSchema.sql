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

INSERT INTO employee(first_name, last_name, email, tel_nr) VALUES
('Adam', 'Bernacki', 'ab@email.com', '123123123'),
('Grzegorz', 'Bernacki', 'gb@email.com', '234234234'),
('Magda', 'Siergiejuk', 'ms@email.com', '345345345');

INSERT INTO projects(title, project_type,description) VALUES
	('Project1', 'WEB_APP','Description for project one'),
	('Project2', 'MOBILE_APP','Unique description for the second project'),
	('Project2', 'DEV_OPS','Another fancy description for the project to make a lot of money');

