package com.company.telegram.commands.user;

import com.company.logic.BotTask;
import com.company.utils.BotMessageSender;
import com.company.validators.TaskListValidator;
import com.company.validators.TaskValidator;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static com.company.logic.BotMessage.createBotMessage;

public class RemindCommand extends BotCommand implements BotMessageSender {
    public static List<BotTask> taskList = new ArrayList<>();

    public RemindCommand(String commandIdentifier, String description) {super(commandIdentifier, description);}

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        TaskValidator taskValidator = new TaskValidator();
        TaskListValidator listValidator = new TaskListValidator();
        String task = String.join(" ", strings);
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        if (taskValidator.validate(task, absSender, user,  message, this.getCommandIdentifier()) && listValidator.isDublicateTask(taskList, task, absSender, user,  message, this.getCommandIdentifier())) {
            BotTask botTask = new BotTask(createBotMessage(task), new Timer(), absSender, message);
            message.setText("Задание " + task + " создано");
            sendMess(absSender, message);
            message.setText("Напоминаю! " + task);
            botTask.createTask(botTask);
        }
    }
}