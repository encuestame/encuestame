package org.jp.core.persistence.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.core.persistence.dao.imp.ICatState;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
/**
 * encuestame:  system online surveys
 * Copyright (C) 2005-2008 encuestame Development Team
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
 * Id: CatStateDaoImp.java Date: 26/04/2009 
 * @author juanpicado
 * package: org.jp.core.persistence.dao
 * @version 1.0
 */
public class CatStateDaoImp extends HibernateDaoSupport implements ICatState {
	
	private Log logger = LogFactory.getLog(this.getClass());
	
	

	public void delete(Object obj) {
		// TODO Auto-generated method stub
		
	}

	public Object find(Class clazz, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer lastRow(Class clase, String id)
			{
		// TODO Auto-generated method stub
		return null;
	}

	public void saveOrUpdate(Object obj) {
		// TODO Auto-generated method stub
		
	}

}
