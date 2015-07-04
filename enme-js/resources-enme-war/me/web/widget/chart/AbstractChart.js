/**
 * Created by jpicado on 14/06/14.
 */

define([
    "dojo/_base/declare",
    "dojo/dom-style",
    "dojo/dom-construct",
    "dojo/dom-attr"],
    function(
        declare,
        domStyle,
        domConstruct,
        domAttr) {

        'use strict';

        return declare(null, {

            MONTHS_YEAR : ["January","February","March","April","May","June","July", "August", "October", "November", "December"],

            WEEK_DAYS : ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"],

            /**
             *
             */
            nodeChart : null,

            /**
             * ChartLayerSupport
             * @param node node to append the cart.
             */
            constructor: function(id, data, period, question, options) {
                this.nodeChart = domConstruct.create("canvas");
                var _width = domStyle.get(id, 'width');
                var _height = domStyle.get(id, 'height');
                domAttr.set(this.nodeChart, 'width', _width);
                domAttr.set(this.nodeChart, 'height', _height);
                domConstruct.place(this.nodeChart, id);
                this.ctx = this.nodeChart.getContext("2d");
                this.chart = new Chart(this.ctx);
                this.period = period;
                this.question = question;
                this.data = data || [];
                this.options = options || {};
                this.render();
            },

            /**
             * Build series.
             */
            _buildSeries : function() {
                //should be implemented in each chart
            },

            /**
             *
             */
            getChart : function() {
                return this.chart;
            },

            /**
             *
             */
            render : function(data) {}
        });
    });