package com.company.validators;

import com.company.exceptions.EmptyTaskException;
import com.company.exceptions.TaskException;
import com.company.exceptions.TaskTimeException;
import com.company.utils.BotMessageSender;
import com.company.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by yurkov.ad on 09.02.2021.
 */
public class TaskValidator implements BotMessageSender {
    public Boolean validate(String task, AbsSender absSender, User user, SendMessage message, String commandIdentifier) {
        try{
            isNullTask(task);
            validateTaskMessage(task);
            beforeTasktime(task);
            afterTastTime(task);
            return true;
        } catch(EmptyTaskException e) {
            logger.error(String.format ("User {} is trying to set empty task."), user, commandIdentifier);
            message.setText(e.getMessage());
            sendMess(absSender, message);
            return false;
        } catch(TaskTimeException e) {
            logger.error(String.format (e.getMessage()), user, commandIdentifier);
            message.setText(e.getMessage());
            sendMess(absSender, message);
            return false;
        } catch(TaskException e) {
            logger.error(String.format (e.getMessage()), user, commandIdentifier);
            message.setText(e.getMessage());
            sendMess(absSender, message);
            return false;
        } catch (NumberFormatException e) {
            logger.error(String.format ("Wrong format of task!"), user, commandIdentifier);
            message.setText("Неверный формат задачи!");
            sendMess(absSender, message);
            return false;
        } catch (DateTimeException e) {
            logger.error(String.format ("Wrong time!"), user, commandIdentifier);
            message.setText("В сутках всего 24 часа!");
            sendMess(absSender, message);
            return false;
        }
    }

    private void isNullTask(String task) throws EmptyTaskException{
        if (task == null || task.isEmpty()) {
            throw new EmptyTaskException("Задание не может быть пустым!");
        } else {
            logger.info("Task is not null");
        }
    }

    private void beforeTasktime(String task) throws TaskTimeException {
        if (Utils.getDateTime(task).isBefore(LocalDateTime.now())) {
            throw new TaskTimeException("Указано некорректное время!");
        } else {
            logger.info("Before task time is valid");
        }
    }

    private void afterTastTime(String task) throws TaskTimeException, DateTimeException {
        LocalDateTime after = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
        if( Utils.getDateTime(task).isAfter(after)) {
            throw new TaskTimeException("Указано некорректное время!");
        } else {
            logger.info("After task time is valid");
        }
    }

    private void validateTaskMessage(String task) throws TaskException {
        if (task == null || task.equals("") || task.indexOf(',') < 0 || task.indexOf(':') < 0) {
            throw new TaskException("Неверный формат задачи!");
        } else {
            logger.info("Task is valid");
        }
    }

    private static Logger logger = LoggerFactory.getLogger(TaskValidator.class);
}