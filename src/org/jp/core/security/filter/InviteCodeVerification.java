package org.jp.core.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2009  encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * Id: InviteCodeVerification.java Date: 20/05/2009 16:34:24
 * @author juanpicado
 * package: org.jp.core.security.filter
 * @version 1.0
 */
public class InviteCodeVerification implements Filter {

	private Log log = LogFactory.getLog(this.getClass());	
	
	public void destroy() {
		log.info("InviteCodeVerification FILTER DSTROY");

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		log.info("InviteCodeVerification FILTER doFilter");

	}

	public void init(FilterConfig arg0) throws ServletException {
		log.info(" InviteCodeVerification FILTER init");

	}
}
