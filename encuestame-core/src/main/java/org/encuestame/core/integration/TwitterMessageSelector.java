package org.encuestame.core.integration;

import org.springframework.integration.Message;
import org.springframework.integration.core.MessageSelector;

public class TwitterMessageSelector implements MessageSelector {

    private static final int MILLI_SECS_IN_MIN = 60 * 1000;
    private static final int MAX_MESSAGES = 6;

    private int messagesLimitPerMinute = MAX_MESSAGES;
    private long limitingMinute = System.currentTimeMillis()
            + MILLI_SECS_IN_MIN;


    public boolean accept(Message<?> message) {
        if (System.currentTimeMillis() < limitingMinute) {
            if (messagesLimitPerMinute > 0) {
                messagesLimitPerMinute--;
                return true;
            } else {
                return false;
            }
        } else {
            limitingMinute += MILLI_SECS_IN_MIN;
            messagesLimitPerMinute = MAX_MESSAGES;
            return false;
        }
    }

}
