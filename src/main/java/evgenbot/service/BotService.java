package evgenbot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotService {
    String processing(Update update);
}
