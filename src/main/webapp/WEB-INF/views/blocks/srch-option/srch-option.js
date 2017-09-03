'use strict';

var SrchOption = {
    updateType:function (e) {
        e.preventDefault();
        SrchOption.setType(this);
        Search.searchForm();
    },
    updateShop:function (e) {
        e.preventDefault();
        SrchOption.setShop(this);
        Search.searchForm();
    },

    setType: function (_this) {
        var obj = $(_this);
        var type = obj.data('type');
        $('.srch-option__type').removeClass('current');
        obj.addClass('current');
        $('#srch-option-type').val(type);
        UrlUtils.set('type', type);
    },
    setShop: function (_this) {
        var obj = $(_this);
        var shop = obj.data('shop');
        $('.srch-option__shop').removeClass('current');
        obj.addClass('current');
        $('#srch-option-shop').val(shop);
        UrlUtils.set('shop', shop);
    }
};

$(function () {
    $(document).on('click', '.srch-option__type', SrchOption.updateType);
    $(document).on('click', '.srch-option__shop', SrchOption.updateShop);

    var type = UrlUtils.get('type');

    $('.srch-option__type').removeClass('current');
    $('.srch-option__type[data-type="' + type + '"]').addClass('current');

    var shop = UrlUtils.get('shop');
    $('.srch-option__shop').removeClass('current');
    $('.srch-option__shop[data-shop="' + shop + '"]').addClass('current');
});