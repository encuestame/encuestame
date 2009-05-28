package org.jp.core.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jp.core.persistence.dao.imp.ICatLocDao;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * encuestame: system online surveys Copyright (C) 2008-2009 encuestame
 * Development Team
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
 * Id: CatLocationDAO.java 1822 23/04/2009 13:22:54
 * 
 * @author juanpicado
 * @version 1.0 package org.jp.core.persistence.dao
 */
public class CatLocationDaoImp extends HibernateDaoSupport  implements ICatLocDao {

	
	private static Logger log = Logger.getLogger(CatLocationDaoImp.class);

	
	public CatLocationDaoImp() {
		super();
	}
	/**
	 * 
	 * @return
	 */
	public List getAccountsumById(){
		try{
			ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
			log.info("context->"+context);
			ApplicationContext appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
			log.info("ApplicationContext->"+appContext);		
			log.info("ApplicationContext getBeanDefinitionCount->"+appContext.getBeanDefinitionCount());
			log.info("**>"+appContext.getBean("sessionFactory"));
			SessionFactory sf = (SessionFactory) appContext.getBean("sessionFactory");
			setSessionFactory(sf);
			HibernateTemplate ht = new HibernateTemplate(sf);
			log.info("HibernateTemplate->"+ht);			
			setHibernateTemplate(ht);
			log.info("getHibernateTemplate->"+getHibernateTemplate());
			
			
			
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
				throws HibernateException, SQLException {
				Query query = session.createQuery("from CatLocation");
				List list = query.list();
				return list;
			}
		});	
		}catch (Exception e) {
			log.fatal(e);
			return null;
		}
		
	}

	
}
