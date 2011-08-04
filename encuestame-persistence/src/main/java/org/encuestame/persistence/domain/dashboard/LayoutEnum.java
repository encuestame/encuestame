/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.domain.dashboard;

/**
 * Layout Enumeration
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
public enum LayoutEnum {

	/** Three columns. **/
	AAA_COLUMNS,

	/** Two Blocks. **/
	BB_BLOCK,

	/** One Block. **/
	B_BLOCK,

	/** One Column and One Block. **/
	AB_COLUMN_BLOCK,

	/** One Block and One column. **/
	BA_BLOCK_COLUMN,

	LayoutEnum(){

	};

	public static LayoutEnum getDashboardLayout(final String layout) {
        if (null == layout) { return null; }
        else if (layout.equalsIgnoreCase("TWEETPOLLS")) { return AAA_COLUMNS; }
        else if (layout.equalsIgnoreCase("SURVEYS")) { return BB_BLOCK; }
        else if (layout.equalsIgnoreCase("POLL")) { return B_BLOCK; }
        else if (layout.equalsIgnoreCase("SURVEYS")) { return AB_COLUMN_BLOCK; }
        else if (layout.equalsIgnoreCase("POLL")) { return BA_BLOCK_COLUMN; }
        else return null;
    }
}
