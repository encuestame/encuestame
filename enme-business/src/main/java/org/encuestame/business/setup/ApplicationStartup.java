/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.setup;

import org.apache.log4j.Logger;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.service.DirectorySetupOperations;
import org.encuestame.core.service.imp.MailServiceOperations;
import org.encuestame.persistence.exception.EnMeStartupException;
import org.encuestame.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Manages encuestame application startup.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 15, 2011
 */
public class ApplicationStartup implements StartupProcess {

    /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /** Say is app is started. **/
    private static boolean started = false;

    /**
     * Mail service provider.
     */
    @Autowired
    private MailServiceOperations mailService;

    /**
     * Constructor.
     */
    public ApplicationStartup() {}

    /**
     * Check if app started.
     * @return return is system is started.
     */
    public static boolean isStarted() {
        return started;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.business.setup.StartupProcess#startProcess()
     */
    public void startProcess() throws EnMeStartupException {
        // check if root directory exist
        try {
            DirectorySetupOperations.validateInternalStructureDirectory(true);
            DirectorySetupOperations.createConfileFile();
            // if email notification is enabled.
            if (EnMePlaceHolderConfigurer.getBooleanProperty("setup.email.notification").booleanValue()) {
                final StringBuilder startupMessage = new StringBuilder();
                startupMessage.append("startup date [");
                startupMessage.append(DateUtil.getCurrentFormatedDate());
                startupMessage.append("]");
                // NOTE: add more information
                // version.
                // host information
                // etc etc.
                mailService.sendStartUpNotification(startupMessage.toString());
            }
            // check internet connection
            //if (EnMePlaceHolderConfigurer.getBooleanProperty(
            //        "setup.check.network").booleanValue()) {
            //    notifyInternetConnection();
            //}
            // check database
            //if (this.install != null) {
            //    final String typeDatabase = EnMePlaceHolderConfigurer.getConfigurationManager().getProperty("database.type");
            //    this.install.initializeDatabase(TypeDatabase
            //            .getTypeDatabaseByString(typeDatabase));
            //} else {
            //   log.fatal("Install operations is not available");
            //    throw new EnmeFailOperation(
            //            "Install operations is not available");
            //}
        } catch (Exception e) {
            log.fatal("Error on Start Up: " + e.getMessage());
            e.printStackTrace();
            throw new EnMeStartupException(e);
        }
    }

    /**
     * Check if exist internet connection. Send pings to popular websites.
     */
//    private void notifyInternetConnection() {
//        log.info("Checking your internet connection");
//        boolean twitter = InternetUtils.pingTwitter();
//        boolean facebook = InternetUtils.pingFacebook();
//        boolean google = InternetUtils.pingGoogle();
//        if (twitter || facebook || google) {
//            log.info("## -------------------------------------------- ##");
//            log.info("## -- EnMe: Your internet connection is OK !!-- ##");
//            log.info("## -------------------------------------------- ##");
//        } else {
//            log.info("## -------------------------------------------------------------- ##");
//            log.info("## -- EnMe: Check your network, internet connection is missing -- ##");
//            log.info("## -------------------------------------------------------------- ##");
//        }
//    }


    /*
     * (non-Javadoc)
     * @see org.encuestame.business.setup.StartupProcess#displayVersionOnStartup()
     */
    public void displayVersionOnStartup() {
        // TODO Auto-generated method stub
    }

    /**
     * @param mailService
     *            the mailService to set
     */
    public void setMailService(MailServiceOperations mailService) {
        this.mailService = mailService;
    }
}
