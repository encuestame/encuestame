/**
 *
 */
package org.encuestame.mvc.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

/**
 * @author juan
 *
 */
public abstract class AbstractEnMeCustomView implements View {

    /**
     *
     */
    public AbstractEnMeCustomView() {
    }

    /**
     * View Content Type.
     */
    private String contentType;

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType
     *            the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public void render(Map<String, ?> map, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        renderView(map, request, response);
    }

    abstract void renderView(Map<String, ?> map, HttpServletRequest request,
            HttpServletResponse response);

}
