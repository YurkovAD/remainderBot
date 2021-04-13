package com.company.telegram.commands.user;

import com.company.logic.BotTask;
import com.company.utils.BotMessageSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Timer;

import static com.company.logic.BotMessage.createBotMessage;
import static com.company.telegram.Bot.taskList;
import static com.company.validator.TaskValidator.validate;

public class RemindCommand extends BotCommand implements BotMessageSender{

    public RemindCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        String task = String.join(" ", strings);
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        System.out.println("taskList before add" + taskList.size());

        if (validate(task, absSender, user,  message, this.getCommandIdentifier())) {
            taskList.add(task);
            System.out.println(taskList.size());
            System.out.println("task " +  task + "added to tasklist");
            BotTask botTask = new BotTask(createBotMessage(task), new Timer(), absSender, message);
            message.setText("Задание " + task + " создано");
            sendMess(absSender, message);
            message.setText("Напоминаю! " + task);
            BotTask.createTask(botTask);
        }
    }
//    private Logger logger = LoggerFactory.getLogger(RemindCommand.class);
}