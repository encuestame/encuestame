package org.encuestame.mvc.controller.json.chart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/chart/**")
public class TestJsonChart {

    @RequestMapping(value = "/chart/pie.json", method = RequestMethod.GET)
    public String loadTestChartPieJson() {
        return "";
    }

}
