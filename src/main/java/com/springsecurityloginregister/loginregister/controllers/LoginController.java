package com.springsecurityloginregister.loginregister.controllers;

import com.springsecurityloginregister.loginregister.models.Employee;
import com.springsecurityloginregister.loginregister.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        Employee employee = new Employee();
        modelAndView.addObject("employee", employee);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewEmployee(@Valid Employee employee, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        Employee employeeExists = employeeService.findByEmail(employee.getEmail());
        if (employeeExists != null) {
            result
                    .rejectValue("email", "error.employee",
                            "There email is taken.");
        }
        if (result.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            employeeService.saveEmployee(employee);
            modelAndView.addObject("successMessage", "Employee is registered successfully.");
            modelAndView.addObject("employee", new Employee());
            modelAndView.setViewName("registration");
        }

        return modelAndView;
    }


    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeService.findByEmail(authentication.getName());
        modelAndView.addObject("employeeName",
                "Welcome" + employee.getName() + " (" + employee.getEmail() + " )");
        modelAndView.addObject("adminMessage", "admin screen");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }


}
