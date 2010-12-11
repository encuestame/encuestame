/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
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

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 18, 2010 11:04:07 PM
 * @version Id:
 */
public class DateUtil {

    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd";

    public static final String DEFAULT_FORMAT_TIME = "hh:mm:ss";


    /**
     * Get Format Date.
     * @param date
     * @return
     */
    public static String getFormatDate(final Date date){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
        return simpleDateFormat.format(date);
    }

}
