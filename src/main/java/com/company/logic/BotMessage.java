package com.company.logic;

import com.company.exceptions.TaskException;
import com.company.exceptions.TaskTimeException;
import com.company.utils.Utils;
import com.company.validator.TaskValidator;

import java.time.ZoneId;
import java.util.Date;

/**
 * Created by yurkov.ad on 08.02.2021.
 */
public class BotMessage {
    private String messge;
    private Date dateTime;

    public BotMessage(String messge, Date dateTime) {
        this.messge = messge;
        this.dateTime = dateTime;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "BotMessage{" +
                "messge='" + messge + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

    public static BotMessage createBotMessage(String task) {
        try {
            TaskValidator.validate(task);
            return new BotMessage(Utils.getTaskMessage(task), Date.from(Utils.getDateTime(task).atZone(ZoneId.systemDefault()).toInstant()));
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат задачи!");
            return null;
        } catch (TaskException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (TaskTimeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}