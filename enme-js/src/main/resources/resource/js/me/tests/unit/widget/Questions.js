define( [
    "intern!object",
    "intern/chai!assert",
    "me/web/widget/questions/Question",
    "me/web/widget/questions/patterns/AbstractAddNewAnswer",
    "me/web/widget/questions/patterns/AbstractImageResponse",
    "me/web/widget/questions/patterns/AbstractMultipleSelection",
    "me/web/widget/questions/patterns/AbstractPattern",
    "me/web/widget/questions/patterns/AbstractSoundResponse",
    "me/web/widget/questions/patterns/AbstractVideoResponse",
    "me/web/widget/questions/patterns/CustomizableSelectionOptionResponse",
    "me/web/widget/questions/patterns/MultipleOptionResponse",
    "me/web/widget/questions/patterns/SingleOptionResponse",
    "me/web/widget/questions/patterns/SingleResponse"
], function(
    registerSuite,
    assert,
    Poll,
    PollNavigate,
    PollNavigateItem,
    PollNavigateItemDetail,
    PollChartDetail,
    AnswerVote,
    PollVote ) {
    registerSuite({
        name: "Questions Widgets",

        "default data": function() {
            var pollNavigate = new PollNavigate({

            });
        }
    });
});
