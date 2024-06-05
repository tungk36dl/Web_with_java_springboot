
$(document).ready(function () {
    var galleryThumbs2 = new Swiper('.PD_Media .gallery-thumbs', {
        spaceBetween: 8,
        slidesPerView: 5,
        // loop: true,
        freeMode: true,
        loopedSlides: 7,
        watchSlidesVisibility: true,
        watchSlidesProgress: true,
        breakpoints: {
            1025: {
                slidesPerView: 4,
            }
        }
    });
    var galleryTop2 = new Swiper('.PD_Media .gallery-top', {
        // loop: true,
        loopedSlides: 7, //looped slides should be the same
        thumbs: {
            swiper: galleryThumbs2,
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
    });

    $('.PD_Media .gallery-thumbs .swiper-slide').click(function () {
        $('.gallery-top .swiper-slide img').removeClass('zoom_01');
        if ($('.gallery-top .swiper-slide').hasClass('swiper-slide-active')) {
            $('.gallery-top .swiper-slide-active img').addClass('zoom_01');
            $('.zoom_01').ezPlus();
        } else {
            $('.gallery-top .swiper-slide img').removeClass('zoom_01');
        }
    });
    $('.PD_Media .gallery-thumbs .swiper-button-prev').click(function () {
        $('.gallery-top .swiper-slide img').removeClass('zoom_01');
        if ($('.gallery-top .swiper-slide').hasClass('swiper-slide-active')) {
            $('.gallery-top .swiper-slide-active img').addClass('zoom_01');
            $('.zoom_01').ezPlus();
        } else {
            $('.gallery-top .swiper-slide img').removeClass('zoom_01');
        }
    });
    $('.PD_Media .gallery-thumbs .swiper-button-next').click(function () {
        $('.gallery-top .swiper-slide img').removeClass('zoom_01');
        if ($('.gallery-top .swiper-slide').hasClass('swiper-slide-active')) {
            $('.gallery-top .swiper-slide-active img').addClass('zoom_01');
            $('.zoom_01').ezPlus();
        } else {
            $('.gallery-top .swiper-slide img').removeClass('zoom_01');
        }
    });
    $('.zoom_01').ezPlus();
    $('#lightgallery').lightGallery({
        selector: '.item',
    });
});
