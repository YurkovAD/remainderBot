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

        if (validate(task, absSender, user,  message, this.getCommandIdentifier())) {
            BotTask botTask = new BotTask(createBotMessage(task), new Timer(), absSender, message);
            System.out.println("taskList size before createTask" + taskList.size());
            message.setText("Задание " + task + " создано");
            sendMess(absSender, message);
            message.setText("Напоминаю! " + task);
            BotTask.createTask(botTask);
            System.out.println("taskList size after createTask" + taskList.size());
        }
    }
//    private Logger logger = LoggerFactory.getLogger(RemindCommand.class);
}