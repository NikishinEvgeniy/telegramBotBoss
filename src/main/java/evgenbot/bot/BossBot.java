package evgenbot.bot;


import evgenbot.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class BossBot extends TelegramLongPollingBot {

    @Autowired
    private BotService botService;
    private String name;

    public BossBot(DefaultBotOptions options, String token, String name) {
        super(options,token);
        this.name = name;
    }

    @Override
    public String getBotUsername() {
        return this.name;
    }


    @Override
    public void onUpdateReceived(Update update) {
        sendTheMessage(generateMessage(update,botService.processing(update)));
    }

    public void sendTheMessage(SendMessage message){
        try {
            if(message != null) execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public SendMessage generateMessage(Update update, String message){
        SendMessage sm = null;
        if(message != null){
            sm =
                    SendMessage.builder()
                            .chatId(update.getMessage().getChatId())
                            .text(message).build();
        }
        return sm;
    }
}
