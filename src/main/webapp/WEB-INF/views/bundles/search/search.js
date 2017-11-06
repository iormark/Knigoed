var Search = {
    searchFormValidate: function () {
        var key = $('.head-search__key');
        key.focus();
        key = $.trim(key.val());
        return typeof key !== 'undefined' && key !== '';
    },
    searchForm: function (callback, params, saveHistory) {
        saveHistory = saveHistory ? true : window.location.pathname.indexOf('/search') === -1;
        var form = $('.head-search');
        params = Object.assign({
            type: $('#head-search-type').val(),
            shop: $('#head-search-shop').val(),
            key: $('.head-search__key').val()
        }, params);

        UrlUtils.set('/search', params, saveHistory);

        var request = $.ajax({
            url: location.href,
            type: 'get',
            data: {ajax: true},
            dataType: 'html'
        });

        request.done(function (response) {
            if (callback)
                callback(response);
        });

        request.fail(function () {

        });
    },

    updateKey: function (e) {
        e.preventDefault();
        Search.searchForm(function (response) {
            $('#page-content').html(response);
        }, {page: 1});
    },

    updateType: function (e) {
        var type = '';
        $('.head-search__type').removeClass('current');

        if (typeof e !== 'undefined') {
            e.preventDefault();
            if (!Search.searchFormValidate())
                return false;

            var curr = $(this);
            type = curr.data('type');
            curr.addClass('current');
        } else {
            type = UrlUtils.get('type');
            $('.head-search__type' + (type ? '[data-type="' + type + '"]' : ':first')).addClass('current');
        }

        $('#head-search-type').val(type);

        if (typeof e !== 'undefined') {
            Search.searchForm(function (response) {
                $('#page-content').html(response);
            }, {page: 1});
        }
    },

    updateShop: function (e) {
        var shop = '';
        $('.head-search__type').removeClass('menu__item_current');

        if (typeof e !== 'undefined') {
            e.preventDefault();
            if (!Search.searchFormValidate())
                return false;

            var curr = $(this);
            shop = curr.data('shop');
            curr.addClass('menu__item_current');
        } else {
            shop = UrlUtils.get('shop');
            $('.srch__menu-item' + (shop ? '[data-shop="' + shop + '"]' : ':first')).addClass('menu__item_current');
        }

        $('#head-search-shop').val(shop);

        if (typeof e !== 'undefined') {
            Search.searchForm(function (response) {
                $('#page-content').html(response);
                $('.srch__menu-item' + (shop ? '[data-shop="' + shop + '"]' : ':first')).addClass('menu__item_current');
            }, {page: 1});
        }
    },

    nextPage: function (e) {
        e.preventDefault();
        var btn = $(this);
        btn.prop('disabled', true);
        var page = UrlUtils.get('page');
        var params = {page: (Utils.isEmpty(page) ? 2 : parseInt(page) + 1)};

        Search.searchForm(function (response) {
            btn.prop('disabled', false);
            var nextPage = $('.next-page');
            var nextPageOffset = nextPage.offset().top;
            nextPage.remove();

            $('.srch:last').after(response);

            $('html, body').animate({
                scrollTop: nextPageOffset - 35
            }, 500);
        }, params, true);
    }
};

$(function () {
    //$('.head-search__key').keyup(Search.keyUp);
    $('.head-search__key').bindWithDelay('keyup', Search.updateKey, 500);

    $(document).on('click', '.head-search__type', Search.updateType);
    $(document).on('click', '.srch__menu-item', Search.updateShop);
    $(document).on('click', '#next-page', Search.nextPage);

    Search.updateType();
    Search.updateShop();
});