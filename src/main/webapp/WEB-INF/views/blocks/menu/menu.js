$(function () {
    'use strict';
    // Menu Hover
    $(document).on('hover', '.menu__hover', function () {
        clearTimeout($.data(this, 'timer'));
        $(this).next('.menu__content').stop(true, true).show();
    }, function () {
        $.data(this, 'timer', setTimeout($.proxy(function () {
            $(this).next('.menu__content').stop(true, true).hide();
        }, this), 250));
    });

    // Menu Click
    $(document).on('click', '.menu__btn', function (e) {
        e.stopPropagation();
        var elem = $(this);
        elem.parent().removeClass('open');
        elem.removeClass('open');

        elem.next('.menu__content').toggleClass('menu__content_show');
        if (elem.hasClass('open')) {
            elem.parent().removeClass('open');
            elem.removeClass('open');
        }

        elem.parent().addClass('open');
        elem.addClass('open');
    });

    $('html').click(function (e) {
        if (!$(e.target).closest('.menu__btn').length) {
            var menu = $('.menu__btn');
            menu.parent().removeClass('open');
            menu.removeClass('open');
            $('.menu__content').removeClass('menu__content_show');
        }
    });
});