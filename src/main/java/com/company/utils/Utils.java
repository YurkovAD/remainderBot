package com.company.utils;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by yurkov.ad on 27.01.2021.
 */
public class Utils {
    /**
     * Формирование имени пользователя
     * @param msg сообщение
     */
    public static String getUserName(Message msg) {
        return getUserName(msg.getFrom());
    }

    /**
     * Формирование имени пользователя. Если заполнен никнейм, используем его. Если нет - используем фамилию и имя
     * @param user пользователь
     */
    public static String getUserName(User user) {
        return (user.getUserName() != null) ? user.getUserName() : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    public static LocalDateTime getDateTime(String task) throws NumberFormatException {

        return LocalDateTime.of(LocalDate.now(), getTime(task));
    }

    public static LocalTime getTime(String task) throws NumberFormatException {

        return toLocalTime(convertToTime(getDate(task)));
    }

    private static String getDate(String task) {

        return task.substring(task.indexOf(',') + 1);
    }

    public static String getTaskMessage(String task) {

        return task.substring(0, task.indexOf(','));
    }

    private static MyTime convertToTime (String sDate) throws NumberFormatException {
        int h, min;
        h = Integer.parseInt(sDate.substring(1, sDate.indexOf(':')));
        min = Integer.parseInt(sDate.substring(sDate.indexOf(':') + 1));

        return new MyTime(h, min);
    }

    private static LocalTime toLocalTime (MyTime mTime) {

        return LocalTime.of(mTime.getHour(), mTime.getMinute());
    }

}
