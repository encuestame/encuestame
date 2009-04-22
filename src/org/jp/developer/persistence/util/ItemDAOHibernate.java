package org.jp.developer.persistence.util;

import org.hibernate.Query;

public class ItemDAOHibernate
extends     GenericHibernateDAO<Item, Long>
implements  ItemDAO {

public Bid getMaxBid(Long itemId) {
Query q = getSession().getNamedQuery(ItemDAO.QUERY_MAXBID);
q.setParameter("itemid", itemId);
return (Bid) q.uniqueResult();
}

public Bid getMinBid(Long itemId) {
Query q = getSession().getNamedQuery(ItemDAO.QUERY_MINBID);
q.setParameter("itemid", itemId);
return (Bid) q.uniqueResult();
}

}

