'use strict';

var Search = {
    searchForm: function () {
        var form = $('.header-search');

        var request = $.ajax({
            type: 'POST',
            url: form.attr('action'),
            data: form.serialize(),
            dataType: 'html'
        });

        request.done(function (response) {
            $('#page-content').html(response);
        });

        request.fail(function () {

        });
    }
};

$(function () {
    //$('.header-search__key').keyup(Search.keyUp);
    $('.header-search__key').bindWithDelay('keyup', Search.searchForm, 600);
});