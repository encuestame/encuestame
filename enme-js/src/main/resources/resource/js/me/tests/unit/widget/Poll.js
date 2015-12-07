define( [
    "intern!object",
    "intern/chai!assert",
    "../Helper",
    "me/web/widget/poll/Poll",
    "me/web/widget/poll/PollNavigate",
    "me/web/widget/poll/PollNavigateItem",
    "me/web/widget/poll/PollNavigateItemDetail",
    "me/web/widget/poll/detail/PollChartDetail",
    "me/web/widget/poll/vote/AnswerVote",
    "me/web/widget/poll/vote/PollVote"
], function(
    registerSuite,
    assert,
    Helper,
    Poll,
    PollNavigate,
    PollNavigateItem,
    PollNavigateItemDetail,
    PollChartDetail,
    AnswerVote,
    PollVote ) {
    registerSuite({
        name: "Poll Widgets",

        setup: function() {
            Helper.init();

//		    Tp = Helper.createElement('mainWrapper');
//		    tp2 = Helper.createElement('previewWrapperFixed');
            Helper.addCss( 1, "../../tests/resources/resources/css/dev/poll.css");
        },

        "Poll Navigate Widget Test": function() {
            var pollNavigate = new PollNavigate({

            });
        },

        "Poll Widget Test": function() {
            var poll = new Poll({

            });
            poll.cancelUnLoadSupport();

            //Helper.removeCss(1);
            assert.isObject( poll, "Poll should be an object" );
        },

        "PollNavigateItem Widget": function() {
            var pollNavigateItem = new PollNavigateItem({
                data: {
                    id: 1,
                    question: {
                        question_name: "test"
                    },
                    password_protected: "3232",
                    hits: 1000,
                    total_votes: 100
                }
            });
            assert.isObject( pollNavigateItem, "PollNavigateItem should be an object" );
        },

        "PollNavigateItemDetail Widget": function() {
            var pollNavigateDetail = new PollNavigateItemDetail({

            });
            assert.isObject( pollNavigateDetail, "PollNavigateItemDetail should be an object" );
        },

        "PollChartDetail Widget": function() {
            var pollChartDetail = new PollChartDetail({
                pollId: 1,
                username: "test"
            });
            assert.isObject( pollChartDetail, "PollChartDetail should be an object" );
        },

        "AnswerVote Widget": function() {
            var answerVote = new AnswerVote({

            });
            assert.isObject( answerVote, "AnswerVote should be an object" );
        },

        "PollVote Widget": function() {

	         //TODO: Require previous [data-dojo-type] in the dom
//            var pollVote = new PollVote({
//
//            });
            //assert.isObject(pollVote, 'PollVote should be an object');
        }
    });
});
