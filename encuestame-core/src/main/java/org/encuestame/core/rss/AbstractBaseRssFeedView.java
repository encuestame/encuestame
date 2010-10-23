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
package org.encuestame.core.rss;

import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

/**
 * Dashboard Rss View.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 2, 2010 9:19:15 AM
 * @version $Id:$
 * http://webcache.googleusercontent.com/search?q=cache:KbNEvZqJCfQJ:blog.jteam.nl/2010/05/05/implementing-rss-feeds-with-new-features-of-spring-3/+spring+rss+rome&hl=es&gl=ni&strip=1
 */
public abstract class AbstractBaseRssFeedView extends AbstractRssFeedView{

    private String atomTitle = "";

    /**
     * @return the atomTitle
     */
    public String getAtomTitle() {
        return atomTitle;
    }

    /**
     * @param atomTitle the atomTitle to set
     */
    public void setAtomTitle(String atomTitle) {
        this.atomTitle = atomTitle;
    }
}
