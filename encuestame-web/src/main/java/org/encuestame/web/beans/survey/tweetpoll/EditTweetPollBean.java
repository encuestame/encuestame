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
package org.encuestame.web.beans.survey.tweetpoll;

import java.io.Serializable;

import org.encuestame.utils.web.UnitTweetPoll;
import org.encuestame.web.beans.MasterBean;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Aug 7, 2010 5:26:20 PM
 * @version Id:
 */
public class EditTweetPollBean extends MasterBean implements Serializable{

    /**
     * Serial.
     */
    private static final long serialVersionUID = 171232309932133556L;

    private UnitTweetPoll editTweetPoll;

    public void updateQuestionName(){
        log.debug("updating question name");
    }

    /**
     * @return the editTweetPoll
     */
    public UnitTweetPoll getEditTweetPoll() {
        return editTweetPoll;
    }

    /**
     * @param editTweetPoll the editTweetPoll to set
     */
    public void setEditTweetPoll(final UnitTweetPoll editTweetPoll) {
        this.editTweetPoll = editTweetPoll;
    }
}
