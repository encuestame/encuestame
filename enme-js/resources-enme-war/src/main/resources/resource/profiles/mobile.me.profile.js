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
  releaseName: '2.0',

  version : "2.0",

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
  selectorEngine: 'lite',

  packages:[{
            name: "dojo",
            location: "dojo"
        },{
            name: "dijit",
            location: "dijit"
        },{
            name: "dojox",
            location: "dojox"
        },{
            name: "chart",
            location: "chart"
        },{
            name: "me",
            location: "me"
        },{
            name: "org",
            location: "org"
        }],

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
       'dojo/i18n',
       'dojo/_base/declare',
       'dojo/parser',
       "dojo/ready",
       "dojo/dom",
       "dojo/html",
       'dojo/_base/unload',
       "dojo/_base/lang",
       'dojo/date/locale',
       "dojo/store/util/QueryResults",
       'dojo/domReady',
       'dojo/cache',
       //'me/main',
       'mobile/mobile-run' ],

      // By default, the build system will try to include `dojo/main` in the built `dojo/dojo` layer, which adds
      // a bunch of stuff we do not want or need. We want the initial script load to be as small and quick to
      // load as possible, so we configure it as a custom, bootable base.
      boot: true,
      customBase: true
    },

    // In the demo application, we conditionally require `app/Dialog` on the client-side, so here we build a
    // separate layer containing just that client-side code. (Practically speaking, you would probably just want
    // to roll everything into a single layer, but this helps provide a basic illustration of multi-layer builds.)
    // Note that when you create a new layer, the module referenced by the layer is always included in the layer
    // (in this case, `app/Dialog`), so it does not need to be explicitly defined in the `include` array.
    'chart/raphael/raphael.amd': {
        include :['chart/eve/eve',
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

    'me/main' : {
         include :[
                   "me/core/enme",
                   "me/core/URLServices",
                   "me/core/main_widgets/WidgetServices",
                   "me/core/main_widgets/EnmeMainLayoutWidget",
                   "me/web/widget/dialog/ModalBox",
                   'dojo/store/util/SimpleQueryEngine',
                   'me/web/widget/ui/Toaster',
                   'me/web/widget/profile/ProfileMenu',
                   'me/web/widget/menu/DashBoardMenu',
                   'me/web/widget/menu/SearchMenu',
                   'me/web/widget/support/ToggleMenu',
                   'me/web/widget/menu/SearchSuggestItemSection',
                   'me/web/widget/menu/SearchSuggestItemsByType',
                   'me/web/widget/suggestion/Suggest',
                   'me/web/widget/suggestion/SuggestItem',
                   'me/core/ui/Loading',
                   'me/web/widget/stream/HashTagInfo',
                   'me/web/widget/pictures/AccountPicture',
                   'me/activity/Activity',
                   "dijit/registry",
                   'dijit/Dialog',
                   "dijit/form/TextBox",
                   "dijit/form/Button",
                   "dijit/_WidgetBase",
                   "dijit/_TemplatedMixin",
                   'dijit/form/ValidationTextBox',
                   "dijit/_WidgetsInTemplateMixin",
                   "dojox/data/QueryReadStore",
                   'dojox/widget/Toaster',
                   'dojox/widget/UpgradeBar'
            ]
    },
    'dojox/cometd' : {
        include :[
          'org/cometd',
          'org/cometd/AckExtension',
          'org/cometd/ReloadExtension',
          'org/cometd/TimeStampExtension',
          'org/cometd/TimeSyncExtension',
          'dojox/cometd/main',
          'dojox/cometd/ack',
          'dojox/cometd/reload',
          'dojox/cometd/timestamp',
          'dojox/cometd/timesync'
       ]
    },

    'me/activity/Activity' : {
        include :[
          'dojox/cometd',
          'me/web/widget/notifications/Notification',
          'me/web/widget/notifications/NotificationItem'
        ]
    },

    // 'me/web/widget/hashtags/Cloud' : {
    //     include :[
    //            "me/web/widget/utils/ToggleText",
    //            "me/web/widget/hashtags/Cloud",
    //            "me/web/widget/rated/Comment",
    //            "me/web/widget/rated/Comments",
    //            "me/web/widget/rated/UsersProfile",
    //            "me/web/widget/rated/RatedProfile",
    //            "me/web/widget/rated/Comments",
    //            "me/web/widget/rated/LikeRate",
    //            "me/web/widget/rated/RatedOperations"
    //         ]
    // },

    "me/web/widget/stream/FrontEnd" : {
      include :[
               "me/web/widget/utils/ToggleText",
               "me/web/widget/stream/FrontEndItem",
               "me/web/widget/home/votes/ItemVote"
            ]
    },

    'me/web/widget/menu/SettingsMenuSwitch' : {
       include :[
          'dojo/hash',
          'me/web/widget/menu/SettingsButton'
      ]
    },

    'me/web/widget/social/SocialAccounts' : {
        include :[
            'me/web/widget/social/SocialButton',
            'me/web/widget/social/SocialAccounts',
            'me/web/widget/social/SocialAccountRow',
            'me/web/widget/social/SocialAccountDetail',
            'dijit/form/Form'
        ]
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
      ]
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
      ]
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

          'dijit/form/Form',
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
        ]
    },

    'me/web/widget/notifications/NotificationList' : {
        include :[
            'me/web/widget/notifications/NotificationListItem'
          ]
    },

    'me/web/widget/hashtags/HashTagGraph' : {
         include :[
            'me/web/widget/hashtags/HashTagGraphStatsButton',
            'me/web/widget/hashtags/HashTagGraphStatsUsageHandler',
            'me/web/widget/hashtags/HashtagChart',
            'me/web/widget/chart/RaphaelSupport',
            'chart/raphael/raphael.amd',
            'chart/raphael/raphael.core',
            'chart/raphael/raphael.svg',
            'chart/raphael/raphael.vml',
            'chart/g.raphael/g.raphael',
            'chart/g.raphael/g.line'
          ]
    },

    'me/web/widget/comments/Comments' : {
          include :[
            'me/web/widget/comments/Comments'
          ]
    },

    'me/web/widget/tweetpoll/detail/TweetPollChartDetail' : {
        include :[

          'me/web/widget/chart/AbstractChartVoteSupport',
          'me/web/widget/chart/EncuestamePieChart',

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
          'dojox/lang/utils'
        ]
    },

    'me/web/widget/poll/PollNavigate' : {
        include :[
          'dojo/colors',
          'dojo/fx/easing',
          'dojo/hash',

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
        ]
    },

    'me/web/widget/results/answers/GenericPercentResult' : {
       include :[
           'me/web/widget/results/answers/ResultSupport'
       ]
    },

    'me/web/widget/social/LinksPublished' : {
        include :[
            'me/web/widget/data/CacheLinkedList',
            'me/web/widget/social/LinksPublishedItem',
            'me/web/widget/ui/More'
        ]
    },

    'me/web/widget/poll/detail/PollChartDetail' : {
        include :[
          'me/web/widget/chart/AbstractChartVoteSupport',
          'me/web/widget/chart/EncuestamePieChart',
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
          'dojox/lang/utils'
        ]
    },

    'me/web/widget/tweetpoll/TweetPoll': {
        include :[
          'dojo/date',
          'dojo/hash',
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
        ]
    },

    'me/web/widget/signup/Signup' : {
        include :[
          'me/web/widget/validator/RealNameValidator',
          'me/web/widget/validator/PasswordValidator',
          'me/web/widget/validator/EmailValidator',
          'me/web/widget/validator/UsernameValidator',
          'me/web/widget/validator/AbstractValidatorWidget',
          'dijit/form/Form'
        ]
    },

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
        ]
    }
  },

  // Providing hints to the build system allows code to be conditionally removed on a more granular level than
  // simple module dependencies can allow. This is especially useful for creating tiny mobile builds.
  // Keep in mind that dead code removal only happens in minifiers that support it! Currently, only Closure Compiler
  // to the Dojo build system with dead code removal.
  // A documented list of has-flags in use within the toolkit can be found at
  // <http://dojotoolkit.org/reference-guide/dojo/has.html>.
  staticHasFeatures: {
    // The trace & log APIs are used for debugging the loader, so we do not need them in the build.
    'dojo-trace-api': 0,
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