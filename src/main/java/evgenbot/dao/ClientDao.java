package evgenbot.dao;

import evgenbot.entity.Client;


public interface ClientDao {
    Client getClient(long id);
    void addClient(Client client);
    void setClientStatus(long id,String state);
    void setEmployeeId(long clientId ,Integer employeeId);
}
