define( [
    "intern!object",
    "intern/chai!assert",
    "me/web/widget/results/answers/AudioResult",
    "me/web/widget/results/answers/GenericPercentResult",
    "me/web/widget/results/answers/ImageResult",
    "me/web/widget/results/answers/LinkResult",
    "me/web/widget/results/answers/ResultSupport",
    "me/web/widget/results/answers/VideoResult"
], function(
    registerSuite,
    assert,
    AudioResult,
    GenericPercentResult,
    ImageResult,
    LinkResult,
    ResultSupport,
    VideoResult ) {
    registerSuite({
        name: "Results Widgets",

        "default data": function() {

        },

        "AudioResult Widget": function() {
            var audioResult = new AudioResult({

            });
            assert.isObject( audioResult, "AudioResult should be an object" );
        },

        "GenericPercentResult Widget": function() {
            var genericPercentResult = new GenericPercentResult({

            });
            assert.isObject( genericPercentResult, "GenericPercentResult should be an object" );
        },

        "ImageResult Widget": function() {
            var imageResult = new ImageResult({

            });
            assert.isObject( imageResult, "ImageResult should be an object" );
        },

        "LinkResult Widget": function() {
            var linkResult = new LinkResult({

            });
            assert.isObject( linkResult, "LinkResult should be an object" );
        },

        "ResultSupport Widget": function() {
            var resultSupport = new ResultSupport({

            });
            assert.isObject( resultSupport, "ResultSupport should be an object" );
        },

        "VideoResult Widget": function() {
            var videoResult = new VideoResult({

            });
            assert.isObject( videoResult, "VideoResult should be an object" );
        }
    });
});
