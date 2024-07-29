package evgenbot.service;

import evgenbot.dao.EmployeeDao;
import evgenbot.entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    @Transactional
    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employeeDao.getEmployees());
    }

    @Override
    @Transactional
    public void deleteEmployee(int id) {
        employeeDao.deleteEmployee(id);
    }

    @Override
    @Transactional
    public Employee getEmployee(int id) {
        return employeeDao.getEmployee(id);
    }

    @Override
    @Transactional
    public void setEmployeeName(int id, String name) {
        employeeDao.setEmployeeName(id,name);
    }

    @Override
    @Transactional
    public void setEmployeeSurname(int id, String surname) {
        employeeDao.setEmployeeSurname(id,surname);
    }

    @Override
    @Transactional
    public void setEmployeeSalary(int id, int salary) {
        employeeDao.setEmployeeSalary(id,salary);
    }

    @Override
    @Transactional
    public void setEmployeeDepartment(int id, String department) {
        employeeDao.setEmployeeDepartment(id,department);
    }

    @Override
    @Transactional
    public void setEmployeeState(int id, String state) {
        employeeDao.setEmployeeState(id,state);
    }

    @Override
    @Transactional
    public void addEmployee(Employee employee) {
        employeeDao.addEmployee(employee);
    }

    @Override
    @Transactional
    public Employee getEmptyEmployee(String clientID) {
        return employeeDao.getEmptyEmployee(clientID);
    }

}
