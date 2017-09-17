var Search = {
    searchFormValidate: function () {
        var key = $('.head-search__key');
        key.focus();
        key = $.trim(key.val());
        return typeof key !== 'undefined' && key !== '';
    },
    searchForm: function () {
        var saveHistory = window.location.pathname.indexOf('/search') === -1;
        var form = $('.head-search');
        UrlUtils.set('/search', {
            type: $('#head-search-type').val(),
            key: $('.head-search__key').val()
        }, saveHistory);

        var request = $.ajax({
            type: 'POST',
            url: '/search',
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
    //$('.head-search__key').keyup(Search.keyUp);
    $('.head-search__key').bindWithDelay('keyup', Search.searchForm, 600);
});