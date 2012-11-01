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

import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Description.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since 20/08/2011
 */
public class RequiredWidgetsResource extends BodyTagSupport {

    /**
     *
     */
    private static final long serialVersionUID = 5390797470987450765L;

    protected String elemSep = "\n";

    public int doEndTag() throws JspException {
        String bodyText = bodyContent.getString();
        StringBuffer tableOut = new StringBuffer();
        StringTokenizer bodyTk = new StringTokenizer(bodyText, elemSep);
        tableOut.append("<script type=\"text/javascript\">");
        while (bodyTk.hasMoreTokens()) {
            String widget = (String) bodyTk.nextToken();
            tableOut.append("dojo.require('");
            tableOut.append(widget);
            tableOut.append("');\n");
        }
        tableOut.append("</script>");
        try {
            pageContext.getOut().print(tableOut);
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }
}
