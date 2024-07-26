package evgenbot.bot;

import evgenbot.constant.MessagesForUser;
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
    private String token;

    public BossBot(DefaultBotOptions options, String token, String name) {
        super(options,token);
        this.token = token;
        this.name = name;
    }

    @Override
    public String getBotUsername() {
        return this.name;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        switch (update.getMessage().getText()){
            case "/add":
                System.out.println("add");
                break;
            case "/show":
                System.out.println("show");
                break;
            case "/delete":
                System.out.println("delete");
                break;
            case "/update":
                System.out.println("update");
                break;
            case "/help":
                String helpMessage = MessagesForUser.HELP.getDescription();
                sendTheMessage(generateMessage(update,helpMessage));
                break;
            case "/start":
                String startMessage = MessagesForUser.START.getDescription() + MessagesForUser.HELP.getDescription();
                sendTheMessage(generateMessage(update,startMessage));
                break;
            default:
                String error = MessagesForUser.ERROR.getDescription();
                sendTheMessage(generateMessage(update,error));
                break;
        }
    }

    public void sendTheMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    public SendMessage generateMessage(Update update, String message){
        SendMessage sm =
                SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text(message).build();
        return sm;
    }
}
