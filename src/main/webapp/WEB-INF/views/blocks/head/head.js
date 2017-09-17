var Head = {
    changeCountry: function (e) {
        var flag = $(this).val();
        $(this).removeClass().addClass('head__select ' + flag);
        $(this).parent().submit();
    },
    updateType: function (e) {
        e.preventDefault();

        if(!Search.searchFormValidate())
            return false;

        var obj = $(this);
        var type = obj.data('type');
        $('.head-search__type').removeClass('current');
        obj.addClass('current');
        $('#head-search-type').val(type);

        Search.searchForm();
    }
};

$(function () {
    $('.head__select').change(Head.changeCountry);


    $(document).on('click', '.head-search__type', Head.updateType);

    var type = UrlUtils.get('type');
    $('.head-search__type').removeClass('current');
    $('.head-search__type' + (type ? '[data-type="' + type + '"]' : ':first')).addClass('current');

});