package org.encuestame.mvc.controller.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.encuestame.utils.web.UnitHashTag;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestJson {

    public TestJson() {
    }

    @RequestMapping(value = "/test.json", method = RequestMethod.GET)
    public String loadTestJson() {
        return "";
    }

    @RequestMapping(value = "/test2.json", method = RequestMethod.GET)
    public String getBookmarksWithTag(@RequestParam(value = "tag") String tag,
            HttpSession session, ModelMap model) {
        final UnitHashTag user = new UnitHashTag();
        user.setHashTagName("Juan");
        user.setId(1L);
        model.addAttribute(user);
        return "tags";
    }

    //Good Example
    //http://rwehner.wordpress.com/2010/06/09/2-ways-to-create-json-response-for-ajax-request-in-spring3/
    @RequestMapping(value = "/jackson.json", method = RequestMethod.GET)
    protected void getJsonDataExampleSimpleDataBinding(
            HttpServletResponse response) throws JsonGenerationException,
            JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> nameStruct = new HashMap<String, String>();
        nameStruct.put("first", "Joe");
        nameStruct.put("last", "Sixpack");
        Map<String, Object> userData = new HashMap<String, Object>();
        userData.put("name", nameStruct);
        userData.put("gender", "MALE");
        userData.put("verified", Boolean.FALSE);
        userData.put("userImage", "Rm9vYmFyIQ==");
        String jsonString = mapper.writeValueAsString(userData);

        AbstractHttpMessageConverter<String> stringHttpMessageConverter = new StringHttpMessageConverter();
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;
        if (stringHttpMessageConverter.canWrite(String.class, jsonMimeType)) {
            try {
                stringHttpMessageConverter.write(jsonString, jsonMimeType,
                        new ServletServerHttpResponse(response));
            } catch (IOException m_Ioe) {
            } catch (HttpMessageNotWritableException p_Nwe) {
            }
        }
    }

}
