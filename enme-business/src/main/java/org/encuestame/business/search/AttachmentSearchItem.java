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

/**
 * Class description.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since Mar 28, 2011
 */
public class AttachmentSearchItem {

    private Long attachId;
    private String description;
    /**
    * @return the attachId
    */
    public Long getAttachId() {
        return attachId;
    }
    /**
    * @param attachId the attachId to set
    */
    public void setAttachId(Long attachId) {
        this.attachId = attachId;
    }
    /**
    * @return the description
    */
    public String getDescription() {
        return description;
    }
    /**
    * @param description the description to set
    */
    public void setDescription(String description) {
        this.description = description;
    }



}
