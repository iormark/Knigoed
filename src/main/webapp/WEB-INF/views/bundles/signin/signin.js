'use strict';

var Signin = {
    signin: function (e) {
        e.preventDefault();
        var form = $(this);
        var btn = form.find('.signin__btn');
        var message = $('.message');
        message.removeClass('message_error');
        form.find('.field-message').hide();
        btn.prop('disabled', true);
        var request = $.ajax({
            type: 'POST',
            url: form.attr("action"),
            data: form.serialize(),
            dataType: 'json'
        });

        request.done(function (response) {
            if (Utils.isEmpty(response)) {
                window.location.replace('/shop');
                return;
            }

            if (typeof response.error !== 'undefined') {
                message.addClass('message_error').find('.message__content').text(response.error);
                btn.prop('disabled', false);
            }

            if (typeof response.fieldErrors !== 'undefined') {
                var fieldErrors = response.fieldErrors;
                for (var field in fieldErrors) {
                    var fieldInput = form.find('#field-message-' + field);
                    fieldInput.text(fieldErrors[field]);
                    fieldInput.show();
                }
            }
            btn.prop('disabled', false);
        });

        request.fail(function () {
            message.addClass('message_error').find('.message__content').text('Unknown auth error');
            btn.prop('disabled', false);
        });
    }
};

$(function () {
    $(document).on('submit', '#signin-form', Signin.signin);
});