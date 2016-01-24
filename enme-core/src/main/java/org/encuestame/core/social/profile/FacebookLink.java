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
package org.encuestame.core.social.profile;

/**
 * Model class representing a link to be posted to a users Facebook wall.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
public class FacebookLink {
    private final String link;
    private final String name;
    private final String caption;
    private final String description;

    /**
     * Creates a FacebookLink.
     *
     * @param link
     *            The link's URL
     * @param name
     *            The name of the link
     * @param caption
     *            A caption to be displayed with the link
     * @param description
     *            The description of the link
     */
    public FacebookLink(String link, String name, String caption, String description) {
        this.link = link;
        this.name = name;
        this.caption = caption;
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }
}
