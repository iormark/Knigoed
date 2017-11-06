/**
 * URL Utils
 * https://www.knigoed.info
 * Version 1.2 Copyright (C) Mark Iordan
 */

'use strict';
var UrlUtils = {
    get: function (variable) {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == variable) {
                return pair[1];
            }
        }
        return null;
    },
    set: function (path, keyValue, saveHistory) {
        var query = document.location.search.substr(1).split('&');
        query = query.filter(function (value) {
            return value !== '';
        });

        var x;
        for (var i = 0; i < query.length; i++) {
            x = query[i].split('=');
            if (keyValue.hasOwnProperty(x[0])) {
                x[1] = encodeURIComponent(keyValue[x[0]]);
                query[i] = x.join('=');
                delete keyValue[x[0]];
            }
        }

        var arr = [];
        for (var key in keyValue) {
            if (!Utils.isEmpty(keyValue[key]))
                arr.push(key + '=' + encodeURIComponent(keyValue[key]));
        }
        $.merge(query, arr);

        var url = path + '?' + query.join('&');
        console.log('UrlUtils: ' + url);
        if (saveHistory) {
            history.pushState({reload: true}, null, null);
            history.pushState({reload: true}, null, url);
        }
        else
            history.replaceState({reload: true}, null, url);
    }
};

// window.onpopstate = function() {console.log(event.state);}; history.pushState({url: "http://www.knigoed.dev/book/6104"}, '');
