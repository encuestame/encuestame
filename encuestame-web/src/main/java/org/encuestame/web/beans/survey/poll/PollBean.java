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
package org.encuestame.web.beans.survey.poll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.utils.web.UnitFolder;
import org.encuestame.utils.web.UnitPoll;
import org.encuestame.web.beans.MasterBean;
import org.richfaces.component.UIPanelBar;
import org.richfaces.component.UIPanelBarItem;

/**
 * PollBean.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 9, 2010 1:53:50 PM
 * @version $Id:$
 */
public class PollBean extends MasterBean implements Serializable{

    /**
     * Serial.
     */
    private static final long serialVersionUID = 121208809431131995L;

    private List<UnitFolder> pollFolders;

    private UnitFolder newFolder = new UnitFolder();

    private UnitFolder selectedFolder;

    private List<UnitPoll> polls = new ArrayList<UnitPoll>();

    private UnitPoll pollSelected;

    private String selectedPanel;

    /**
     * @return the pollFolders
     */
    public List<UnitFolder> getPollFolders() {
        if(pollFolders == null){
            this.loadAllFolders();
        }
        return pollFolders;
    }

    /**
     *
     */
    private void loadAllFolders(){
          try {
              this.pollFolders = getPollService().retrieveFolderPoll(getUserPrincipalUsername());
          } catch (EnMeExpcetion e) {
             log.error(e);
             addErrorMessage(e.getMessage(), e.getMessage());
          }
    }

    public void panelBarChanged(final ValueChangeEvent evt) {
         log.debug("Got a value changed event, new value: #0, old value #1"+ evt.getNewValue()+ ""+evt.getOldValue());
         UIPanelBarItem barItem =
         (UIPanelBarItem) getFacesContext().getViewRoot().findComponent(evt.getNewValue().toString());
         UIPanelBar panelBar = barItem.getPanel();
         log.debug("selected panelBar: #0"+panelBar.getSelectedPanel());
         log.debug("submittedValue #0"+panelBar.getSubmittedValue());
         log.debug("value: #0"+panelBar.getValue());
         String name = (String) barItem.getName();
         log.debug("name: #0"+name);
     }

    /**
     * Load Poll Item View.
     */
    public void loadPollItemView(){
        log.debug(getPollSelected());
    }

    /**
     * Save New Folder.
     */
    public void saveNewFolder(){
        if(newFolder != null){
            try {
                getPollService().createPollFolder(newFolder.getFolderName(), getUserPrincipalUsername());
                this.loadAllFolders();
            } catch (EnMeDomainNotFoundException e) {
                log.error(e.getMessage());
                e.printStackTrace();
                addErrorMessage(e.getMessage(), "");
            }
        }
    }

    /**
     * Load Folder Items.
     */
    public void loadFolderItems(){
        if(getSelectedFolder() != null){
            log.debug("Items size "+getPolls().size());
               try {
                this.polls = getPollService().getPollsByFolder(getSelectedFolder(), getUserPrincipalUsername());
                log.debug("total polls "+this.polls.size());
            } catch (Exception e) {
               log.error(e);
               e.printStackTrace();
               addErrorMessage(e.getMessage(), "");
            } finally{
            }

        }
    }

    /**
     * @param pollFolders the pollFolders to set
     */
    public void setPollFolders(List<UnitFolder> pollFolders) {
        this.pollFolders = pollFolders;
    }

    /**
     * @return the newFolder
     */
    public UnitFolder getNewFolder() {
        return newFolder;
    }

    /**
     * @param newFolder the newFolder to set
     */
    public void setNewFolder(final UnitFolder newFolder) {
        this.newFolder = newFolder;
    }

    /**
     * @return the selectedFolder
     */
    public UnitFolder getSelectedFolder() {
        return selectedFolder;
    }

    /**
     * @param selectedFolder the selectedFolder to set
     */
    public void setSelectedFolder(UnitFolder selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    /**
     * @return the polls
     */
    public List<UnitPoll> getPolls() {
        return polls;
    }

    /**
     * @param polls the polls to set
     */
    public void setPolls(List<UnitPoll> polls) {
        this.polls = polls;
    }

    /**
     * @return the selectedPanel
     */
    public String getSelectedPanel() {
        return selectedPanel;
    }

    /**
     * @param selectedPanel the selectedPanel to set
     */
    public void setSelectedPanel(String selectedPanel) {
        this.selectedPanel = selectedPanel;
    }

    /**
     * @return the pollSelected
     */
    public UnitPoll getPollSelected() {
        return pollSelected;
    }

    /**
     * @param pollSelected the pollSelected to set
     */
    public void setPollSelected(UnitPoll pollSelected) {
        this.pollSelected = pollSelected;
    }


}
