package com.bernacki.hrapp.service;

import com.bernacki.hrapp.model.Employee;
import com.bernacki.hrapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
