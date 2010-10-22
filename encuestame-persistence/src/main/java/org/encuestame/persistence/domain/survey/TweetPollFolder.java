/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */

package org.encuestame.persistence.domain.survey;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.encuestame.persistence.domain.AbstractFolder;

/**
 * Poll Domain.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since October 04, 2010
 * @version $Id: $
 */

@Entity
@Table(name = "tweetPoll_Folder")
public class TweetPollFolder extends AbstractFolder{
     private Long tweetPollFolderId;

    /**
     * @return the tweetPollFolderId
     */
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(name = "tweetPollFolderId", unique = true, nullable = true)
    public Long getTweetPollFolderId() {
        return tweetPollFolderId;
    }

    /**
     * @param tweetPollFolderId the tweetPollFolderId to set
     */
    public void setTweetPollFolderId(Long tweetPollFolderId) {
        this.tweetPollFolderId = tweetPollFolderId;
    }

}
