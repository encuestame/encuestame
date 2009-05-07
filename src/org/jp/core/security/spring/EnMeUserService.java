package org.jp.core.security.spring;

import org.jp.core.persistence.pojo.SecUsers;

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
 * Id: EnMeUserService.java Date: 07/05/2009 14:20:30
 * @author juanpicado
 * package: org.jp.core.security.spring
 * @version 1.0
 */
public interface EnMeUserService {
	
	/** 
	 * Returns Survey User by Name 
	 */
	public SecUsers getSurveyUser(String username);
	
	
	public String getSurveyUserPassword(String username);

}
