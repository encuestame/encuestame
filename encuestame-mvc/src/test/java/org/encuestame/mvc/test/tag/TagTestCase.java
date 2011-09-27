package org.encuestame.mvc.tag;

import javax.servlet.jsp.JspException;

import org.encuestame.mvc.tag.IncludeResource;
import org.junit.Ignore;
import org.junit.Test;

import junit.framework.TestCase;

@Ignore
public class TagTestCase extends TestCase {

    /**
     * @throws JspException
     *
     */
    @Ignore
    @Test
    //TODO: pending, we need a way to test jsp tags.
    public void testIncludeResource() throws JspException {
        IncludeResource includeResource = new IncludeResource();
        includeResource.doStartTag();
        System.out.println(includeResource.doStartTag());
    }
}
