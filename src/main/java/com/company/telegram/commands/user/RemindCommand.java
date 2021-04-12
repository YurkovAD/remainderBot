package com.company.telegram.commands.user;

import com.company.logic.BotTask;
import com.company.validator.TaskValidator;
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

        if(TaskValidator.validate(task, absSender, user,  message, this.getCommandIdentifier())){
//        isNullTask(task, absSender, user, message);
            try {
                BotTask botTask = new BotTask(createBotMessage(task), new Timer(), absSender, message);
                message.setText("Задание " + task + " создано");
                sendMess(absSender, message);
                message.setText("Напоминаю! " + task);
                BotTask.createTask(botTask);
            } catch (NumberFormatException e) {
                logger.error(String.format ("Wrong format of task!"), user, this.getCommandIdentifier());
                message.setText("Неверный формат задачи!");
                sendMess(absSender, message);
          } //catch (TaskException e) {
    //            logger.error(String.format (e.getMessage()), user, this.getCommandIdentifier());
    //            message.setText(e.getMessage());
    //            sendMess(absSender, message);
    //        } catch (TaskTimeException e) {
    //            logger.error(String.format (e.getMessage()), user, this.getCommandIdentifier());
    //            message.setText(e.getMessage());
    //            sendMess(absSender, message);
    //        }
        }
    }

//    private void isNullTask(String task, AbsSender absSender, User user, SendMessage message) {
//        if (task == null || task.isEmpty()) {
//            logger.error(String.format ("User {} is trying to set empty task."), user, this.getCommandIdentifier());
//            message.setText("Задание не может быть пустым!");
//            sendMess(absSender, message);
//        }
//    }

    private void sendMess (AbsSender absSender, SendMessage message) {
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private Logger logger = LoggerFactory.getLogger(RemindCommand.class);
}