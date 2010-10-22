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
package org.encuestame.mvc.view;

import com.sun.syndication.feed.atom.Link;

/**
 * Feed Utils.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 3, 2010 10:19:37 AM
 * @version $Id:$
 */
public class FeedUtils {


    /**
     * Create Feed Link.
     * @param url
     * @param title
     * @return
     */
    public final static Link createLink(final String url, final String title){
        Link link = new Link();
        link.setHref(url);
        link.setTitle(title);
        return link;
    }
}
