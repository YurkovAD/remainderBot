package com.company.telegram.botTools;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yurkov.ad on 08.02.2021.
 */
public class BotTask extends TimerTask {
    private BotMessage botMessage;
    private Timer timer;

    public BotTask(BotMessage botMessage, Timer timer) {
        this.botMessage = botMessage;
        this.timer = timer;
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

    @Override
    public void run() {
        System.out.println(botMessage.getMessge());
        System.out.println("task " + botMessage.getMessge() + " was invoked at " + LocalDateTime.now());
        timer.cancel();
    }

    public static void createTask (BotTask botTask) {
        if (botTask.getBotMessage() != null) {
            botTask.getTimer().schedule(botTask, botTask.getBotMessage().getDateTime());
        }
    }

    public static void updateTask (BotTask botTaskOld, BotTask botTasknew) {
        deleteTask(botTaskOld);
        createTask(botTasknew);
    }

    public static void deleteTask(BotTask botTask) {
        botTask.getTimer().cancel();
    }
}