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

import org.encuestame.core.service.ILocationService;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationFolder;
import org.encuestame.web.beans.MasterBean;
import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

import com.sun.facelets.FaceletException;

/**
 * Location Bean.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 26/05/2009 12:58:17
 * @version $Id$
 **/
public class LocationBean extends MasterBean implements Serializable {

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
    private TreeNode rootNode = null;
    private String nodeTitle;
    private List<String> selectedNodeChildren = new ArrayList<String>();

    private UnitLocationBean[] locations = new UnitLocationBean[10];

    public LocationBean() {
        final UnitLocationBean a = new UnitLocationBean();
        final UnitLocationBean b = new UnitLocationBean();
        locations[0] = a;
        locations[1] = b;

    }

    /**
     * @return the locations
     */
    public UnitLocationBean[] getLocations() {
        return locations;
    }

    /**
     * @param locations
     *            the locations to set
     */
    public void setLocations(UnitLocationBean[] locations) {
        this.locations = locations;
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
            rootNode = new TreeNodeImpl();
            final ILocationService locationService = getServicemanager().getApplicationServices().getLocationService();
            final List<UnitLocationFolder> locationFolders = locationService.retrieveLocationFolderByUser(getUsername());
            log.debug("location folders size "+locationFolders.size());
            addFolders(rootNode, locationFolders);
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
    private void addItems(final TreeNode node, final List<UnitLocationBean> items){
        int i = 1;
        for (UnitLocationBean unitLocationBean : items) {
            final TreeNodeImpl<String> item = new TreeNodeImpl<String>();
            item.setData(unitLocationBean.getDescription());
            node.addChild(i, item);
            i++;
        }
    }


    //http://livedemo.exadel.com/richfaces-demo/richfaces/tree.jsf?tab=model&cid=418299
    private void addFolders(final TreeNode node, final List<UnitLocationFolder> locationFolders) {
        int i = 1;
        final ILocationService locationService = getServicemanager().getApplicationServices().getLocationService();
        for (UnitLocationFolder unitLocationFolder : locationFolders) {
                final TreeNodeImpl<String> nodeImpl = new TreeNodeImpl<String>();
                log.debug("folder "+unitLocationFolder.getName());
                nodeImpl.setData(unitLocationFolder.getName());
                //adding to principal node
                node.addChild(i, nodeImpl);
                i++;
                //add items if folder have.
                final List<UnitLocationBean> locationBeans =  locationService.retrieveLocationFolderItemsById(
                        unitLocationFolder.getLocationFolderId(), getUsername());
                log.debug("items on folder "+locationBeans.size());
                this.addItems(nodeImpl, locationBeans);
        }
         /*TreeNodeImpl nodeImpl2 = new TreeNodeImpl();
         nodeImpl2.setData("folder 2");
         node.addChild(new Integer(2), nodeImpl2);

         TreeNodeImpl nodeImpl3 = new TreeNodeImpl();
         nodeImpl3.setData("folder 3");
         nodeImpl2.addChild(new Integer(1), nodeImpl3);*/
        //addNodes(key, nodeImpl, properties);
    }

    /**
     * Load Tree Node.
     * @return
     */
    public TreeNode getTreeNode() {
        if (rootNode == null) {
            loadTree();
        }

        return rootNode;
    }

    /**
     * Process Selection.
     * @param event event
     */
    public void processSelection(NodeSelectedEvent event) {
       log.info("event "+event);
       HtmlTree tree = (HtmlTree) event.getComponent();
       log.info("tree "+tree);
       nodeTitle = (String) tree.getRowData();
       log.info("nodeTitle "+nodeTitle);
       selectedNodeChildren.clear();
       TreeNode currentNode = tree.getModelTreeNode(tree.getRowKey());
       log.info("currentNode "+currentNode);
       if (currentNode.isLeaf()){
           selectedNodeChildren.add((String)currentNode.getData());
       }else
       {
           Iterator<Map.Entry<Object, TreeNode>> it = currentNode.getChildren();
           log.info("it "+it);
           while (it!=null &&it.hasNext()) {
               Map.Entry<Object, TreeNode> entry = it.next();
               selectedNodeChildren.add(entry.getValue().getData().toString());
           }
       }
    }

    public TreeNodeImpl<UnitLocationFolder> getTreeNodes() {
        TreeNodeImpl<UnitLocationFolder> rootNode = new TreeNodeImpl<UnitLocationFolder>();

        TreeNode node = new TreeNodeImpl<UnitLocationFolder>();
        final UnitLocationFolder item = new UnitLocationFolder();
        node.setData(item);
        node.setParent(rootNode);
        rootNode.addChild("someIdentifier", node);
        return rootNode;
    }

    /**
     * @return the nodeTitle
     */
    public String getNodeTitle() {
        return nodeTitle;
    }

    /**
     * @param nodeTitle the nodeTitle to set
     */
    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }
}
