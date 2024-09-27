package com.bernacki.hrapp.repository;

import com.bernacki.hrapp.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Page<Employee> findByFirstNameLikeAndLastNameLikeIgnoreCase(String firstName, String lastName, Pageable pageable);
    Page<Employee> findByEmailLike(String email, Pageable pageable);
    Page<Employee> findByTelephoneNumberLike(String telephoneNumber, Pageable pageable);
    Page<Employee> findBySeniority(String seniority, Pageable pageable);
    Page<Employee> findByPosition(String position, Pageable pageable);
    Page<Employee> findBySeniorityAndPosition(String seniority, String position, Pageable pageable);

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.employeeActivities ea " +
            "WHERE (:onlyActive = false OR ea.active=true) " +
            "AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee = e)" +
            "ORDER BY ea.active DESC, e.id ASC")
    Page<Employee> findAllOrActiveWithCurrentActivity(@Param("onlyActive") boolean onlyActive, Pageable pageable);

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.employeeActivities ea " +
            "WHERE (:onlyActive = false OR ea.active=true) " +
            "AND ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 WHERE ea2.employee=e) " +
            "AND LOWER(e.firstName) LIKE LOWER(:firstName) " +
            "AND LOWER(e.lastName) LIKE LOWER(:lastName) " +
            "ORDER BY ea.active DESC, e.id ASC")
    Page<Employee> findAllOrActiveByFirstNameLikeAndLastNameLikeWithCurrentActivity(@Param("firstName") String firstName,
                                                                @Param("lastName") String lastName,
                                                                @Param("onlyActive") boolean onlyActive,
                                                                Pageable pageable);

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.employeeActivities ea " +
            "WHERE ea.date=(SELECT MAX(ea2.date) FROM EmployeeActivity ea2 where ea2.employee = e) " +
            "AND e.id =:employeeId")
    Employee findEmployeeWithCurrentActivityByEmployeeId(@Param("employeeId") int employeeId);
}
