package com.bernacki.hrapp.service;

import com.bernacki.hrapp.dao.EmployeeDao;
import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.model.ProjectAssignmentId;
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
    private EmployeeActivityService employeeActivityService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeDao employeeDao, ProjectAssignmentService projectAssignmentService, EmployeeActivityService employeeActivityService) {
        this.employeeRepository = employeeRepository;
        this.employeeDao = employeeDao;
        this.projectAssignmentService = projectAssignmentService;
        this.employeeActivityService = employeeActivityService;
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
    public Page<Employee> findAllPaginated(Pageable pageable){
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> findByFullName(String firstName, String lastName, Pageable pageable) {
        String firstNameSearchPattern = "%" + firstName + "%";
        String lastNameSearchPattern = "%" + lastName + "%";
        return employeeRepository.findByFirstNameLikeAndLastNameLikeIgnoreCase(firstNameSearchPattern,
                lastNameSearchPattern, pageable);
    }

    @Override
    public Page<Employee> findByEmail(String email, Pageable pageable) {
        String emailSearchPattern = "%" + email + "%";
        return employeeRepository.findByEmailLike(emailSearchPattern, pageable);
    }

    @Override
    public Page<Employee> findByTelephoneNumber(String telephoneNumber, Pageable pageable) {
        String telephoneNumberSearchPattern = "%" + telephoneNumber + "%";
        return employeeRepository.findByTelephoneNumberLike(telephoneNumberSearchPattern, pageable);
    }

    @Override
    public Page<Employee> findBySeniority(String seniority, Pageable pageable) {

        return employeeRepository.findBySeniority(seniority, pageable);
    }

    @Override
    public Page<Employee> findByPosition(String position, Pageable pageable) {
        return employeeRepository.findByPosition(position, pageable);
    }

    @Override
    public Page<Employee> findBySeniorityAndPosition(String seniority, String position, Pageable pageable) {
        return employeeRepository.findBySeniorityAndPosition(seniority, position, pageable);
    }

    @Override
    public Page<Employee> getEmployeeListSearched(String searchBy,
                                                  Map<String, String> searchParams ,
                                                  Pageable pageable,
                                                  boolean onlyActiveUsers){

        String searchByFirstName = searchParams.get("searchByFirstName");
        String searchByLastName = searchParams.get("searchByLastName");
        String searchByEmail = searchParams.get("searchByEmail");
        String searchByTelNr = searchParams.get("searchByTelNr");
        String searchBySeniority = searchParams.get("searchBySeniority");
        String searchByPosition = searchParams.get("searchByPosition");

        Page<Employee> employeePage = null;
        if(searchBy ==null || searchBy.isEmpty()){
            employeePage = findAllPaginated(pageable);
        } else if (searchBy.equals("Full Name")) {
            employeePage = findByFullName(
                    searchByFirstName, searchByLastName
                    ,pageable);
        } else if (searchBy.equals("Email")) {
            employeePage = findByEmail(searchByEmail, pageable);
        } else if (searchBy.equals("Telephone Number")){
            employeePage = findByTelephoneNumber(searchByTelNr, pageable);
        } else if (searchBy.equals("Seniority")){
            employeePage = findBySeniority(searchBySeniority, pageable);
        } else if (searchBy.equals("Position")){
            employeePage = findByPosition(searchByPosition, pageable);
        } else if (searchBy.equals("Full Position")){
            employeePage = findBySeniorityAndPosition(
                    searchBySeniority,
                    searchByPosition,
                    pageable
            );
        }
        else{
            employeePage = findAllPaginated(pageable);
        }
        return employeePage;
    }

    @Override
    public Page<Employee> findAllSearchedAndSortedWithActivities(String searchBy, Map<String, String> searchParams, String sortBy, String sortDirection,Pageable pageable, boolean onlyActive) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        String searchByFirstName = searchParams.get("searchByFirstName");
        String searchByLastName = searchParams.get("searchByLastName");
        String searchByEmail = searchParams.get("searchByEmail");
        String searchByTelNr = searchParams.get("searchByTelNr");
        String searchBySeniority = searchParams.get("searchBySeniority");
        String searchByPosition = searchParams.get("searchByPosition");

        StringBuilder query = new StringBuilder("SELECT e FROM Employee e LEFT JOIN FETCH e.employeeActivities ea ");
        query.append("WHERE (:onlyActive=false OR ea.active=true) ");
        query.append("AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee = e) ");
        String firstSearchParam = "";
        String secondSearchParam = "";
        int paramCount = 0;
        if (searchBy.equals("Full Name")) {
            query.append("AND LOWER(e.firstName) LIKE LOWER(:firstSearchParam) ");
            query.append("AND LOWER(e.lastName) LIKE LOWER(:secondSearchParam) ");
            firstSearchParam=searchParams.get("searchByFirstName");
            secondSearchParam=searchParams.get("searchByLastName");
            paramCount = 2;
        } else if (searchBy.equals("Email")) {
            query.append("AND LOWER(e.email) LIKE LOWER(:firstSearchParam) ");
            firstSearchParam=searchParams.get("searchByEmail");
            paramCount = 1;

        } else if (searchBy.equals("Telephone Number")){
            query.append("AND LOWER(e.telephoneNumber) LIKE LOWER(:firstSearchParam) ");
            firstSearchParam=searchParams.get("searchByTelNr");
            paramCount = 1;

        } else if (searchBy.equals("Seniority")){
            query.append("AND e.seniority=:firstSearchParam ");
            firstSearchParam=searchParams.get("searchBySeniority");
            paramCount = 1;

        } else if (searchBy.equals("Position")){
            query.append("AND e.position=:firstSearchParam ");
            firstSearchParam=searchParams.get("searchByPosition");
            paramCount = 1;

        } else if (searchBy.equals("Full Position")){
            query.append("AND e.seniority=:firstSearchParam ");
            query.append("AND e.position=:secondSearchParam ");
            firstSearchParam=searchParams.get("searchBySeniority");
            secondSearchParam=searchParams.get("searchByPosition");
            paramCount=2;
        }


        query.append("ORDER BY ea.active DESC, ");

        if(sortBy.equals("id")){
            query.append("e.id ");
        } else if(sortBy.equals("firstName")){
            query.append("e.firstName ");
        }else if(sortBy.equals("lastName")){
            query.append("e.lastName ");
        }else if(sortBy.equals("email")){
            query.append("e.email ");
        }else if(sortBy.equals("telephoneNumber")){
            query.append("e.telephoneNumber ");
        } else if(sortBy.equals("seniority")){
            query.append("e.seniority ");
        }

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
    public void delete(int id) {
        List<ProjectAssignmentId> projectAssignmentIdList= projectAssignmentService.findProjectAssignmentIdsByEmployeeId(id);
        projectAssignmentService.deleteAllByIds(projectAssignmentIdList);
        employeeRepository.deleteById(id);
    }
}
