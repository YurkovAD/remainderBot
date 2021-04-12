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

        if (TaskValidator.validate(task, absSender, user,  message, this.getCommandIdentifier())) {
//            try {
                BotTask botTask = new BotTask(createBotMessage(task), new Timer(), absSender, message);
                message.setText("Задание " + task + " создано");
                sendMess(absSender, message);
                message.setText("Напоминаю! " + task);
                BotTask.createTask(botTask);
//            } catch (NumberFormatException e) {
//                logger.error(String.format ("Wrong format of task!"), user, this.getCommandIdentifier());
//                message.setText("Неверный формат задачи!");
//                sendMess(absSender, message);
//          }
        }
    }

    private void sendMess (AbsSender absSender, SendMessage message) {
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private Logger logger = LoggerFactory.getLogger(RemindCommand.class);
}