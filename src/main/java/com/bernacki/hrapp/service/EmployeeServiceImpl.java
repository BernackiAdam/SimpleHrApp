package com.bernacki.hrapp.service;

import com.bernacki.hrapp.dao.EmployeeDao;
import com.bernacki.hrapp.dto.EmployeeDto;
import com.bernacki.hrapp.entity.Employee;
import com.bernacki.hrapp.entity.EmployeeActivity;
import com.bernacki.hrapp.entity.ProjectAssignmentId;
import com.bernacki.hrapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;
    private EmployeeDao employeeDao;
    private ProjectAssignmentService projectAssignmentService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeDao employeeDao, ProjectAssignmentService projectAssignmentService) {
        this.employeeRepository = employeeRepository;
        this.employeeDao = employeeDao;
        this.projectAssignmentService = projectAssignmentService;

    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int id) {
        Optional<Employee> result = employeeRepository.findById(id);
        Employee employee = null;

        if(result.isPresent()){
            employee = result.get();
        }
        return employee;
    }

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }


    @Override
    public Page<Employee> findAllSearchedAndSortedWithActivities(String searchBy, Map<String, String> searchParams, String sortBy, String sortDirection,Pageable pageable, boolean onlyActive) {

        StringBuilder query = new StringBuilder("SELECT e FROM Employee e LEFT JOIN FETCH e.employeeActivities ea ");
        query.append("WHERE (:onlyActive=false OR ea.active=true) ");
        query.append("AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee = e) ");
        String firstSearchParam = "";
        String secondSearchParam = "";
        int paramCount = 0;
        switch (searchBy) {
            case "Full Name" -> {
                query.append("AND LOWER(e.firstName) LIKE LOWER(:firstSearchParam) ");
                query.append("AND LOWER(e.lastName) LIKE LOWER(:secondSearchParam) ");
                firstSearchParam = searchParams.get("searchByFirstName");
                secondSearchParam = searchParams.get("searchByLastName");
                paramCount = 2;
            }
            case "Email" -> {
                query.append("AND LOWER(e.email) LIKE LOWER(:firstSearchParam) ");
                firstSearchParam = searchParams.get("searchByEmail");
                paramCount = 1;
            }
            case "Telephone Number" -> {
                query.append("AND LOWER(e.telephoneNumber) LIKE LOWER(:firstSearchParam) ");
                firstSearchParam = searchParams.get("searchByTelNr");
                paramCount = 1;
            }
            case "Seniority" -> {
                query.append("AND e.seniority=:firstSearchParam ");
                firstSearchParam = searchParams.get("searchBySeniority");
                paramCount = 1;
            }
            case "Position" -> {
                query.append("AND e.position=:firstSearchParam ");
                firstSearchParam = searchParams.get("searchByPosition");
                paramCount = 1;
            }
            case "Full Position" -> {
                query.append("AND e.seniority=:firstSearchParam ");
                query.append("AND e.position=:secondSearchParam ");
                firstSearchParam = searchParams.get("searchBySeniority");
                secondSearchParam = searchParams.get("searchByPosition");
                paramCount = 2;
            }
        }

        query.append("ORDER BY ea.active DESC, ");

        query.append("e.").append(sortBy).append(" ");

        if(sortDirection.equalsIgnoreCase("asc")){
            query.append("ASC");
        }
        else {
            query.append("DESC");
        }

        List<Employee> employees = employeeDao.executeQueryWithSearchingAndSorting(query.toString(), paramCount, firstSearchParam, secondSearchParam, onlyActive);
        int total = employees.size();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), total);
        return new PageImpl<>(employees.subList(start, end), pageable, total);
    }

    @Override
    public Employee findEmployeeWithCurrentActivityByEmployeeId(int employeeId) {
        return employeeRepository.findEmployeeWithCurrentActivityByEmployeeId(employeeId);
    }

    @Override
    public List<Employee> findInactiveEmployeesWithCurrentActivity() {
        return employeeRepository.findInactiveEmployeesWithCurrentActivity();
    }

    @Override
    public void delete(int id) {
        List<ProjectAssignmentId> projectAssignmentIdList= projectAssignmentService.findProjectAssignmentIdsByEmployeeId(id);
        projectAssignmentService.deleteAllByIds(projectAssignmentIdList);
        employeeRepository.deleteById(id);
    }

    @Override
    public void saveByEmployeeDto(EmployeeDto employeeDto) {
        Employee employee = null;
        if(employeeDto.getId() == 0){
            employee = new Employee();
            EmployeeActivity employeeActivity = new EmployeeActivity();
            employeeActivity.setActive(true);
            employeeActivity.setEmployee(employee);
            employee.setEmployeeActivities(List.of(employeeActivity));
        }
        else{
            employee = findById(employeeDto.getId());
        }
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setTelephoneNumber(employeeDto.getTelephoneNumber());
        employee.setSeniority(employeeDto.getSeniority());
        employee.setPosition(employeeDto.getPosition());

        save(employee);
    }
}
