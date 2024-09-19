package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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
        return employeeRepository.findDistinctByFirstNameLikeAndLastNameLikeIgnoreCase(firstNameSearchPattern,
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
        return employeeRepository.findDistinctBySeniorityAndPosition(seniority, position, pageable);
    }

    @Override
    public Page<Employee> getEmployeeListSearched(String searchBy,
                                                  Map<String, String> searchParams , Pageable pageable){

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
}
