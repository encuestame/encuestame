/*global module */

var server = require('./widget/tests/server/app');

module.exports = function (grunt) {

    // Project configuration.
    grunt.initConfig({

	      pkg: grunt.file.readJSON('package.json'),

	      dirs : {
		      src: 'me/',
		      tests: 'me/tests/',
		      widget_dist: 'widget/dist',
		      widget_build: 'widget/build',
		      widget_src : 'widget/modules',
		      widget_test: 'widget/tests'
	      },

		    banner: '/*! <%= pkg.title || pkg.name %> - v<%= pkg.version %> - ' +
		    '<%= grunt.template.today("yyyy-mm-dd") %>\n' +
		    '<%= pkg.homepage ? "* " + pkg.homepage + "\\n" : "" %>' +
		    '* Copyright (c) <%= grunt.template.today("yyyy") %> <%= pkg.author.name %>;' +
		    ' Licensed <%= _.pluck(pkg.licenses.type, "type").join(", ") %> */\n',

	      clean: {
			    dist: ['<%= dirs.widget_build %>','<%= dirs.dist %>']
		    },

		    browserify: {
			    client: {
				    src: ['<%= dirs.widget_src %>/**/*.js'],
				    dest: '<%= dirs.widget_build %>/widget.js'
			    }
		    },

		    uglify: {
			    options: {
				   mangle: true,
				   banner: '<%= banner.full %>'
			    },
			    widget: {
				    files: {
					    '<%= dirs.widget_dist %>/widget.js': ['<%= dirs.widget_build %>/*.js']
				    }
			    }
		    },

		    mocha_phantomjs: {
			    all: ['<%= dirs.widget_test %>/**/*.html']
		    },

	    intern: {
            local_browser: {
                options: {
                    runType: 'runner', 
                    config: '<%= dirs.tests %>/intern_local_browser'
                }
            },
            remote_local: {
                options: {
                    runType: 'runner', 
                    config: '<%= dirs.tests %>/intern_remote_local_test'
                }
            },
            remote: {
                options	: {
                    runType: 'runner', 
                    config: '<%= dirs.tests %>/intern_remote'
                }
            },
            remote_saucelabs: {
                options	: {
                    runType: 'runner', 
                    config: '<%= dirs.tests %>/intern_remote_saucelabs'
                }
            }
        },

        jshint: {
            options: {
                reporter: require('jshint-stylish'),
	              jshintrc: './.jshintrc'
            },
            all: [
	            'Gruntfile.js',
	            '<%= dirs.tests %>/**/*.js',
	            '<%= dirs.tests %>/*.js',
	            '!<%= dirs.tests %>/resources/**/*.js'],
	        js_widget: [
		        '<%= dirs.widget_src %>/util/**/*.js',
		        '<%= dirs.widget_src %>/widgets/**/*.js',
		        '<%= dirs.widget_src %>/widget.js'],
	        'test-unit': ['<%= dirs.widget_test %>/spec/**/*.js']
        },

        connect: {
            server: {
                options: {
                    port: 3000,
                    keepalive: true,
                    hostname: 'localhost',
                    base: '.',
                    middleware: function(connect, options) {
                        return [
                            function(req, res, next) {
                                res.setHeader('Access-Control-Allow-Origin', '*');
                                res.setHeader('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE');
                                res.setHeader('Access-Control-Allow-Headers', 'Content-Type');

                                // don't just call next() return it
                                return next();
                            },

                            // add other middlewares here
                            connect.static(require('path').resolve('.'))

                        ];
                    }
                }
            }
        }
    });

    // plugins
    grunt.loadNpmTasks('intern');
    grunt.loadNpmTasks('grunt-contrib-connect');
    grunt.loadNpmTasks('grunt-contrib-jshint');
		grunt.loadNpmTasks('grunt-contrib-clean');
		grunt.loadNpmTasks('grunt-contrib-watch');
		grunt.loadNpmTasks('grunt-contrib-uglify');
		grunt.loadNpmTasks('grunt-browserify');
		grunt.loadNpmTasks('grunt-mocha-phantomjs');


    // task to execute test in saucelabs remotely
    grunt.registerTask('test-saucelabs', [ 'jshint:all', 'intern:remote_saucelabs' ]);
		grunt.registerTask('widget', ['jshint:js_widget', 'browserify', 'uglify:widget' ]);
		// task to execute test in browserstack.com remotely
		grunt.registerTask('test', [ 'jshint:all', 'intern:remote' ]);
    grunt.registerTask('travis', [ 'jshint:all' ]);
    // this task require a local selenium server running
    grunt.registerTask('test-local', ['jshint:all', 'intern:local_browser']);
    // this task is defined for remote quick testing
    grunt.registerTask('test-remote', ['jshint:all', 'intern:remote_local' ]);
    grunt.registerTask('dev', [ 'jshint:all', 'connect:server' ]);
    grunt.registerTask('default', [ 'widget', 'test-remote' ]);

};
