package com.bernacki.hrapp.controller;


import com.bernacki.hrapp.dto.EmployeeDto;
import com.bernacki.hrapp.entity.Employee;
import com.bernacki.hrapp.entity.EmployeeActivity;
import com.bernacki.hrapp.service.EmployeeActivityService;
import com.bernacki.hrapp.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/manage/employee")
public class EmployeeManageController {

    @Value("${seniority}")
    private List<String> seniorityList;

    @Value("${position}")
    private List<String> positionList;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeActivityService employeeActivityService;

    private void populateModel(Model model){
        model.addAttribute("seniorityList", seniorityList);
        model.addAttribute("positionList", positionList);
    }

    @InitBinder
    private void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, editor);
    }

    @GetMapping("/add")
    public String addEmployee(Model model){
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employee", employeeDto);
        populateModel(model);
        return "manage/employee-add-form";
    }

    @PostMapping("/save")
    public String saveEmployee(
            @Valid @ModelAttribute("employee") EmployeeDto employeeDto,
            BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            populateModel(model);
            return "manage/employee-add-form";
        }
        employeeService.saveByEmployeeDto(employeeDto);
        return "redirect:/manage/";
    }


    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("employeeId") int id){

        employeeService.delete(id);
        return "redirect:/employee/list";
    }

    @GetMapping("/edit")
    public String editEmployee(@RequestParam("employeeId") int employeeId,
                               Model model){
        EmployeeDto employeeDto = employeeService.populateEmployeeDtoByEmployeeId(employeeId);
        populateModel(model);
        model.addAttribute("employee",employeeDto);
        return "manage/employee-add-form";
    }

    @PostMapping("/deactivate")
    public String deactivateEmployee(@RequestParam("employeeId") int employeeId,
                                     @RequestParam(value = "deactivationDate", defaultValue = "") LocalDate deactivationDate,
                                     @RequestParam(value = "reactivationDate", defaultValue = "") LocalDate reactivationDate,
                                     @RequestParam(value = "deactivationReason", defaultValue = "") String reason){

        EmployeeActivity employeeActivity = new EmployeeActivity();
        employeeActivity.setActive(false);
        employeeActivity.setEmployee(employeeService.findById(employeeId));
        if(deactivationDate==null){
            employeeActivity.setDate(Date.from(Instant.now()));
        }
        else {
            employeeActivity.setDate(Date.from(deactivationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        if(reactivationDate!=null){
            employeeActivity.setReactivationDate(Date.from(reactivationDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        employeeActivity.setDeactivationReason(reason);
        employeeActivityService.save(employeeActivity);
        return "redirect:/employee/info?employeeId=" + employeeId;
    }

    @PostMapping("/reactivate")
    public String reactivateEmployee(@RequestParam("employeeId") int employeeId){
        Employee employee = employeeService.findEmployeeWithCurrentActivityByEmployeeId(employeeId);
        if (employee.getEmployeeActivities().stream().anyMatch(activity -> !activity.isActive())){
            Optional<EmployeeActivity> result = employee.getEmployeeActivities().stream().findFirst();
            EmployeeActivity employeeActivity = result.orElse(null);
            if(employeeActivity.getReactivationDate()==null || employeeActivity.getReactivationDate().after(Date.from(Instant.now()))){
                employeeActivity.setReactivationDate(Date.from(Instant.now()));
                employeeActivityService.save(employeeActivity);
            }
            EmployeeActivity newEmployeeActivity = new EmployeeActivity();
            newEmployeeActivity.setActive(true);
            newEmployeeActivity.setEmployee(employee);
            employeeActivityService.save(newEmployeeActivity);
        }
        return "redirect:/employee/info?employeeId=" + employeeId;
    }
}
