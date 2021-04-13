package com.company.telegram.commands.user;

import com.company.logic.BotTask;
import com.company.utils.BotMessageSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static com.company.telegram.commands.user.RemindCommand.taskList;

/**
 * Created by yurkov.ad on 31.03.2021.
 */
public class RemoveCommand extends BotCommand implements BotMessageSender {

    public RemoveCommand(String commandIdentifier, String description) {super(commandIdentifier, description);}

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        //find
        String task = String.join(" ", strings);
        for (BotTask bt : taskList) {
            if(bt.getBotMessage().equals(task)) {

            }
        }
        BotTask botTask = null;

        //delete
        botTask.deleteTask(botTask);
    }
}
