/**
 * Created by jpicado on 17/11/15.
 */

var path = require( "path" );

var webpack = require( "webpack" );

var ROOT_PATH = path.resolve( __dirname );
var APP_PATH = path.resolve( ROOT_PATH, "js/mobile/init.js");
var COMMON_PATH = path.resolve( ROOT_PATH, "js/mobile/common.js");
var BUILD_PATH = path.resolve( ROOT_PATH, "js/mobile/build");
console.log( APP_PATH );

var config = {
  entry: {
    app: APP_PATH,
    common: COMMON_PATH
  },
  output: {
    path: BUILD_PATH,
    filename: "[name].js"
  },
  plugins: [
		new webpack.ProvidePlugin({

			// Automtically detect jQuery and $ as free var in modules
			// and inject the jquery library
			// This is required by many jquery plugins
			jQuery: "jquery",
			$: "jquery"
		})
	],
  module: {
    loaders:[
      { test: /\.css$/, loader: "style-loader!css-loader" },
      { test: /\.js[x]?$/, exclude: /node_modules/, loader: "babel-loader" }
    ]
  },
  debug: false
};

module.exports = config;
