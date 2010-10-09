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
package org.encuestame.web.beans.survey.poll;

import java.io.Serializable;
import java.util.List;

import org.encuestame.core.exception.EnMeDomainNotFoundException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.utils.web.UnitFolder;
import org.encuestame.web.beans.MasterBean;
import org.springframework.stereotype.Component;

/**
 * PollBean.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 9, 2010 1:53:50 PM
 * @version $Id:$
 */
@Component("pollBean")
public class PollBean extends MasterBean implements Serializable{

    /**
     * Serial.
     */
    private static final long serialVersionUID = 121208809431131995L;

    private List<UnitFolder> pollFolders;

    /**
     * @return the pollFolders
     */
    public List<UnitFolder> getPollFolders() {
        if(pollFolders == null){
            try {
                this.pollFolders = getPollService().retrieveFolderPoll(getUserPrincipalUsername());
            } catch (EnMeExpcetion e) {
               log.error(e);
               addErrorMessage(e.getMessage(), e.getMessage());
            }
        }
        return pollFolders;
    }

    /**
     * @param pollFolders the pollFolders to set
     */
    public void setPollFolders(List<UnitFolder> pollFolders) {
        this.pollFolders = pollFolders;
    }
}
