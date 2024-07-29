package evgenbot.service;

import evgenbot.entity.Client;

import java.util.List;

public interface ClientService {
    Client getClient(Long id);
    void addClient(Client client);
    void setClientStatus(Long id,String state);
    void setEmployeeId(Long clientId ,Integer employeeId);
}
