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

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;

/**
 * Abstract Atom Feed View.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 2, 2010 8:01:10 PM
 * @version $Id:$
 */
public abstract class AbstractBaseAtomFeedView extends AbstractAtomFeedView {

    protected Logger log = Logger.getLogger(this.getClass());

    private String atomTitle = "";

    /**
     * Build Feed Metadata.
     */
    @Override
    protected abstract void buildFeedMetadata(Map<String, Object> model, Feed feed,
            HttpServletRequest request);


    /**
     * Build Feed Entries.
     */
     @SuppressWarnings("unchecked")
     @Override
     protected List<Entry> buildFeedEntries(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List<Entry> contentList = (List<Entry>) model.get("items");
        return contentList;
     }

    /**
     * @return the atomTitle
     */
    public String getAtomTitle() {
        return atomTitle;
    }

    /**
     * @param atomTitle
     *            the atomTitle to set
     */
    public void setAtomTitle(final String atomTitle) {
        this.atomTitle = atomTitle;
    }
}