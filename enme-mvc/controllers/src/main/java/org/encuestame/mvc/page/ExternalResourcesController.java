package org.encuestame.mvc.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.mvc.controller.AbstractViewController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExternalResourcesController extends AbstractViewController {

	/**
	 * Log.
	 */
	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * Return a empty html to display in iframe contexts.
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/display/{type}/{id}/iframe", method = RequestMethod.GET)
	    public String showIframe(
	    		ModelMap model, 
	    		HttpServletRequest request,
	    		@PathVariable String id,
	            @PathVariable String type,
	            HttpServletResponse response) {
		  log.debug("iframe display -> " + id);
		  log.debug("iframe display -> " + type);
		  return "display/iframe";
	  }}
