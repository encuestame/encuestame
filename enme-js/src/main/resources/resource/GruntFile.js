/*global module */

var server = require('./src/widget/tests/server/app');

module.exports = function (grunt) {

// Project configuration.
grunt.initConfig({

  pkg: grunt.file.readJSON('package.json'),

  dirs : {
    src: 'js/me/',
    tests: 'js/me/tests/',
    widget_dist: 'js/widget',
    widget_build: 'src/widget/build',
    widget_src : 'src/widget/modules',
    widget_test: 'src/widget/tests',
    less_src : 'css/less',
    css_src : 'css/pages',
    commons_src: 'src/commons',
    commons: 'js/commons'
  },

  banner: '/*! <%= pkg.title || pkg.name %> - v<%= pkg.version %> - ' +
  '<%= grunt.template.today("yyyy-mm-dd") %>\n' +
  '<%= pkg.homepage ? "* " + pkg.homepage + "\\n" : "" %>' +
  '* Copyright (c) <%= grunt.template.today("yyyy") %> <%= pkg.author.name %>;' +
  ' Licensed <%= _.pluck(pkg.licenses.type, "type").join(", ") %> */\n',

  clean: {
    dist: ['<%= dirs.widget_build %>','<%= dirs.dist %>']
  },

  concat: {
    basic_and_extras: {
      files: {
        '<%= dirs.commons %>/commons.js': ['<%= dirs.commons_src %>/Chart.js','<%= dirs.commons_src %>/offline.js','<%= dirs.commons_src %>/intro.js','<%= dirs.commons_src %>/alertify/lib/alertify.js'],
        '<%= dirs.commons %>/commons-mobile.js': ['<%= dirs.commons_src %>/Chart.js','<%= dirs.commons_src %>/offline.js', '<%= dirs.commons_src %>/alertify/lib/alertify.js'],
        '<%= dirs.commons %>/maps.js': ['<%= dirs.commons_src %>/leaflet-0.7.1/leaflet-src.js'],
        '<%= dirs.commons %>/init.js': ['<%= dirs.commons_src %>/modernizr.js', 'req/json_services.js']
      }
    }
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
    },
    commons: {
      files: {
        '<%= dirs.commons %>/commons.min.js': ['<%= dirs.commons %>/commons.js'],
        '<%= dirs.commons %>/commons-mobile.min.js': ['<%= dirs.commons %>/commons-mobile.js'],
        '<%= dirs.commons %>/maps.min.js': ['<%= dirs.commons %>/maps.js'],
        '<%= dirs.commons %>/init.min.js': ['<%= dirs.commons %>/init.js']
      }
    }
  },

  less: {
    development: {
      options: {
        compress: false,
        yuicompress: false,
        //sourceMap : true,
        optimization: 1
      },
      files: {
        "<%= dirs.css_src %>/tweetpoll.css": "<%= dirs.less_src %>/tweetpoll.less",
        "<%= dirs.css_src %>/home.css": "<%= dirs.less_src %>/home.less",
        "<%= dirs.css_src %>/admon.css": "<%= dirs.less_src %>/admon.less",
        "<%= dirs.css_src %>/dashboard.css": "<%= dirs.less_src %>/dashboard.less",
        "<%= dirs.css_src %>/notifications.css": "<%= dirs.less_src %>/notifications.less",
        "<%= dirs.css_src %>/poll.css": "<%= dirs.less_src %>/poll.less",
        "<%= dirs.css_src %>/question.css": "<%= dirs.less_src %>/question.less",
        "<%= dirs.css_src %>/settings.css": "<%= dirs.less_src %>/settings.less",
        "<%= dirs.css_src %>/setup.css": "<%= dirs.less_src %>/setup.less",
        "<%= dirs.css_src %>/survey.css": "<%= dirs.less_src %>/survey.less",
        "<%= dirs.css_src %>/user.css": "<%= dirs.less_src %>/user.less",
        "<%= dirs.css_src %>/vote.css": "<%= dirs.less_src %>/vote.less",
        "<%= dirs.css_src %>/resources.css": "<%= dirs.less_src %>/resources.less",
        "<%= dirs.css_src %>/menu_logged.css": "<%= dirs.less_src %>/menu_logged.less",
        "<%= dirs.css_src %>/map.css": "<%= dirs.less_src %>/map.less",
        "<%= dirs.css_src %>/mobile_vote.css": "<%= dirs.less_src %>/mobile_vote.less",
        "<%= dirs.css_src %>/mobile_user.css": "<%= dirs.less_src %>/mobile_user.less",
        "<%= dirs.css_src %>/mobile_tweetpoll.css": "<%= dirs.less_src %>/mobile_tweetpoll.less",
        "<%= dirs.css_src %>/mobile_survey.css": "<%= dirs.less_src %>/mobile_survey.less",
        "<%= dirs.css_src %>/mobile_setup.css": "<%= dirs.less_src %>/mobile_setup.less",
        "<%= dirs.css_src %>/mobile_settings.css": "<%= dirs.less_src %>/mobile_settings.less",
        "<%= dirs.css_src %>/mobile_question.css": "<%= dirs.less_src %>/mobile_question.less",
        "<%= dirs.css_src %>/mobile_poll.css": "<%= dirs.less_src %>/mobile_poll.less",
        "<%= dirs.css_src %>/mobile_notifications.css": "<%= dirs.less_src %>/mobile_notifications.less",
        "<%= dirs.css_src %>/mobile_home.css": "<%= dirs.less_src %>/mobile_home.less",
        "<%= dirs.css_src %>/mobile_dashboard.css": "<%= dirs.less_src %>/mobile_dashboard.less",
        "<%= dirs.css_src %>/mobile_admon.css": "<%= dirs.less_src %>/mobile_admon.less",
        //embebed
        "<%= dirs.css_src %>/embebed/detail.css": "<%= dirs.less_src %>/embebed/detail.less",
        "<%= dirs.css_src %>/embebed/form.css": "<%= dirs.less_src %>/embebed/form.less",
        "<%= dirs.css_src %>/embebed/results.css": "<%= dirs.less_src %>/embebed/results.less",
        //bootstrap 3
        //"<%= dirs.css_src %>/bootstrap3.css": "<%= dirs.less_src %>/bootstrap3/bootstrap.less"
      }
    },
    production: {
      options: {
        compress: true,
        yuicompress: true,
        optimization: 2
      },
      files: {
        "<%= dirs.css_src %>/tweetpoll.min.css": "<%= dirs.less_src %>/tweetpoll.less",
        "<%= dirs.css_src %>/home.min.css": "<%= dirs.less_src %>/home.less",
        "<%= dirs.css_src %>/admon.min.css": "<%= dirs.less_src %>/admon.less",
        "<%= dirs.css_src %>/dashboard.min.css": "<%= dirs.less_src %>/dashboard.less",
        "<%= dirs.css_src %>/notifications.min.css": "<%= dirs.less_src %>/notifications.less",
        "<%= dirs.css_src %>/poll.min.css": "<%= dirs.less_src %>/poll.less",
        "<%= dirs.css_src %>/question.min.css": "<%= dirs.less_src %>/question.less",
        "<%= dirs.css_src %>/settings.min.css": "<%= dirs.less_src %>/settings.less",
        "<%= dirs.css_src %>/setup.min.css": "<%= dirs.less_src %>/setup.less",
        "<%= dirs.css_src %>/survey.min.css": "<%= dirs.less_src %>/survey.less",
        "<%= dirs.css_src %>/user.min.css": "<%= dirs.less_src %>/user.less",
        "<%= dirs.css_src %>/vote.min.css": "<%= dirs.less_src %>/vote.less",
        "<%= dirs.css_src %>/resources.min.css": "<%= dirs.less_src %>/resources.less",
        "<%= dirs.css_src %>/menu_logged.min.css": "<%= dirs.less_src %>/menu_logged.less",
        "<%= dirs.css_src %>/map.min.css": "<%= dirs.less_src %>/map.less",
        "<%= dirs.css_src %>/mobile_vote.min.css": "<%= dirs.less_src %>/mobile_vote.less",
        "<%= dirs.css_src %>/mobile_user.min.css": "<%= dirs.less_src %>/mobile_user.less",
        "<%= dirs.css_src %>/mobile_tweetpoll.min.css": "<%= dirs.less_src %>/mobile_tweetpoll.less",
        "<%= dirs.css_src %>/mobile_survey.min.css": "<%= dirs.less_src %>/mobile_survey.less",
        "<%= dirs.css_src %>/mobile_setup.min.css": "<%= dirs.less_src %>/mobile_setup.less",
        "<%= dirs.css_src %>/mobile_settings.min.css": "<%= dirs.less_src %>/mobile_settings.less",
        "<%= dirs.css_src %>/mobile_question.min.css": "<%= dirs.less_src %>/mobile_question.less",
        "<%= dirs.css_src %>/mobile_poll.min.css": "<%= dirs.less_src %>/mobile_poll.less",
        "<%= dirs.css_src %>/mobile_notifications.min.css": "<%= dirs.less_src %>/mobile_notifications.less",
        "<%= dirs.css_src %>/mobile_home.min.css": "<%= dirs.less_src %>/mobile_home.less",
        "<%= dirs.css_src %>/mobile_dashboard.min.css": "<%= dirs.less_src %>/mobile_dashboard.less",
        "<%= dirs.css_src %>/mobile_admon.min.css": "<%= dirs.less_src %>/mobile_admon.less",
        //embebed
        "<%= dirs.css_src %>/embebed/detail.min.css": "<%= dirs.less_src %>/embebed/detail.less",
        "<%= dirs.css_src %>/embebed/form.min.css": "<%= dirs.less_src %>/embebed/form.less",
        "<%= dirs.css_src %>/embebed/results.min.css": "<%= dirs.less_src %>/embebed/results.less"
      }
    }
  },

  //mocha_phantomjs: {
   // all: ['<%= dirs.widget_test %>/**/*.html']
  //},

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
                          return next();
                      },
                      connect.static(require('path').resolve('.'))
                  ];
              }
          }
      }
  },

  watch: {
    styles: {
      files: ['<%= dirs.less_src %>/**/*.less'], // which files to watch
      tasks: ['less:development'],
      options: {
        nospawn: true
      }
    }
  }
});

  // plugins
  grunt.loadNpmTasks('intern');
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-connect');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-browserify');
  //grunt.loadNpmTasks('grunt-mocha-phantomjs');
  grunt.loadNpmTasks('grunt-contrib-less');



  // task to execute test in saucelabs remotely
  grunt.registerTask('test-saucelabs', [ 'jshint-all', 'intern:remote_saucelabs' ]);
  grunt.registerTask('widget', ['jshint:js_widget', 'browserify', 'uglify:widget' ]);
  // task to execute test in browserstack.com remotely
  grunt.registerTask('jshint-all', [ 'jshint:all' ]);
  grunt.registerTask('test', [ 'jshint-all', 'intern:remote' ]);
  grunt.registerTask('commons', [ 'concat', 'uglify:commons' ]);
  grunt.registerTask('css', ['less:development', 'less:production' ]);
  // this task require a local selenium server running
  grunt.registerTask('test-local', ['jshint-all', 'intern:local_browser']);
  // this task is defined for remote quick testing
  grunt.registerTask('test-remote', ['intern:remote_local' ]);
  grunt.registerTask('dev', [ 'jshint-all', 'connect:server' ]);
  grunt.registerTask('default', ['jshint-all', 'css', 'commons', 'widget']);
  grunt.registerTask('production', ['jshint-all', 'css', 'commons', 'widget']);

};
