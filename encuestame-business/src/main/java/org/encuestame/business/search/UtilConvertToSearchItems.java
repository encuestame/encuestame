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
package org.encuestame.business.search;

import java.util.ArrayList;
import java.util.List;

import org.encuestame.persistence.domain.question.Question;

/**
 * Utils to convert items to {@link GlobalSearchItem}.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 26, 2011
 */
public class UtilConvertToSearchItems {

    /**
     *
     * @param question
     * @return
     */
    public static GlobalSearchItem convertQuestionToSearchItem(final Question question){
        final GlobalSearchItem globalSearchItem = new GlobalSearchItem();
        return globalSearchItem;
    }

    /**
     *
     * @param questions
     * @return
     */
    public static List<GlobalSearchItem> convertQuestionToSearchItem(final List<Question> questions){
        final List<GlobalSearchItem> globalSearchItems = new ArrayList<GlobalSearchItem>();
        for (Question question : questions) {
            globalSearchItems.add(UtilConvertToSearchItems.convertQuestionToSearchItem(question));
        }
        return globalSearchItems;
    }
}
