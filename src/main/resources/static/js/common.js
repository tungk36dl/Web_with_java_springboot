(function ($) {
    "use strict";
    var iOS = /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream;
    var isMobile = {
        Android: function () {
            return navigator.userAgent.match(/Android/i);
        },
        BlackBerry: function () {
            return navigator.userAgent.match(/BlackBerry/i);
        },
        iOS: function () {
            return navigator.userAgent.match(/iPhone|iPad|iPod/i);
        },
        Opera: function () {
            return navigator.userAgent.match(/Opera Mini/i);
        },
        Windows: function () {
            return navigator.userAgent.match(/IEMobile/i);
        },
        any: function () {
            return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
        }
    }

    //backToTop
    function backToTop() {
        $(window).scroll(function () {
            if ($(window).scrollTop() >= 200) {
                $('#to_top').fadeIn();
            } else {
                $('#to_top').fadeOut();
            }
        });

        $("#to_top").click(function () {
            $("html, body").animate({
                scrollTop: 0
            });
            return false;
        });
    }

    //resizeSite
    function resizeSite() {
        var heightVideo = $('#player_playing').height() - 64;
        $('.detail_right .scrollbar-inner').height(heightVideo);
    }
    //onCLick
    function onCLick() {
        $('.search-btn').click(function () {
            $('.main-nav').removeClass('show-all-menu');
            $('.all-menu').removeClass('close-menu-tablet');
            if (!$(this).hasClass('is-clicked')) {
                $(this).addClass('is-clicked');
                $('.search-wrap').fadeIn();
                $('.search-wrap input').focus();
            } else {
                $(this).removeClass('is-clicked');
                $('.search-wrap').fadeOut();
            }
        });
        $(".all-menu-tablet").click(function () {
            $(this).toggleClass("close-menu-tablet");
        });
        $(".all-menu").click(function () {
            $('body').toggleClass('open');
            $('.search-btn').removeClass('is-clicked');
            $('.search-wrap').fadeOut();
            $(".main-nav").toggleClass("show-all-menu");
        });

        $(".filter-control").click(function () {
            $('.product_categories').toggleClass('open');
            $('.close-show').addClass('active');
            $(".mask-content").toggle();
        });
		$(".mask-content").click(function () {
			$(this).hide();
            $(".product_categories").removeClass("open");
            $('.close-show').removeClass('active');
		});
		$(".close-show").click(function () {
            $(this).removeClass('active');
            $(".product_categories").removeClass("open");
            $(".mask-content").toggle();
		});

        $(".tab-default a").click(function (event) {
            $(".tab-default a").removeClass("active")
            if (!$(this).hasClass("active")) {
                $(this).addClass("active");
            } else {
                $(this).removeClass("active");
            }
            event.preventDefault();
            var tab = $(this).attr("href");
            $(".tab-content >div").not(tab).css("display", "none");
            $(tab).fadeIn();
        });

        $('.sub-icon').click(function () {
            if ($(this).next('ul').css('display') == 'none') {
                $(this).html('-');
            } else {
                $(this).html('+');
            };
            $(this).next('ul').slideToggle("slow", function () {});
        });

        $('.sub-icon2').click(function () {
            if ($(this).next('ul').css('display') == 'none') {
                $(this).html('-');
            } else {
                $(this).html('+');
            };
            $(this).next('ul').slideToggle("slow", function () {});
        });


    }
    //scrollBar
    function slideSwiper() {
        var swiper_banner = new Swiper('.section-banner-home .swiper-container', {
            spaceBetween: 30,
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
        });
        var swiper_customer = new Swiper('.section-customer .swiper-container', {
            spaceBetween: 30,
            loop: true,
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
        });

    }


    $(function () {
        backToTop();
        onCLick();
        slideSwiper();
        cssVars({});
    });
    $(window).on('load resize', function () {
        resizeSite()
    });
})(jQuery);