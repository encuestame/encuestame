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
package org.encuestame.core.rss;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import com.sun.syndication.feed.rss.Item;

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

    /**
     * Build Feed Item Body.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Item> items = (List<Item>) model.get("items");
        return items;
    }
}
