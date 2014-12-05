/*
 *
 *  * Copyright 2014 encuestame
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package org.encuestame.mvc.test.config;

import org.encuestame.mvc.page.jsonp.EmbebedJsonServices;
import org.encuestame.utils.enums.MethodJson;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jpicado on 05/12/14.
 */
public abstract class AbstractJSONPMvc  extends AbstractMvcUnitBeans{

    @Autowired
    EmbebedJsonServices embebedJsonServices;

    /*
      functionYes({
        "body" : "<a data-id=\"1\"\n   class=\"enme-poll-form\"\n   data-url=\"http://www.encuestame.org/encuestame-business\"\n   target=\"_blank\"/>\n<script>\n    !function(d,s,id){\n        var js,\n            fjs=d.getElementsByTagName(s)[0],\n            p=/^http:/.test(d.location)?'http':'https';\n        if(!d.getElementById(id)){\n            js=d.createElement(s);\n            js.id=id;js.src=p+\":///localhost/resources/js/widget/dist/widget.js\";\n            fjs.parentNode.insertBefore(js,fjs);\n        }\n    }(document,\"script\",\"widget-enme-js\");\n</script>",
        "config" : null,
        "header" : null,
        "aditionalInfo" : null
        })
     */
    public JSONObject getJSON(String response, String functionName) {
        final String test = response.replace(functionName + "(", "").replace(")", "");
        return (JSONObject) JSONValue.parse(test);
    }

    /**
     *
     * @param response
     * @param functionName
     * @return
     */
    public JSONObject getBody(String response, String functionName) throws Exception{
        return (JSONObject) getJSON(response, functionName).get("body");
    }

    /**
     *
     * @param formatWidget
     * @param mp
     * @return
     * @throws Exception
     */
    public JSONObject getWidget(
            final String type,
            final String formatWidget,
            HashMap<String, String> mp) throws Exception{
        String functionName = "functionYes";
        String path = "/api/jsonp/generate/code/{type}/embedded";
        path = path.replace("{type}", type);
        request = new MockHttpServletRequest(MethodJson.GET.toString(), path);
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            logPrint(pairs.getKey() + " = " + pairs.getValue());
            it.remove();
            request.setParameter(pairs.getKey().toString(), pairs.getValue().toString());
        }
        request.setParameter("callback", functionName);
        request.setParameter("embedded_type", formatWidget);
        handlerAdapter.handle(request, response, embebedJsonServices);
        logPrint(response.getContentAsString());
        return getJSON(response.getContentAsString(), functionName);
    }
}
