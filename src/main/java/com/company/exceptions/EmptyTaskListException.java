package com.company.exceptions;

/**
 * Created by yurkov.ad on 13.04.2021.
 */
public class EmptyTaskListException extends Exception {
    public EmptyTaskListException(String message) {
        super(message);
    }
}
