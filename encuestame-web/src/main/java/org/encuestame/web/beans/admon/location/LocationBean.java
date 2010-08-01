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

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;

import org.encuestame.core.persistence.pojo.LocationFolderType;
import org.encuestame.core.service.ILocationService;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.utils.web.TypeTreeNode;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.utils.web.UtilTreeNode;
import org.encuestame.web.beans.MasterBean;
import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;


import com.googlecode.gmaps4jsf.component.marker.MarkerValue;
import com.googlecode.gmaps4jsf.services.GMaps4JSFServiceFactory;
import com.googlecode.gmaps4jsf.services.data.PlaceMark;

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
    private TreeNode<UtilTreeNode> rootNode = null;
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

    /**
     * Load Tree Items.
     */
    private void loadTree() {
        try {
            rootNode = new TreeNodeImpl<UtilTreeNode>();
            final ILocationService locationService = getServicemanager().getApplicationServices().getLocationService();
            final List<UnitLocationFolder> locationFolders = locationService.retrieveLocationFolderByUser(getSecurityContextUsername());
            log.debug("location folders size "+locationFolders.size());
            addFolders(rootNode, ConvertDomainBean.convertFolderToDragrable(locationFolders, TypeTreeNode.FOLDER));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        }
    }

    /**
     * Add Items.
     * @param node node
     * @param items list of items
     */
    private void addItems(final TreeNode<UtilTreeNode> node, final List<UtilTreeNode> items){
        int i = 1;
        log.info("Add Items size "+items.size());
        for (UtilTreeNode utilTreeNode : items) {
            final TreeNodeImpl<UtilTreeNode> item = new TreeNodeImpl<UtilTreeNode>();
            log.info("Adding Item "+utilTreeNode.getId());
            item.setData(utilTreeNode);
            node.addChild(i, item);
            i++;
        }
    }

    /**
     * Add Folders.
     * @param node node to attach
     * @param items Folders items folder
     */
    private void addFolders(final TreeNode<UtilTreeNode> node, final List<UtilTreeNode> itemsFolders) {
        log.debug("Add FOLDERS "+itemsFolders.size());
        log.debug("Parent Node Name "+node.getData());
        int i = 1;
        final ILocationService locationService = getServicemanager().getApplicationServices().getLocationService();
        for (UtilTreeNode itemNode : itemsFolders) {
                final TreeNodeImpl<UtilTreeNode> nodeImpl = new TreeNodeImpl<UtilTreeNode>();
                log.debug("folder "+itemNode.getName());
                nodeImpl.setData(itemNode);
                //adding to principal node
                node.addChild(i, nodeImpl);
                i++;

              //add items if folder have.
                final List<UnitLocationBean> locationBeans =  locationService.retrieveLocationFolderItemsById(
                        itemNode.getId(), getSecurityContextUsername());
                log.debug("items on folder "+locationBeans.size());
                this.addItems(nodeImpl, ConvertDomainBean.convertItemToDragrable(locationBeans, TypeTreeNode.ITEM));

                //adding subfolders
                final List<UnitLocationFolder> unitLocationSubFolder = locationService
                      .retrieveLocationSubFolderByUser(itemNode.getId(), getSecurityContextUsername());
                log.debug("subfolders found "+unitLocationSubFolder.size());
                if(unitLocationSubFolder.size() > 0){
                    this.addFolders(nodeImpl, ConvertDomainBean.convertFolderToDragrable(unitLocationSubFolder, TypeTreeNode.FOLDER));
                }
        }
    }

    /**
     * Load Tree Node.
     * @return
     */
    public TreeNode<UtilTreeNode> getTreeNode() {
        if (rootNode == null) {
            this.loadTree();
        }
        return rootNode;
    }

    /**
     * Update Item Name.
     */
    public void updateTreeNodeName(){
        try{
            log.info("update name");
            getLocationService().updateLocationName(getDetailLocation(), getSecurityContextUsername());
            this.loadTree();
        }
        catch (Exception e) {
            log.error("error on update name "+e.getMessage());
        }
    }

    public void updateFolderName(){
        try{
            log.debug("Update folder Name to "+getDetailFolderLocation().getName());
            getLocationService().updateLocationFolder(getDetailFolderLocation(), getSecurityContextUsername(), "name");
            log.info("folder name updated");
            this.loadTree();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("error on update name "+e.getMessage());
        }
    }

    /**
     * Add New Item.
     */
    public void addNewItem(){
        try{
            log.info("Add New Item for "+getDetailFolderLocation().getId());
            getLocationService().createDefaultILocationItem(getDetailFolderLocation(), getSecurityContextUsername());
            this.loadTree();
            this.getItemsByLocationFolder();
        }
        catch (Exception e) {
             e.printStackTrace();
             log.error("error on update name "+e.getMessage());
        }
    }

    /**
     * Get Items by Location Folder.
     */
    private void getItemsByLocationFolder(){
            setItemsBySelectedFolder(getLocationService()
            .retrieveLocationFolderItemsById(getDetailFolderLocation().getId(), getSecurityContextUsername()));
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
            getLocationService().createLocationFolder(newFolder, getSecurityContextUsername());
            this.loadTree();
        }
        catch (Exception e) {
           e.printStackTrace();
           log.error("error "+e);
        }
    }

    /**
     * Delete Location Folder.
     */
    public void deleteLocationFolder(){
        try{
            log.info("deleting location folder");
            getLocationService().deleteLocationFolder(getDetailFolderLocation(), getSecurityContextUsername());
            setDetailFolderLocation(null);
            this.loadTree();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("error "+e);
        }
    }

    /**
     * Delete Location Folder.
     */
    public void deleteLocationItem(){
        try{
            log.info("deleting location item");
            getLocationService().deleteLocationItem(getDetailLocation(), getSecurityContextUsername());
            setDetailLocation(null);
            this.loadTree();
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("error "+e);
        }
    }

    /**
     * Process Selection.
     * @param event event
     */
    @SuppressWarnings("unchecked")
    public void processSelection(NodeSelectedEvent event) {
       //reset bean
       setDetailLocation(null);
       setDetailFolderLocation(null);
       HtmlTree tree = (HtmlTree) event.getComponent();
       log.info("tree "+tree);
       nodeTitle = (UtilTreeNode) tree.getRowData();
       log.info("nodeTitle "+nodeTitle.getId());
       log.info("nodeTitle "+nodeTitle.getName());
       selectedNodeChildren.clear();
       //get tree selected.
       final TreeNode<UtilTreeNode> currentNode = tree.getModelTreeNode(tree.getRowKey());
       log.info("currentNode "+currentNode.getData());
       if (currentNode.isLeaf()){
           selectedNodeChildren.add((UtilTreeNode)currentNode.getData());
       }
       else{
           Iterator<Entry<Object, TreeNode<UtilTreeNode>>> it = currentNode.getChildren();
           while (it!=null &&it.hasNext()) {
               Map.Entry<Object, TreeNode<UtilTreeNode>> entry = it.next();
               selectedNodeChildren.add(entry.getValue().getData());
           }
       }
       if(nodeTitle.getNode() == TypeTreeNode.ITEM){
           this.detailLocation = searchItemLocationDetail(nodeTitle);
           log.info("detailLocation "+detailLocation);
       }
       else{
           this.detailFolderLocation = searchFolderLocationDetail(nodeTitle);
           this.getItemsByLocationFolder();
           log.info("detailFolderLocation "+detailFolderLocation);
       }
    }

    /**
     *
     * @param event
     * @throws AbortProcessingException
     */
    public void processValueMarkeChange(ValueChangeEvent event) throws AbortProcessingException {
        final MarkerValue markerValue = (MarkerValue) event.getNewValue();
        log.info("this.detailLocation "+ this.detailLocation.getId());
        try {
           final PlaceMark placeMark = GMaps4JSFServiceFactory.getReverseGeocoderService().getPlaceMark(markerValue.getLatitude(), markerValue.getLongitude());
           log.info("Longitude "+ markerValue.getLongitude());
           log.info("Latitude "+ markerValue.getLatitude());
           getDetailLocation().setAccuracy(placeMark.getAccuracy());
           getDetailLocation().setCountryName(ignoreNull(placeMark.getCountryName()));
           getDetailLocation().setCountryCode(ignoreNull(placeMark.getCountryCode()));
           getDetailLocation().setAddress(ignoreNull(placeMark.getAddress()));
           getDetailLocation().setLat(Float.valueOf(markerValue.getLatitude()));
           getDetailLocation().setLng(Float.valueOf(markerValue.getLongitude()));
           log.info("item id moved "+getDetailLocation().getId());
           getLocationService().updateLocationMap(getDetailLocation(), getDetailLocation().getId(), getSecurityContextUsername());
        } catch (Exception ex) {
            log.error("error location map update");
        }
    }

    /**
     * Search Location Detail.
     * @param treeNode Selected Tree Node.
     * @return
     */
    private UnitLocationBean searchItemLocationDetail(final UtilTreeNode treeNode){
         log.info("searchLocationDetail "+treeNode.getId());
         final ILocationService locationService = getServicemanager().getApplicationServices().getLocationService();
         final UnitLocationBean retrievedLocation = locationService.getLocationItem(treeNode.getId(), getSecurityContextUsername());
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
     */
    private UnitLocationFolder searchFolderLocationDetail(final UtilTreeNode treeNode){
        log.info("searchLocationDetail "+treeNode.getId());
        final ILocationService locationService = getServicemanager().getApplicationServices().getLocationService();
        final UnitLocationFolder retrievedLocation = locationService.getFolderLocation(treeNode.getId(), getSecurityContextUsername());
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