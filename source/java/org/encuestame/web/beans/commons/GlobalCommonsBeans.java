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
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Id: GlobalCommonsBeans.java Date: 11/05/2009 16:39:17
 *
 * @author juanpicado package: org.encuestame.web.beans.commons
 * @version 1.0
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
