/**
 * Copyright 2014 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

var jshint = require('gulp-jshint');
var gulp = require('gulp');
var gutil = require('gulp-util');
var source = require('vinyl-source-stream');
var mochaPhantomJS = require('gulp-mocha-phantomjs');
var browserify = require('gulp-browatchify');
var watchify = require('watchify');
var del = require('del');

gulp.task('default', ['clean'], function() {
    gulp.start('lint', 'browserify');
});

gulp.task('dev', ['clean'], function() {
    gulp.start('lint', 'browserify');
});

gulp.task('clean', function(cb) {
    del(['dist/*'], cb)
});

gulp.task('build', ['clean'], function() {
    gulp.start('lint', 'test', 'browserify');
});

gulp.task('lint', function() {
    return gulp.src('./modules/widgets/*.js')
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

gulp.task('test', function () {
    return gulp
        .src('tests/runner.html')
        .pipe(mochaPhantomJS());
});

gulp.task('browserify', function () {
    gulp.src('./modules/widget.js')
        .pipe(browserify({
            debug: !process.env.production
        }))
        .pipe(source('../dist/enme-widget.js'))
        .pipe(gulp.dest('./modules'))
});

gulp.watch('./modules/**/*.js', ['browserify']);

gulp.task('watch', function() {
    var bundler = watchify(browserify('src/index.js', watchify.args));

    // Optionally, you can apply transforms
    // and other configuration options on the
    // bundler just as you would with browserify
    bundler.transform('brfs');

    bundler.on('update', rebundle);

    function rebundle() {
        return bundler.bundle()
            // log errors if they happen
            .on('error', gutil.log.bind(gutil, 'Browserify Error'))
            .pipe(source('bundle.js'))
            .pipe(gulp.dest('./dist'));
    }

    return rebundle();
});
