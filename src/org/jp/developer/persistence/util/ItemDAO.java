package org.jp.developer.persistence.util;

public interface ItemDAO extends GenericDAO<Item, Long> {

    public static final String QUERY_MAXBID = "ItemDAO.QUERY_MAXBID";
    public static final String QUERY_MINBID = "ItemDAO.QUERY_MINBID";

    Bid getMaxBid(Long itemId);
    Bid getMinBid(Long itemId);

}
