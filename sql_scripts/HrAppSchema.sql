DROP SCHEMA hrapp;
CREATE SCHEMA hrapp;
USE hrapp;

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS projects;
DROP TABLE IF EXISTS projects_users;
DROP TABLE IF EXISTS phase;
DROP TABLE IF EXISTS project_phase;

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
    date_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description BLOB NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (id)
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

-- CREATE TABLE projects_phases
-- (
-- 	project_id INT NOT NULL,
--     phase_id INT NOT NULL,
--     PRIMARY KEY(project_id, phase_id),
--     KEY fk_proj_ph_id (project_id),
--     KEY fk_phase_id (phase_id),
--     CONSTRAINT fk_proj_ph_id FOREIGN KEY(project_id)
--     REFERENCES projects(id) 
--     ON UPDATE NO ACTION ON DELETE NO ACTION,
--     CONSTRAINT fk_phase_id FOREIGN KEY(phase_id)
--     REFERENCES phase(id)
--     ON UPDATE NO ACTION ON DELETE NO ACTION
-- );

CREATE TABLE projects_users
(
	employee_id INT NOT NULL,
    project_id INT NOT NULL,
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



INSERT INTO employee(first_name, last_name, email, tel_nr) VALUES
('Adam', 'Bernacki', 'ab@email.com', '123123123'),
('Grzegorz', 'Bernacki', 'gb@email.com', '234234234'),
('Pawel', 'Jumper', 'jp@email.com', '654654654'),
('Magda', 'Siergiejuk', 'ms@email.com', '345345345');


INSERT INTO projects(title, project_type,description) VALUES
	('Project1', 'WEB_APP','Description for project one'),
	('Project2', 'MOBILE_APP','Unique description for the second project'),
	('Project2', 'DEV_OPS','Another fancy description for the project to make a lot of money');

INSERT INTO projects_users VALUES
(1,1), (2,1), 
(2,2), (3,2),
(1,3), (3,3);

INSERT INTO project_phase(project_id,phase) VALUES
(1,'DEVELOPMENT'),
(2,'SECURITY'),
(3,'INTEGRATION');



