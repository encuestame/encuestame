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
package org.encuestame.web.beans.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.web.beans.MasterBean;
import org.encuestame.web.beans.admon.UnitPermission;
import org.encuestame.web.beans.survey.UnitPatternBean;
/**
 * Global Commons Beans.
 * @author Picado, Juan juan@encuestame.org
 * @since 11/05/2009 16:39:17
 * @version $Id$
 */
public class GlobalCommonsBeans extends MasterBean {

    private Collection lista = null;
    private List<SelectItem> select = null;
    private Log log = LogFactory.getLog(this.getClass());

    public GlobalCommonsBeans() {
        log.info("init GlobalCommonsBeans");
    }

    /**
     * load selectItem state
     *
     * @return
     */
    public List<SelectItem> getLoadListState() {
        lista = new LinkedList();
        select = new ArrayList<SelectItem>();
        // log.info("get load list state");
        select.add(new SelectItem(null, ""));
        lista = getServicemanager().getDataEnMeSource().getStateDao()
                .findAll();
        // log.info("get load list state total->" + lista.size());
        if (lista != null && lista.size() != 0) {
            Iterator iterd = lista.iterator();
            while (iterd.hasNext()) {
                CatState state = (CatState) iterd.next();
                select.add(new SelectItem(state.getIdState(), state
                        .getDescState()));
            }
        }
        return select;
    }

    /**
     * load list of permisssions
     *
     * @return
     */
    public List<SelectItem> getLoadListPermissions() {
        lista = new LinkedList();
        select = new LinkedList<SelectItem>();
        select.add(new SelectItem(null, ""));
        lista = getServicemanager().getApplicationServices().getSecurityService()
                .loadAllListPermission();
        if (lista != null && lista.size() != 0) {
            Iterator iterd = lista.iterator();
            while (iterd.hasNext()) {
                UnitPermission permission = (UnitPermission) iterd.next();
                select.add(new SelectItem(permission.getId(), permission
                        .getDescription()));
            }
        }
        return select;
    }

    /**
     * load list patron
     * @return
     */
    public List<SelectItem> getLoadListQuestionPattern() {
        lista = new LinkedList();
        select = new LinkedList<SelectItem>();
        select.add(new SelectItem(null, ""));
        try {
            lista = getServicemanager().getApplicationServices()
            .getSecurityService().getSurveyService().loadAllPatrons();
            if (lista != null && lista.size() != 0) {
                Iterator iterd = lista.iterator();
                while (iterd.hasNext()) {
                    UnitPatternBean patron = (UnitPatternBean) iterd.next();
                    select.add(new SelectItem(patron.getId(), patron
                            .getPatronType()));
                }
            }
        } catch (HibernateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return select;
    }

}
