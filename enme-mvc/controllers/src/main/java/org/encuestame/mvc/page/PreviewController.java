package org.encuestame.mvc.page;

import org.encuestame.core.util.WidgetUtil;
import org.encuestame.mvc.controller.AbstractViewController;
import org.encuestame.utils.enums.TypeSearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Preview Controller.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 6, 2010 10:58:02 AM
 */
@Controller

public class PreviewController extends AbstractViewController {

   /**
    * Preview of the widget wrappered into a single iframe
    * @param model
    * @param type
    * @param id
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = "/embebed/iframe/preview/{type}/{id}", method = RequestMethod.GET)
   public String embebedPreviewIframe(
           final ModelMap model,
           @PathVariable String type,
           @PathVariable String id,
           HttpServletRequest request,
           HttpServletResponse response) {
           model.put("id", id);
           model.put("class_type", TypeSearchResult.getCSSClass(TypeSearchResult.getTypeSearchResult(type)));
           model.put("domain", WidgetUtil.getRelativeDomain(request));
           model.put("url", "#");
           return "display/iframe";
   }

}
