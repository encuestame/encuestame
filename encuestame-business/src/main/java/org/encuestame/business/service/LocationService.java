/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.business.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.business.service.imp.ILocationService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.encuestame.persistence.domain.GeoPointType;
import org.encuestame.persistence.domain.GeoPointFolderType;
import org.encuestame.persistence.domain.Status;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.springframework.stereotype.Service;

/**
 * Location Service.
 * @author Picado, Juan juanATencuestame.org
 * @since May 15, 2010 8:17:15 PM
 * @version $Id: $
 */
@Service
public class LocationService  extends AbstractBaseService implements ILocationService{

    /** Log. **/
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Create Cat LocationType.
     * @param locatTypeBean {@link UnitLocationTypeBean}
     * @return locatTypeBean type bean
     * @throws EnMeExpcetion exception
     */
    public UnitLocationTypeBean createCatLocationType(
            final UnitLocationTypeBean locatTypeBean, final String username) throws EnMeExpcetion {
        if (locatTypeBean != null) {
            try {
                final GeoPointType locationTypeDomain = new GeoPointType();
                locationTypeDomain.setLocationTypeDescription(locatTypeBean
                        .getLocTypeDesc());
                locationTypeDomain.setLocationTypeLevel(locatTypeBean
                        .getLevel());
                locationTypeDomain.setUsers(getUser(username).getAccount());
                getCatLocationTypeDao().saveOrUpdate(locationTypeDomain);
                locatTypeBean.setIdLocType(locationTypeDomain
                        .getLocationTypeId());
            } catch (Exception e) {
                throw new EnMeExpcetion(e);
            }
            return locatTypeBean;
        } else {
            throw new EnMeExpcetion("Cat Location Type is null");
        }
    }

    /**
     * Update Cat Location.
     * @param locationBean locationBean
     * @param username username
     * @throws EnMeDomainNotFoundException
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void updateCatLocation(final UnitLocationBean locationBean, final String username) throws EnMeDomainNotFoundException, EnMeExpcetion{
       final GeoPoint catLocation =  getLocation(locationBean.getId(), username);
        if (catLocation!=null){
            catLocation.setLocationStatus(Status.valueOf(locationBean.getStatus()));
            catLocation.setLocationDescription(locationBean.getName());
            catLocation.setLocationLatitude(locationBean.getLat());
            catLocation.setLocationLongitude(locationBean.getLng());
            getGeoPointDao().saveOrUpdate(catLocation);
        }else{
            throw new EnMeExpcetion("location not found");
        }
   }


    /**
     * Update Cat Location Type.
     * @param locationTypeBean locationTypeBean
     * @throws EnMeExpcetion EnMeExpcetion
     * @throws EnMeDomainNotFoundException
     */
    public void updateCatLocationType(final UnitLocationTypeBean locationTypeBean) throws EnMeExpcetion, EnMeDomainNotFoundException{
        final GeoPointType catLocationType = getCatLocationTypeDao().getLocationById(locationTypeBean.getIdLocType());
        if (catLocationType!=null){
            catLocationType.setLocationTypeDescription(locationTypeBean.getLocTypeDesc());
            catLocationType.setLocationTypeLevel(locationTypeBean.getLevel());
            getCatLocationTypeDao().saveOrUpdate(catLocationType);
        }
        else{
            throw new EnMeDomainNotFoundException("location type not found");
        }
    }

    /**
     * Create Cat Location.
     * @param location {@link LocationBean}
     * @throws EnMeExpcetion exception
     */
    public UnitLocationBean createCatLocation(final UnitLocationBean location, final String username) throws EnMeExpcetion{
        if (location != null){
            try{
                final GeoPoint catLocationDomain = new GeoPoint();
                catLocationDomain.setLocationDescription(location.getName());
                catLocationDomain.setLocationStatus(Status.ACTIVE);
                catLocationDomain.setLocationLatitude(location.getLat());
                catLocationDomain.setAccount(getUser(username).getAccount());
                catLocationDomain.setLocationLongitude(location.getLng());
                if(location.getTidtype() != null){
                    catLocationDomain.setTidtype(getCatLocationTypeDao().getLocationById(location.getTidtype()));
                }
                getGeoPointDao().saveOrUpdate(catLocationDomain);
                location.setId(catLocationDomain.getLocateId());
                createNotification(NotificationEnum.LOCATION_NEW, location.getName() +" is created.", catLocationDomain.getAccount());
            } catch (Exception e) {
                throw new EnMeExpcetion(e);
            }
            return location;
        } else {
            throw new EnMeExpcetion("location info not found");
        }
    }

    /**
     * Create Location Folder.
     * @param locationFolder {@link UnitLocationFolder}
     * @return {@link UnitLocationFolder}.
     * @throws EnMeDomainNotFoundException
     */
    public UnitLocationFolder createLocationFolder(final UnitLocationFolder locationFolder, final String username) throws EnMeDomainNotFoundException{
        final GeoPointFolder catLocationFolder = new GeoPointFolder();
        catLocationFolder.setFolderType(GeoPointFolderType.valueOf(locationFolder.getType()));
        catLocationFolder.setLocationFolderName(locationFolder.getName());
        catLocationFolder.setAccount(getUser(username).getAccount());
        getGeoPointDao().saveOrUpdate(catLocationFolder);
        locationFolder.setId(catLocationFolder.getLocationFolderId());
        createNotification(NotificationEnum.LOCATION_FOLDER_NEW, "New Folder "+locationFolder.getName() +" is created.", catLocationFolder.getAccount());
        return locationFolder;
    }

    /**
     * Assign Location to Location Folder.
     * @param location
     */
    public void assignLocationToLocationFolder(final GeoPoint location, final GeoPointFolder catLocationFolder){
            location.setCatLocationFolder(catLocationFolder);
            getGeoPointDao().saveOrUpdate(location);
    }

    /**
     * Retrieve Location Folders by User.
     * @param currentName
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitLocationFolder> retrieveLocationFolderByUser(final String currentUserName) throws EnMeDomainNotFoundException{
        return ConvertDomainBean.convertListToUnitLocationFolderBean(getGeoPointDao()
                                .getLocationFolders(getPrimaryUser(currentUserName)));
    }

    /**
     * Retrieve Location Sub Folders by User.
     * @param currentName
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitLocationFolder> retrieveLocationSubFolderByUser(final Long locationFolderId, final String currentUserName) throws EnMeDomainNotFoundException{
        return ConvertDomainBean.convertListToUnitLocationFolderBean(getGeoPointDao()
                                .getLocationFoldersByLocationFolderId(locationFolderId, getPrimaryUser(currentUserName)));
    }

    /**
     * Retrieve Locations Folder Items by Folder Id and User Id.
     * @param locationFolderId location folder id
     * @param username username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitLocationBean> retrieveLocationFolderItemsById(final Long locationFolderId, final String username) throws EnMeDomainNotFoundException{
        return ConvertDomainBean.convertListToUnitLocationBean(getGeoPointDao()
                                .getLocationByFolder(locationFolderId, getPrimaryUser(username)));
    }

    /**
     * Retrieve Locations Items by Username
     * @param username username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public List<UnitLocationBean> retrieveLocationItemsByUsername(final String username) throws EnMeDomainNotFoundException{
        return ConvertDomainBean.convertListToUnitLocationBean(getGeoPointDao()
                                .getLocationByUser(getPrimaryUser(username)));
    }

    /**
     * Get Location Item.
     * @param locationId location id
     * @param username username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public UnitLocationBean getLocationItem(final Long locationId, final String username) throws EnMeDomainNotFoundException{
        return ConvertDomainBean.convertLocationToBean(getLocation(locationId, username));
    }

    /**
     * Get Folder Location Detail.
     * @param folderLocationId folder location  Id.
     * @param username username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    public UnitLocationFolder getFolderLocation(final Long folderLocationId, final String username) throws EnMeDomainNotFoundException{
        return ConvertDomainBean.convertCatLocationFolderDomainToBean(getGeoPointDao()
                                .getLocationFolderByIdAndUserId(folderLocationId, getPrimaryUser(username)));
    }

    /**
     * Update Location Map.
     * @param Latitude
     * @param Longitude
     * @param locationId
     * @param username
     * @throws EnMeExpcetion
     * @throws EnMeDomainNotFoundException
     */
    public void updateLocationMap(final UnitLocationBean locationBean, final Long locationId, final String username) throws EnMeExpcetion, EnMeDomainNotFoundException{
        final GeoPoint location = getLocation(locationId, username);
        log.info("location map location "+location);
        if(location == null){
            throw new EnMeExpcetion("location not found");
        }
        else{
            location.setLocationAccuracy(locationBean.getAccuracy());
            location.setLocationLatitude(locationBean.getLat());
            location.setLocationAddress(locationBean.getAddress());
            location.setLocationCountryCode(locationBean.getCountryCode());
            location.setLocationCountryName(locationBean.getCountryName());
            location.setLocationLongitude(locationBean.getLng());
            getGeoPointDao().saveOrUpdate(location);
            createNotification(NotificationEnum.LOCATION_GMAP_UPDATED, "Updated to "+ locationBean.getAddress(), location.getAccount());
            log.info("location map updated");
        }
    }

    /**
     * Get {@link GeoPoint}.
     * @param locationId location Id
     * @param username username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    private GeoPoint getLocation(final Long locationId, final String username) throws EnMeDomainNotFoundException{
        return getGeoPointDao().getLocationById(locationId, getPrimaryUser(username));
    }

    /**
     *
     * @param locationFolderId
     * @param username
     * @return
     * @throws EnMeDomainNotFoundException
     */
    private GeoPointFolder getLocationFolder(final Long locationFolderId, final String username) throws EnMeDomainNotFoundException{
        return getGeoPointDao().getLocationFolderByIdAndUserId(locationFolderId, getPrimaryUser(username));
    }

    /**
     * Update Location Name.
     * @param locationBean {@link UnitLocationBean}.
     * @param username username logged
     * @throws EnMeExpcetion exception
     * @throws EnMeDomainNotFoundException
     */
    public void updateLocationName(final UnitLocationBean locationBean, final String username) throws EnMeDomainNotFoundException{
        final GeoPoint location = getLocation(locationBean.getId(), username);
        if(location == null){
            throw new EnMeDomainNotFoundException("location not found");
        }
        else{
            final String lastName = location.getLocationDescription();
            location.setLocationDescription(locationBean.getName());
            getGeoPointDao().saveOrUpdate(location);
            log.info("location name updated");
            createNotification(NotificationEnum.LOCATION_GMAP_CHANGED_NAME,
                               lastName+" is update to "+locationBean.getName(), location.getAccount());
        }
    }

    /**
     * Update Location Folder.
     * @param locationBean
     * @param username
     * @param typeUpdate
     * @throws EnMeExpcetion
     * @throws EnMeDomainNotFoundException
     */
    public void updateLocationFolder(final UnitLocationFolder locationFolderBean,
            final String username, final String typeUpdate)
            throws EnMeDomainNotFoundException {
        final GeoPointFolder locationFolder = getLocationFolder(locationFolderBean
                .getId(), username);
        if (locationFolder == null) {
            throw new EnMeDomainNotFoundException("location folder not found");
        }
        else {
            if (typeUpdate.equals("name")) {
                log.debug("updating folder name");
                locationFolder.setLocationFolderName(locationFolderBean.getName());
            }
            getGeoPointDao().saveOrUpdate(locationFolder);
            createNotification(NotificationEnum.LOCATION_GMAP_CHANGED_NAME, "Folder name change to "
                                + locationFolderBean.getName(), locationFolder.getAccount());
        }
    }

    /**
     * Create Default Location Item.
     * @param locationFolder
     * @param username
     * @throws EnMeExpcetion
     */
    public void createDefaultILocationItem(final UnitLocationFolder locationFolderBean, final String username)
           throws EnMeDomainNotFoundException{
        log.info("createDefaultILocationItem");
        final GeoPointFolder locationFolder = getLocationFolder(locationFolderBean
                .getId(), username);
        log.info("createDefaultILocationItem locationFolder "+locationFolder);
        if (locationFolder == null) {
            throw new EnMeDomainNotFoundException("location folder not found");
        }
        else {
            final GeoPoint catLocation = new GeoPoint();
            catLocation.setCatLocationFolder(locationFolder);
            catLocation.setAccount(getUser(username).getAccount());
            catLocation.setLocationStatus(Status.ACTIVE);
            catLocation.setLocationDescription("Default Item Name");
            getGeoPointDao().saveOrUpdate(catLocation);
            log.info("Default Location Item Created");
        }
    }

    /**
     * Delete Location Folder.
     * @param unitLocationFolder
     * @param username
     * @throws EnMeExpcetion
     */
    public void deleteLocationFolder(final UnitLocationFolder unitLocationFolder, final String username) throws EnMeDomainNotFoundException{
        final GeoPointFolder locationFolder = getLocationFolder(unitLocationFolder.getId(), username);
        log.info("deleteLocationFolder locationFolder "+locationFolder);
        if (locationFolder == null) {
            throw new EnMeDomainNotFoundException("location folder not found");
        }
        else {
            //TODO: we need remove items on CASCADE.
            final List<GeoPoint> itemsToDelete = getGeoPointDao()
                                    .getLocationByFolder(locationFolder.getLocationFolderId(), getPrimaryUser(username));
            for (GeoPoint catLocation : itemsToDelete) {
                 getGeoPointDao().delete(catLocation);
            }
            getGeoPointDao().delete(locationFolder);
            log.info("delete location folder");
        }
    }

    /**
     * Delete Location Item.
     * @param unitLocationBean
     * @param username
     */
    public void deleteLocationItem(final UnitLocationBean unitLocationBean, final String username) throws EnMeDomainNotFoundException{
        final GeoPoint location = getLocation(unitLocationBean.getId(), username);
        if(location == null){
            throw new EnMeDomainNotFoundException("location not found");
        }
        else{
           //TODO: Maybe we have conflict in the future if this location was used on other tables, delete on cascade
           // will not a good option, we need think how to resolve this problem.
           // A possible solution is change status to INACTIVE, and it not show on tree.
           getGeoPointDao().delete(location);
        }
    }
}
