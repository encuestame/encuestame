package org.encuestame.business.cron;

import org.encuestame.config.startup.EnMePlaceHolderConfigurer;

/**
 * Created by jpicado on 26/06/16.
 */
public class Scheduled {
    public enum ScheduledConst {
    	FOO("");
   	 	public static final String PUBLISH = EnMePlaceHolderConfigurer.getProperty("cron.publishAllScheduled");
   	 	public static final String SPAM = EnMePlaceHolderConfigurer.getProperty("cron.removeSpamComments");
   	 	public static final String NOTIFICATION = EnMePlaceHolderConfigurer.getProperty("cron.sendNotifications");
   	 	public static final String UNCONFIRMED = EnMePlaceHolderConfigurer.getProperty("cron.removeUnconfirmedAccount");

        /**
         *
         * @param notificationAsString
         */
    	ScheduledConst(final String notificationAsString) {}        
    }
}
