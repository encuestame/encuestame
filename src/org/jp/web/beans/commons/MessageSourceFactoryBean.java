package org.jp.web.beans.commons;

import java.util.Locale;

import javax.faces.context.FacesContext;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

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
 * Id: MessageSourceFactoryBean.java Date: 12/05/2009 9:17:43
 * 
 * @author juanpicado package: org.jp.web.beans.commons
 * @version 1.0
 */
public class MessageSourceFactoryBean implements MessageSource {

	private MessageSource messagesource;

	public void setMessagesource(MessageSource messagesource) {
		this.messagesource = messagesource;
	}

	/**
	 * 
	 */
	public String getMessage(MessageSourceResolvable resolvable, Locale locale)
			throws NoSuchMessageException {
		return messagesource.getMessage(resolvable, getDefaultLocale(locale));
	}
	
	/**
	 * 
	 */
	public String getMessage(String code, Object[] args, Locale locale)
			throws NoSuchMessageException {
		return messagesource.getMessage(code, args, code,
				getDefaultLocale(locale));

	}
	
	/**
	 * 
	 */
	public String getMessage(String code, Object[] args, String defaultMessage,
			Locale locale) {
		return messagesource.getMessage(code, args, defaultMessage,
				getDefaultLocale(locale));
	}

	/**
	 * get default locate for faces context
	 * 
	 * @param locale
	 * @return
	 */
	protected Locale getDefaultLocale(Locale locale) {
		if (locale != null) {
			return locale;
		}
		// try to get it from faces context
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			return context.getViewRoot().getLocale();
		} else {
			return null;
		}

	}
}
