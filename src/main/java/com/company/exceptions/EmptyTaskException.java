package com.company.exceptions;

/**
 * Created by yurkov.ad on 08.04.2021.
 */
public class EmptyTaskException extends Exception {
    public EmptyTaskException(String message) {
        super(message);
    }
}
