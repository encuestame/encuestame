/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.test.config;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.encuestame.core.util.XMLConfigurationFileSupport;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Test for {@link XMLConfigurationFileSupport}.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 4, 2011
 */
public class XmlConfigurationText extends TestCase {

    /**
     * @throws ConfigurationException
     *
     */
    @Test
    public void testXmlFile() {
        // final ConfigurationManager configurationManager = new
        // ConfigurationManager("encuestame-config.xml");
        // System.out.println(configurationManager.getIntProperty("encuestame.database.version"));
        XMLConfiguration xmlConfiguration = null;
        try {
            Resource res = new ClassPathResource(
                    "properties-test/encuestame-test-config.xml");
            // System.out.println(res.getFilename());
            // System.out.println(res.getFile().getAbsolutePath());
            xmlConfiguration = new XMLConfiguration(res.getFile());
            // System.out.println(xmlConfiguration.getString("encuestame.database.version"));
            // System.out.println(xmlConfiguration.getString("database.version"));
            xmlConfiguration.setAutoSave(true);
            xmlConfiguration.addProperty("juan", "juan");

            // System.out.println(xmlConfiguration.getString("administration"));
            // System.out.println(xmlConfiguration.getString("version"));

            // System.out.println(xmlConfiguration.getRootElementName());
            // System.out.println(xmlConfiguration.getKeys());
            final Iterator i = xmlConfiguration.getKeys();
            while (i.hasNext()) {
                Object object = (Object) i.next();
                //System.out.println(object);
            }

            // System.out.println(xmlConfiguration.getList("social-networks.social-network.social-network-name"));
            // System.out.println(xmlConfiguration.getList("social-networks.social-network.social-network-name").size());
            // System.out.println(xmlConfiguration.getList("social-networks"));
            // System.out.println(xmlConfiguration.getList("social-networks").size());

            List fields = xmlConfiguration
                    .configurationsAt("tables.table(0).fields.field");
            for (Iterator it = fields.iterator(); it.hasNext();) {
                HierarchicalConfiguration sub = (HierarchicalConfiguration) it
                        .next();
                // sub contains all data about a single field
                String fieldName = sub.getString("name");
                String fieldType = sub.getString("type");
            }

        } catch (ConfigurationException e) {

            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
