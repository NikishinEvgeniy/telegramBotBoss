package evgenbot.dao;

import evgenbot.entity.Client;

import java.util.List;

public interface ClientDao {
    List<Client> getClients();
    Client getClient(long id);
    void addClient(Client client);
    void setClientStatus(long id,String state);
}
