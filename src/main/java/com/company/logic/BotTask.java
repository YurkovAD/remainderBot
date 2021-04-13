package com.company.logic;

import com.company.utils.BotMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import static com.company.telegram.commands.user.RemindCommand.taskList;

/**
 * Created by yurkov.ad on 08.02.2021.
 */
public class BotTask extends TimerTask implements BotMessageSender{
    private BotMessage botMessage;
    private Timer timer;
    private AbsSender absSender;
    private SendMessage message;

    public BotTask(BotMessage botMessage, Timer timer, AbsSender absSender, SendMessage message) {
        this.botMessage = botMessage;
        this.timer = timer;
        this.absSender = absSender;
        this.message = message;
    }

    public BotMessage getBotMessage() {
        return botMessage;
    }

    public void setBotMessage(BotMessage botMessage) {
        this.botMessage = botMessage;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public AbsSender getAbsSender() {
        return absSender;
    }

    public void setAbsSender(AbsSender absSender) {
        this.absSender = absSender;
    }

    public SendMessage getMessage() {
        return message;
    }

    public void setMessage(SendMessage message) {
        this.message = message;
    }

    @Override
    public void run() {
        logger.info("task " + botMessage.getMessge() + " was invoked at " + LocalDateTime.now());
        sendMess(absSender, message);
        timer.cancel();
        taskList.remove(this);
//        System.out.println("taskList after /remind " + taskList.size());
    }

    public void createTask (BotTask botTask) {
        if (botTask.getBotMessage() != null) {
            botTask.getTimer().schedule(botTask, botTask.getBotMessage().getDateTime());
            taskList.add(botTask);
//            System.out.println("taskList after createTask  " + taskList.size());
        }
    }

    public void updateTask (BotTask botTaskOld, BotTask botTasknew) {
        deleteTask(botTaskOld);
        createTask(botTasknew);
    }

    public void deleteTask(BotTask botTask) {
        botTask.getTimer().cancel();
        taskList.remove(botTask);
    }

    private Logger logger = LoggerFactory.getLogger(BotTask.class);
}