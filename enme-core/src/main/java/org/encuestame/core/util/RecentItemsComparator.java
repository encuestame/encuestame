/*
 ************************************************************************************
 * Copyright (C) 2001-2012 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.util;

import java.util.Comparator;
import java.util.Date;

import org.encuestame.utils.json.HomeBean;

/**
 * Recent Items Comparator.
 * @author Picado, Juan juanATencuestame.org
 * @since April 21, 2012 4:12:57 PM
 */
public class RecentItemsComparator implements Comparator<HomeBean> {
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(HomeBean arg0, HomeBean arg1) {		
		/*
	     Use compareTo method of java Date class to compare two date objects.
	     compareTo returns value grater than 0 if first date is after another date,
	     returns value less than 0 if first date is before another date and returns
	     0 if both dates are equal.
	    */
		Date st = arg0.getCreateDateComparable();
        Date s2 = arg1.getCreateDateComparable();
        return s2.compareTo(st);
	}
}
