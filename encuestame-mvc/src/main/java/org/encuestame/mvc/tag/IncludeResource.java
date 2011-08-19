/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.mvc.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * Include Dojo Type Tag.
 * @author Picado, Juan juanATencuestame.org
 * @since 19/08/2011
 */
public class IncludeResource extends TagSupport {

    private String widget = "";

    /**
     * @param widget the widget to set
     */
    public void setWidget(String widget) {
        this.widget = widget;
    }

    /**
     *
     */
    private static final long serialVersionUID = 6456833470316681314L;

    /*
     * (non-Javadoc)
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {
        try {
            final JspWriter out = pageContext.getOut();
            final StringBuffer buffer = new StringBuffer("<div");
            if (StringUtils.startsWith(widget, "encuestame.org")) {
                buffer.append(" dojoType=\"");
                buffer.append(widget);
                buffer.append("\">");
            } else {
                buffer.append("> ");
            }
            buffer.append(" </div>");
            out.write(buffer.toString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        return EVAL_PAGE;
    }

}
