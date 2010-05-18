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
package org.encuestame.core.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.core.persistence.pojo.CatLocationFolder;
import org.encuestame.core.persistence.pojo.CatLocationType;
import org.encuestame.core.persistence.pojo.LocationFolderType;
import org.encuestame.core.persistence.pojo.Status;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.utils.web.LocationTypeBean;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.springframework.stereotype.Service;

/**
 * Location Service.
 * @author Picado, Juan juan@encuestame.org
 * @since May 15, 2010 8:17:15 PM
 * @version $Id: $
 */
@Service
public class LocationService  extends AbstractBaseService implements ILocationService{

    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Create Cat LocationType.
     * @param locatTypeBean {@link LocationTypeBean}
     * @return locatTypeBean
     * @throws EnMeExpcetion exception
     */
    public UnitLocationTypeBean createCatLocationType(
            final UnitLocationTypeBean locatTypeBean, final String username) throws EnMeExpcetion {
        if (locatTypeBean != null) {
            try {
                final CatLocationType locationTypeDomain = new CatLocationType();
                locationTypeDomain.setLocationTypeDescription(locatTypeBean
                        .getLocTypeDesc());
                locationTypeDomain.setLocationTypeLevel(locatTypeBean
                        .getLevel());
                locationTypeDomain.setUsers(getUser(username).getSecUser());
                getCatLocationTypeDao().saveOrUpdate(locationTypeDomain);
                locatTypeBean.setIdLocType((locationTypeDomain
                        .getLocationTypeId()));
            } catch (Exception e) {
                throw new EnMeExpcetion(e);
            }
            return locatTypeBean;
        } else {
            throw new EnMeExpcetion("Cat Location Type is null");
        }
    }

    /**
     * @param locationBean locationBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void updateCatLocation(final UnitLocationBean locationBean) throws EnMeExpcetion
    {
       log.info("Update Location");
       final CatLocation catLocation = getCatLocationDao().getLocationById(locationBean.getLocateId());
        if (catLocation!=null){
            catLocation.setLocationStatus(Status.valueOf(locationBean.getStatus()));
            catLocation.setlocationDescription(locationBean.getDescription());
            catLocation.setLocationLatitude(locationBean.getLat());
            catLocation.setLocationLongitude(locationBean.getLng());
            getCatLocationDao().saveOrUpdate(catLocation);
        }else{
            throw new EnMeExpcetion("location not found");
        }
   }


    /**
     * @param locationTypeBean locationTypeBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void updateCatLocationType(final UnitLocationTypeBean locationTypeBean) throws EnMeExpcetion{
        log.info("update LocationType");
        final CatLocationType catLocationType = getCatLocationTypeDao().getLocationById(locationTypeBean.getIdLocType());
        if (catLocationType!=null){
            catLocationType.setLocationTypeDescription(locationTypeBean.getLocTypeDesc());
            catLocationType.setLocationTypeLevel(locationTypeBean.getLevel());
            getCatLocationTypeDao().saveOrUpdate(catLocationType);
        }
        else{
            throw new EnMeExpcetion("location type not found");
        }
    }

    /**
     * create Cat Location.
     * @param location {@link LocationBean}
     * @throws EnMeExpcetion exception
     */
    public UnitLocationBean createCatLocation(final UnitLocationBean location) throws EnMeExpcetion
    {
        if (location != null){
            try{
                final CatLocation catLocationDomain = new CatLocation();
                catLocationDomain.setlocationDescription(location.getDescription());
                catLocationDomain.setLocationStatus(Status.ACTIVE);
                catLocationDomain.setLocationLatitude(location.getLat());
                catLocationDomain.setLocationLongitude(location.getLng());
                if(location.getTidtype() != null){
                    catLocationDomain.setTidtype(getCatLocationTypeDao().getLocationById(location.getTidtype()));
                }
                getCatLocationDao().saveOrUpdate(catLocationDomain);
                log.debug("create location domain");
                location.setLocateId(catLocationDomain.getLocateId());
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
     */
    public UnitLocationFolder createLocationFolder(final UnitLocationFolder locationFolder, final String username){
        final CatLocationFolder catLocationFolder = new CatLocationFolder();
        catLocationFolder.setFolderType(LocationFolderType.valueOf(locationFolder.getType()));
        catLocationFolder.setLocationFolderName(locationFolder.getName());
        catLocationFolder.setSecUsers(getUser(username).getSecUser());
        return locationFolder;
    }

    /**
     * Assign Location to Location Folder.
     * @param location
     */
    public void assignLocationToLocationFolder(final CatLocation location, final CatLocationFolder catLocationFolder){
            location.setCatLocationFolder(catLocationFolder);
            getCatLocationDao().saveOrUpdate(location);
    }

    /**
     * Retrieve Location Folders by User.
     * @param currentName
     */
    public List<UnitLocationFolder> retrieveLocationFolderByUser(final String currentUserName){
        return ConvertDomainBean.convertListToUnitLocationFolderBean(getCatLocationDao()
               .getLocationFolders(getPrimaryUser(currentUserName)));
    }
}
