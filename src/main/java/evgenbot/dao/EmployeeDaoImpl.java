package evgenbot.dao;

import evgenbot.entity.Employee;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    @Autowired
    private SessionFactory factory;

    public List<Employee> getEmployees(){
       return factory.getCurrentSession().createQuery("from Employee", Employee.class).getResultList();
    }

    @Override
    public void addEmployee(Employee employee) {

    }

    @Override
    public void deleteEmployee(int id) {
        Employee employee = factory.getCurrentSession().get(Employee.class,id);
        factory.getCurrentSession().createQuery("DELETE FROM Employee WHERE id = :idEmp ")
                .setParameter("idEmp",id).executeUpdate();
    }
}
