package evgenbot.service;

import evgenbot.dao.EmployeeDao;
import evgenbot.entity.Employee;
import evgenbot.handler.DefaultCommandHandler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BotServiceImpl implements BotService{
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DefaultCommandHandler defaultCommandHandler;

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
}
