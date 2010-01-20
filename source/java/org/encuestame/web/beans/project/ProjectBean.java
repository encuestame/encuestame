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
     *
     */
    public Boolean editDetail;
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
     * @return List of {@link UnitProjectBean}
     */
    public Collection<UnitProjectBean> loadListProjects(){
        return  getServicemanager().getDataEnMeSource()
                .loadListProjects();
    }

    /**
     * Save data new proyect
     */
    public void saveProyect() {
        try {
            log.info("save proyect");
            log.info("name->" + getBeanUProyect().getName());
            if (getBeanUProyect() != null) {
                getServicemanager().getDataEnMeSource().createProject(
                        getBeanUProyect());
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
        log.info("edit project selected->" + getProjectSelected());
        if (getProjectSelected() != null) {
            setNoProyects(false);
            setEditDetail(true);
            loadProjectInfo(getProjectSelected());
        } else {
            addWarningMessage("Error getProjectSelected", "");
        }
    }

    /**
     * loadProjectInfo
     *
     * @param id
     */
    private void loadProjectInfo(Integer id) {
        try {
            log.info("loadProjectInfo");
            cleanProyect();
            getBeanUProyect().setId(Long.valueOf(getProjectSelected()));
            setBeanUProyect(getServicemanager().getDataEnMeSource()
                    .loadProjectInfo(getBeanUProyect()));
            fullFormEditProject(getBeanUProyect());
            log.info("projecto Cargado");
        } catch (Exception e) {
            addErrorMessage("Error Cargando Datos Proyecto->" + e.getMessage(),
                    "");
        }
    }

    /**
     * full form edit project
     * @param project
     */
    private void fullFormEditProject(UnitProjectBean project) {
        log.info("fullFormEditProject");
        try {
            log.info("INFO EDIT PRO->"+project.getDescription());
            log.info("INFO EDIT PRO->"+project);
            setBeanUProyect(project);
            log.info("BEAN proyect->"+getBeanUProyect());
        } catch (Exception e) {
            addErrorMessage("Imposible Llena Formulario->" + e.getMessage(), "");
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
     * clear form project
     */
    private void cleanProyect() {
        getBeanUProyect().setDateFinish(null);
        getBeanUProyect().setDateInit(null);
        getBeanUProyect().setDescription(null);
        getBeanUProyect().setName(null);
        getBeanUProyect().setState(null);
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
     * @return the beanUProyect
     */
    public UnitProjectBean getBeanUProyect() {
        log.info("DDDDDDDget UnitProjectBean->"+unitProjectBean.getName());
        log.info("get UnitProjectBean->"+unitProjectBean);
        return unitProjectBean;
    }

    /**
     * @param beanUProyect
     *            the beanUProyect to set
     */
    public void setBeanUProyect(UnitProjectBean beanUProyect) {
        log.info("set UnitProjectBean->"+beanUProyect);
        this.unitProjectBean = beanUProyect;
    }

    /**
     * @return the list_unitBeans
     */
    public Collection<UnitProjectBean> getList_unitBeans() {
        try {
            loadListProjects();
            if (listProjectsBeans.size() > 0)
                setOneRow(true);
            else
                setOneRow(false);
            return listProjectsBeans;
        } catch (Exception e) {
            addErrorMessage("Error Cargando Datos->" + e.getMessage(), e
                    .getMessage());
            return null;
        }
    }

    /**
     * @param list_unitBeans
     *            the list_unitBeans to set
     */
    public void setList_unitBeans(Collection<UnitProjectBean> list_unitBeans) {
        this.listProjectsBeans = list_unitBeans;
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
    public void setEditDetail(Boolean editDetail) {
        this.editDetail = editDetail;
    }

}
