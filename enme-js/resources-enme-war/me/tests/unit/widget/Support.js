define([
    'intern!object',
    'intern/chai!assert',
    'me/web/widget/support/AbstractFilterSupport',
    'me/web/widget/support/ActionDialogHandler',
    'me/web/widget/support/CommentsModeration',
    'me/web/widget/support/DnD',
    'me/web/widget/support/DnDSupport',
    'me/web/widget/support/ItemsFilterSupport',
    'me/web/widget/support/OrderMenu',
    'me/web/widget/support/PanelWipe',
    'me/web/widget/support/SearchMenu',
    'me/web/widget/support/SocialFilterMenu',
    'me/web/widget/support/SocialFilterMenuItem',
    'me/web/widget/support/SocialLinkResume',
    'me/web/widget/support/ToggleMenu',
    'me/web/widget/support/VotesFilterMenu',
    'me/web/widget/support/Wipe'    
], function (
    registerSuite,
    assert,
    AbstractFilterSupport,
    ActionDialogHandler,
    CommentsModeration,
    DnD,
    DnDSupport,
    ItemsFilterSupport,
    OrderMenu,
    PanelWipe,
    SearchMenu,
    SocialFilterMenu,
    SocialFilterMenuItem,
    SocialLinkResume,
    ToggleMenu,
    Wipe) {
    registerSuite({
        name: 'Stream Widgets',
        
        'default data': function () {
            var commentsModeration = new CommentsModeration({

            });            
        },

        'AbstractFilterSupport Widget': function () {
            var abstractFilter = new AbstractFilterSupport({

            });
            assert.isObject(abstractFilter, 'AbstractFilterSupport should be an object');
        },

        'ActionDialogHandler Widget': function () {
            var actionDialog = new ActionDialogHandler({

            });
            assert.isObject(actionDialog, 'ActionDialogHandler should be an object');
        },

        'DnD Widget': function () {
            var dnD = new DnD({

            });
            assert.isObject(dnD, 'DnD should be an object');
        },

        'DnDSupport Widget': function () {
            var dnDSupport = new DnDSupport({

            });
            assert.isObject(dnDSupport, 'DnDSupport should be an object');
        },

        'ItemsFilterSupport Widget': function () {
            var itemsFilterSupport = new ItemsFilterSupport({

            });
            assert.isObject(itemsFilterSupport, 'ItemsFilterSupport should be an object');
        },

        'OrderMenu Widget': function () {
            var orderMenu = new OrderMenu({

            });
            assert.isObject(orderMenu, 'OrderMenu should be an object');
        },

        'PanelWipe Widget': function () {
            var panelWipe = new PanelWipe({

            });
            assert.isObject(panelWipe, 'PanelWipe should be an object');
        },

        'SearchMenu Widget': function () {
            var searchMenu = new SearchMenu({

            });
            assert.isObject(searchMenu, 'SearchMenu should be an object');
        },

        'SocialFilterMenu Widget': function () {
            var socialFilterMenu = new SocialFilterMenu({

            });
            assert.isObject(socialFilterMenu, 'SocialFilterMenu should be an object');
        },

        'SocialFilterMenuItem Widget': function () {
            var socialFilterMenuItem = new SocialFilterMenuItem({
                data : {
                    picture_url : "http://google.es",
                    type_account : "TWITTER",
                    account : "test_account"
                }
            });
            assert.isObject(socialFilterMenuItem, 'SocialFilterMenuItem should be an object');
        },

        'SocialLinkResume Widget': function () {
            var socialLinkeResume = new SocialLinkResume({

            });
            assert.isObject(socialLinkeResume, 'SocialLinkResume should be an object');
        },

        'ToggleMenu Widget': function () {
            var toggleMenu = new ToggleMenu({

            });
            assert.isObject(toggleMenu, 'ToggleMenu should be an object');
        },


        'Wipe Widget': function () {
            var wipe = new Wipe({

            });
            assert.isObject(wipe, 'Wipe should be an object');
        },
    });
});