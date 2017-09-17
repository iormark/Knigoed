'use strict';

$(function () {
    window.onpopstate = function (e) {
        var state = e.state;
        if (state.hasOwnProperty('reload'))
            document.location.reload();
    };
});