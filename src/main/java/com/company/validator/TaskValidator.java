package com.company.validator;

import com.company.exceptions.TaskException;
import com.company.exceptions.TaskTimeException;
import com.company.utils.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by yurkov.ad on 09.02.2021.
 */
public class TaskValidator {
    public static void validate(String task) throws TaskException, TaskTimeException {
        validateTaskMessage(task);
        beforeTasktime(task);
        afterTastTime(task);
    }

    private static void beforeTasktime(String task) throws TaskTimeException {
        if (Utils.getDateTime(task).isBefore(LocalDateTime.now())) {
            throw new TaskTimeException("Указано некорректное время!");
        } else {
            System.out.println("Task time is valid");
        }
    }

    private static void afterTastTime(String task) throws TaskTimeException {
        LocalDateTime after = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
        if( Utils.getDateTime(task).isAfter(after)) {
            throw new TaskTimeException("Указано некорректное время!");
        } else {
            System.out.println("Task time is valid");
        }
    }

    private static void validateTaskMessage(String task) throws TaskException {
        if (task == null || task.equals("") || task.indexOf(',') < 0 || task.indexOf(':') < 0) {
            throw new TaskException("Неверный формат задачи!");
        } else {
            System.out.println("Task is valid");
        }
    }
}