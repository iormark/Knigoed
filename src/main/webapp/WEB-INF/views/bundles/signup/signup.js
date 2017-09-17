'use strict';

var Signup = {
    signup: function (e) {
        e.preventDefault();
        var form = $(this);
        var btn = form.find('.signup__btn');
        var message = $('.message');
        message.removeClass('message_error');
        btn.prop('disabled', true);
        var request = $.ajax({
            type: 'post',
            url: form.attr("action"),
            data: {
                name: form.find('input[name=username]').val(),
                email: form.find('input[name=email]').val(),
                password: md5(form.find('input[name=password]').val()),
                rememberMe: true
            },
            dataType: 'json'
        });

        request.done(function (response) {
            if (typeof response.error !== 'undefined') {
                message.addClass('message_error').find('.message__content').text(response.error);
                btn.prop('disabled', false);
            }
            else
                window.location.replace('/shop-list');
        });

        request.fail(function () {
            message.addClass('message_error').find('.message__content').text('Unknown auth error');
            btn.prop('disabled', false);
        });
    }
};

$(function () {
    $(document).on('submit', '#signup-form', Signup.signup);
});