package com.company.telegram.commands.user;

import com.company.exceptions.TaskException;
import com.company.exceptions.TaskTimeException;
import com.company.logic.BotTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Timer;

import static com.company.logic.BotMessage.createBotMessage;

public class RemindCommand extends BotCommand {

    public RemindCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        String task = String.join(" ", strings);
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (task == null || task.isEmpty()) {
            logger.error(String.format ("User {} is trying to set empty task."), user, this.getCommandIdentifier());
            message.setText("Задание не может быть пустым!");
            sendMess(absSender, message);
        }
        try {
            BotTask botTask = new BotTask(createBotMessage(task), new Timer());
            message.setText("Задание " + task + " создано");
            sendMess(absSender, message);
            BotTask.createTask(botTask);
            message.setText("пора делать " + botTask.getBotMessage().toString());
            sendMess(absSender, message);
            /*absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();*/
        } catch (NumberFormatException e) {
            logger.error(String.format ("Wrong format of task!"), user, this.getCommandIdentifier());
            message.setText("Неверный формат задачи!");
            sendMess(absSender, message);
        } catch (TaskException e) {
            logger.error(String.format (e.getMessage()), user, this.getCommandIdentifier());
            message.setText(e.getMessage());
            sendMess(absSender, message);
        } catch (TaskTimeException e) {
            logger.error(String.format (e.getMessage()), user, this.getCommandIdentifier());
            message.setText(e.getMessage());
            sendMess(absSender, message);
        }

    }

    private void sendMess (AbsSender absSender, SendMessage message) {
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    //    private BotTask botTask;
//
//    RemindCommand(String identifier, String description, BotTask botTask) {
//        super(identifier, description);
//        this.botTask = botTask;
//    }
//
//    /**
//     * Отправка ответа пользователю
//     */
//    void sendAnswer(AbsSender absSender, Long chatId, BotTask botTask, String description,
//                    String commandName, String userName) {
//        try {
//            absSender.execute(createDocument(chatId, botTask, description));
//        } catch (IOException | RuntimeException e) {
//            logger.error(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
//            sendError(absSender, chatId, commandName, userName);
//            e.printStackTrace();
//        } catch (TelegramApiException e) {
//            logger.error(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Создание документа для отправки пользователю
//     * @param chatId id чата
//     * @param operations список типов операций (сложение и/или вычитание)
//     * @param fileName имя, которое нужно присвоить файлу
//     */
//    private SendDocument createDocument(Long chatId, List<OperationEnum> operations, String fileName) throws IOException {
//        FileInputStream stream = service.getPlusMinusFile(operations, Bot.getUserSettings(chatId));
//        SendDocument document = new SendDocument();
//        document.setChatId(chatId.toString());
//        document.setDocument(new InputFile(stream, String.format("%s.docx", fileName)));
//        return document;
//    }
//
//    /**
//     * Отправка пользователю сообщения об ошибке
//     */
//    private void sendError(AbsSender absSender, Long chatId, String commandName, String userName) {
//        try {
//            absSender.execute(new SendMessage(chatId.toString(), "Задача не может быть пустой!"));
//        } catch (TelegramApiException e) {
//            logger.error(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
//            e.printStackTrace();
//        }
//    }
//
    private Logger logger = LoggerFactory.getLogger(RemindCommand.class);

}