package com.company.telegram.commands.user;

import com.company.logic.BotTask;
import com.company.utils.BotMessageSender;
import com.company.validators.TaskListValidator;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;

import static com.company.telegram.commands.user.RemindCommand.taskList;

/**
 * Created by yurkov.ad on 31.03.2021.
 */
public class RemoveCommand extends BotCommand implements BotMessageSender {

    public RemoveCommand(String commandIdentifier, String description) {super(commandIdentifier, description);}

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        TaskListValidator taskListValidator = new TaskListValidator();
        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());
        String task = String.join(" ", strings);

        if(taskListValidator.validate(taskList, task, absSender, user, message, this.getCommandIdentifier())) {
            //find
            BotTask botTask = null;
            List<BotTask> tmpTasks = new ArrayList<>();
            taskList.forEach(bt -> {
//                try {
                    if(bt.getBotMessage().equals(task)) {
                        tmpTasks.add(bt);
                    }
//                } catch(Exception e) {
//
//                }
            });
            //delete
            if(tmpTasks.size() == 0 || tmpTasks.isEmpty()){
                message.setText("В моем списке нет такой задачи");
                sendMess(absSender, message);
            } else if(tmpTasks.size() == 1 ){
                botTask.deleteTask(tmpTasks.get(0));
                message.setText("Задание удалено");
                sendMess(absSender, message);
            }
        }
    }
}
