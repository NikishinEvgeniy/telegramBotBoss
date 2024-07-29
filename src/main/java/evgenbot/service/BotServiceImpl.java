package evgenbot.service;

import evgenbot.constant.BotCommand;
import evgenbot.constant.EmployeeStatus;
import evgenbot.help.Parser;
import evgenbot.constant.MessagesForUser;
import evgenbot.entity.Client;
import evgenbot.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import evgenbot.constant.BotState;

import java.util.List;

@Service
public class BotServiceImpl implements BotService{

    @Autowired
    private ClientService clientService;
    @Autowired
    private EmployeeService employeeService;

    @Override
    public String processing(Update update){
        User user = update.getMessage().getFrom();
        Client client = clientService.getClient(user.getId());
        if(client == null){
            client = new Client(user.getFirstName(),user.getLastName(),user.getId(), BotState.BEGIN);
            clientService.addClient(client);
        }
        updateState(update,client);
        client = clientService.getClient(user.getId());
        return analyseState(update,client);
    }

    @Override
    public void updateState(Update update,Client client){
        switch (update.getMessage().getText()){
                case BotCommand.START: clientService.setClientStatus(client.getId(), BotState.BEGIN); break;
                case BotCommand.ADD: clientService.setClientStatus(client.getId(), BotState.ADD); break;
                case BotCommand.CHANGE: clientService.setClientStatus(client.getId(), BotState.CHANGE); break;
                case BotCommand.SHOW: clientService.setClientStatus(client.getId(), BotState.SHOW); break;
                case BotCommand.HELP: clientService.setClientStatus(client.getId(), BotState.HELP); break;
        }
    }

    @Override
    public String analyseState(Update update,Client client){
        String message = null;
        switch (client.getState()){
            case BotState.BEGIN:
                message = MessagesForUser.START.getDescription() + MessagesForUser.HELP.getDescription();
                clientService.setClientStatus(client.getId(), BotState.EMPTY);
                break;
            case BotState.HELP:
                message = MessagesForUser.HELP.getDescription();
                clientService.setClientStatus(client.getId(), BotState.EMPTY);
                break;
            case BotState.SHOW:
                message = getStringOfEmployees() + MessagesForUser.HELP.getDescription();
                clientService.setClientStatus(client.getId(), BotState.EMPTY);
                break;
            case BotState.CHANGE:
                message = MessagesForUser.ID.getDescription();
                clientService.setClientStatus(client.getId(), BotState.ASK_ID);
                break;
            case BotState.CHOOSE:
                int choose = Parser.getParsedNumeric(update.getMessage().getText());
                switch (choose){
                    case 1:
                        message = MessagesForUser.DELETE.getDescription() + MessagesForUser.HELP.getDescription();
                        employeeService.deleteEmployee(client.getEmployee().getId());
                        clientService.setClientStatus(client.getId(), BotState.EMPTY);
                        clientService.setEmployeeId(client.getId(),null);
                        break;
                    case 2:
                        message = MessagesForUser.UPDATE.getDescription();
                        clientService.setClientStatus(client.getId(), BotState.UPDATE_CHOOSE);
                        employeeService.setEmployeeState(client.getEmployee().getId(), EmployeeStatus.UPDATE);
                        break;
                    default:
                        message = MessagesForUser.ERROR.getDescription();
                        break;
                }
                break;
            case BotState.UPDATE_CHOOSE:
                choose = Parser.getParsedNumeric(update.getMessage().getText());
                switch (choose) {
                    case 1:
                        message = MessagesForUser.ASK_NAME.getDescription();
                        clientService.setClientStatus(client.getId(), BotState.ASK_NAME);
                        break;
                    case 2:
                        message = MessagesForUser.ASK_SURNAME.getDescription();
                        clientService.setClientStatus(client.getId(), BotState.ASK_SURNAME);
                        break;
                    case 3:
                        message = MessagesForUser.ASK_SALARY.getDescription();
                        clientService.setClientStatus(client.getId(), BotState.ASK_SALARY);
                        break;
                    case 4:
                        message = MessagesForUser.ASK_DEPARTMENT.getDescription();
                        clientService.setClientStatus(client.getId(), BotState.ASK_DEPARTMENT);
                        break;
                    default:
                        message = MessagesForUser.ERROR.getDescription();
                        break;
                }
                break;
            case BotState.ASK_SURNAME:
                employeeService.setEmployeeSurname(client.getEmployee().getId(),update.getMessage().getText());
                switch (client.getEmployee().getStatus()){
                    case EmployeeStatus.ADD:
                        clientService.setClientStatus(client.getId(), BotState.ASK_DEPARTMENT);
                        message = MessagesForUser.ASK_DEPARTMENT.getDescription();
                        break;
                    case EmployeeStatus.UPDATE:
                        employeeService.setEmployeeState(client.getEmployee().getId(), EmployeeStatus.OK);
                        clientService.setClientStatus(client.getId(), BotState.EMPTY);
                        clientService.setEmployeeId(client.getId(),null);
                        message = MessagesForUser.SUCCESS.getDescription() + MessagesForUser.HELP.getDescription();
                        break;
                }
                break;
            case BotState.ASK_NAME:
                employeeService.setEmployeeName(client.getEmployee().getId(),update.getMessage().getText());
                switch (client.getEmployee().getStatus()){
                    case EmployeeStatus.ADD:
                        clientService.setClientStatus(client.getId(), BotState.ASK_SURNAME);
                        message = MessagesForUser.ASK_SURNAME.getDescription();
                        break;
                    case EmployeeStatus.UPDATE:
                        clientService.setClientStatus(client.getId(), BotState.EMPTY);
                        message = MessagesForUser.SUCCESS.getDescription() + MessagesForUser.HELP.getDescription();
                        employeeService.setEmployeeState(client.getEmployee().getId(), EmployeeStatus.OK);
                        clientService.setEmployeeId(client.getId(),null);
                        break;
                }
                break;
            case BotState.ASK_DEPARTMENT:
                employeeService.setEmployeeDepartment(client.getEmployee().getId(),update.getMessage().getText());
                switch (client.getEmployee().getStatus()){
                    case EmployeeStatus.ADD:
                        clientService.setClientStatus(client.getId(), BotState.ASK_SALARY);
                        message = MessagesForUser.ASK_SALARY.getDescription();
                        break;
                    case EmployeeStatus.UPDATE:
                        clientService.setClientStatus(client.getId(), BotState.EMPTY);
                        message = MessagesForUser.SUCCESS.getDescription() + MessagesForUser.HELP.getDescription();
                        employeeService.setEmployeeState(client.getEmployee().getId(), EmployeeStatus.OK);
                        clientService.setEmployeeId(client.getId(),null);
                        break;
                }
                break;
            case BotState.ASK_SALARY:
                switch (client.getEmployee().getStatus()){
                    case EmployeeStatus.ADD:
                        int salaryAdd = Parser.getParsedNumeric(update.getMessage().getText());
                        if(salaryAdd != -1){
                            employeeService.setEmployeeSalary(client.getEmployee().getId(),salaryAdd);
                            clientService.setClientStatus(client.getId(), BotState.EMPTY);
                            clientService.setEmployeeId(client.getId(),null);
                            message = MessagesForUser.SUCCESS.getDescription() + MessagesForUser.HELP.getDescription();
                            employeeService.setEmployeeState(client.getEmployee().getId(), EmployeeStatus.OK);
                        }
                        else message = MessagesForUser.SALARY_ERROR.getDescription();
                        break;
                    case EmployeeStatus.UPDATE:
                        int salary = Parser.getParsedNumeric(update.getMessage().getText());
                        if(salary != -1){
                            employeeService.setEmployeeSalary(client.getEmployee().getId(),salary);
                            clientService.setClientStatus(client.getId(), BotState.EMPTY);
                            message = MessagesForUser.SUCCESS.getDescription() + MessagesForUser.HELP.getDescription();
                            employeeService.setEmployeeState(client.getEmployee().getId(), EmployeeStatus.OK);
                            clientService.setEmployeeId(client.getId(),null);
                        }
                        else message = MessagesForUser.SALARY_ERROR.getDescription();
                        break;
                }
                break;
            case BotState.ASK_ID:
                String userInput = update.getMessage().getText();
                int id = Parser.getParsedNumeric(userInput);
                if(id != -1){
                    if(employeeService.getEmployee(id) != null){
                        message = MessagesForUser.CHOOSE.getDescription();
                        clientService.setEmployeeId(client.getId(),id);
                        clientService.setClientStatus(client.getId(), BotState.CHOOSE);
                    }
                    else { message = MessagesForUser.ID_ERROR.getDescription(); }
                }
                else {
                    message = MessagesForUser.ID_ERROR.getDescription();
                }
                break;
            case BotState.ADD:
                message = MessagesForUser.ADD.getDescription() + MessagesForUser.ASK_NAME.getDescription();
                String clientID = String.valueOf(client.getId());
                Employee employee = new Employee(clientID,EmployeeStatus.ADD);
                employeeService.addEmployee(employee);
                clientService.setEmployeeId(client.getId(),employeeService.getEmptyEmployee(clientID).getId());
                clientService.setClientStatus(client.getId(), BotState.ASK_NAME);
                break;
            default:
                message = MessagesForUser.ERROR.getDescription();
                clientService.setClientStatus(client.getId(), BotState.EMPTY);
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
