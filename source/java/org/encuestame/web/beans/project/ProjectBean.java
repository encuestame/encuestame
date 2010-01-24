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
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.web.beans.MasterBean;
import org.hibernate.HibernateException;

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
    private UnitProjectBean unitProjectBean;

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
            setListProjectsBeans(getServicemanager().getDataEnMeSource().loadListProjects());
        }catch (EnMeExpcetion e) {
            addErrorMessage("", "error loading projects");
        }
    }

    /**
     * Save data new proyect
     */
    public void saveProyect() {
        try {
            log.info("save proyect");
            log.info("name->" + getUnitProjectBean().getName());
            if (getUnitProjectBean() != null) {
                getServicemanager().getDataEnMeSource().createProject(
                        getUnitProjectBean());
                log.info("projecto creado");
                addInfoMessage("Proyecto Creado", "");
                cleanProyect();
            } else {
                log.error("error create project");
                addErrorMessage(
                        "No se pudo recuperar los datos del formulario", "");
            }
        } catch (HibernateException e) {
            log.error("error create project" + e);
            addErrorMessage("1Error Creando Proyecto", "");
        } catch (EnMeExpcetion e) {
            log.error("error create project " + e);
            addErrorMessage("2Error Creando Proyecto", "");
        } catch (Exception e) {
            log.error("error create project " + e);
            addErrorMessage("3Error Creando Proyecto", "");
        }
    }

    /**
     *
     */
    public void editProject() {
        if (getProjectSelected() != null) {
            log.info("project selected->" + getProjectSelected());
            //render edit detail view
            this.setEditDetail(Boolean.TRUE);
            //charge load project info
            this.loadProjectInfo(getProjectSelected());
        } else {
            addWarningMessage("Error getProjectSelected", "");
        }
    }

    /**
     * Load {@link Project} by  {@link UnitProjectBean} Id.
     * @param id project id.
     */
    private void loadProjectInfo(final Integer id) {
        try {
            this.cleanProyect();
            //setting id
            getUnitProjectBean().setId(Long.valueOf(getProjectSelected()));
            // load project by id.
            setUnitProjectBean(getServicemanager().getDataEnMeSource()
                    .loadProjectInfo(getUnitProjectBean()));
            log.info("project loaded.");
            log.debug("project id"+getUnitProjectBean().getId());
            log.info("project name"+getUnitProjectBean().getName());
            log.info("project init."+getUnitProjectBean().getDateInit());
            log.info("project dead."+getUnitProjectBean().getDateFinish());
        }
        catch (EnMeExpcetion e) {
               addErrorMessage("Error Cargando Datos Proyecto->" + e.getMessage(),"");
        }
    }

    /**
     *
     */
    public void deselectedProject(){
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
    public void changeCreate() {
        try {
            log.info("changeCreate");
            deselectedProject();
            setCreate(true);
            setEdit(false);
            log.info("Create " + getCreate());
            log.info("Edit " + getEdit());
        } catch (Exception e) {
            addErrorMessage("No se puede cambiar->" + e, e.getMessage());
        }
    }

    /**
     * Change view to List of Projects.
     */
    public void changeEdit() {
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
    public Boolean getNoProyects() {
        return noProyects;
    }

    /**
     * @param noProyects
     *            the noProyects to set
     */
    public void setNoProyects(Boolean noProyects) {
        this.noProyects = noProyects;
    }

    /**
     * @return the create
     */
    public Boolean getCreate() {
        return create;
    }

    /**
     * @param create
     *            the create to set
     */
    public void setCreate(Boolean create) {
        this.create = create;
    }

    /**
     * @return the edit
     */
    public Boolean getEdit() {
        return edit;
    }

    /**
     * @param edit
     *            the edit to set
     */
    public void setEdit(Boolean edit) {
        this.edit = edit;
    }


    /**
     * @return the unitProjectBean
     */
    public UnitProjectBean getUnitProjectBean() {
        log.debug("get "+unitProjectBean);
        return unitProjectBean;
    }

    /**
     * @param unitProjectBean the unitProjectBean to set
     */
    public void setUnitProjectBean(final UnitProjectBean unitProjectBean) {
        log.debug("set "+unitProjectBean);
        this.unitProjectBean = unitProjectBean;
    }

    /**
     * @return the listProjectsBeans
     */
    public Collection<UnitProjectBean> getListProjectsBeans() {
        this.loadListProjects();
        return listProjectsBeans;
    }

    /**
     * @param listProjectsBeans the listProjectsBeans to set
     */
    public void setListProjectsBeans(final Collection<UnitProjectBean> listProjectsBeans) {
        this.listProjectsBeans = listProjectsBeans;
    }

    /**
     * @return the projectSelected
     */
    public Integer getProjectSelected() {
        log.info("projectSelected->" + projectSelected);
        return projectSelected;
    }

    /**
     * @param projectSelected
     *            the projectSelected to set
     */
    public void setProjectSelected(Integer projectSelected) {
        this.projectSelected = projectSelected;
    }

    /**
     * @return the editDetail
     */
    public Boolean getEditDetail() {
        return editDetail;
    }

    /**
     * @param editDetail
     *            the editDetail to set
     */
    public void setEditDetail(final Boolean editDetail) {
        log.info("editDetail "+editDetail);
        this.editDetail = editDetail;
    }

}
