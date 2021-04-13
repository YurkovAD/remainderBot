package com.company.telegram.commands.user;

import com.company.logic.BotTask;
import com.company.utils.BotMessageSender;
import com.company.validators.TaskListValidator;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.text.SimpleDateFormat;
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
                    if(bt.getBotMessage().getMessge().equals(task)) {
                        tmpTasks.add(bt);
                    }
//                } catch(Exception e) {
//
//                }
            });
            //delete
            if(tmpTasks.size() == 0 || tmpTasks.isEmpty()){
                message.setText("В моём списке нет такой задачи!");
                sendMess(absSender, message);
            } else if(tmpTasks.size() == 1 ){
                botTask = tmpTasks.get(0);
                botTask.deleteTask(botTask);
                message.setText("Задание " + botTask.getBotMessage().getMessge() + " удалено!");
                sendMess(absSender, message);
            } else {
                SimpleDateFormat formater = new SimpleDateFormat("hh.mm");
                message.setText("Введиет порядковый номер задания, которое нужно удалить:\r\n");
                tmpTasks.forEach(tmpT -> {
                    message.setText(message.getText() + tmpT.getBotMessage().getMessge() + ", " + formater.format(tmpT.getBotMessage().getDateTime()) + "\r\n");
                });
                sendMess(absSender, message);
            }
        }
    }
}
