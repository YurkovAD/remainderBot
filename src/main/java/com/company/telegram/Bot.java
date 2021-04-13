package com.company.telegram;

import com.company.logic.BotTask;
import com.company.telegram.commands.service.HelpCommand;
import com.company.telegram.commands.service.StartCommand;
import com.company.telegram.commands.user.RemindCommand;
import com.company.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public final class Bot extends TelegramLongPollingCommandBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    public static List<BotTask> taskList = new ArrayList<>();
//    @Getter
//    private final User user;

    public Bot(String botName, String botToken) {
        super();
        logger.debug("Конструктор суперкласса отработал");
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        logger.debug("Имя и токен присвоены");
//        this.user = new User();
//        logger.debug("Класс обработки сообщения, не являющегося командой, создан");
        register(new StartCommand("start", "Старт"));
        logger.debug("Команда start создана");
        register(new HelpCommand("help", "Помощь"));
        logger.debug("Команда help создана");
        register(new RemindCommand("remind", "Создать задание"));
        logger.debug("Команда remind создана");
//        register(new PlusCommand("plus", "Сложение"));
//        logger.debug("Команда plus создана");
//        register(new MinusCommand("minus", "Вычитание"));
//        logger.debug("Команда minus создана");
//        register(new PlusMinusCommand("plusminus", "Сложение и вычитание"));
//        logger.debug("Команда plusminus создана");

//        register(new SettingsCommand("settings", "Мои настройки"));
//        logger.debug("Команда settings создана");
//        userSettings = new HashMap<>();
        logger.info("Бот создан!");
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = Utils.getUserName(msg);
        String answer = "Простите, я не понимаю Вас. Похоже, что Вы ввели сообщение, не соответствующее формату\n\n" +
                "Возможно, Вам поможет /help";
        setAnswer(chatId, userName, answer);
    }

    /**
     * Отправка ответа
     * @param chatId id чата
     * @param userName имя пользователя
     * @param text текст ответа
     */
    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            logger.error(String.format("Ошибка %s. Сообщение, не являющееся командой. Пользователь: %s", e.getMessage(),
                    userName));
            e.printStackTrace();
        }
    }

    private Logger logger = LoggerFactory.getLogger(Bot.class);
}
