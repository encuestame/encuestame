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
package org.encuestame.web.beans.project;

import java.util.Collection;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.web.beans.MasterBean;

/**
 * Project Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since 26/05/2009 10:25:05
 * @version $Id$
 */
public class ProjectBean extends MasterBean {

    /**
     *
     */
    public Boolean noProyects = true;
    /**
     *
     */
    public Boolean create = true;

    /**
     *
     */
    public Boolean edit;

    /**
     * Show the edit view.
     */
    public Boolean editDetail = false;
    /**
     * {@link UnitProjectBean}.
     */
    private UnitProjectBean unitProjectBean = new UnitProjectBean();


    /**
     * New Project Bean.
     */
    private UnitProjectBean newProjectBean = new UnitProjectBean();

    /**
     * Project Id selected.
     */
    private Integer projectSelected;

    /**
     * List of Projects.
     */
    private Collection<UnitProjectBean> listProjectsBeans;

    /**
     * Constructor.
     */
    public ProjectBean() {}

    /**
     * Load List of Projects.
     */
    private void loadListProjects(){
        try{
            setListProjectsBeans(getServicemanager().getDataEnMeSource()
                                .loadListProjects(getUsernameByName().getSecUser().getUid()));
        }catch (EnMeExpcetion e) {
            log.error(e);
            addErrorMessage("", "error loading projects");
        }
    }

    /**
     * Save data new proyect
     */
    public final void saveProject() {
        try {
            getNewProjectBean().setUserId(getUsernameByName().getSecUser().getUid());
            if (this.getNewProjectBean() != null) {
                getServicemanager().getDataEnMeSource().createProject(getNewProjectBean());
                log.info("project created");
                setNewProjectBean(new UnitProjectBean());
            }
            else {
                log.error("error create project");
                addErrorMessage("error saving project", "error saving project");
            }
        } catch (EnMeExpcetion e) {
            e.printStackTrace();
            addErrorMessage(e.getMessage(), e.getMessage());
            log.error("error create project:" + e);
        }
    }

    /**
     *
     */
    public final void editProject() {
        if (getProjectSelected() != null) {
            log.info("project selected " + getProjectSelected());
            //render edit detail view
            this.setEditDetail(Boolean.TRUE);
            //charge load project info
            this.loadProjectInfo(getProjectSelected());
        } else {
            log.warn("error get project selected");
            addWarningMessage("Error getProjectSelected", "");
        }
    }

    /**
     * Load {@link Project} by  {@link UnitProjectBean} Id.
     * @param id project id.
     */
    private void loadProjectInfo(final Integer projectId) {
        try {
            this.cleanProyect();
            //setting id
            getUnitProjectBean().setId(Long.valueOf(projectId));
            // load project by id.
            setUnitProjectBean(getServicemanager().getDataEnMeSource()
                    .loadProjectInfo(getUnitProjectBean()));
            getUnitProjectBean().setClients(getServicemanager()
                                .getDataEnMeSource().loadSelecItemClientsByProjectId(Long.valueOf(projectId)));
            getUnitProjectBean().setListUsers(getSecurityService().loadSelectItemSecondaryUser(getUsernameByName().getSecUser().getUid()));
            log.info("project loaded.");
            log.debug("project id"+getUnitProjectBean().getId());
            log.info("project name"+getUnitProjectBean().getName());
            log.info("project init."+getUnitProjectBean().getDateInit());
            log.info("project dead."+getUnitProjectBean().getDateFinish());
            log.info("project grops."+getUnitProjectBean().getGroupList().size());
        }
        catch (EnMeExpcetion e) {
               log.error("error load project info"+ e);
               addErrorMessage("Error Cargando Datos Proyecto->" + e.getMessage(),"");
        }
    }

    /**
     *
     */
    public final void deselectedProject(){
        log.info("deselectedProject");
        cleanProyect();
        setEditDetail(false);
        setNoProyects(true);
    }

    /**
     * clear form project.
     */
    private void cleanProyect() {
       log.debug("clearing unit bean");
       setUnitProjectBean(new UnitProjectBean());
    }

    /**
     * change to create form
     */
    public final void changeCreate() {
        try {
            log.info("changeCreate");
            deselectedProject();
            setCreate(true);
            setEdit(false);
            log.info("Create " + getCreate());
            log.info("Edit " + getEdit());
        } catch (Exception e) {
            log.error("change create form error: "+e);
            addErrorMessage("No se puede cambiar->" + e, e.getMessage());
        }
    }

    /**
     * Change view to List of Projects.
     */
    public final void changeEdit() {
        log.info("changeEdit");
        deselectedProject();
        setCreate(false);
        setEdit(true);
        log.info("Create " + getCreate());
        log.info("Edit " + getEdit());
    }

    /**
     * @return the noProyects
     */
    public final Boolean getNoProyects() {
        return noProyects;
    }

    /**
     * @param noProyects
     *            the noProyects to set
     */
    public final void setNoProyects(final Boolean noProyects) {
        this.noProyects = noProyects;
    }

    /**
     * @return the create
     */
    public final Boolean getCreate() {
        return create;
    }

    /**
     * @param create
     *            the create to set
     */
    public final void setCreate(final Boolean create) {
        this.create = create;
    }

    /**
     * @return the edit
     */
    public final Boolean getEdit() {
        return edit;
    }

    /**
     * @param edit
     *            the edit to set
     */
    public final void setEdit(final Boolean edit) {
        this.edit = edit;
    }


    /**
     * @return the unitProjectBean
     */
    public final UnitProjectBean getUnitProjectBean() {
        log.debug("get "+unitProjectBean);
        return unitProjectBean;
    }

    /**
     * @param unitProjectBean the unitProjectBean to set
     */
    public final void setUnitProjectBean(final UnitProjectBean unitProjectBean) {
        log.debug("set "+unitProjectBean);
        this.unitProjectBean = unitProjectBean;
    }

    /**
     * @return the listProjectsBeans
     */
    public final Collection<UnitProjectBean> getListProjectsBeans() {
        this.loadListProjects();
        return listProjectsBeans;
    }

    /**
     * @param listProjectsBeans the listProjectsBeans to set
     */
    public final void setListProjectsBeans(final Collection<UnitProjectBean> listProjectsBeans) {
        this.listProjectsBeans = listProjectsBeans;
    }

    /**
     * @return the projectSelected
     */
    public final Integer getProjectSelected() {
        log.info("projectSelected->" + projectSelected);
        return projectSelected;
    }

    /**
     * @param projectSelected
     *            the projectSelected to set
     */
    public final void setProjectSelected(final Integer projectSelected) {
        this.projectSelected = projectSelected;
    }

    /**
     * @return the editDetail
     */
    public final Boolean getEditDetail() {
        return editDetail;
    }

    /**
     * @param editDetail
     *            the editDetail to set
     */
    public final void setEditDetail(final Boolean editDetail) {
        log.info("editDetail "+editDetail);
        this.editDetail = editDetail;
    }

    /**
     * @return the newProjectBean
     */
    public final UnitProjectBean getNewProjectBean() {
        return newProjectBean;
    }

    /**
     * @param newProjectBean the newProjectBean to set
     */
    public final void setNewProjectBean(final UnitProjectBean newProjectBean) {
        this.newProjectBean = newProjectBean;
    }

}
