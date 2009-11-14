/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
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
 */
package org.encuestame.core.persistence.util;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

/**
 * EnMeSchemaExport.
 * @author Picado, Juan juan@encuestame.org
 * @since October 19, 2009
 */
public class EnMeSchemaExport {

    /** spring config files. **/
     private static final String[] SPRING_CONFIG_FILES =
         new String[]{
         "source/config/spring/encuestame-hibernate-context.xml",
         "source/config/spring/encuestame-param-context.xml"
         };

     /**
      * Drop schema and create schema.
      */
     public void create(){
         final FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext(SPRING_CONFIG_FILES);
         final AnnotationSessionFactoryBean annotationSF = (AnnotationSessionFactoryBean)  appContext.getBean("&sessionFactory");
         //annotationSF.dropDatabaseSchema();
         annotationSF.createDatabaseSchema();
     }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new EnMeSchemaExport().create();
    }

}
