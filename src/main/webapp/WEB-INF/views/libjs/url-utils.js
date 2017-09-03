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
        return (false);
    },
    set: function (key, value) {
        var key = encodeURIComponent(key);
        var value = encodeURIComponent(value);
        var query = document.location.search.substr(1).split('&');

        var i = query.length;
        var x;
        while (i--) {
            x = query[i].split('=');
            if (x[0] == key) {
                x[1] = value;
                query[i] = x.join('=');
                break;
            }
        }
        if (i < 0)
            query[query.length] = [key, value].join('=');
        history.replaceState(null, null, '/search?' + query.join('&'));
    }
};