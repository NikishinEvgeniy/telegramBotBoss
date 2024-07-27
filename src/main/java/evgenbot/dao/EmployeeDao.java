package evgenbot.dao;

import evgenbot.entity.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> getEmployees();
    void addEmployee(Employee employee);
    void deleteEmployee(int id);
}
