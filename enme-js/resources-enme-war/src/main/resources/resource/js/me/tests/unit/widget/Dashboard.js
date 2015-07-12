define([
    'intern!object',
    'intern/chai!assert',
    '../Helper',
    'me/web/widget/dashboard/AbstractGadget',
    'me/web/widget/dashboard/DashboardGridContainer',
    'me/web/widget/dashboard/DashboardWrapper',
    'me/web/widget/dashboard/Gadget',
    'me/web/widget/dashboard/GadgetDirectory',
    'me/web/widget/dashboard/LayoutSelecter',
    'me/web/widget/dashboard/chart/DashboardColumn2D',
    'me/web/widget/dashboard/chart/DashboardLine',
    'me/web/widget/dashboard/chart/DashboardPie',
    'me/web/widget/gadget/Activity',
    'me/web/widget/gadget/Comments',
    'me/web/widget/gadget/Gadget',
    'me/web/widget/gadget/TweetPollVotes'
], function (
    registerSuite,
    assert,
    Helper,
    AbstractGadget,
    DashboardGridContainer,
    DashboardWrapper,
    Gadget,
    GadgetDirectory,
    LayoutSelecter,
    DashboardColumn2D,
    DashboardLine,
    DashboardPie,
    Activity,
    Comments,
    TweetPollVotes
    ) {
    registerSuite({
        name: 'Dashboard Widgets',

        setup: function () {
            Helper.init();
            tp = Helper.createElement('mainWrapper');
            tp2 = Helper.createElement('previewWrapperFixed');
            Helper.addCss(5, "../../tests/resources/resources/css/dev/dashboard.css");
        },


        'default data': function () {
            var dashboardWrapper = new DashboardWrapper({

            });
            Helper.place(dashboardWrapper.domNode, 'mainWrapper');
        },

        'DashboardContainer Widget': function () {
            var boardContainer = new DashboardGridContainer({

            });
            assert.isObject(boardContainer, 'DashboardGrid Container should be an object');
            Helper.place(boardContainer.domNode, 'mainWrapper');
        },

        'Gadget Widget': function () {
            var gadget = new Gadget({
				data : {
					gadget_name : 'name1'
				}
            });
            assert.isObject(gadget, 'Gadget should be an object');
        },

        'GadgetDirectory Widget': function () {
            var gadgetDirectory = new GadgetDirectory({

            });
            assert.isObject(gadgetDirectory, 'Gadget Directory should be an object');
        },

        'DashboardLine Widget': function () {
            var boardLine = new DashboardLine({

            });
            assert.isObject(boardLine, 'DashboardLine  should be an object');
        },

        'DashboardColumn2D Widget': function () {
            var boardColumn2D = new GadgetDirectory({

            });
            assert.isObject(boardColumn2D, 'Dashboard Column2D  should be an object');
        },

        'DashboardPie Widget': function () {
            var boardPie = new DashboardPie({

            });
            assert.isObject(boardPie, 'DashboardPie should be an object');
        },

        'LayoutSelecter Widget': function () {
            var boardPie = new DashboardPie({

            });
            assert.isObject(boardPie, 'LayoutSelecter should be an object');
        },

        'Activity Widget': function () {
            var activity = new Activity({

            });
            assert.isObject(activity, 'Activity should be an object');
        },

        'Comments Widget': function () {
            var comments = new Comments({

            });
            assert.isObject(comments, 'Comments should be an object');
        },

        'TweetPollVotes Widget': function () {
            var tweetPollVotes = new TweetPollVotes({

            });
            assert.isObject(tweetPollVotes, 'TweetPollVotes should be an object');
        }

    });
});
