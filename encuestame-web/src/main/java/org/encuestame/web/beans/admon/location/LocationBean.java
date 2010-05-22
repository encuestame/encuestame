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

import com.sun.facelets.FaceletException;

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
    private List<UtilTreeNode> selectedNodeChildren = new ArrayList<UtilTreeNode>();

    private UnitLocationBean detailLocation;

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
            final List<UnitLocationFolder> locationFolders = locationService.retrieveLocationFolderByUser(getUsername());
            log.debug("location folders size "+locationFolders.size());
            addFolders(rootNode, ConvertDomainBean.convertFolderToDragrable(locationFolders, TypeTreeNode.FOLDER));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FaceletException(e.getMessage(), e);
        }
    }

    /**
     * Add Items.
     * @param node node
     * @param items list of items
     */
    private void addItems(final TreeNode<UtilTreeNode> node, final List<UtilTreeNode> items){
        int i = 1;
        for (UtilTreeNode utilTreeNode : items) {
            final TreeNodeImpl<UtilTreeNode> item = new TreeNodeImpl<UtilTreeNode>();
            item.setData(utilTreeNode);
            node.addChild(i, item);
            i++;
        }
    }


    //http://livedemo.exadel.com/richfaces-demo/richfaces/tree.jsf?tab=model&cid=418299
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
                        itemNode.getId(), getUsername());
                log.debug("items on folder "+locationBeans.size());
                this.addItems(nodeImpl, ConvertDomainBean.convertItemToDragrable(locationBeans, TypeTreeNode.ITEM));

                //adding subfolders
                final List<UnitLocationFolder> unitLocationSubFolder = locationService
                      .retrieveLocationSubFolderByUser(itemNode.getId(), getUsername());
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
            loadTree();
        }

        return rootNode;
    }

    /**
     * Process Selection.
     * @param event event
     */
    @SuppressWarnings("unchecked")
    public void processSelection(NodeSelectedEvent event) {
       //reset bean
       log.info("before detailLocation 1 "+detailLocation);
       //log.info("before detailLocation 2 "+detailLocation.getName());
       setDetailLocation(null);
       HtmlTree tree = (HtmlTree) event.getComponent();
       log.info("tree "+tree);
       nodeTitle = (UtilTreeNode) tree.getRowData();
       selectedNodeChildren.clear();
       //get tree selected.
       TreeNode<UtilTreeNode> currentNode = tree.getModelTreeNode(tree.getRowKey());
       log.info("currentNode "+currentNode);
       if (currentNode.isLeaf()){
           selectedNodeChildren.add((UtilTreeNode)currentNode.getData());
       }else
       {
           Iterator<Entry<Object, TreeNode<UtilTreeNode>>> it = currentNode.getChildren();
           log.info("it "+it);
           while (it!=null &&it.hasNext()) {
               Map.Entry<Object, TreeNode<UtilTreeNode>> entry = it.next();
               selectedNodeChildren.add(entry.getValue().getData());
           }
       }
       if(nodeTitle.getNode() == TypeTreeNode.ITEM){
           this.detailLocation = searchLocationDetail(nodeTitle);
       }
       else{
           log.info("SEARCHING FOLDER INFO");
       }
       log.info("detailLocation 1 "+detailLocation);
       log.info("detailLocation 2"+detailLocation.getName());
    }

    /**
     * Search Location Detail.
     * @param treeNode Selected Tree Node.
     * @return
     */
    private UnitLocationBean searchLocationDetail(final UtilTreeNode treeNode){
         log.info("searchLocationDetail "+treeNode);
         final ILocationService locationService = getServicemanager().getApplicationServices().getLocationService();
         final UnitLocationBean retrievedLocation = locationService.getLocationItem(treeNode.getId(), getUsername());
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
        log.info("getDetailLocation  "+detailLocation);
        return detailLocation;
    }

    /**
     * @param detailLocation the detailLocation to set
     */
    public void setDetailLocation(final UnitLocationBean detailLocation) {
        log.info("setDetailLocation  "+detailLocation);
        this.detailLocation = detailLocation;
    }
}