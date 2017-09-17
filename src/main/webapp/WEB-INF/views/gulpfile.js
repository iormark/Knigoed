'use strict';
require('es6-promise').polyfill();
/*
cd src/main/webapp/WEB-INF/views
npm install --save es6-promise \
gulp-csscomb gulp-uglify gulp-clean-css gulp-htmlclean gulp-group-css-media-queries gulp-concat gulp-autoprefixer gulp-rigger gulp-rename gulp-imagemin gulp-flatten
*/

var gulp = require('gulp'),
    csscomb = require('gulp-csscomb'),
    minifyJS = require('gulp-uglify'),
    minifyCSS = require('gulp-clean-css'),
    minifyHTML = require('gulp-htmlclean'),
    gcmq = require('gulp-group-css-media-queries'),
    concat = require('gulp-concat'),
    autoprefixer = require('gulp-autoprefixer'),
    rigger = require('gulp-rigger'),
    rename = require("gulp-rename"),
    imagemin = require('gulp-imagemin'),
    flatten = require('gulp-flatten');


gulp.task('style', function () {
    var files = [
        //'bundles/normalize.css',
        'bundles/common.css',
        'bundles/button.css',
        'bundles/**/*.css',
        'blocks/**/*.css'
    ];
    gulp.src(files)
        //.pipe(autoprefixer({browsers: ['last 20 versions']}))
        //.pipe(minifyCSS({compatibility: 'ie8'}))
        .pipe(concat('style.css'))
        .pipe(gcmq())
        .pipe(gulp.dest('../../builds'));
});


gulp.task('script', function () {
    var files = [
        'libjs/**',
        'bundles/common.js',
        'bundles/**/*.js',
        '!bundles/add/add.js',
        'blocks/**/*.js'
    ];
    gulp.src(files)
        //.pipe(minifyJS())
        .pipe(concat('script.js'))
        .pipe(gulp.dest('../../builds'));
});

gulp.task('images', function () {
    return gulp.src('blocks/**/*.{jpg,jpeg,png,gif}')
        .pipe(imagemin({
            optimizationLevel: 3,
            progessive: true,
            interlaced: true
        }))
        .pipe(flatten())
        .pipe(gulp.dest('../../images'));
});


gulp.task('default', ['style', 'script', 'images']);

gulp.task('watch', function () {
    gulp.watch([
        './bundles/**/*.css',
        './blocks/**/*.css',
        './bundles/**/*.js',
        './blocks/**/*.js',
        './bundles/**/*.html',
        './blocks/**/*.html'
    ], function (event) {
        gulp.run(['style', 'script']);
    });
});