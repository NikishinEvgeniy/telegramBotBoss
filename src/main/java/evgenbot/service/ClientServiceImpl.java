package evgenbot.service;

import evgenbot.dao.ClientDao;
import evgenbot.entity.Client;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientDao clientDao;


    @Override
    @Transactional
    public Client getClient(Long id) {
        return clientDao.getClient(id);
    }

    @Override
    @Transactional
    public void addClient(Client client) {
        clientDao.addClient(client);
    }

    @Override
    @Transactional
    public void setClientStatus(Long id,String state) {
        clientDao.setClientStatus(id,state);
    }

    @Override
    @Transactional
    public void setEmployeeId(Long clientId ,Integer employeeId) {
        clientDao.setEmployeeId(clientId,employeeId);
    }
}
