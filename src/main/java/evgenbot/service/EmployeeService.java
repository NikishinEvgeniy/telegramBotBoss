package evgenbot.service;


import evgenbot.entity.Employee;

import java.util.List;

public interface BotService {
    List<Employee> getEmployees();
    void deleteEmployee(int id);
}
