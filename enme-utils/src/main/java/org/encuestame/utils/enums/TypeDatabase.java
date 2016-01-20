
package org.encuestame.utils.enums;


/**
 * Define type of database install supported by encuestame.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 21, 2011
 */
public enum TypeDatabase {
        ORACLE("ORACLE"), MYSQL("MYSQL"), POSTGRES("POSTGRES"), MSSQL("MSSQL"), DERBY("DERBY"), HSQLDB("HSQLDB"), DB2("DB2");

    private String optionAsString;

    /**
     * Constructor.
     */
    TypeDatabase(final String optionAsString){
        this.optionAsString = optionAsString;
    }

    /**
     * Return the database selected.
     * @param database type of database
     * @return
     */
    public static TypeDatabase getTypeDatabaseByString(final String database) {
        if (null == database) { return HSQLDB; }
        else if (database.equalsIgnoreCase("oracle")) { return ORACLE; }
        else if (database.equalsIgnoreCase("hsqldb")) { return HSQLDB; }
        else if (database.equalsIgnoreCase("mysql")) { return MYSQL; }
        else if (database.equalsIgnoreCase("postgres")) { return POSTGRES; }
        else if (database.equalsIgnoreCase("mssql")) { return MSSQL; }
        else if (database.equalsIgnoreCase("derby")) { return DERBY; }
        else if (database.equalsIgnoreCase("db2")) { return DB2; }
        else return HSQLDB;
    }
}
