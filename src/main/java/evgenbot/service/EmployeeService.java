package evgenbot.service;


import evgenbot.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployees();
    void deleteEmployee(int id);
    Employee getEmployee(int id);
    void setEmployeeName(int id, String name);
    void setEmployeeSurname(int id, String surname);
    void setEmployeeSalary(int id, int salary);
    void setEmployeeDepartment(int id, String department);
    void setEmployeeState(int id, String state);
    void addEmployee(Employee employee);
    Employee getEmptyEmployee(String clientID);
}
