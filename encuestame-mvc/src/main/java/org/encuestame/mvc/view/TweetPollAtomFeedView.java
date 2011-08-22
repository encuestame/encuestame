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
package org.encuestame.mvc.view;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.encuestame.core.rss.AbstractBaseAtomFeedView;
import com.sun.syndication.feed.atom.Feed;

/**
 * TweetPoll Published Atom Feed View.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 3, 2010 2:31:28 AM
 * @version $Id:$
 */
public final class TweetPollAtomFeedView extends AbstractBaseAtomFeedView {


    /**
     * Build Feed Meta Data.
     */
    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed,
            HttpServletRequest request) {
        setAtomTitle(model.get("feedTitle") == null ? "TweetPoll Published " : model.get("feedTitle").toString());
        feed.setId(getAtomTitle());
        feed.setTitle(getAtomTitle());
    }
}
