var Head = {
    changeCountry: function (e) {
        var flag = $(this).val();
        $(this).removeClass().addClass('head-logo__select ' + flag);
        $(this).parent().submit();
    }
};

$(function () {
    $('.head-logo__select').change(Head.changeCountry);



});