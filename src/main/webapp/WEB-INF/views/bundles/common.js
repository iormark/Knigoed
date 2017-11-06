"use strict";

var message = {
    responseFail: 'Временно недоступно. Попробуйте позже'
};

var Utils = {
    isEmpty: function (obj) {
        // null and undefined are "empty"
        if ($.trim(obj) === '') return true;
        if (obj === null) return true;
        if (typeof obj === 'undefined') return true;

        if (typeof obj === "object" && obj.length === 0) return true;

        // Otherwise, does it have any properties of its own?
        // Note that this doesn't handle
        // toString and valueOf enumeration bugs in IE < 9
        for (var key in obj) {
            if (hasOwnProperty.call(obj, key)) return false;
        }

        return false;
    }
};

$(function () {
    window.onpopstate = function (e) {
        var state = e.state;
        if (state.hasOwnProperty('reload'))
            document.location.reload();
    };
});