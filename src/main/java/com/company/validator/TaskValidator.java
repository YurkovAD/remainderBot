package com.company.validator;

import com.company.exceptions.EmptyTaskException;
import com.company.exceptions.TaskException;
import com.company.exceptions.TaskTimeException;
import com.company.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by yurkov.ad on 09.02.2021.
 */
public class TaskValidator {
    public static void validate(String task, AbsSender absSender, User user, SendMessage message, String commandIdentifier) {
        try{
            isNullTask(task, absSender, user, message, commandIdentifier);
            validateTaskMessage(task, absSender, user, message, commandIdentifier);
            beforeTasktime(task, absSender, user, message, commandIdentifier);
            afterTastTime(task, absSender, user, message, commandIdentifier);
        } catch(EmptyTaskException e) {
            logger.error(String.format ("User {} is trying to set empty task."), user, commandIdentifier);
//            message.setText("Задание не может быть пустым!");
            message.setText(e.getMessage());
            sendMess(absSender, message);
            return;
        } catch(TaskTimeException e) {
            logger.error(String.format (e.getMessage()), user, commandIdentifier);
            message.setText(e.getMessage());
            sendMess(absSender, message);
            return;
        } catch(TaskException e) {
            logger.error(String.format (e.getMessage()), user, commandIdentifier);
            message.setText(e.getMessage());
            sendMess(absSender, message);
            return;
        }
//        catch (NumberFormatException e) {
//            logger.error(String.format ("Wrong format of task!"), user, commandIdentifier);
//            message.setText("Неверный формат задачи!");
//            sendMess(absSender, message);
//            return;
//        }
    }

    private static void isNullTask(String task, AbsSender absSender, User user, SendMessage message, String commandIdentifier) throws EmptyTaskException{
        if (task == null || task.isEmpty()) {
            throw new EmptyTaskException("Задание не может быть пустым!");
//            logger.error(String.format ("User {} is trying to set empty task."), user, botCommand.getCommandIdentifier());
//            message.setText("Задание не может быть пустым!");
//            sendMess(absSender, message);
        } else {
            logger.info("Task is not null");
        }
    }

    private static void beforeTasktime(String task, AbsSender absSender, User user, SendMessage message, String commandIdentifier) throws TaskTimeException {
        if (Utils.getDateTime(task).isBefore(LocalDateTime.now())) {
            throw new TaskTimeException("Указано некорректное время!");
        } else {
            logger.info("Task time is valid");
        }
    }

    private static void afterTastTime(String task, AbsSender absSender, User user, SendMessage message, String commandIdentifier) throws TaskTimeException {
        LocalDateTime after = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
        if( Utils.getDateTime(task).isAfter(after)) {
            throw new TaskTimeException("Указано некорректное время!");
        } else {
            logger.info("Task time is valid");
        }
    }

    private static void validateTaskMessage(String task, AbsSender absSender, User user, SendMessage message, String commandIdentifier) throws TaskException {
        if (task == null || task.equals("") || task.indexOf(',') < 0 || task.indexOf(':') < 0) {
            throw new TaskException("Неверный формат задачи!");
        } else {
            logger.info("Task is valid");
        }
    }

    private static void sendMess (AbsSender absSender, SendMessage message) {
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = LoggerFactory.getLogger(TaskValidator.class);
}