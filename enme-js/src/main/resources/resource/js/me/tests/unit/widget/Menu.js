define( [
    "intern!object",
    "intern/chai!assert",
    "me/web/widget/menu/DashBoardMenu",
    "dijit/form/TextBox",
    "me/web/widget/menu/DropDownMenuItem",
    "me/web/widget/menu/DropDownMenuSelect",
    "me/web/widget/menu/OptionMenu",
    "me/web/widget/menu/SearchMenu",
    "me/web/widget/menu/SearchSuggestItemsByType",
    "me/web/widget/menu/SearchSuggestItemSection",
    "me/web/widget/menu/SettingsButton",
    "me/web/widget/menu/SettingsMenuSwitch",
    "me/web/widget/menu/TimeRangeDropDownMenu"
], function(
    registerSuite,
    assert,
    DashBoardMenu,
    TextBox,
    DropDownMenuItem,
    DropDownMenuSelect,
    OptionMenu,
    SearchMenu,
    SearchSuggestItemsByType,
    SearchSuggestItemSection,
    SettingsButton,
    SettingsMenuSwitch,
    TimeRangeDropDownMenu ) {
    registerSuite({
        name: "Menu Widgets",

        "default data": function() {
            var searchMenu = new SearchMenu({

            });
        },

        "DashboardMenu Widget": function() {
            var boardMenu = new DashBoardMenu({

            });
            assert.isObject( boardMenu, "DashBoardMenu should be an object" );
        },

        "DropDownMenuItem Widget": function() {
            var dropdownItem = new DropDownMenuItem({

            });
            assert.isObject( dropdownItem, "DropDownMenuItem should be an object" );
        },

        "DropDownMenuSelect Widget": function() {
            var dropDownMenuSelect = new DropDownMenuSelect({

            });
            assert.isObject( dropDownMenuSelect, "DropDownMenuSelect should be an object" );
        },

        "OptinMenu Widget": function() {
            var optionMenu = new OptionMenu({

            });
            assert.isObject( optionMenu, "OptionMenu should be an object" );
        },

        "SearchSuggestItemsbyType Widget": function() {
            var myTextBox = new TextBox({
                name: "firstname",
                value: "",
                placeHolder: "type in your name"
            }, "firstname");
            var searchSuggestbyType = new SearchSuggestItemsByType({
                parentWidget: {
                    defaultNoResults: 1,
                    textBoxWidget: myTextBox
                }
            });
            assert.isObject( searchSuggestbyType, "SearchSuggestItemsbyType should be an object" );
        },

        "SearchSuggestItemSection Widget": function() {
            var searchSuggest = new SearchSuggestItemSection({

            });
            assert.isObject( searchSuggest, "SearchSuggestItemSection should be an object" );
        },

        "SettingsButton Widget": function() {
            var settingsButton = new SettingsButton({
				label: "test"
            });
            assert.isObject( settingsButton, "SettingsButton should be an object" );
        },

//         FIXME: require pre parser widget structure
//        'SettingsMenuSwitch Widget': function () {
//            var settingsMenu = new SettingsMenuSwitch({
//
//            });
//            assert.isObject(settingsMenu, 'SettingsMenuSwitch should be an object');
//        },

        "TimeRangeDropDownMenu Widget": function() {
            var timeRange = new TimeRangeDropDownMenu({

            });
            assert.isObject( timeRange, "TimeRangeDropDownMenu should be an object" );
        }
    });
});
