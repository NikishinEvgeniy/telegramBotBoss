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

    @Override
    public List<Employee> getEmployees(){
       return factory.getCurrentSession().createQuery("from Employee where status = 'OK'", Employee.class).getResultList();
    }

    @Override
    public void addEmployee(Employee employee) {
        factory.getCurrentSession().save(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        factory.getCurrentSession().createQuery("DELETE FROM Employee WHERE id = :idEmp ")
                .setParameter("idEmp",id).executeUpdate();
    }

    @Override
    public Employee getEmployee(int id) {
        return factory.getCurrentSession().get(Employee.class,id);
    }

    @Override
    public void setEmployeeName(int id, String name) {
        factory.getCurrentSession()
                .createQuery("UPDATE Employee SET name =:name WHERE id =:id ")
                .setParameter("name",name)
                .setParameter("id",id)
                .executeUpdate();
    }

    @Override
    public void setEmployeeSurname(int id, String surname) {
        factory.getCurrentSession()
                .createQuery("UPDATE Employee SET surname =: surname WHERE id =:id ")
                .setParameter("surname",surname)
                .setParameter("id",id)
                .executeUpdate();
    }

    @Override
    public void setEmployeeSalary(int id, int salary) {
        factory.getCurrentSession()
                .createQuery("UPDATE Employee SET salary =:salary WHERE id =:id ")
                .setParameter("salary",salary)
                .setParameter("id",id)
                .executeUpdate();
    }

    @Override
    public void setEmployeeDepartment(int id, String department) {
        factory.getCurrentSession()
                .createQuery("UPDATE Employee SET department =:department WHERE id =:id ")
                .setParameter("department",department)
                .setParameter("id",id)
                .executeUpdate();
    }

    @Override
    public void setEmployeeState(int id, String state) {
        factory.getCurrentSession()
                .createQuery("UPDATE Employee SET status =:state WHERE id =:id ")
                .setParameter("state",state)
                .setParameter("id",id)
                .executeUpdate();
    }

    @Override
    public Employee getEmptyEmployee(String clientID) {
        Employee employee = factory.getCurrentSession()
                .createQuery("FROM Employee WHERE name = :clientID ", Employee.class)
                .setParameter("clientID",clientID).getResultList().get(0);
        return employee;
    }
}
