var Search = {
    searchFormValidate: function () {
        var key = $('.head-search__key');
        key.focus();
        key = $.trim(key.val());
        return typeof key !== 'undefined' && key !== '';
    },
    searchForm: function (callback) {
        var saveHistory = window.location.pathname.indexOf('/search') === -1;
        var form = $('.head-search');
        UrlUtils.set('/search', {
            type: $('#head-search-type').val(),
            shop: $('#head-search-shop').val(),
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
            if (typeof callback !== 'undefined')
                callback(response);
        });

        request.fail(function () {

        });
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
            Search.searchForm();
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
                $('.srch__menu-item' + (shop ? '[data-shop="' + shop + '"]' : ':first')).addClass('menu__item_current');
            });
        }
    }
};

$(function () {
    //$('.head-search__key').keyup(Search.keyUp);
    $('.head-search__key').bindWithDelay('keyup', Search.searchForm, 600);

    $(document).on('click', '.head-search__type', Search.updateType);
    $(document).on('click', '.srch__menu-item', Search.updateShop);

    Search.updateType();
    Search.updateShop();
});