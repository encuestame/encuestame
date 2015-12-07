define( [
    "intern!object",
    "intern/chai!assert",
	"../Helper",
    "me/web/widget/publish/PublishEmailSupport",
    "me/web/widget/publish/PublishEmbebedSupport",
    "me/web/widget/publish/PublishPanelItem",
    "me/web/widget/publish/PublishSocialStatus",
    "me/web/widget/publish/PublishSocialSupport",
    "me/web/widget/publish/PublishSupport",
    "me/core/support/ContextSupport"

], function(
    registerSuite,
    assert,
    Helper,
    PublishEmailSupport,
    PublishEmbebedSupport,
    PublishPanelItem,
    PublishSocialStatus,
    PublishSocialSupport,
    PublishSupport,
    ContextSupport ) {

    "use strict";

    var createPublishPanelItem  = function(
                contentWidget,
                context,
                title,
                defaultDisplayHide,
                tp1,
                mainWrapper ) {

        var publishPanelItem = new PublishPanelItem({
            contentWidget: contentWidget,
            context: context,
            title: title,
            defaultDisplayHide: defaultDisplayHide

        });

        Helper.place( publishPanelItem.domNode, mainWrapper );
        Helper.removeElement( tp1 );
        assert.isObject( publishPanelItem, "PublishPanelItem should be an object" );
        };

    registerSuite({
        name: "Publish Widgets",
	    setup: function() {
		    Helper.init({
			    params: {
				    counter_zero: "0"
			    }
		    });
	    },

        "default data": function() {
            var publishSupport = new PublishSupport({
            });

        },

        "PublishEmailSupport Widget": function() {
            var publishEmailSupport = new PublishEmailSupport({

            });
            assert.isObject( publishEmailSupport, "PublishEmailSupport should be an object" );
        },

        "PublishEmbebedSupport Widget": function() {
            var tp1 = Helper.createElement( "mainWrapper" );

            var ctxSupport = new ContextSupport({
                context: "tweetpoll"
            });
            var publishEmbebed = new PublishEmbebedSupport({
                contentWidget: ctxSupport,
                itemId: "12345",
                name: "Publish test"
            });
            assert.isObject( publishEmbebed, "PublishEmbebedSupport should be an object" );

            createPublishPanelItem( publishEmbebed, ctxSupport, "title", true, tp1, "mainWrapper");

        },

        "PublishPanelItem Widget": function() {
            var tp1 = Helper.createElement( "mainWrapper" );
	        var publishSupport = new PublishSupport({

	        });
            var publishPanelItem = new PublishPanelItem({
	            contentWidget: publishSupport
            });
            Helper.place( publishPanelItem.domNode, "mainWrapper" );
            Helper.removeElement( tp1 );
            assert.isObject( publishPanelItem, "PublishPanelItem should be an object" );
        },

        "PublishSocialStatus Widget": function() {
            var publishSocialStatus = new PublishSocialStatus({

            });
            assert.isObject( publishSocialStatus, "PublishSocialStatus should be an object" );
        },

        "PublishSocialSupport Widget": function() {
            var publishSocialSupport = new PublishSocialSupport({

            });
            assert.isObject( publishSocialSupport, "PublishSocialSupport should be an object" );
        },

        "PublishSupport Widget": function() {
            var publishSupport = new PublishSupport({

	        });
            assert.isObject( publishSupport, "PublishSupport should be an object" );
        }
    });
});
