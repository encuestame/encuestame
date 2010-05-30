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
package org.encuestame.web.beans.admon.location;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.utils.dnd.ItemFolderDrag;
import org.encuestame.utils.web.UnitLocationBean;
import org.richfaces.event.DragEvent;
import org.richfaces.event.DragListener;
import org.richfaces.event.DropEvent;
import org.richfaces.event.DropListener;

/**
 * Description Class.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since May 19, 2010 8:03:24 PM
 * @version Id:
 */
public class ItemDragable extends UnitLocationBean implements DragListener,
        DropListener, ItemFolderDrag {

    /**
     *
     */
    private static final long serialVersionUID = 3232432432432L;

    private Log log = LogFactory.getLog(this.getClass());

    private String value;

    public ItemDragable(final String value) {
        this.value = value;
    }

    public ItemDragable() {
        super();
    }

    public void processDrag(DragEvent arg0) {
        log.info("***>>> drop " + this.value);

    }

    public void processDrop(DropEvent arg0) {
        log.info("***>>> drop " + this.value);

    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
