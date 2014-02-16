package org.encuestame.mvc.controller.jsonp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.encuestame.core.security.util.WidgetUtil;
import org.encuestame.mvc.controller.AbstractJsonControllerV1;
import org.encuestame.mvc.controller.jsonp.beans.JavascriptEmbebedBody;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.enums.EmbeddedType;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.web.PollDetailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmbebedJsonServices extends AbstractJsonControllerV1 {
	
	/**
	 * 
	 */
    @Autowired
    private VelocityEngine velocityEngine;
    
    /**
     * 
     */
    private final String CODE_TEMPLATES = "/org/encuestame/business/widget/code/templates/";
    
    /**
     * 
     */
    private final String HTML_TEMPLATES = "/org/encuestame/business/widget/templates";
    
    
	/**
	 * Generate the body of selected item.
	 * @param itemId
	 * @param callback
	 * @param request
	 * @param response
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/api/jsonp/generate/code/{type}/embedded", method = RequestMethod.GET)
	public void embedded(
			@RequestParam(value = "id", required = true) Long itemId,
			@PathVariable final String type,
			@RequestParam(value = "callback", required = true) String callback,
			@RequestParam(value = "embedded_type", required = false) String embedded,
			HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		PrintWriter out = response.getWriter();
		String text = "";
		try {
	        @SuppressWarnings("rawtypes")
	        final Map model = new HashMap();	       
			final TypeSearchResult typeItem = TypeSearchResult.getTypeSearchResult(type);
			final EmbeddedType embeddedType = EmbeddedType.getEmbeddedType(embedded);
			final JavascriptEmbebedBody embebedBody = new JavascriptEmbebedBody();			
			final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			response.setContentType("text/javascript; charset=UTF-8");			
			model.put("domain", WidgetUtil.getDomain(request, false));
			model.put("embedded_type", embeddedType.toString().toLowerCase());
			model.put("typeItem", typeItem.toString().toLowerCase());
			model.put("itemId", itemId);
			model.put("domain_config", WidgetUtil.getDomain(request, true));
			if (TypeSearchResult.TWEETPOLL.equals(typeItem)) {
			text = VelocityEngineUtils.mergeTemplateIntoString(
					velocityEngine, CODE_TEMPLATES  + embeddedType.toString().toLowerCase() + "_"
			+ typeItem.toString().toLowerCase() +"_form_code.vm", "utf-8", model);
			} else if (TypeSearchResult.POLL.equals(typeItem)) { 
				final Poll poll = getPollService().getPollById(itemId);
				model.put("url_poll", WidgetUtil.getDomain(request, true) +
						 "/poll/" + poll.getPollId() + "/" + poll.getQuestion().getSlugQuestion());
				text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, CODE_TEMPLATES  + embeddedType.toString().toLowerCase() + "_"
						+ typeItem.toString().toLowerCase() +"_form_code.vm", "utf-8", model);
			} else if (TypeSearchResult.HASHTAG.equals(typeItem)) {
				text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, CODE_TEMPLATES  + embeddedType.toString().toLowerCase() + "_"
						+ typeItem.toString().toLowerCase() +"_form_code.vm", "utf-8", model);				
			} else if (TypeSearchResult.PROFILE.equals(typeItem)) {
				text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, CODE_TEMPLATES  + embeddedType.toString().toLowerCase() + "_"
						+ typeItem.toString().toLowerCase() +"_code.vm", "utf-8", model);
			}
			String string = new String(text.getBytes("UTF-8"));
	        embebedBody.setBody(string);
			final String json = ow.writeValueAsString(embebedBody);			
			out.print(callback + "(" + json + ")");
		} catch (Exception e) {
			e.printStackTrace();
			out.print(callback + "(" + Boolean.FALSE + ")");
		}
	}	    
	
	/**
	 * Generate the body of selected item.
	 * @param pollId
	 * @param callback
	 * @param request
	 * @param response
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/api/jsonp/{type}/embedded", method = RequestMethod.GET)
	public void typeJavascriptJSONP(
			@RequestParam(value = "id", required = true) Long pollId,
			@PathVariable final String type,
			@RequestParam(value = "callback", required = true) String callback,
			HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		PrintWriter out = response.getWriter();
		String text = "";
		try {
	        @SuppressWarnings("rawtypes")
	        final Map model = new HashMap();	       
			final TypeSearchResult typeItem = TypeSearchResult.getTypeSearchResult(type);
			final JavascriptEmbebedBody embebedBody = new JavascriptEmbebedBody();			
			final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			response.setContentType("text/javascript; charset=UTF-8");
			if (TypeSearchResult.TWEETPOLL.equals(typeItem)) {
				// generate tweetpoll body
				model.put("hellow", "world");
				text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, HTML_TEMPLATES + "/tweetpoll_form.vm", "utf-8", model);
				 
			} else if (TypeSearchResult.POLL.equals(typeItem)) {
				// generate poll body
				final Poll poll = getPollService().getPollById(pollId);
				final PollDetailBean detailBean = getPollService().getPollDetailInfo(poll.getPollId());
				model.put("title", poll.getQuestion().getQuestion());
				model.put("poll", poll);
				model.put("action", WidgetUtil.getDomain(request) + "/poll/vote/post");
				model.put("detailBean", detailBean);
				model.put("vote_title", "Vote");
				text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, HTML_TEMPLATES + "/poll_form.vm", "utf-8", model);
			} else if (TypeSearchResult.HASHTAG.equals(typeItem)) {
				// generate hashtag body 
				model.put("hellow", "world");
				text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, HTML_TEMPLATES + "/hashtag.vm", "utf-8", model);
			}				
			//final String d = new String(text.toString(), "UTF-8");
			String string = new String(text.getBytes("UTF-8"));
	        embebedBody.setBody(string);
			final String json = ow.writeValueAsString(embebedBody);			
			out.print(callback + "(" + json + ")");
			// buffer.append(callback + "(" + Boolean.TRUE + ")");
		} catch (Exception e) {
			e.printStackTrace();
			out.print(callback + "(" + Boolean.FALSE + ")");
		}
	}	
	
	/**
	 * 
	 * @param pollId
	 * @param callback
	 * @param request
	 * @param response
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/jsonp/poll.json", method = RequestMethod.GET)
	public void pollJSONP(
			@RequestParam(value = "id", required = true) Long pollId,
			@RequestParam(value = "callback", required = true) String callback,
			HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/javascript; charset=UTF-8");
			getPollService().getPollById(pollId);
			out.print(callback + "(" + Boolean.TRUE + ")");
			// buffer.append(callback + "(" + Boolean.TRUE + ")");
		} catch (EnMeExpcetion e) {
			out.print(callback + "(" + Boolean.FALSE + ")");
		}
	}
	
	/**
	 * 
	 * @param username
	 * @param callback
	 * @param request
	 * @param response
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/jsonp/user.json", method = RequestMethod.GET)
	public void userJSONP(
			@RequestParam(value = "id", required = true) String username,
			@RequestParam(value = "callback", required = true) String callback,
			HttpServletRequest request, HttpServletResponse response)
			throws JsonGenerationException, JsonMappingException, IOException {
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/javascript; charset=UTF-8");
			getSecurityService().getUserAccount(username);
			out.print(callback + "(" + Boolean.TRUE + ")");
			// buffer.append(callback + "(" + Boolean.TRUE + ")");
		} catch (EnMeExpcetion e) {
			out.print(callback + "(" + Boolean.FALSE + ")");
		}
	}	
	
    /**
     * @return the velocityEngine
     */
    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    /**
     * @param velocityEngine the velocityEngine to set
     */
    public void setVelocityEngine(final VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }	
}
