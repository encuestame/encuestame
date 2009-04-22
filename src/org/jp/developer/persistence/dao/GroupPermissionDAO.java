package org.jp.developer.persistence.dao;
// Generated 01-13-2009 09:54:12 PM by Hibernate Tools 3.2.2.GA


import java.util.List;
import javax.naming.InitialContext;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.jp.developer.persistence.pojo.CatState;
import org.jp.developer.persistence.util.DataAccessLayerException;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class GroupPermission.
 * @see org.jp.developer.persistence.dao.GroupPermission
 * @author Hibernate Tools
 */
public class GroupPermissionDAO extends AbstractDao{

    private static final Log log = LogFactory.getLog(GroupPermissionDAO.class);

    public GroupPermissionDAO() {
		super();
	}

	
	public void updateState(CatState state)
			throws DataAccessLayerException {
		log.debug("update state instance");
		super.saveOrUpdate(state);
		log.debug("Update state successful");
	}

	
	public void deleteState(CatState state)
			throws DataAccessLayerException {
		log.debug("delete state instance");
		super.delete(state);
		log.debug("delete state successful");

	}

	/**
	 * 
	 * @param estado
	 * @throws DataAccessLayerException
	 */
	public void createState(CatState state)
			throws DataAccessLayerException {
		super.saveOrUpdate(state);
	}

	/**
	 * Finds all BmEstados in the database.
	 * 
	 * @return
	 */
	public List<CatState> listAllStates() throws DataAccessLayerException {
		return super.findAll(CatState.class);
	}

	/**
	 * Find an Event by its primary key.
	 * 
	 * @param id
	 * @return
	 */
	public CatState findStateById(Integer id) throws DataAccessLayerException {
		return (CatState) super.find(CatState.class, id);
	}
    
   
}

