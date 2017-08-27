'use strict';

var Signin = {
    signin: function (e) {
        e.preventDefault();
        var form = $(this);
        var btn = form.find('.signin-form__btn');
        var message = form.find('.message');
        btn.prop('disabled', true);
        var request = $.ajax({
            type: 'post',
            url: form.attr("action"),
            data: form.find('input').serialize(),
            dataType: 'json'
        });

        request.done(function (response) {
            if (typeof response.error !== 'undefined') {
                message.text(response.error);
            } else if(typeof response.redirect !== 'undefined')
                window.location.replace(response.redirect);
        });

        request.fail(function () {
            message.text('Не предвиденная ошибка, простите.');
            btn.prop('disabled', false);
        });
    }
};

$(function () {
    $(document).on('submit', '#signin-form', Signin.signin);
});