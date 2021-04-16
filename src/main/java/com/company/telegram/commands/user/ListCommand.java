package com.company.telegram.commands.user;

import com.company.utils.BotMessageSender;
import com.company.validators.TaskListValidator;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.company.telegram.Bot.formater;
import static com.company.telegram.commands.user.RemindCommand.taskList;

/**
 * Created by yurkov.ad on 15.04.2021.
 */
public class ListCommand extends BotCommand implements BotMessageSender {
    public ListCommand(String commandIdentifier, String description) {super(commandIdentifier, description);}

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        TaskListValidator taskListValidator = new TaskListValidator();
        SendMessage message = new SendMessage();
        message.setText("Список задач на сегодня: \n\r");
        message.setChatId(chat.getId().toString());

        if(taskListValidator.validate(taskList, absSender, user, message, this.getCommandIdentifier())){
            sendMess(absSender, message);
            taskList.forEach(bt -> {
                message.setText(bt.getBotMessage().getMessge() + ", " + formater.format(bt.getBotMessage().getDateTime()));
                sendMess(absSender, message);
            });
        }
    }
}
