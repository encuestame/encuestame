
package org.encuestame.business.setup.install;


/**
 * Define type of database install suported by encuestame.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 21, 2011
 */
public enum TypeDatabase {
    ORACLE, MYSQL, POSTGRES, MSSQL, DERBY, HSQLDB, DB2,

    /**
     * Constructor.
     */
    TypeDatabase(){};

    public static TypeDatabase getTypeDatabaseByString(final String database) {
        if (null == database) { return HSQLDB; }
        else if (database.equalsIgnoreCase("oracle")) { return ORACLE; }
        else if (database.equalsIgnoreCase("mysql")) { return MYSQL; }
        else if (database.equalsIgnoreCase("postgres")) { return POSTGRES; }
        else if (database.equalsIgnoreCase("mssql")) { return MSSQL; }
        else if (database.equalsIgnoreCase("derby")) { return DERBY; }
        else if (database.equalsIgnoreCase("db2")) { return DB2; }
        else return HSQLDB;
    }
}
