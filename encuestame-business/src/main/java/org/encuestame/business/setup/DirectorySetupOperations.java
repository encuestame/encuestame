package org.encuestame.business.setup;

import java.io.File;

import org.apache.log4j.Logger;
import org.encuestame.business.config.EncuestamePlaceHolderConfigurer;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeStartupException;
import org.encuestame.persistence.exception.EnmeFailOperation;

/**
 * Directory Operations.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 20, 2011
 */
public class DirectorySetupOperations {

    protected static Logger log = Logger
            .getLogger(DirectorySetupOperations.class);
    private final static String DEFAULT_ROOT_FOLDER = "encuestameXY";
    private final static String PICTURES_DEFAULT_FOLDER = "pictures";
    private final static String PROFILES_DEFAULT_FOLDER = "profiles";
    private final static String INDEXES_DEFAULT_FOLDER = "indexes";

    /**
     *
     */
    public DirectorySetupOperations() {
    }

    /**
     * Check If Exist Root Directory.
     *
     * @return
     * @throws EnmeFailOperation
     */
    public static boolean checkIfExistRootDirectory() throws EnmeFailOperation {
        final File rootDir = new File(
                DirectorySetupOperations.getRootDirectory());
        log.debug("root directory " + rootDir);
        DirectorySetupOperations.checkIfStructureDirectoryExist(rootDir,
                Boolean.TRUE);
        return rootDir.exists();
    }

    /**
     *
     * @param createIfNotExist
     * @return
     * @throws EnmeFailOperation
     */
    public static boolean checkIfExistProfilesDirectory(boolean createIfNotExist)
            throws EnmeFailOperation {
        final File rootDir = new File(
                DirectorySetupOperations.getProfilesDirectory());
        log.debug("profile directory " + rootDir);
        if (createIfNotExist) {
            boolean indexes = rootDir.mkdir();
            if (!indexes) {
                log.error("Encuestame Directory, not able to create picture folder");
                throw new EnmeFailOperation(
                        "EnMe: not able to create index folder");
            }
        }
        return rootDir.exists();
    }

    /**
     *
     * @param createIfNotExist
     * @return
     * @throws EnmeFailOperation
     */
    public static boolean checkIfExistPicturesDirectory(boolean createIfNotExist)
            throws EnmeFailOperation {
        final File rootDir = new File(
                DirectorySetupOperations.getPictureDirectory());
        log.debug("picture directory " + rootDir);
        if (createIfNotExist) {
            boolean indexes = rootDir.mkdir();
            if (!indexes) {
                log.error("Encuestame Directory, not able to create picture folder");
                throw new EnmeFailOperation(
                        "EnMe: not able to create index folder");
            }
        }
        return rootDir.exists();
    }

    /**
     *
     * @param createIfNotExist
     * @return
     * @throws EnmeFailOperation
     */
    public static boolean checkIfExistIndexedDirectory(boolean createIfNotExist)
            throws EnmeFailOperation {
        final File rootDir = new File(
                DirectorySetupOperations.getIndexesDirectory());
        log.debug("indexes directory " + rootDir);
        if (createIfNotExist) {
            boolean indexes = rootDir.mkdir();
            if (!indexes) {
                log.error("Encuestame Directory, not able to create index folder");
                throw new EnmeFailOperation(
                        "EnMe: not able to create index folder");
            }
        }
        return rootDir.exists();
    }

    /**
     * Get Root Folder.
     *
     * @return
     */
    private static String getRootDirectory() {
        String root = EncuestamePlaceHolderConfigurer
                .getProperty("dir.data.warehouse");
        if (root == null) {
            root = DirectorySetupOperations.DEFAULT_ROOT_FOLDER;
        }

        if (!root.endsWith("/")) {
            root = root + "/";
        }
        log.debug("getRootFolder " + root);
        return root;
    }

    /**
     * Get picture directory path.
     *
     * @return
     */
    private static String getPictureDirectory() {
        String picture = EncuestamePlaceHolderConfigurer
                .getProperty("dir.data.picture");
        if (picture == null) {
            picture = getRootDirectory()
                    + DirectorySetupOperations.PICTURES_DEFAULT_FOLDER;
        } else {
            picture = getRootDirectory() + picture;
        }
        log.debug("getPictureDirectory " + picture);
        return picture;
    }

    /**
     * Get profile directory path.
     *
     * @return
     */
    private static String getProfilesDirectory() {
        String profiles = EncuestamePlaceHolderConfigurer
                .getProperty("dir.data.profiles");
        if (profiles == null) {
            profiles = DirectorySetupOperations.PROFILES_DEFAULT_FOLDER;
        }  else {
            profiles = getRootDirectory() + profiles;
        }
        log.debug("getProfilesDirectory " + profiles);
        return profiles;
    }

    /**
     * Get indexes directory.
     *
     * @return
     */
    private static String getIndexesDirectory() {
        String index = EncuestamePlaceHolderConfigurer
                .getProperty("dir.data.index");
        if (index == null) {
            index = getRootDirectory()
                    + DirectorySetupOperations.INDEXES_DEFAULT_FOLDER;
        } else {
            index = getRootDirectory() + index;
        }
        log.debug("getIndexesDirectory " + index);
        return index;
    }

    /**
     * Create root folder and required
     *
     * @throws EnmeFailOperation
     *             exception on try to create directory.
     */
    public static void createRootFolder() throws EnmeFailOperation {
        final File rootFolder = new File(
                DirectorySetupOperations.getRootDirectory());
        boolean success = rootFolder.mkdirs();
        log.info("created root folder");
        if (success && rootFolder.canWrite() && rootFolder.isDirectory()) {
            DirectorySetupOperations.checkIfStructureDirectoryExist(rootFolder,
                    true);
        } else {
            throw new EnmeFailOperation("EnMe: not able to create ROOT folder");
        }
    }

    /**
     * Create required directory structure.
     *
     * @param rootFolder
     * @throws EnMeStartupException
     */
    private static void checkIfStructureDirectoryExist(final File rootFolder,
            final boolean createIfNoExist) throws EnmeFailOperation {
        if (rootFolder.exists() && rootFolder.isDirectory()) {
            if (!DirectorySetupOperations
                    .checkIfExistIndexedDirectory(createIfNoExist)) {
                log.debug("EnMe: index folder not found, creating one...");
            }
            if (!DirectorySetupOperations
                    .checkIfExistProfilesDirectory(createIfNoExist)) {
                log.debug("EnMe: index folder not found, creating one...");
            }
            if (!DirectorySetupOperations
                    .checkIfExistPicturesDirectory(createIfNoExist)) {
                log.debug("EnMe: index folder not found, creating one...");
            }
        } else {
            throw new EnmeFailOperation("EnMe: Root directory not found");
        }
    }
}
