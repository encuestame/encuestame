/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.service;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.core.files.PathUtil;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.exception.EnMeStartupException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Directory Operations.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 20, 2011
 */
public class DirectorySetupOperations {

    /** Log. **/
    private static Logger log = Logger
            .getLogger(DirectorySetupOperations.class);
    /** Default root directory name. */
    private static final String DEFAULT_ROOT_FOLDER = "encuestame-store";
    /** Default picture directory name. **/
    private static final String PICTURES_DEFAULT_FOLDER = "pictures";
    /** Default profiles directory name. **/
    private static final String PROFILES_DEFAULT_FOLDER = "profiles";
    /** Default indexes directory name. **/
    private static final String INDEXES_DEFAULT_FOLDER = "indexes";

    /**
     * Constructor.
     */
    public DirectorySetupOperations() {}

    /**
     * Check if ROOT directory exist, by default is not created.
     * @return if the directoy exist or not.
     * @throws EnmeFailOperation exception if exist issues on create directory.
     */
    public static boolean checkIfExistRootDirectory() throws EnmeFailOperation {
        final File rootDir = new File(DirectorySetupOperations.getRootDirectory());
        log.debug("EnMe: Root directory: " + rootDir);
        return rootDir.exists();
    }

    /**
     * Create config file.
     * encuestame-config.xml
     * @throws IOException
     */
    public static void createConfileFile() throws IOException {
        log.debug("createConfileFile");
        final StringBuffer config = new StringBuffer(getRootDirectory());
        config.append(PathUtil.configFileName);
        final File configFile = new File(config.toString());
        if (!configFile.exists()) {
            final Resource resource = new ClassPathResource("encuestame-config-sample.xml");
            log.debug("createConfileFile resource"+resource);
            log.debug("createConfileFile configFile"+configFile.getAbsolutePath());
            final File sourceFile = resource.getFile();
            log.debug("createConfileFile sourceFile"+sourceFile.getAbsolutePath());
            EnMeUtils.copy(sourceFile, configFile);
            EnMePlaceHolderConfigurer.getConfigurationManager().reloadConfigFile();
        }
    }

    /**
     * Check if profiles directory exist, by default is not created.
     * @param createIfNotExist true to create if not exist.
     * @return if the directoy exist or not.
     * @throws EnmeFailOperation exception if exist issues on create directory.
     */
    public static boolean checkIfProfilesDirectoryExist(boolean createIfNotExist)
            throws EnmeFailOperation {
        final File rootDir = new File(
                DirectorySetupOperations.getProfilesDirectory());
        if (createIfNotExist  && !rootDir.exists()) {
            boolean indexes = rootDir.mkdirs();
            if (!indexes) {
                log.error("Encuestame Directory, not able to create picture folder");
                throw new EnmeFailOperation(
                        "EnMe: not able to create index folder");
            }
        }
        return rootDir.exists();
    }

    /**
     * Check if pictures directory exist, by default is not created.
     * @param createIfNotExist true to create if not exist.
     * @return if the directoy exist or not.
     * @throws EnmeFailOperation exception if exist issues on create directory.
     */
    public static boolean checkIfPicturesDirectoryExist(boolean createIfNotExist)
            throws EnmeFailOperation {
        final File rootDir = new File(
                DirectorySetupOperations.getPictureDirectory());
        if (createIfNotExist && !rootDir.exists()) {
            boolean indexes = rootDir.mkdirs();
            if (!indexes) {
                log.error("Encuestame Directory, not able to create picture folder");
                throw new EnmeFailOperation(
                        "EnMe: not able to create index folder");
            }
        }
        return rootDir.exists();
    }

    /**
     * Check if indexed directory exist, by default is not created.
     * @param createIfNotExist true to create if not exist.
     * @return if the directoy exist or not.
     * @throws EnmeFailOperation exception if exist issues on create directory.
     */
    public static boolean checkIfIndexedDirectoryExist(boolean createIfNotExist)
            throws EnmeFailOperation {
        final File indexedDir = new File(DirectorySetupOperations.getIndexesDirectory());
        //if autocreate is enabled and directory not exist
        if (createIfNotExist && !indexedDir.exists()) {
            boolean indexes = indexedDir.mkdirs();
            if (!indexes) {
                log.error("Encuestame Directory, not able to create index folder");
                throw new EnmeFailOperation(
                        "EnMe: not able to create index folder");
            }
        }
        return indexedDir.exists();
    }

    /**
     * Get root directory path.
     * @return real path of roor directory.
     */
    public static String getRootDirectory() {
        String root = EnMePlaceHolderConfigurer.getProperty("encuestame.home");
        if (root == null) {
            root = System.getProperty("user.home")+ "/" + DirectorySetupOperations.DEFAULT_ROOT_FOLDER;
        }
        log.debug("root directory: "+root);
        if (!root.endsWith("/")) {
            root = root + "/";
        }
        log.debug("getRootFolder:{" + root);
        return root;
    }

    /**
     * Get picture directory real path.
     * @return picture path.
     */
    public static String getPictureDirectory() {
        final StringBuffer picture = new StringBuffer(getRootDirectory());
        picture.append(DirectorySetupOperations.PICTURES_DEFAULT_FOLDER);
        log.debug("getPictureDirectory:{ " + picture);
        return picture.toString();
    }

    /**
     * Get profile directory path.
     * @return real path of directory path.
     */
    public static String getProfilesDirectory() {
        final StringBuffer profiles = new StringBuffer(getRootDirectory());
        profiles.append(DirectorySetupOperations.PROFILES_DEFAULT_FOLDER);
        log.debug("getProfilesDirectory:{" + profiles);
        return profiles.toString();
    }

    /**
     *
     * @param accountId
     * @return
     */
    public static String getProfilesDirectory(final String accountId) {
        final StringBuilder path = new StringBuilder(DirectorySetupOperations.getProfilesDirectory());
        path.append("/");
        path.append(accountId.toString());
        path.append("/");
        log.debug("Path builded "+path.toString());
        return path.toString();
    }

    /**
     * Get indexes directory path.
     * @return indexed real path.
     */
    public static String getIndexesDirectory() {
        final String index = getRootDirectory() + DirectorySetupOperations.INDEXES_DEFAULT_FOLDER;
        log.debug("getIndexesDirectory " + index);
        return index;
    }

    /**
     * Create root folder and required.
     * @throws EnmeFailOperation
     *             exception on try to create directory.
     */
    public static void createRootFolder() throws EnmeFailOperation {
        final File rootFolder = new File(DirectorySetupOperations.getRootDirectory());
        if (!rootFolder.exists()) {
            boolean success = rootFolder.mkdirs();
            log.info("created root folder");
            if (success && rootFolder.canWrite() && rootFolder.isDirectory()) {
                //TODO:
            } else {
                throw new EnmeFailOperation("EnMe: not able to create ROOT folder");
            }
        }
    }

    /**
     * Check the installation folder.
     * @return
     */
    public static Boolean checkInstallationFolder(){
        final File rootFolder = new File(DirectorySetupOperations.getRootDirectory());
        log.debug("checkInstallationFolder "+rootFolder.exists());
        return rootFolder.exists();
    }

    /**
     * Create required directory structure.
     * @param rootFolder {@link File} reference or root directory.
     * @throws EnMeStartupException
     */
    public static void validateInternalStructureDirectory(final boolean createIfNoExist) throws EnmeFailOperation {
            log.debug("EnMe: validateInternalStructureDirectory."+createIfNoExist);
            if (!DirectorySetupOperations.checkIfIndexedDirectoryExist(createIfNoExist)) {
                log.debug("EnMe: index folder not found, creating one...");
            }
            if (!DirectorySetupOperations
                    .checkIfProfilesDirectoryExist(createIfNoExist)) {
                log.debug("EnMe: index folder not found, creating one...");
            }
            if (!DirectorySetupOperations
                    .checkIfPicturesDirectoryExist(createIfNoExist)) {
                log.debug("EnMe: index folder not found, creating one...");
            }
    }
}
