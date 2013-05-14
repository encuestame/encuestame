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
package org.encuestame.core.util;

import java.util.Comparator;

import org.encuestame.utils.json.HomeBean;

/**
 * FrontEnd items comparator.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since October 05, 2011
 */
public class FrontEndItemsComparator implements Comparator<HomeBean>{

    public FrontEndItemsComparator() {
    // TODO Auto-generated constructor stub
    }

    /**
     * Compare by home item id.
     */
    public int compare(HomeBean o1, HomeBean o2) {
         return (int) (o1.getId() - o2.getId());
    }

}
