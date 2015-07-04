/*global module */

module.exports = function (grunt) {

    // Project configuration.
    grunt.initConfig({

	      dirs : {
		      src: 'me/',
		      tests: 'me/tests/'
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
                reporter: require('jshint-stylish')
            },
            all: ['Gruntfile.js', '<%= dirs.tests %>/**/*.js', '<%= dirs.tests %>/*.js', '!<%= dirs.tests %>/resources/**/*.js']
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

    // task to execute test in saucelabs remotely
    grunt.registerTask('test-saucelabs', [ 'jshint:all', 'intern:remote_saucelabs' ]);
    // task to execute test in browserstack.com remotely
    grunt.registerTask('test', [ 'jshint:all', 'intern:remote' ]);
    grunt.registerTask('travis', [ 'jshint:all' ]);
    // this task require a local selenium server running
    grunt.registerTask('test-local', ['jshint:all', 'intern:local_browser']);
    // this task is defined for remote quick testing
    grunt.registerTask('test-remote', ['jshint:all', 'intern:remote_local' ]);
    grunt.registerTask('dev', [ 'jshint:all', 'connect:server' ]);
    grunt.registerTask('default', [ 'test-local' ]);

};
