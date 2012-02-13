/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
dojo.provide("encuestame.org.core.commons.chart.ChartLayerSupport");

dojo.require("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");

dojo.declare("encuestame.org.core.commons.chart.ChartLayerSupport", null, {

    nodeChart : null,

    widgetChart : null,

    _size : 100,

    /**
     * ChartLayerSupport
     * @param node node to append the cart.
     */
    constructor: function() {},

    /**
     * Build a new Chart.
     * @param options
     */
    buildChart : function(options) {
        this.widgetChart = new encuestame.org.core.commons.dashboard.chart.EncuestamePieChart(options.id, options.results, this._size);
        return this.widgetChart;
    },

    /**
     * Render the chart
     */
    renderChart : function(render) {
         if ( this.widgetChart) {
             this.widgetChart.render();
         }
    }

});
