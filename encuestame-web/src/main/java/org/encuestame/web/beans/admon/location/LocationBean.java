/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.web.beans.admon.location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.encuestame.business.service.imp.ILocationService;
import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.LocationFolderType;
import org.encuestame.utils.web.TypeTreeNode;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UtilTreeNode;
import org.encuestame.web.beans.MasterBean;


/**
 * Location Bean.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since 26/05/2009 12:58:17
 * @version $Id$
 **/
public final class LocationBean extends MasterBean implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = -2052033748543513121L;
    private Long locateId;
    private Long tidtype;
    private String description;
    private Integer level;
    private String active;
    private Float lat;
    private Float lng;
    private UtilTreeNode nodeTitle;
    private String folderNewName = new String();
    private List<UtilTreeNode> selectedNodeChildren = new ArrayList<UtilTreeNode>();
    private List<UnitLocationBean> itemsBySelectedFolder = new ArrayList<UnitLocationBean>();
    private UnitLocationBean detailLocation;
    private UnitLocationFolder detailFolderLocation;
    private static final Float DEFAULT_LATITUDE = 12.1494F;
    private static final Float DEFAULT_LONGITUDE = -86.3058F;

    /**
     * Constructor.
     */
    public LocationBean() {
    }

    /**
     * @return locateId
     */
    public final Long getLocateId() {
        return locateId;
    }

    /**
     * @param locateId
     *            locateId
     */
    public final void setLocateId(Long locateId) {
        this.locateId = locateId;
    }

    /**
     * @return description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @param description
     *            description
     */
    public final void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return level
     */
    public final Integer getLevel() {
        return level;
    }

    /**
     * @param level
     *            level
     */
    public final void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return active
     */
    public final String getActive() {
        return active;
    }

    /**
     * @param active
     *            active
     */
    public final void setActive(String active) {
        this.active = active;
    }

    /**
     * @return lat
     */
    public final Float getLat() {
        return lat;
    }

    /**
     * @param lat
     *            lat
     */
    public final void setLat(Float lat) {
        this.lat = lat;
    }

    /**
     * @return lng
     */
    public final Float getLng() {
        return lng;
    }

    /**
     * @param lng
     *            lng
     */
    public final void setLng(Float lng) {
        this.lng = lng;
    }

    /**
     * @return the tidtype
     */
    public final Long getTidtype() {
        return tidtype;
    }

    /**
     * @param tidtype
     *            the tidtype to set
     */
    public final void setTidtype(Long tidtype) {
        this.tidtype = tidtype;
    }

    public void updateFolderName(){
        try{
            log.debug("Update folder Name to "+getDetailFolderLocation().getName());
            getLocationService().updateLocationFolder(getDetailFolderLocation(), getUserPrincipalUsername(), "name");
            log.info("folder name updated");
 //           this.loadTree();
        }
        catch (Exception e) {
            log.error("error on update name "+e.getMessage());
        }
    }

    /**
     * Add New Item.
     */
    public void addNewItem(){
        try{
            log.info("Add New Item for "+getDetailFolderLocation().getId());
            getLocationService().createDefaultILocationItem(getDetailFolderLocation(), getUserPrincipalUsername());
   //         this.loadTree();
            this.getItemsByLocationFolder();
        }
        catch (Exception e) {
             log.error("error on update name "+e.getMessage());
        }
    }

    /**
     * Get Items by Location Folder.
     * @throws EnMeDomainNotFoundException
     */
    private void getItemsByLocationFolder() throws EnMeDomainNotFoundException{
            setItemsBySelectedFolder(getLocationService()
            .retrieveLocationFolderItemsById(getDetailFolderLocation().getId(), getUserPrincipalUsername()));
    }

    /**
     * Create Folder.
     */
    public void createFolder(){
        final UnitLocationFolder newFolder = new UnitLocationFolder();
        try{
            //default name
            log.info("newFolder "+newFolder);
            log.info("getFolderNewName "+getFolderNewName());
            newFolder.setName(getFolderNewName().isEmpty() ? "Update this name" : getFolderNewName());
            newFolder.setType(LocationFolderType.GROUPING.name());
            getLocationService().createLocationFolder(newFolder, getUserPrincipalUsername());
     //       this.loadTree();
        }
        catch (Exception e) {
           log.error("error "+e);
        }
    }

    /**
     * Delete Location Folder.
     */
    public void deleteLocationFolder(){
        try{
            log.info("deleting location folder");
            getLocationService().deleteLocationFolder(getDetailFolderLocation(), getUserPrincipalUsername());
            setDetailFolderLocation(null);
       //     this.loadTree();
        }
        catch (Exception e) {
            log.error("error "+e);
        }
    }

    /**
     * Delete Location Folder.
     */
    public void deleteLocationItem(){
        try{
            log.info("deleting location item");
            getLocationService().deleteLocationItem(getDetailLocation(), getUserPrincipalUsername());
            setDetailLocation(null);
    //        this.loadTree();
        }
        catch (Exception e) {
            log.error("error "+e);
        }
    }


    /**
     * Search Location Detail.
     * @param treeNode Selected Tree Node.
     * @return
     * @throws EnMeDomainNotFoundException
     */
    private UnitLocationBean searchItemLocationDetail(final UtilTreeNode treeNode) throws EnMeDomainNotFoundException{
         log.info("searchLocationDetail "+treeNode.getId());
         final ILocationService locationService = getServicemanager().getApplicationServices().getLocationService();
         final UnitLocationBean retrievedLocation = locationService.getLocationItem(treeNode.getId(), getUserPrincipalUsername());
         if(retrievedLocation.getLat() == null){
             retrievedLocation.setLat(DEFAULT_LATITUDE);
         }
         if(retrievedLocation.getLng() == null){
             retrievedLocation.setLng(DEFAULT_LONGITUDE);
         }
         log.info("retrievedLocation "+retrievedLocation);
         return retrievedLocation;
    }

    /**
     * Search Folder Location Detail.
     * @param treeNode folder treeNode.
     * @return {@link UnitLocationFolder}.
     * @throws EnMeDomainNotFoundException
     */
    private UnitLocationFolder searchFolderLocationDetail(final UtilTreeNode treeNode) throws EnMeDomainNotFoundException{
        log.info("searchLocationDetail "+treeNode.getId());
        final ILocationService locationService = getServicemanager().getApplicationServices().getLocationService();
        final UnitLocationFolder retrievedLocation = locationService.getFolderLocation(treeNode.getId(), getUserPrincipalUsername());
        log.info("retrievedLocation "+retrievedLocation);
        return retrievedLocation;
    }

    /**
     * @return the nodeTitle
     */
    public UtilTreeNode getNodeTitle() {
        return nodeTitle;
    }

    /**
     * @param nodeTitle the nodeTitle to set
     */
    public void setNodeTitle(final UtilTreeNode nodeTitle) {
        this.nodeTitle = nodeTitle;
    }

    /**
     * @return the detailLocation
     */
    public UnitLocationBean getDetailLocation() {
        return detailLocation;
    }

    /**
     * @param detailLocation the detailLocation to set
     */
    public void setDetailLocation(final UnitLocationBean detailLocation) {
        log.info("setDetailLocation  "+detailLocation);
        this.detailLocation = detailLocation;
    }

    /**
     * @return the detailFolderLocation
     */
    public UnitLocationFolder getDetailFolderLocation() {
        return detailFolderLocation;
    }

    /**
     * @param detailFolderLocation the detailFolderLocation to set
     */
    public void setDetailFolderLocation(final UnitLocationFolder detailFolderLocation) {
        this.detailFolderLocation = detailFolderLocation;
    }

    /**
     * @return the folderNewName
     */
    public String getFolderNewName() {
        return folderNewName;
    }

    /**
     * @param folderNewName the folderNewName to set
     */
    public void setFolderNewName(final String folderNewName) {
        this.folderNewName = folderNewName;
    }

    /**
     * @return the itemsBySelectedFolder
     */
    public List<UnitLocationBean> getItemsBySelectedFolder() {
        return itemsBySelectedFolder;
    }

    /**
     * @param itemsBySelectedFolder the itemsBySelectedFolder to set
     */
    public void setItemsBySelectedFolder(
            final List<UnitLocationBean> itemsBySelectedFolder) {
        this.itemsBySelectedFolder = itemsBySelectedFolder;
    }
}