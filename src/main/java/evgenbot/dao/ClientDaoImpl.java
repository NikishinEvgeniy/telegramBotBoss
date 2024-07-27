package evgenbot.dao;


import evgenbot.entity.Client;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDao {
    @Autowired
    private SessionFactory factory;

    @Override
    public List<Client> getClients() {
        return factory.getCurrentSession().createQuery("from Client", Client.class).getResultList();
    }

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
}
