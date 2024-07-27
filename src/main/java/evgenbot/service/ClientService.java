package evgenbot.service;

import evgenbot.entity.Client;

import java.util.List;

public interface ClientService {
    Client getClient(long id);
    void addClient(Client client);
    void setClientStatus(long id,String state);
}
