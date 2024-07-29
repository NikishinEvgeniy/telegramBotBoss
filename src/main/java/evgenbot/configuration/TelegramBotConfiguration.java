package evgenbot.configuration;

import evgenbot.bot.BossBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Configuration
@ComponentScan("evgenbot")
@PropertySource("myApplication.properties")
public class TelegramBotConfiguration {
    @Bean("bossBot")
    public BossBot bossBot(
            @Value("${bossBot.token}") String botToken,
            @Value("${bossBot.name}") String botName){
        return new BossBot(new DefaultBotOptions(),botToken, botName);
    }


    public static void main(String[] args) throws TelegramApiException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TelegramBotConfiguration.class, HibernateConfiguration.class);
        TelegramLongPollingBot bot = context.getBean("bossBot", BossBot.class);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }
}
