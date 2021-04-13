package com.company.validators;

import com.company.exceptions.EmptyTaskListException;
import com.company.logic.BotTask;
import com.company.utils.BotMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

/**
 * Created by yurkov.ad on 13.04.2021.
 */
public class TaskListValidator implements BotMessageSender {
    public Boolean validate(List<BotTask> taskList, String task, AbsSender absSender, User user, SendMessage message, String commandIdentifier) {
        try{
            isNullTaskList(taskList);
            return true;
         } catch (EmptyTaskListException e) {
             logger.error(String.format ("User's {} taskList is empty."), user, commandIdentifier);
             message.setText(e.getMessage());
             sendMess(absSender, message);
             return false;
        }
    }
    private void isNullTaskList (List<BotTask> taskList) throws EmptyTaskListException{
        if(taskList == null || taskList.isEmpty()){
            throw new EmptyTaskListException("Список заданий пуст!");
        } else {
            logger.info("TaskList is not empty.");
        }
    }

    private static Logger logger = LoggerFactory.getLogger(TaskListValidator.class);
}
