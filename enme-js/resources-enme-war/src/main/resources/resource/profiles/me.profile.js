/**
 * This is the default application build profile used by the boilerplate. While it looks similar, this build profile
 * is different from the package build profile at `app/package.js` in the following ways:
 *
 * 1. you can have multiple application build profiles (e.g. one for desktop, one for tablet, etc.), but only one
 *    package build profile;
 * 2. the package build profile only configures the `resourceTags` for the files in the package, whereas the
 *    application build profile tells the build system how to build the entire application.
 *
 * Look to `util/build/buildControlDefault.js` for more information on available options and their default values.
 */

var excludeDijit = [
    "dijit/main",
    "dijit/selection",
    "dijit/typematic",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dijit/registry",
    "dijit/Dialog",
    "dijit/_Widget",
    "dijit/focus",
    "dijit/form/Button",
    "dijit/form/TextBox",
    "dijit/form/ComboBox",
    "dijit/form/DropDownButton"
];

var emne_core = [
    "me/main",
    "me/core/enme",
    "me/core/constants",
    "me/core/IconSupport",
    "me/core/Modernizr",
    "me/core/URLServices",
    "me/core/main_widgets/WidgetServices",
    "me/core/main_widgets/PublicViewWidget",
    "me/core/main_widgets/LoggedUtilities",
    "me/core/main_widgets/EnmeMainLayoutWidget",
    "me/core/support/ContextSupport",
    "me/core/support/iosOverlay",
    "me/core/support/moment",
    "me/core/support/PublishSupport",
    "me/core/support/Spinner",
    "me/core/ui/Loading"
];

var chart_dojo = ['dojo/colors',
    'dojo/fx/easing',
    'dojox/charting/action2d/Base',
    'dojox/charting/plot2d/Base',
    'dojox/charting/Chart',
    'dojox/charting/Element',
    'dojox/charting/action2d/Highlight',
    'dojox/charting/widget/Legend',
    'dojox/charting/themes/MiamiNice',
    'dojox/charting/action2d/MoveSlice',
    'dojox/charting/plot2d/Pie',
    'dojox/charting/action2d/PlotAction',
    'dojox/charting/Series',
    'dojox/charting/SimpleTheme',
    'dojox/charting/action2d/Tooltip',
    'dojox/charting/plot2d/_PlotEvents',
    'dojox/gfx/_base',
    'dojox/lang/functional/array',
    'dojox/charting/themes/common',
    'dojox/charting/plot2d/common',
    'dojox/charting/axis2d/common',
    'dojox/charting/scaler/common',
    'dojox/lang/functional/fold',
    'dojox/lang/functional',
    'dojox/gfx/fx',
    'dojox/gfx',
    'dojox/gfx/gradutils',
    'dojox/lang/functional/lambda',
    'dojox/gfx/matrix',
    'dojox/lang/functional/object',
    'dojox/gfx/path',
    'dojox/gfx/renderer',
    'dojox/lang/functional/reversed',
    'dojox/lang/functional/scan',
    'dojox/gfx/shape',
    'dojox/gfx/svg',
    'dojox/lang/utils'];

var profile = {
    // `basePath` is relative to the directory containing this profile file; in this case, it is being set to the
    // src/ directory, which is the same place as the `baseUrl` directory in the loader configuration. (If you change
    // this, you will also need to update run.js.)
    basePath: '../js/',

    // This is the directory within the release directory where built packages will be placed. The release directory
    // itself is defined by `build.sh`. You should probably not use this; it is a legacy option dating back to Dojo
    // 0.4.
    // If you do use this, you will need to update build.sh, too.
    //releaseDir : '/release',
    releaseName: '1.5.0',

    version : "1.5.0",

    // Builds a new release.
    action: 'release',

    // Strips all comments and whitespace from CSS files and inlines @imports where possible.
    cssOptimize: 'comments',

    // Excludes tests, demos, and original template files from being included in the built version.
    mini: true,

    // Uses Closure Compiler as the JavaScript minifier. This can also be set to "shrinksafe" to use ShrinkSafe,
    // though ShrinkSafe is deprecated and not recommended.
    // This option defaults to "" (no compression) if not provided.
    optimize: 'closure',

    // We're building layers, so we need to set the minifier to use for those, too.
    // This defaults to "shrinksafe" if not provided.
    layerOptimize: 'closure',

    // Strips all calls to console functions within the code. You can also set this to "warn" to strip everything
    // but console.error, and any other truthy value to strip everything but console.warn and console.error.
    // This defaults to "normal" (strip all but warn and error) if not provided.
    stripConsole: 'normal',

    // The default selector engine is not included by default in a dojo.js build in order to make mobile builds
    // smaller. We add it back here to avoid that extra HTTP request. There is also a "lite" selector available; if
    // you use that, you will need to set the `selectorEngine` property in `app/run.js`, too. (The "lite" engine is
    // only suitable if you are not supporting IE7 and earlier.)
    // http://dojotoolkit.org/reference-guide/1.8/dojo/query.html
    selectorEngine: 'lite',

    hasReport: true,
    //defaultCopyright: 'copy right juan 2015',
    //defaultBuildNotice: 'build notice juan 2015',

    packages:[
        {
            name: "dojo",
            copyright: "copyright-dojo.txt",
            location: "dojo"
        },{
            name: "dijit",
            copyright: "copyright-dojo.txt",
            location: "dijit"
        },{
            name: "dojox",
            copyright: "copyright-dojo.txt",
            location: "dojox"
        },{
            name: "org",
            location: "org",
            copyright: "copyright-enme.txt"
        },{
            name: "chart",
            location: "chart",
            copyright: "copyright-enme.txt"
        },{
            name: "me",
            location: "me",
            copyright: "copyright-enme.txt"

        },
        {
            name: "hammer",
            location: "hammer",
            main: "hammer",
            copyright: "copyright-hammer.txt"

        }
    ],

    // Builds can be split into multiple different JavaScript files called "layers". This allows applications to
    // defer loading large sections of code until they are actually required while still allowing multiple modules to
    // be compiled into a single file.
    layers: {
        // This is the main loader module. It is a little special because it is treated like an AMD module even though
        // it is actually just plain JavaScript. There is some extra magic in the build system specifically for this
        // module ID.
        'dojo/dojo': {
            // In addition to the loader `dojo/dojo` and the loader configuration file `app/run`, we are also including
            // the main application `app/main` and the `dojo/i18n` and `dojo/domReady` modules because, while they are
            // all conditional dependencies in `app/main`, we do not want to have to make extra HTTP requests for such
            // tiny files.
            include: [
                'dojo/main',
                'dojo/_base/declare',
                'dojo/_base/unload',
                'dojo/_base/lang',
                'dojo/i18n',
                'dojo/has',
                'dojo/hash',
                'dojo/dom',
                'dojo/parser',
                "dojo/ready",
                "dojo/require",
                "dojo/Deferred",
                "dojo/dom",
                "dojo/html",
                'dojo/date',
                'dojo/date/locale',
                "dojo/store/util/QueryResults",
                'dojo/domReady',
                'dojo/cache',
                'dojo/store/util/SimpleQueryEngine',
                "dojo/data/ItemFileReadStore",
                'dojo/io/iframe',
                'dojo/request/iframe',
                'dojo/request/notify',
                'dojo/dom-construct',
                'dijit/dijit',
                'dijit/main',
                'dijit/_base',
                'dijit/_base/scroll',
                'dijit/_base/window',
                'dijit/_base/sniff',
                'dijit/typematic',
                'dijit/_base/focus',
                'dijit/layout/_LayoutWidget',
                'dijit/_base/place',
                'dijit/_base/popup',
                'dijit/_base/wai',
                'dijit/_Contained',
                'dijit/form/Form',
                "dijit/form/Button",
                "dijit/form/TextBox",
                "dijit/form/ComboBox",
                "dijit/form/DropDownButton",
                "dijit/form/Button",
                "dojox/widget/Standby"
            ],

            // By default, the build system will try to include `dojo/main` in the built `dojo/dojo` layer, which adds
            // a bunch of stuff we do not want or need. We want the initial script load to be as small and quick to
            // load as possible, so we configure it as a custom, bootable base.
            boot: true,
            customBase: true
        },

//        'dijit/dijit': {
//            // In addition to the loader `dojo/dojo` and the loader configuration file `app/run`, we are also including
//            // the main application `app/main` and the `dojo/i18n` and `dojo/domReady` modules because, while they are
//            // all conditional dependencies in `app/main`, we do not want to have to make extra HTTP requests for such
//            // tiny files.
//            include: [
//                //'dijit/dijit',
//                'dijit/main',
//                'dijit/_base',
//                'dijit/_base/scroll',
//                'dijit/_base/window',
//                'dijit/_base/sniff',
//                'dijit/typematic',
//                'dijit/_base/focus',
//                'dijit/layout/_LayoutWidget',
//                'dijit/_base/place',
//                'dijit/_base/popup',
//                'dijit/_base/wai',
//                'dijit/_Contained',
//                "dijit/form/Button",
//                "dijit/form/TextBox",
//                "dijit/form/ComboBox",
//                "dijit/form/DropDownButton",
//                "dijit/form/Button"
//            ]
//        },

        // In the demo application, we conditionally require `app/Dialog` on the client-side, so here we build a
        // separate layer containing just that client-side code. (Practically speaking, you would probably just want
        // to roll everything into a single layer, but this helps provide a basic illustration of multi-layer builds.)
        // Note that when you create a new layer, the module referenced by the layer is always included in the layer
        // (in this case, `app/Dialog`), so it does not need to be explicitly defined in the `include` array.
        'chart/raphael/raphael.amd': {
            include :[
                'chart/eve/eve',
                'chart/g.raphael/g.bar',
                'chart/g.raphael/g.dot',
                'chart/g.raphael/g.line',
                'chart/g.raphael/g.pie',
                'chart/g.raphael/g.raphael',
                'chart/raphael/raphael.core',
                'chart/raphael/raphael.svg',
                'chart/raphael/raphael.vml'
            ]
        },

        'me/run' : {
            include :[
                'me/main',
                "me/web/widget/dialog/ModalBox",
                'me/web/widget/ui/Toaster',
                'me/web/widget/profile/ProfileMenu',
                'me/web/widget/menu/DashBoardMenu',
                'me/web/widget/menu/SearchMenu',
                'me/web/widget/support/ToggleMenu',
                'me/web/widget/menu/SearchSuggestItemSection',
                'me/web/widget/menu/SearchSuggestItemsByType',
                'me/web/widget/suggestion/Suggest',
                'me/web/widget/suggestion/SuggestItem',
                'me/web/widget/stream/HashTagInfo',
                'me/web/widget/pictures/AccountPicture',
                'me/support/Offline',
                'me/web/widget/signup/LoginDialog',
                'me/support/Websocket',
                'me/web/widget/ui/More',
                'me/web/widget/utils/ToggleText',
                'me/web/widget/data/CacheLinkedList',
                'me/web/widget/menu/TimeRangeDropDownMenu',
                'me/web/widget/ui/UpgradeBar'
            ].concat(emne_core)
        },

        "me/web/widget/stream/FrontEnd" : {
            include :[
                "me/web/widget/utils/ToggleText",
                "me/web/widget/stream/FrontEndItem",
                "me/web/widget/home/votes/ItemVote",
                "me/web/widget/rated/Comments",
                "me/web/widget/hashtags/Cloud",
                "me/web/widget/rated/UsersProfile",
                "me/web/widget/utils/ToggleText",
                "me/web/widget/hashtags/Cloud",
                "me/web/widget/rated/Comment",
                "me/web/widget/rated/Comments",
                "me/web/widget/rated/UsersProfile",
                "me/web/widget/rated/RatedProfile",
                "me/web/widget/rated/Comments",
                "me/web/widget/rated/LikeRate",
                "me/web/widget/rated/RatedOperations"
            ],
            exclude : excludeDijit.concat(emne_core)
        },

        'me/web/widget/hashtags/HashTagGraph' : {
            include :[
                "me/web/widget/menu/TimeRangeDropDownMenu",
                "dojox/gfx/path",
                "dojox/gfx/svg"
            ],
            exclude : excludeDijit.concat(emne_core)
        },

        'me/web/widget/stats/TopProfiles' : {
            include :[
                "me/web/widget/rated/RatedProfile",
                "me/web/widget/rated/Comment",
                "me/web/widget/rated/RatedOperations",
                "me/web/widget/rated/UsersProfile",
                "me/web/widget/rated/LikeRate"
            ],
            exclude : excludeDijit.concat(emne_core)
        },

        "me/web/widget/social/LinksPublished" :{
            include :[
                "me/web/widget/data/CacheLinkedList",
                "me/web/widget/social/LinksPublishedItem"
            ],
            exclude : excludeDijit.concat(emne_core)
        },

        "me/web/widget/stats/GenericStats" : {
            include :[
                "me/web/widget/chart/EnMeLineChart",
                "me/web/widget/social/LinksPublishedItem",
                "me/web/widget/hashtags/HashTagGraphStatsButton",
                "me/web/widget/hashtags/HashTagGraphStatsUsageHandler"

            ],
            exclude : excludeDijit.concat(emne_core)
        },

        'me/web/widget/tweetpoll/detail/TweetPollChartDetail' : {
            include :[
                'me/web/widget/chart/AbstractChartVoteSupport',
                'me/web/widget/chart/EncuestamePieChart'
            ].concat(chart_dojo),
            exclude : excludeDijit.concat(emne_core)
        },

        'me/web/widget/comments/AddComment' : {
            include :[
                'me/web/widget/comments/Comment',
                "dijit/form/Button",
                "dijit/form/CheckBox",
                "dijit/form/Select",
                "dijit/form/Textarea"
            ].concat(chart_dojo),
            exclude : excludeDijit.concat(emne_core)
        },

        'me/web/widget/results/answers/GenericPercentResult' : {
            include :[
                'me/web/widget/results/answers/ResultSupport'
            ],
            exclude : excludeDijit.concat(emne_core)
        },

        'me/web/widget/poll/detail/PollChartDetail' : {
            include :[
                'me/web/widget/chart/AbstractChartVoteSupport',
                'me/web/widget/chart/EncuestamePieChart'
            ].concat(chart_dojo),
            exclude : excludeDijit.concat(emne_core)
        },

        "me/web/widget/dashboard/DashboardWrapper" :{
            include :[
                'me/web/widget/chart/AbstractChartVoteSupport',
                'me/web/widget/chart/EncuestamePieChart',
                "me/web/widget/dashboard/LayoutSelecter",
                "me/web/widget/dashboard/GadgetDirectory",
                "me/web/widget/dashboard/DashboardGridContainer"
            ].concat(chart_dojo),
            exclude : excludeDijit.concat(emne_core)
        },

        'me/web/widget/notifications/Notification' : {
            include :[
                'me/web/widget/notifications/NotificationItem'
            ],
            exclude : excludeDijit.concat(emne_core)
        },

        //

    'me/web/widget/menu/SettingsMenuSwitch' : {
       include :[
          'me/web/widget/menu/SettingsButton',
          'me/web/widget/profile/Profile',
          'me/web/widget/profile/UploadProfilePicture',
          'dojo/io/iframe',
          'dojo/request/iframe',
          'dijit/form/_ToggleButtonMixin',
          'dijit/form/_RadioButtonMixin',
          'dijit/form/CheckBox',
          'dijit/form/_CheckBoxMixin',
          'dijit/form/ToggleButton',
          'dijit/form/RadioButton',
          'dijit/DropDownMenu',
          'dijit/_MenuBase',
          'dijit/form/Select',
          'dijit/MenuSeparator',
          'dijit/MenuItem',
          'dijit/form/Form',
          'dijit/DropDownMenu',
          'dijit/_KeyNavContainer',
          'dijit/form/_FormSelectWidget',
          'dijit/_Contained'
      ],
      exclude : excludeDijit.concat(emne_core)
    },

    'me/web/widget/social/SocialAccounts' : {
        include :[
            'me/web/widget/social/SocialButton',
            'me/web/widget/social/SocialAccounts',
            'me/web/widget/social/SocialAccountRow',
            'me/web/widget/social/SocialAccountDetail'
        ],
        exclude : excludeDijit.concat(emne_core)
    },

    'me/web/widget/profile/UploadProfilePicture' : {
       include :[
          'dojo/io/iframe',
          'dojo/request/iframe',
          'dijit/form/_ToggleButtonMixin',
          'dijit/form/_RadioButtonMixin',
          'dijit/form/CheckBox',
          'dijit/form/_CheckBoxMixin',
          'dijit/form/ToggleButton',
          'dijit/form/RadioButton'
      ],
      exclude : excludeDijit.concat(emne_core)
    },

    'me/web/widget/profile/Profile' : {
      include :[
          'dijit/DropDownMenu',
          'dijit/_MenuBase',
          'dijit/form/Select',
          'dijit/MenuSeparator',
          'dijit/MenuItem',
          //'dijit/_KeyNavMixin',
          'dijit/form/Form',
          'dijit/DropDownMenu',
          'dijit/_KeyNavContainer',
          'dijit/form/_FormSelectWidget',
          'dijit/_Contained'
      ],
      exclude : excludeDijit.concat(emne_core)
    },

    'me/web/widget/poll/Poll' : {
        include :[
          'dojo/data/ItemFileReadStore',
          'dojo/data/util/simpleFetch',
          'me/web/widget/options/LimitVotes',
          'me/web/widget/options/AbstractOptionSelect',
          'me/web/widget/questions/patterns/AbstractPattern',
          'me/web/widget/support/ActionDialogHandler',
          'me/web/widget/options/CheckSingleOption',
          'me/web/widget/options/CommentsOptions',
          'me/web/widget/options/ConstrainNumberPicker',
          'me/web/widget/utils/ContextSupport',
          'me/core/support/ContextSupport',
          'me/web/widget/options/DateToClose',
          'me/web/widget/support/DnD',
          'me/web/widget/dialog/Dialog',
          'me/web/widget/folder/FolderOperations',
          'me/web/widget/folder/FolderSelect',
          'me/web/widget/folder/FoldersItemAction',
          'me/web/widget/pictures/Icon',
          'me/web/widget/ui/MessageSearch',
          'me/web/widget/publish/PublishEmailSupport',
          'me/web/widget/publish/PublishEmbebedSupport',
          'me/web/widget/publish/PublishPanelItem',
          'me/web/widget/publish/PublishSocialStatus',
          'me/web/widget/publish/PublishSocialSupport',
          'me/core/support/PublishSupport',
          'me/web/widget/publish/PublishSocialSupport',
          'me/web/widget/questions/Question',
          'me/web/widget/options/RepeatedVotes',
          'me/web/widget/options/ResultsOptions',
          'me/web/widget/questions/patterns/SingleResponse',
          'me/web/widget/social/SocialAccountsSupport',
          'me/web/widget/support/SocialFilterMenuItem',
          'me/web/widget/tweetpoll/TweetPollPublishItemFAILUREStatus',
          'me/web/widget/tweetpoll/TweetPollPublishItemSUCCESStatus',
          'me/web/widget/tweetpoll/TweetPollPublishItemStatus',
          'me/web/widget/support/Wipe',
//          'dijit/form/Form',
          'dijit/InlineEditBox',
          'dijit/CalendarLite',
          'dijit/Calendar',
          'dijit/form/CheckBox',
          'dijit/form/_CheckBoxMixin',
          'dijit/form/ComboBox',
          'dijit/form/_DateTimeTextBox',
          'dijit/form/_RadioButtonMixin',
          'dijit/form/_Spinner',
          'dijit/_TimePicker',
          'dijit/form/_ToggleButtonMixin',
          'dijit/typematic',
          'dijit/form/DateTextBox',
          'dijit/form/DropDownButton',
          'dijit/form/NumberSpinner',
          'dijit/form/NumberTextBox',
          'dijit/form/RadioButton',
          'dijit/form/RangeBoundTextBox',
          'dijit/form/TimeTextBox',
          'dijit/form/ToggleButton',
           //TODO: remove when new dnd it's integrated with tweetpoll
          'dojo/dnd/Source',
          'dojo/dnd/Container',
          'dojo/dnd/Selector',
          'dojo/dnd/Manager',
          'dojo/dnd/Moveable',
          'dojo/dnd/Avatar',
          'dojo/dnd/autoscroll',
          'dojo/dnd/Mover',
          'dojo/dnd/TimedMoveable'
        ],
        exclude : excludeDijit.concat(emne_core)
    },

    'me/web/widget/notifications/NotificationList' : {
        include :[
            'me/web/widget/notifications/NotificationListItem'
          ],
        exclude : excludeDijit.concat(emne_core)
    },

    'me/web/widget/comments/Comments' : {
          include :[
            'me/web/widget/comments/Comments'
          ],
        exclude : excludeDijit.concat(emne_core)
    },
//

//
    'me/web/widget/poll/PollNavigate' : {
        include :[
          'dojo/colors',
          'dojo/fx/easing',

          'me/web/widget/poll/PollNavigateItem',
          'me/web/widget/poll/PollNavigateItemDetail',
          'me/web/widget/chart/ChartLayerSupport',
          'me/web/widget/menu/DropDownMenuItem',
          'me/web/widget/menu/DropDownMenuSelect',
          'me/web/widget/chart/EncuestamePieChart',
          'me/web/widget/data/FilterList',
          "me/web/widget/folder/FolderOperations",
          "me/web/widget/folder/FoldersActions",
          "me/web/widget/folder/FoldersItemAction",
          'me/web/widget/ui/More',
          'me/web/widget/support/PanelWipe',
          'me/web/widget/data/TableLinkedList',
          'me/web/widget/utils/UpdateDefaultOptions',
          'me/web/widget/options/YesNoWidget',

          'dijit/form/CheckBox',
          'dijit/form/_CheckBoxMixin',
          'dijit/InlineEditBox',
          'dijit/form/ToggleButton',
          'dijit/form/_ToggleButtonMixin',

          // chart
          'dojox/charting/action2d/Base',
          'dojox/charting/plot2d/Base',
          'dojox/charting/Chart',
          'dojox/charting/Element',
          'dojox/charting/action2d/Highlight',
          'dojox/charting/widget/Legend',
          'dojox/charting/themes/MiamiNice',
          'dojox/charting/action2d/MoveSlice',
          'dojox/charting/plot2d/Pie',
          'dojox/charting/action2d/PlotAction',
          'dojox/charting/Series',
          'dojox/charting/SimpleTheme',
          'dojox/charting/action2d/Tooltip',
          'dojox/charting/plot2d/_PlotEvents',
          'dojox/gfx/_base',
          'dojox/lang/functional/array',
          'dojox/charting/themes/common',
          'dojox/charting/plot2d/common',
          'dojox/charting/axis2d/common',
          'dojox/charting/scaler/common',
          'dojox/lang/functional/fold',
          'dojox/lang/functional',
          'dojox/gfx/fx',
          'dojox/gfx',
          'dojox/gfx/gradutils',
          'dojox/lang/functional/lambda',
          'dojox/gfx/matrix',
          'dojox/lang/functional/object',
          'dojox/gfx/path',
          'dojox/gfx/renderer',
          'dojox/lang/functional/reversed',
          'dojox/lang/functional/scan',
          'dojox/gfx/shape',
          'dojox/gfx/svg',
          'dojox/lang/utils',

           // removed when new dnd it's integrated with tweetpoll
          'dojo/dnd/Source',
          'dojo/dnd/Container',
          'dojo/dnd/Selector',
          'dojo/dnd/Manager',
          'dojo/dnd/Moveable',
          'dojo/dnd/Avatar',
          'dojo/dnd/autoscroll',
          'dojo/dnd/Mover',
          'dojo/dnd/TimedMoveable'
        ],
        exclude : excludeDijit.concat(emne_core)
    },
//

//
    'me/web/widget/tweetpoll/TweetPoll': {
        include :[
          'dojo/cldr/nls/gregorian',
          'dojo/cldr/nls/en/gregorian',
          'dojo/cldr/supplemental',
          'dijit/CalendarLite',
          'dijit/Calendar',
          'dijit/_Container',
          'dijit/DialogUnderlay',
          'dijit/form/_DateTimeTextBox',
          'dijit/form/_FormMixin',
          'dijit/form/NumberSpinner',
          'dijit/form/NumberTextBox',
          'dijit/layout/_ContentPaneResizeMixin',
          'dijit/typematic',
          'dijit/layout/utils',
          'dijit/form/CheckBox',
          'dijit/form/_CheckBoxMixin',
          'dijit/form/DropDownButton',
          'dijit/form/RangeBoundTextBox',
          'dijit/form/TimeTextBox',
          'dijit/form/_ToggleButtonMixin',
          'dijit/_DialogMixin',
          'dijit/Viewport',
          'dijit/form/ToggleButton',
          'dijit/form/DateTextBox',
          'me/web/widget/tweetpoll/TweetPollCore',
          'me/web/widget/menu/OptionMenu',
          'me/web/widget/menu/OptionMenuItem',
          'me/web/widget/tweetpoll/AnswerItem',
          'me/web/widget/tweetpoll/HashTags',
          'me/web/widget/tweetpoll/HashTagsItem',
          'me/web/widget/tweetpoll/TweetPollPreview',
          'me/web/widget/tweetpoll/Answers',
          'me/web/widget/social/SocialAccountPicker',
          'me/web/widget/social/SocialAccountsSupport',
          'me/web/widget/tweetpoll/TweetPollPublishInfo',
          'me/web/widget/ui/HelpContext',
          'me/web/widget/social/SocialPickerAccount',
          'me/web/widget/tweetpoll/TweetPollPublishItemStatus',
          'me/web/widget/tweetpoll/TweetPollPublishItemFAILUREStatus',
          'me/web/widget/tweetpoll/TweetPollPublishItemSUCCESStatus',
          'me/web/widget/tweetpoll/HashTagsSuggest',
          'dijit/_TimePicker',
          'dijit/layout/ContentPane',
          'dijit/form/_Spinner',
           // TODO: remove when new dnd it's integrated with tweetpoll
          'dojo/dnd/Source',
          'dojo/dnd/Container',
          'dojo/dnd/Selector',
          'dojo/dnd/Manager',
          'dojo/dnd/Moveable',
          'dojo/dnd/Avatar',
          'dojo/dnd/autoscroll',
          'dojo/dnd/Mover',
          'dojo/dnd/TimedMoveable'
        ],
        exclude : excludeDijit.concat(emne_core)
    },
//
        'me/web/widget/signup/Signup' : {
            include :[
              'me/web/widget/validator/RealNameValidator',
              'me/web/widget/validator/PasswordValidator',
              'me/web/widget/validator/EmailValidator',
              'me/web/widget/validator/UsernameValidator',
              'me/web/widget/validator/AbstractValidatorWidget',
              'dijit/form/Form'
            ],
            exclude : excludeDijit.concat(emne_core)
        },
//
        'me/web/widget/tweetpoll/TweetPollList': {
            include :[
              'me/web/widget/tweetpoll/TweetPollList',
              'me/web/widget/tweetpoll/TweetPollListDetail',
              'me/web/widget/tweetpoll/TweetPollListItem',
              'me/web/widget/tweetpoll/detail/TweetPollAnswer',
              'me/web/widget/support/Wipe',
              'me/web/widget/options/YesNoWidget',
              "me/core/main_widgets/LoggedUtilities",
              "me/web/widget/folder/FoldersActions",
              "me/web/widget/support/ItemsFilterSupport",
              "me/web/widget/ui/MessageSearch",
              "dojox/gfx/svg",
              "dojox/gfx/path"
            ].concat(chart_dojo),
            exclude : excludeDijit.concat(emne_core)
        }
    },

    // Providing hints to the build system allows code to be conditionally removed on a more granular level than
    // simple module dependencies can allow. This is especially useful for creating tiny mobile builds.
    // Keep in mind that dead code removal only happens in minifiers that support it! Currently, only Closure Compiler
    // to the Dojo build system with dead code removal.
    // A documented list of has-flags in use within the toolkit can be found at
    // <http://dojotoolkit.org/reference-guide/dojo/has.html>.
    //http://dojotoolkit.org/documentation/tutorials/1.7/build/
    staticHasFeatures: {

        //Assumes that all modules are AMD
        //'dojo-amd-factory-scan': 0,

        // Ensures the code is built to run on a browser platform
        'host-browser': 1,

        //Ensures the has feature detection API is available.
        'dojo-has-api': 1,

        // The trace & log APIs are used for debugging the loader, so we do not need them in the build.
        'dojo-trace-api': 0,

        // Disables some diagnostic information
        'dojo-debug-messages': 0,

        //Disables Firebug Lite for browsers that don't have a developer console (e.g. IE6)
        'dojo-firebug': 0,

        //Ensures that the console is available in browsers that don't have it available (e.g. IE6)
        'dojo-guarantee-console': 0,

        //Disables the logging code of the loader
        'dojo-log-api': 0,

        // This causes normally private loader data to be exposed for debugging. In a release build, we do not need
        // that either.
        'dojo-publish-privates': 0,

        // This application is pure AMD, so get rid of the legacy loader.
        'dojo-sync-loader': 0,

        // `dojo-xhr-factory` relies on `dojo-sync-loader`, which we have removed.
        'dojo-xhr-factory': 0,

        // We are not loading tests in production, so we can get rid of some test sniffing code.
        'dojo-test-sniff': 0
    }
};