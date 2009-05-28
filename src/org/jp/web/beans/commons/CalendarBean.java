package org.jp.web.beans.commons;

import java.util.Date;
import java.util.Locale;

import javax.faces.event.ValueChangeEvent;

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
 * Id: CalendarBean.java Date: 11/05/2009 16:39:17
 * 
 * @author juanpicado package: org.jp.web.beans.commons
 * @version 1.0
 */

public class CalendarBean {

	private Locale locale;
	private boolean popup;
	private String pattern;
	private Date selectedDate;
	private boolean showApply=true;
	private boolean useCustomDayLabels;

	private String listaCredenciales;
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public CalendarBean() {

		locale = Locale.US;
		popup = true;
		pattern = "d/M/yy HH:mm";
	}
		
	public void selectLocale(ValueChangeEvent event) {

		String tLocale = (String) event.getNewValue();
		if (tLocale != null) {
			String lang = tLocale.substring(0, 2);
			String country = tLocale.substring(3);
			locale = new Locale(lang, country, "");
		}
	}

	public boolean isUseCustomDayLabels() {
		return useCustomDayLabels;
	}

	public void setUseCustomDayLabels(boolean useCustomDayLabels) {
		this.useCustomDayLabels = useCustomDayLabels;
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public boolean isShowApply() {
		return showApply;
	}

	public void setShowApply(boolean showApply) {
		this.showApply = showApply;
	}
	
	

}