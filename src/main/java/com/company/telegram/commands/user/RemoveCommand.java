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
            SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
//            if(containsTask(taskList, task, formater)){
                taskList.forEach(bt -> {
                    String s = bt.getBotMessage().getMessge() + ", " + formater.format(bt.getBotMessage().getDateTime());
                    if(s.equals(task)) {
                        BotTask botTask = bt;
                        botTask.deleteTask(botTask);
                        message.setText("Задание " + botTask.getBotMessage().getMessge() + " удалено!");
                        sendMess(absSender, message);
                        return;
                    }
                });
//            } else {
//                message.setText("В моём списке нет такого задания!");
//                sendMess(absSender, message);
//            }
        }
    }

//    private Boolean containsTask(List<BotTask> taskList, String task, SimpleDateFormat formater) {
//        for(BotTask bt : taskList){
//            String s = bt.getBotMessage().getMessge() + ", " + formater.format(bt.getBotMessage().getDateTime());
//            if(s.equals(task))
//            {return true;}
//        }
//
//        return false;
//    }
}
