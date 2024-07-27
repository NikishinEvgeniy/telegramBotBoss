package evgenbot.service;


import evgenbot.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees();
    void deleteEmployee(int id);
}
