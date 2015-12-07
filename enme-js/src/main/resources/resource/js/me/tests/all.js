window.config = "/";

define( [
	"./unit/core/enme",
	"./unit/core/_base/_config",
	"./unit/core/_base/_browser",
	"./unit/core/_base/_storage",
	"./unit/core/_base/_messages",
	"./unit/core/_base/_utils",
	"./unit/core/_base/_xhr",
	"intern/node_modules/dojo/has!host-browser?./unit/core/features/base",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/SocialsNetworks",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/tweetpoll/test",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Options",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Poll",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Folders",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Dashboard",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Geo",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Hashtag",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Graphs",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/data",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Menu",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Notifications",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Home",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Questions",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Rates",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Results",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Stream",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Support",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Publish",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Users",
	"intern/node_modules/dojo/has!host-browser?./unit/widget/Suggestion",

	// This test causes problems
	//'intern/node_modules/dojo/has!host-browser?./unit/widget/SignUpTest'
], function() {});
