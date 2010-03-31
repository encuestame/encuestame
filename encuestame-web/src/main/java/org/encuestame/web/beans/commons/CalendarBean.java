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

import java.util.Date;
import java.util.Locale;

import javax.faces.event.ValueChangeEvent;
/**
 * Calendar Bean.
 * @author Picado, Juan juan@encuestame.org
 * @since 11/05/2009 16:39:17
 * @version $Id$
 */
public class CalendarBean {

    private Locale locale;
    private boolean popup;
    private String pattern;
    private Date selectedDate;
    private boolean showApply=true;
    private boolean useCustomDayLabels;

    private String listaCredenciales;

    public final Locale getLocale() {
        return locale;
    }

    public final void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public final boolean isPopup() {
        return popup;
    }

    public final void setPopup(final boolean popup) {
        this.popup = popup;
    }

    public final String getPattern() {
        return pattern;
    }

    public final void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    public CalendarBean() {

        locale = Locale.US;
        popup = true;
        pattern = "d/M/yy HH:mm";
    }

    public final void selectLocale(ValueChangeEvent event) {

        String tLocale = (String) event.getNewValue();
        if (tLocale != null) {
            String lang = tLocale.substring(0, 2);
            String country = tLocale.substring(3);
            locale = new Locale(lang, country, "");
        }
    }

    public final boolean isUseCustomDayLabels() {
        return useCustomDayLabels;
    }

    public final void setUseCustomDayLabels(final boolean useCustomDayLabels) {
        this.useCustomDayLabels = useCustomDayLabels;
    }

    public final Date getSelectedDate() {
        return selectedDate;
    }

    public final void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public final boolean isShowApply() {
        return showApply;
    }

    public final void setShowApply(final boolean showApply) {
        this.showApply = showApply;
    }



}
