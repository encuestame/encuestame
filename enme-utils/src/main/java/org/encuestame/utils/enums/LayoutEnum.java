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
package org.encuestame.utils.enums;

/**
 * Layout Enumeration
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
public enum LayoutEnum {

    /** Two Blocks. **/
    BB_BLOCK("BB"),

    /** One Block. **/
    B_BLOCK("B"),

    /** One Column and One Block. **/
    AB_COLUMN_BLOCK("AB"),

    /** One Block and One column. **/
    BA_BLOCK_COLUMN("BA");

    /**
     *
     */
    private String layoutAsString;

    /**
     *
     * @param layoutAsString
     */
    LayoutEnum(String layoutAsString){
        this.layoutAsString = layoutAsString;
    }
}
