package evgenbot.service;

import evgenbot.entity.Client;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotService {
    String processing(Update update);
    void updateState(Update update, Client client);
    String analyseState(Update update,Client client);
}
