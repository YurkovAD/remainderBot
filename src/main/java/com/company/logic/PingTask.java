package com.company.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yurkov.ad on 15.04.2021.
 */
public class PingTask extends TimerTask {
    private String url = "https://google.com";

    public String getUrl() {
        return url;
    }

    public static void pingMe() {
        Timer timer = new Timer();
        timer.schedule(new PingTask(), 100, 86400000);
    }

    @Override
    public void run() {
        try {
            URL url = new URL(getUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            logger.info("Ping {}, OK: response code {}", url.getHost(), connection.getResponseCode());
            connection.disconnect();
        } catch (IOException e) {
            logger.error("Ping FAILED");
            e.printStackTrace();
        }
    }

    private Logger logger = LoggerFactory.getLogger(PingTask.class);
}