package evgenbot.dao;


import evgenbot.entity.Client;
import evgenbot.entity.Employee;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ClientDaoImpl implements ClientDao {
    @Autowired
    private SessionFactory factory;



    @Override
    public Client getClient(long id) {
        return factory.getCurrentSession().get(Client.class,id);
    }

    @Override
    public void addClient(Client client) {
        factory.getCurrentSession().save(client);
    }

    @Override
    public void setClientStatus(long id, String state) {
        getClient(id).setState(state);
    }

    @Override
    public void setEmployeeId(long clientId, Integer employeeId) {
        Employee employee = null;
        if(employeeId != null) employee = factory.getCurrentSession().get(Employee.class,employeeId);
        Client client = getClient(clientId);
        client.setEmployee(employee);
    }
}
