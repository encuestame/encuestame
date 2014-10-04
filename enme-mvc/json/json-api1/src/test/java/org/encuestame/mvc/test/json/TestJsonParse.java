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
package org.encuestame.mvc.test.json;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import junit.framework.TestCase;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.encuestame.core.util.JSONUtils;
import org.encuestame.utils.categories.test.SlowTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test to json parse.
 * @author Picado, Juan juanATencuestame.org
 * @since 13/08/2011
 */

public class TestJsonParse extends TestCase {

    /**
     *
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
	@Category(SlowTest.class)
    @Test
    public void test() throws JsonGenerationException, JsonMappingException,
            IOException {
        final String json = JSONUtils.convertObjectToJsonString(new TestItem());
        assertEquals("{\"prop\":[]}", json);
        final String json2 = JSONUtils.convertObjectToJsonString(new Item());
        assertEquals("{\"name\":null,\"values\":null}", json2);
    }
	  
	@Test
	public void testPrueba(){
		System.out.println("TES");
	}

    /**
     * Test case class.
     * @author Picado, Juan juanATencuestame.org
     * @since 13/08/2011
     */
    public class TestItem {

        private List<Item> properties = new ArrayList<Item>();

        /**
         * @return the properties
         */
        @JsonProperty(value = "prop")
        public List<Item> getProperties() {
            return properties;
        }

        /**
         * @param properties
         *            the properties to set
         */
        public void setProperties(List<Item> properties) {
            this.properties = properties;
        }

    }

    public class Item implements Serializable{
        /**
         *
         */
        private static final long serialVersionUID = 8563759258621204152L;

        private String name;
        private String value;

        /**
         * @return the name
         */
        @JsonProperty(value = "name")
        public String getName() {
            return name;
        }

        /**
         * @param name
         *            the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the value
         */
        @JsonProperty(value = "values")
        public String getValue() {
            return value;
        }

        /**
         * @param value
         *            the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }
    }
}