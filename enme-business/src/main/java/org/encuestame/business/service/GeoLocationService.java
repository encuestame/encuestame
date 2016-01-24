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
package org.encuestame.business.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.service.IGeoLocationSupport;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.core.util.EnMePlaceHolderConfigurer;
import org.encuestame.core.util.EnMeUtils;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.GeoPointFolder;
import org.encuestame.persistence.domain.GeoPointType;
import org.encuestame.persistence.domain.GeoPointFolderType;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.Survey;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSavedPublishedStatus;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeSearchException;
import org.encuestame.utils.enums.NotificationEnum;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.Status;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UnitLocationTypeBean; 
import org.encuestame.utils.web.geo.ItemGeoLocationBean; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Location Service.
 * @author Picado, Juan juanATencuestame.org
 * @since May 15, 2010 8:17:15 PM
 */
@Service
@Transactional
public class GeoLocationService extends AbstractBaseService implements IGeoLocationSupport {

    /** Log. **/
    private Log log = LogFactory.getLog(this.getClass());
    
	private int earth_radius = EnMePlaceHolderConfigurer
			.getIntegerProperty("geo.earth.radius.km");

    /**
     * Create Cat LocationType.
     * @param locatTypeBean {@link UnitLocationTypeBean}
     * @return locatTypeBean type bean
     * @throws EnMeExpcetion exception
     */
    public UnitLocationTypeBean createGeoPointType(
            final UnitLocationTypeBean locatTypeBean) throws EnMeExpcetion {
        if (locatTypeBean != null) {
            try {
                final GeoPointType locationTypeDomain = new GeoPointType();
                locationTypeDomain.setLocationTypeDescription(locatTypeBean
                        .getLocTypeDesc());
                locationTypeDomain.setLocationTypeLevel(locatTypeBean
                        .getLevel());
                locationTypeDomain.setUsers(getUserAccount(getUserPrincipalUsername()).getAccount());
                getGeoPointTypeDao().saveOrUpdate(locationTypeDomain);
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
     * @throws EnMeNoResultsFoundException
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void updateGeoPoint(final UnitLocationBean locationBean) throws EnMeNoResultsFoundException, EnMeExpcetion{
       final GeoPoint geoPoint =  getLocation(locationBean.getId(), getUserPrincipalUsername());
        if (geoPoint!=null){
            geoPoint.setLocationStatus(Status.valueOf(locationBean.getStatus()));
            geoPoint.setLocationDescription(locationBean.getName());
            geoPoint.setLocationLatitude(locationBean.getLat());
            geoPoint.setLocationLongitude(locationBean.getLng());
            getGeoPointDao().saveOrUpdate(geoPoint);
        }else{
            throw new EnMeExpcetion("geoPoint not found");
        }
   }


    /**
     * Update Cat Location Type.
     * @param locationTypeBean locationTypeBean
     * @throws EnMeExpcetion EnMeExpcetion
     * @throws EnMeNoResultsFoundException
     */
    public void updateGeoPointType(final UnitLocationTypeBean locationTypeBean) throws EnMeExpcetion, EnMeNoResultsFoundException{
        final GeoPointType geoPointType = getGeoPointTypeDao().getLocationById(locationTypeBean.getIdLocType());
        if (geoPointType!=null){
            geoPointType.setLocationTypeDescription(locationTypeBean.getLocTypeDesc());
            geoPointType.setLocationTypeLevel(locationTypeBean.getLevel());
            getGeoPointTypeDao().saveOrUpdate(geoPointType);
        }
        else{
            throw new EnMeNoResultsFoundException("location type not found");
        }
    }

    /**
     * Create Cat Location.
     * @param location {@link LocationBean}
     * @throws EnMeExpcetion exception
     */
    public UnitLocationBean createGeoPoint(final UnitLocationBean location) throws EnMeExpcetion{
        if (location != null){
            try{
                final GeoPoint geoPointDomain = new GeoPoint();
                geoPointDomain.setLocationDescription(location.getName());
                geoPointDomain.setLocationStatus(Status.ACTIVE);
                geoPointDomain.setLocationLatitude(location.getLat());
                geoPointDomain.setAccount(getUserAccount(getUserPrincipalUsername()).getAccount());
                geoPointDomain.setLocationLongitude(location.getLng());
                if(location.getTidtype() != null){
                    geoPointDomain.setTidtype(getGeoPointTypeDao().getLocationById(location.getTidtype()));
                }
                getGeoPointDao().saveOrUpdate(geoPointDomain);
                location.setId(geoPointDomain.getLocateId());
                createNotification(NotificationEnum.LOCATION_NEW, location.getName() +" is created.", geoPointDomain.getAccount());
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
     * @throws EnMeNoResultsFoundException
     */
    public UnitLocationFolder createGeoPointFolder(final UnitLocationFolder locationFolder) throws EnMeNoResultsFoundException{
        final GeoPointFolder geoPointFolder = new GeoPointFolder();
        geoPointFolder.setFolderType(GeoPointFolderType.valueOf(locationFolder.getType()));
        geoPointFolder.setFolderName(locationFolder.getName());
        geoPointFolder.setUsers(getUserAccount(getUserPrincipalUsername()).getAccount());
        getGeoPointDao().saveOrUpdate(geoPointFolder);
        locationFolder.setId(geoPointFolder.getLocationFolderId());
        createNotification(NotificationEnum.LOCATION_FOLDER_NEW, "New Folder "+locationFolder.getName() +" is created.", geoPointFolder.getUsers());
        return locationFolder;
    }

    /**
     * Assign Location to Location Folder.
     * @param location
     */
    public void assignLocationToLocationFolder(final GeoPoint location, final GeoPointFolder geoPointFolder){
            location.setGeoPointFolder(geoPointFolder);
            getGeoPointDao().saveOrUpdate(location);
    }

    /**
     * Retrieve Location Folders by User.
     * @param currentName
     * @throws EnMeNoResultsFoundException
     */
    public List<UnitLocationFolder> retrieveLocationFolderByUser(final String currentUserName) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertListToUnitLocationFolderBean(getGeoPointDao()
                                .getLocationFolders(getUserAccountId(currentUserName)));
    }

    /**
     * Retrieve Location Sub Folders by User.
     * @param currentName
     * @throws EnMeNoResultsFoundException
     */
    public List<UnitLocationFolder> retrieveLocationSubFolderByUser(final Long locationFolderId, final String currentUserName) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertListToUnitLocationFolderBean(getGeoPointDao()
                                .getLocationFoldersByLocationFolderId(locationFolderId, getUserAccountId(currentUserName)));
    }

    /**
     * Retrieve Locations Folder Items by Folder Id and User Id.
     * @param locationFolderId location folder id
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<UnitLocationBean> retrieveLocationFolderItemsById(final Long locationFolderId, final String username) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertListToUnitLocationBean(getGeoPointDao()
                                .getLocationByFolder(locationFolderId, getUserAccountId(username)));
    }

    /**
     * Retrieve Locations Items by Username
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public List<UnitLocationBean> retrieveLocationItemsByUsername(final String username) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertListToUnitLocationBean(getGeoPointDao()
                                .getLocationByUser(getUserAccountId(username)));
    }

    /**
     * Get Location Item.
     * @param locationId location id
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UnitLocationBean getLocationItem(final Long locationId, final String username) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertLocationToBean(getLocation(locationId, username));
    }

    /**
     * Get Folder Location Detail.
     * @param folderLocationId folder location  Id.
     * @param username username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    public UnitLocationFolder getFolderLocation(final Long folderLocationId, final String username) throws EnMeNoResultsFoundException{
        return ConvertDomainBean.convertGeoPointFolderDomainToBean(getGeoPointDao()
                                .getLocationFolderByIdAndUserId(folderLocationId, getUserAccountId(username)));
    }

    /**
     * Update Location Map.
     * @param Latitude
     * @param Longitude
     * @param locationId
     * @param username
     * @throws EnMeExpcetion
     * @throws EnMeNoResultsFoundException
     */
    public void updateLocationMap(final UnitLocationBean locationBean, final Long locationId, final String username) throws EnMeExpcetion, EnMeNoResultsFoundException{
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
     * @throws EnMeNoResultsFoundException
     */
    private GeoPoint getLocation(final Long locationId, final String username) throws EnMeNoResultsFoundException{
        return getGeoPointDao().getLocationById(locationId, getUserAccountId(username));
    }

    /**
     *
     * @param locationFolderId
     * @param username
     * @return
     * @throws EnMeNoResultsFoundException
     */
    private GeoPointFolder getLocationFolder(final Long locationFolderId, final String username) throws EnMeNoResultsFoundException{
        return getGeoPointDao().getLocationFolderByIdAndUserId(locationFolderId, getUserAccountId(username));
    }

    /**
     * Update Location Name.
     * @param locationBean {@link UnitLocationBean}.
     * @param username username logged
     * @throws EnMeExpcetion exception
     * @throws EnMeNoResultsFoundException
     */
    public void updateLocationName(final UnitLocationBean locationBean, final String username) throws EnMeNoResultsFoundException{
        final GeoPoint location = getLocation(locationBean.getId(), username);
        if(location == null){
            throw new EnMeNoResultsFoundException("location not found");
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
     * @throws EnMeNoResultsFoundException
     */
    public void updateLocationFolder(final UnitLocationFolder locationFolderBean,
            final String username, final String typeUpdate)
            throws EnMeNoResultsFoundException {
        final GeoPointFolder locationFolder = getLocationFolder(locationFolderBean
                .getId(), username);
        if (locationFolder == null) {
            throw new EnMeNoResultsFoundException("location folder not found");
        }
        else {
            if (typeUpdate.equals("name")) {
                log.debug("updating folder name");
                locationFolder.setFolderName(locationFolderBean.getName());
            }
            getGeoPointDao().saveOrUpdate(locationFolder);
            createNotification(NotificationEnum.LOCATION_GMAP_CHANGED_NAME, "Folder name change to "
                                + locationFolderBean.getName(), locationFolder.getUsers());
        }
    }

    /**
     * Create Default Location Item.
     * @param locationFolder
     * @param username
     * @throws EnMeExpcetion
     */
    public void createDefaultILocationItem(final UnitLocationFolder locationFolderBean, final String username)
           throws EnMeNoResultsFoundException{
        log.info("createDefaultILocationItem");
        final GeoPointFolder locationFolder = getLocationFolder(locationFolderBean
                .getId(), username);
        log.info("createDefaultILocationItem locationFolder "+locationFolder);
        if (locationFolder == null) {
            throw new EnMeNoResultsFoundException("location folder not found");
        }
        else {
            final GeoPoint geopoint = new GeoPoint();
            geopoint.setGeoPointFolder(locationFolder);
            geopoint.setAccount(getUserAccount(username).getAccount());
            geopoint.setLocationStatus(Status.ACTIVE);
            geopoint.setLocationDescription("Default Item Name");
            getGeoPointDao().saveOrUpdate(geopoint);
            log.info("Default Location Item Created");
        }
    }

    /**
     * Delete Location Folder.
     * @param unitLocationFolder
     * @param username
     * @throws EnMeExpcetion
     */
    public void deleteLocationFolder(final UnitLocationFolder unitLocationFolder, final String username) throws EnMeNoResultsFoundException{
        final GeoPointFolder locationFolder = getLocationFolder(unitLocationFolder.getId(), username);
        log.info("deleteLocationFolder locationFolder "+locationFolder);
        if (locationFolder == null) {
            throw new EnMeNoResultsFoundException("location folder not found");
        }
        else {
            //TODO: we need remove items on CASCADE.
            final List<GeoPoint> itemsToDelete = getGeoPointDao()
                                    .getLocationByFolder(locationFolder.getLocationFolderId(), getUserAccountId(username));
            for (GeoPoint geoPoint : itemsToDelete) {
                 getGeoPointDao().delete(geoPoint);
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
    public void deleteLocationItem(final UnitLocationBean unitLocationBean, final String username) throws EnMeNoResultsFoundException{
        final GeoPoint location = getLocation(unitLocationBean.getId(), username);
        if(location == null){
            throw new EnMeNoResultsFoundException("location not found");
        }
        else{
           //TODO: Maybe we have conflict in the future if this location was used on other tables, delete on cascade
           // will not a good option, we need think how to resolve this problem.
           // A possible solution is change status to INACTIVE, and it not show on tree.
           getGeoPointDao().delete(location);
        }
    }
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.encuestame.core.service.imp.GeoLocationSupport#retrieveItemsByGeo
	 * (double, java.lang.Integer, org.encuestame.utils.enums.TypeSearchResult,
	 * double, double)
	 */
	public List<ItemGeoLocationBean> retrieveItemsByGeo(final double range,
			final Integer maxItem, final TypeSearchResult itemType,
			final double longitude, final double latitude, final SearchPeriods period) {
		List<ItemGeoLocationBean> itemsByGeoLocation = new ArrayList<ItemGeoLocationBean>();
	/*	final int earthRadius = EnMePlaceHolderConfigurer
				.getIntegerProperty("geo.earth.radius.km"); */
		List<Object[]> distanceFromOrigin = new ArrayList<Object[]>();
		final double latitudeInRadians = EnMeUtils
				.convertDegreesToRadians(latitude);
		final double longitudeInRadians = EnMeUtils
				.convertDegreesToRadians(longitude);
		if (itemType.equals(TypeSearchResult.ALL)) {

			List<Object[]> distanceFromOriginTweetPoll = this
					.getItemsByDistanceFromOrigin(range, maxItem,
							TypeSearchResult.TWEETPOLL, longitudeInRadians,
							latitudeInRadians, this.earth_radius, period);
			itemsByGeoLocation.addAll(this.retrieveGeoDataForObjectList(
					distanceFromOriginTweetPoll, TypeSearchResult.TWEETPOLL));
			log.debug("Total tweetpolls with geolocations --->"
					+ distanceFromOriginTweetPoll.size());
			List<Object[]> distanceFromOriginPoll = this
					.getItemsByDistanceFromOrigin(range, maxItem,
							TypeSearchResult.POLL, longitudeInRadians,
							latitudeInRadians, this.earth_radius, period);
			itemsByGeoLocation.addAll(this.retrieveGeoDataForObjectList(distanceFromOriginPoll,
					TypeSearchResult.POLL));
			log.debug("Total polls with geolocations --->"
					+ distanceFromOriginPoll.size());
			List<Object[]> distanceFromOriginSurvey = this
					.getItemsByDistanceFromOrigin(range, maxItem,
							TypeSearchResult.SURVEY, longitudeInRadians,
							latitudeInRadians, this.earth_radius, period);
			itemsByGeoLocation.addAll(this.retrieveGeoDataForObjectList(
					distanceFromOriginSurvey, TypeSearchResult.SURVEY));
			log.debug("Total surveys with geolocations -->"
					+ distanceFromOriginSurvey.size());
		} else {
			distanceFromOrigin = this.getItemsByDistanceFromOrigin(range,
					maxItem, itemType, longitudeInRadians, latitudeInRadians,
					this.earth_radius, period);
			itemsByGeoLocation.addAll(this.retrieveGeoDataForObjectList(distanceFromOrigin,
					itemType));
			log.debug("Total Items with geolocations --->"
					+ itemsByGeoLocation.size() + "----" + itemType);
		}

		log.debug("Total items bean geolocated -->" + itemsByGeoLocation.size()
				+ " type ----" + itemType);
		return itemsByGeoLocation;
	}
 
	/**
	 * Get the items {@link TweetPoll}, {@link Poll}, {@link Survey} with the
	 * distances from a point source. 
	 * @param range
	 * @param maxItem
	 * @param itemType
	 * @param longitudeInRadians
	 * @param latitudeInRadians
	 * @param earthRadius
	 * @return
	 */
	private List<Object[]> getItemsByDistanceFromOrigin(final double range,
			final Integer maxItem, final TypeSearchResult itemType,
			final double longitudeInRadians, final double latitudeInRadians,
			final int earthRadius, final SearchPeriods period) {
		List<Object[]> distanceFromOrigin = new ArrayList<Object[]>();
		distanceFromOrigin = getTweetPollDao()
				.retrieveTweetPollsBySearchRadiusOfGeoLocation(
						latitudeInRadians, longitudeInRadians, range,
						earthRadius, maxItem, itemType, period);
		return distanceFromOrigin;
	}
	
	/**
	 * Get for an object list data about geolocations and other information
	 * about {@link TweetPoll}, {@link Poll} or {@link Survey}.
	 * 
	 * @param objectList
	 * @param itemType
	 * @return
	 */
	private List<ItemGeoLocationBean> retrieveGeoDataForObjectList(
			final List<Object[]> objectList, TypeSearchResult itemType) {
		List<ItemGeoLocationBean> itemsGeoBeanList = new ArrayList<ItemGeoLocationBean>();

		ItemGeoLocationBean itemGeoBean = new ItemGeoLocationBean();
		for (Object[] objects : objectList) {
			itemGeoBean = this.createItemGeoLocationBean((Long) objects[0],
					itemType, (Float) objects[1], (Float) objects[2],
					objects[3].toString(), (Double) objects[4]);
			itemsGeoBeanList.add(itemGeoBean);
		}
		// TODO: It's necessary to sort by distance
		return itemsGeoBeanList; 
	}
	
	/**
	 * Create {@link ItemGeoLocationBean} helper.
	 * @param itemId
	 * @param itemType
	 * @param latitude
	 * @param longitude
	 * @param question
	 * @param distance
	 * @return
	 */
	private ItemGeoLocationBean createItemGeoLocationBean(final Long itemId,
			final TypeSearchResult itemType, final float latitude,
			final float longitude, final String question, final double distance) {
		final ItemGeoLocationBean locationBean = new ItemGeoLocationBean();
		locationBean.setItemId(itemId);
		locationBean.setItemType(itemType);
		locationBean.setLatitude(latitude);
		locationBean.setLongitude(longitude);
		locationBean.setQuestion(question);
		locationBean.setDistance(distance);
		return locationBean;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.encuestame.core.service.imp.GeoLocationSupport#retreiveHashTagUsebyGeoLo
	 * (double, java.lang.Integer, org.encuestame.utils.enums.TypeSearchResult,
	 * double, double, java.lang.String,
	 * org.encuestame.utils.enums.SearchPeriods)
	 */
	public List<ItemGeoLocationBean> retreiveHashTagUsebyGeoLo(
			final double range, final Integer maxItem,
			final TypeSearchResult itemType, final double longitude,
			final double latitude, final String tagName,
			final SearchPeriods period) throws EnMeSearchException { 
 
		List<ItemGeoLocationBean> geoBeanList = new ArrayList<ItemGeoLocationBean>();

		final double latitudeInRadians = EnMeUtils
				.convertDegreesToRadians(latitude);
		final double longitudeInRadians = EnMeUtils
				.convertDegreesToRadians(longitude);

		if (itemType.equals(TypeSearchResult.ALL)) {
			List<Object[]> distanceFromOriginTweetPoll = this
					.getItemsByDistanceFromOrigin(range, maxItem,
							TypeSearchResult.TWEETPOLL, longitudeInRadians,
							latitudeInRadians, this.earth_radius, period);
			geoBeanList.addAll(this.checkIfExist(
					distanceFromOriginTweetPoll, tagName, period, itemType));
			log.debug("Total tweetpolls with geolocations --->"
					+ distanceFromOriginTweetPoll.size());

			// Poll
			List<Object[]> distanceFromOriginPoll = this
					.getItemsByDistanceFromOrigin(range, maxItem,
							TypeSearchResult.POLL, longitudeInRadians,
							latitudeInRadians, this.earth_radius, period);
			geoBeanList.addAll(this.checkIfExist(distanceFromOriginPoll,
					tagName, period, itemType));
			log.debug("Total Polls with geolocations --->"
					+ distanceFromOriginPoll.size());

			// Survey

			List<Object[]> distanceFromSurvey = this
					.getItemsByDistanceFromOrigin(range, maxItem,
							TypeSearchResult.POLL, longitudeInRadians,
							latitudeInRadians, this.earth_radius, period);
			geoBeanList.addAll(this.checkIfExist(distanceFromSurvey,
					tagName, period, itemType));
			log.debug("Total Surveys with geolocations --->"
					+ distanceFromSurvey.size());

		} else {
			final List<Object[]> tpollsGeo = this.getItemsByDistanceFromOrigin(
					range, maxItem, itemType, longitudeInRadians,
					latitudeInRadians, this.earth_radius, period);
			geoBeanList = this.checkIfExist(tpollsGeo, tagName, period,
					itemType);
		}

		return geoBeanList;
	}
	
	/**
	 * 
	 * @param itemsByGeo
	 * @param tagName
	 * @param periods
	 * @param itemType
	 * @return
	 */
	private List<ItemGeoLocationBean> checkIfExist(
			final List<Object[]> itemsByGeo, final String tagName,
			final SearchPeriods periods, final TypeSearchResult itemType) {
		ItemGeoLocationBean itemGeoBean = new ItemGeoLocationBean();
		List<ItemGeoLocationBean> itemsGeoBeanList = new ArrayList<ItemGeoLocationBean>();
		for (Object[] objects : itemsByGeo) {

			final TweetPoll pollchecked = getTweetPollDao()
					.checkIfTweetPollHasHashTag(tagName, periods,
							(Long) objects[0]);
			if (pollchecked == null) {
				log.debug("Tweetpoll with this hashtag has not been found."
								+ objects[0]);
			} else {
				itemGeoBean = this.createItemGeoLocationBean((Long) objects[0],
						itemType, (Float) objects[1], (Float) objects[2],
						objects[3].toString(), (Double) objects[4]);
				itemsGeoBeanList.add(itemGeoBean);
			}
		}

		return itemsGeoBeanList;
	}  
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.encuestame.core.service.imp.GeoLocationSupport#
	 * retrieveSocialNetworksPublicationsbyGeoLocation(double,
	 * java.lang.Integer, org.encuestame.utils.enums.TypeSearchResult, double,
	 * double, org.encuestame.utils.enums.SearchPeriods)
	 */
	public List<ItemGeoLocationBean> retrieveSocialNetworksPublicationsbyGeoLocation(
			final double range, final Integer maxItem,
			final TypeSearchResult itemType, final double longitude,
			final double latitude, final SearchPeriods period)
			throws EnMeSearchException, EnMeNoResultsFoundException {

		List<ItemGeoLocationBean> socialGeoBean = new ArrayList<ItemGeoLocationBean>();
		List<Object[]> socialItemsFromOrigin = new ArrayList<Object[]>();
		final double latitudeInRadians = EnMeUtils
				.convertDegreesToRadians(latitude);
		final double longitudeInRadians = EnMeUtils
				.convertDegreesToRadians(longitude);
		List<TweetPollSavedPublishedStatus> tpSavedPublished = new ArrayList<TweetPollSavedPublishedStatus>();
		TweetPoll itemTweetPoll;
		Poll itemPoll;

		if (itemType.equals(TypeSearchResult.ALL)) {
			// POLL
			List<Object[]> distanceFromOriginTweetPoll = this
					.getItemsByDistanceFromOrigin(range, maxItem,
							TypeSearchResult.TWEETPOLL, longitudeInRadians,
							latitudeInRadians, this.earth_radius, period);
			log.debug("Retrieved items by distance -->"
					+ distanceFromOriginTweetPoll.size());

			for (Object[] objects : distanceFromOriginTweetPoll) {
				itemTweetPoll = this.getTweetPollById((Long) objects[0]);
				tpSavedPublished = getTweetPollDao().getLinksByTweetPoll(
						itemTweetPoll, null, null, TypeSearchResult.TWEETPOLL);

				log.debug("Tweetpolls published on social networks -->"
						+ itemTweetPoll.getTweetPollId());

				socialGeoBean.addAll(ConvertDomainBean
						.convertTweetPollSavedPublishedToSocialGeoLocationBean(
								tpSavedPublished,
								itemTweetPoll.getTweetPollId(), itemType,
								(Float) objects[1], (Float) objects[2],
								objects[3].toString(), (Double) objects[4]));
			}
			// POLL
			List<Object[]> distanceFromOriginPoll = this
					.getItemsByDistanceFromOrigin(range, maxItem,
							TypeSearchResult.POLL, longitudeInRadians,
							latitudeInRadians, this.earth_radius, period);
			log.debug("Retrieved items by distance -->"
					+ distanceFromOriginTweetPoll.size());

			for (Object[] objects : distanceFromOriginPoll) {
				itemPoll = this.getPollById((Long) objects[0]);
				tpSavedPublished = getTweetPollDao().getLinksByTweetPoll(
						null, null, itemPoll, TypeSearchResult.POLL);

				log.debug("Polls published on social networks -->"
						+ itemPoll.getPollId());

				socialGeoBean.addAll(ConvertDomainBean
						.convertTweetPollSavedPublishedToSocialGeoLocationBean(
								tpSavedPublished,
								itemPoll.getPollId(), itemType,
								(Float) objects[1], (Float) objects[2],
								objects[3].toString(), (Double) objects[4]));
			}


		} else {
			socialItemsFromOrigin = this.getItemsByDistanceFromOrigin(range,
					maxItem, itemType, longitudeInRadians, latitudeInRadians,
					this.earth_radius, period);
			socialGeoBean.addAll(this.retrieveGeoDataForObjectList(
					socialItemsFromOrigin, itemType));
			log.debug("Total Items with geolocations --->"
					+ socialItemsFromOrigin.size() + "----" + itemType);
		}

		return socialGeoBean;
	}

}
