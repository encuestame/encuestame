
package org.encuestame.utils.enums;


/**
 * Define type of database install supported by encuestame.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 21, 2011
 */
public enum TypeDatabase {
    ORACLE("ORACLE"),
    MYSQL("MYSQL"),
    POSTGRES("POSTGRES"),
    MSSQL("MSSQL"),
    DERBY("DERBY"),
    HSQLDB("HSQLDB"),
    DB2("DB2");

    /**
     *
     */
    private String optionAsString;

    /**
     * Constructor.
     */
    TypeDatabase(final String optionAsString){
        this.optionAsString = optionAsString;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
       return this.optionAsString;
    }
}
