define([
    'intern!object',
    'intern/chai!assert',
    'me/web/widget/chart/AbstractChartVoteSupport',
    'me/web/widget/chart/ChartLayerSupport',
    'me/web/widget/chart/EncuestamePieChart'
], function (
    registerSuite,
    assert,
    AbstractChartVoteSupport,
    ChartLayerSupport,
    EncuestamePieChart) {
    registerSuite({
        name: 'Encuestame Pie Chart',
        
        'default data': function () {
//            var encuestamePieChart = new EncuestamePieChart({
//
//            });
        },

        'AbstractChartVote Widget': function () {
            var abstractChart = new AbstractChartVoteSupport({

            });
            assert.isObject(abstractChart, 'AbstractChartVote should be an object');
        },

        'ChartLayerSupport Widget': function () {
            var chartLayer = new ChartLayerSupport({

            });
            assert.isObject(chartLayer, 'ChartLayerSupport should be an object');
        }

    });
});