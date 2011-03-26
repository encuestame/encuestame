package org.encuestame.business.setup;

import org.apache.log4j.Logger;
import org.encuestame.business.config.EncuestamePlaceHolderConfigurer;
import org.encuestame.business.setup.install.InstallDatabaseOperations;
import org.encuestame.business.setup.install.TypeDatabase;
import org.encuestame.core.mail.MailService;
import org.encuestame.core.util.InternetUtils;
import org.encuestame.persistence.exception.EnMeStartupException;
import org.encuestame.persistence.exception.EnmeFailOperation;
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
    protected Logger log = Logger.getLogger(this.getClass());

    /** Say is app is started. **/
    private static boolean started = false;

    /**
     * Install database operations.
     */
    @Autowired
    public InstallDatabaseOperations install;

    /**
     * Mail service provider.
     */
    @Autowired
    public MailService mailService;

    /**
     * Constructor.
     */
    public ApplicationStartup() {
    }

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
            // verify directory structure.
            if (!DirectorySetupOperations.checkIfExistRootDirectory()) {
                log.info("EnMe: Root directory is missing");
                DirectorySetupOperations.createRootFolder();
            }
            // if email notification is enabled.
            if (EncuestamePlaceHolderConfigurer.getBooleanProperty(
                    "setup.email.notification").booleanValue()) {
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
            if (EncuestamePlaceHolderConfigurer.getBooleanProperty(
                    "setup.check.network").booleanValue()) {
                notifyInternetConnection();
            }
            // check database
            if (install != null) {
                install.initializeDatabase(TypeDatabase
                        .getTypeDatabaseByString(EncuestamePlaceHolderConfigurer
                                .getProperty("datasource.database")));
            } else {
                log.fatal("Install operations is not available");
                throw new EnmeFailOperation(
                        "Install operations is not available");
            }
        } catch (EnmeFailOperation e) {
            log.fatal("Error on Start Up " + e.getMessage());
            throw new EnMeStartupException(e);
        }
    }

    /**
     * Check if exist internet connection. Send pings to popular websites.
     */
    private void notifyInternetConnection() {
        log.info("Checking your internet connection");
        boolean twitter = InternetUtils.pingTwitter();
        boolean facebook = InternetUtils.pingFacebook();
        boolean google = InternetUtils.pingGoogle();
        if (twitter || facebook || google) {
            log.info("## -------------------------------------------- ##");
            log.info("## -- EnMe: Your internet connection is OK !!-- ##");
            log.info("## -------------------------------------------- ##");
        } else {
            log.info("## -------------------------------------------------------------- ##");
            log.info("## -- EnMe: Check your network, internet connection is missing -- ##");
            log.info("## -------------------------------------------------------------- ##");
        }
    }

    /**
     * Set {@link InstallDatabaseOperations}.
     * @param install the install to set
     */
    public void setInstall(final InstallDatabaseOperations install) {
        this.install = install;
    }

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
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
}
