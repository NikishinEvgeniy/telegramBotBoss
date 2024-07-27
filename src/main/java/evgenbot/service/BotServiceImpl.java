package evgenbot.service;

import evgenbot.help.Parser;
import evgenbot.constant.MessagesForUser;
import evgenbot.entity.Client;
import evgenbot.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

@Service
public class BotServiceImpl implements BotService{
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmployeeService employeeService;

    public String processing(Update update){
        User user = update.getMessage().getFrom();
        Client client = clientService.getClient(user.getId());
        if(client == null){
            client = new Client(user.getFirstName(),user.getLastName(),user.getId(),"BEGIN_STATE");
            clientService.addClient(client);
        }
        updateState(update,client);
        return analyseState(update,client);
    }
    private void updateState(Update update,Client client){
        switch (update.getMessage().getText()){
                case "/start": client.setState("BEGIN_STATE"); break;
                case "/add": client.setState("ADD_STATE"); break;
                case "/delete": client.setState("DELETE_STATE"); break;
                case "/show": client.setState("SHOW_STATE"); break;
                case "/update": client.setState("SHOW_STATE"); break;
                case "/help": client.setState("HELP_STATE"); break;
                default:
                    if(client.getState().equals("EMPTY_STATE")) client.setState("ERROR_STATE");
                    break;
        }
    } // Изменять статус через базу данных.
    private String analyseState(Update update,Client client){
        String message;
        switch (client.getState()){
            case "BEGIN_STATE":
                message = MessagesForUser.START.getDescription() + MessagesForUser.HELP.getDescription();
                clientService.setClientStatus(client.getId(), "EMPTY_STATE");
                break;
            case "HELP_STATE":
                message = MessagesForUser.HELP.getDescription();
                clientService.setClientStatus(client.getId(), "EMPTY_STATE");
                break;
            case "SHOW_STATE":
                message = getStringOfEmployees();
                clientService.setClientStatus(client.getId(), "EMPTY_STATE");
                break;
            case "DELETE_STATE":
                message = MessagesForUser.DELETE.getDescription();
                clientService.setClientStatus(client.getId(), "ASK_ID");
                break;
            case "UPDATE_STATE":
                message = MessagesForUser.DELETE.getDescription();
                clientService.setClientStatus(client.getId(), "ASK_ID"); // Сделать только обновление информации и там будет удаление.
                break;
            case "ASK_SURNAME":
                message = "";
                break;
            case "ASK_NAME":
                message = "";
                break;
            case "ASK_DEPARTMENT":
                message = "";
                break;
            case "ASK_SALARY":
                message = "";
                break;
            case "ASK_ID":
                String str = update.getMessage().getText();
                int id = Parser.getParsedNumeric(str);
                if(id != -1){
                    employeeService.deleteEmployee(id); // Обработать то, что если нет сотрудника с таким айди, то не удаляем
                    message = getStringOfEmployees();
                    clientService.setClientStatus(client.getId(), "EMPTY_STATE");
                }
                else {
                    message = MessagesForUser.ID_ERROR.getDescription();
                }
                break;
            default:
                message = MessagesForUser.ERROR.getDescription();
                clientService.setClientStatus(client.getId(), "EMPTY_STATE");
                break;
        }
        return message;
    }

    private String getStringOfEmployees(){
        StringBuilder stringBuilder = new StringBuilder();
        List<Employee> employeeList = employeeService.getEmployees();
        if(employeeList.isEmpty()) return "\n Сотрудники отсутствуют \n";
        employeeList.stream().forEach(x -> stringBuilder.append(x + "\n\n"));
        return stringBuilder.toString();
    }
}
