import {aw} from "../admin/assets/vendor/chart.js/chunks/helpers.segment";

(function ($) {
    "use strict";

    $('[data-bg-image]').each(function () {
        $(this).css({'background-image': 'url(' + $(this).data('bg-image') + ')'});
    });

    $('[data-bg-color]').each(function () {
        $(this).css({'background-color': $(this).data('bg-color')});
    });
    //menu popup

    $('#toggle').on('click', function () {
        $(this).toggleClass('active');
        $('#overlay').toggleClass('open');
    });

    $(window).load(function () {
        // Animate loader off screen
        $("#loader").hide();

    });


    //order popup
   /* $('.entry-order-content').each(function(){
        var selectedLocation = sessionStorage.getItem('selectedLocation');
        var selectedMovie = sessionStorage.getItem('selectedMovie');
        var selectedDate = sessionStorage.getItem('selectedDate');
        var selectedTime = sessionStorage.getItem('selectedTime');

        axios.get(`/api/theater/findTheaterIdByName?name=${encodeURIComponent(selectedLocation)}`)
            .then(function (theaterResponse) {
                theaterId = theaterResponse.data;  // theaterId를 변수에 저장
                return axios.get(`/api/movies/findMovieIdByName?name=${encodeURIComponent(selectedMovie)}`);
            })
            .then(function (movieResponse) {
                movieId = movieResponse.data.id;  // movieId를 변수에 저장
                return $.ajax({
                    // 여기 부분 수정 필요!!
                    // 여기서 scheduleId 이용하고, 해당하는 scheduleId에 대한 reservation 리스트 정보를 갖고오게 해야함

                    url: '/api/reservation/list',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        userId: 1,
                        theaterId: theaterId,
                        movieId: movieId,
                        location: selectedLocation,
                        movie: selectedMovie,
                        date: selectedDate,
                        time: selectedTime
                    }),
                    success: function (response) {
                        updateSeatMap(response.seats);
                    },
                    error: function (error) {
                        console.error("좌석 데이터 전송 오류:", error);
                    }
                });
            })
            .catch(function (error) {
                console.error("에러 발생:", error);
            });

        function updateSeatMap(seats) {
            if (sc) {
                $('#seat-map').empty();
            }

            sc = $('#seat-map').seatCharts({
                map: [
                    'aaaaaaa_aaaaaaa_aaaaaaa',
                    'aaaaaaa_aaaaaaa_aaaaaaa',
                    'aaaaaaa_aaaaaaa_aaaaaaa',
                    'aaaaaaa_aaaaaaa_aaaaaaa',
                    'aaaaaaa_aaaaaaa_aaaaaaa'
                ],
                naming: {
                    top: false,
                    getLabel: function (character, row, column) {
                        return column;
                    }
                },
                legend: {
                    node: $('#legend'),
                    items: [
                        ['a', 'available', 'Available'],
                        ['a', 'unavailable', 'Unavailable'],
                        ['a', 'selected', 'Selected'],
                    ]
                },
                click: function () {
                    if (this.status() == 'available') {
                        $('<li>R' + (this.settings.row + 1) + ' S' + this.settings.label + '</li>')
                            .attr('id', 'cart-item-' + this.settings.id)
                            .data('seatId', this.settings.id)
                            .appendTo($cart);

                        selectedSeats.push({
                            row: this.settings.row,
                            column: this.settings.label
                        });

                        $counter.text(sc.find('selected').length);
                        $total.text(recalculateTotal(sc));

                        return 'selected';
                    } else if (this.status() == 'selected') {
                        selectedSeats = selectedSeats.filter(seat => seat.row !== this.settings.row || seat.column !== this.settings.label);

                        $counter.text(sc.find('selected').length - 1);
                        $total.text(recalculateTotal(sc));
                        $('#cart-item-' + this.settings.id).remove();
                        return 'available';
                    } else if (this.status() == 'unavailable') {
                        return 'unavailable';
                    } else {
                        return this.style();
                    }
                }
            });

            seats.forEach(function (seat) {
                sc.get([seat.row + '_' + seat.column]).status('unavailable');
            });
        }
    });
*/







    // Circle chart
    $('.circle-chart').each(function (ci) {
        var _circle = $(this),
            _id = 'circle-chart' + ci,
            _width = _circle.data('circle-width'),
            _percent = _circle.data('percent'),
            _text = _circle.data('text');

        _percent = (_percent + '').replace('%', '');
        _width = parseInt(_width, 10);

        _circle.attr('id', _id);
        var _cc = Circles.create({
            id: _id,
            value: _percent,
            text: _text,
            radius: 100,
            width: _width,
            colors: ['rgba(255,255,255, .05)', '#fb802d']
        });

    });

    // header search action
    // header search action
    $('#header-search').on('click', function () {
        $('#overlay-search').addClass('active');

        setTimeout(function () {
            $('#overlay-search').find('input').eq(0).focus();
        }, 400);
    });
    $('#overlay-search').find('.close-window').on('click', function () {
        $('#overlay-search').removeClass('active');

    });
    $('#overlay-search').find('.fa-search').on('click', () => {
        const searchStr = document.getElementById('searchStr');
        axios.get({
            url: '/api/movie/search',
            params: {searchStr}
        })
            .then(response => {
                const movieList = response.data.searchMovieList;
            })
            .catch(error => {
                console.log("error: " + error);
            })
    })


    /* Modal video player */
    $('.video-player').each(function () {
        var _video = $(this);
        _video.magnificPopup({
            type: 'iframe'
        });
    });
    $('.featured-image').on('click', function () {
        $(this).find('.video-player').on('click');
    });

    $(document).ready(function () {

        // Sticky menu execution
        if ($('body').hasClass('sticky-menu')) {

            var headerBottom = $('#header').offset().top + $('#header').height();

            var lastScrollTop = 0;
            $(window).scroll(function (event) {
                var st = $(this).scrollTop();

                if ($(window).width() < 992) {
                    if (st > lastScrollTop) {
                        // downscroll code
                        $("body").removeClass("stick");
                    } else {
                        // upscroll code
                        $("body").addClass("stick");
                    }
                } else {
                    if (st >= headerBottom) {
                        $("body").addClass("stick");
                    } else {
                        $("body").removeClass("stick");
                    }
                }
                if (st == 0) {
                    $("body").removeClass("stick");
                }
                lastScrollTop = st;
            });

        }

        //header slider
    });

    $('.carousel').carousel({
        interval: 5000 //changes the speed
    })
    // Carousel

    $('.ticket-carousel').each(function () {
        var $this = $(this);
        var _col = $this.data('columns');
        //$this.find('.carousel-container').css('width', '100%');
        $this.find('.carousel-container').swiper({
            slidesPerView: '5',
            spaceBetween: 0,
            autoHeight: true,
            nextButton: $this.find('.swiper-button-next'),
            prevButton: $this.find('.swiper-button-prev'),
            breakpoints: {
                1300: {
                    slidesPerView: 4
                },
                996: {
                    slidesPerView: 3
                },
                600: {
                    slidesPerView: 1
                }
            }
        });
    });


    $('.comming-slider').each(function () {
        var $this = $(this);
        var comming = new Swiper('#commingslider', {
            loop: true,
            centeredSlides: true,
            spaceBetween: 20,
            slidesPerView: 'auto',
            autoHeight: true,
            nextButton: $this.find('.swiper-button-next'),
            prevButton: $this.find('.swiper-button-prev'),
            breakpoints: {
                1300: {
                    slidesPerView: 4
                },
                996: {
                    slidesPerView: 3
                },
                600: {
                    slidesPerView: 1
                }
            }
        });
    });

    $('.single-slider').each(function () {
        var $this = $(this);
        var single = new Swiper('#singleslider', {
            spaceBetween: 0,
            slidesPerView: 'auto',
            autoHeight: true,
            centeredSlides: true,
            loop: true,
            nextButton: $this.find('.swiper-button-next'),
            prevButton: $this.find('.swiper-button-prev'),
            breakpoints: {
                1300: {
                    slidesPerView: 4
                },
                996: {
                    slidesPerView: 3
                },
                600: {
                    slidesPerView: 1
                }
            }
        });

    });


    //progress bar
    function wpcProgress() {
        if ($('.wpc-skills').length) {
            $('.wpc-skills').not('.animated').each(function () {
                var self = $(this);
                if ($(window).scrollTop() >= self.offset().top - $(window).height()) {
                    self.addClass('animated').find('.timer').countTo();

                    self.find('.line-fill').each(function () {
                        var objel = $(this);
                        var pb_width = objel.attr('data-width-pb');
                        objel.css({'width': pb_width});
                    });
                }

            });
        }
    }

    // Carousel

    $('.wpc-testimonails').each(function () {
        var $this = $(this);
        $this.find('.swiper-container').swiper({
            nextButton: $this.find('.swiper-button-next'),
            prevButton: $this.find('.swiper-button-prev'),
            pagination: $this.find('.swiper-pagination'),
            paginationClickable: true,
            spaceBetween: 30,
            centeredSlides: true,
            //autoplay: 5000,
            autoplayDisableOnInteraction: false,
            // mousewheelControl: true,
            loop: true,

        });
    });

    //sold seat
    //sc.get(['2_9', '2_11', '2_12','2_13','2_14','2_15','2_10','3_11','3_12','3_13',]).status('unavailable');





        function doAnimations(elems) {
            //Cache the animationend event in a variable
            var animEndEv = 'webkitAnimationEnd animationend';

            elems.each(function () {
                var $this = $(this),
                    $animationType = $this.data('animation');
                $this.addClass($animationType).one(animEndEv, function () {
                    $this.removeClass($animationType);
                });
            });
        }

        //Variables on page load
        var $myCarousel = $('#headerslider'),
            $firstAnimatingElems = $myCarousel.find('.item').find("[data-animation ^= 'animated']");

        //Initialize carousel
        $myCarousel.carousel();

        //Animate captions in first slide on page load
        doAnimations($firstAnimatingElems);

        //Pause carousel
        $myCarousel.carousel('pause');


        //Other slides to be animated on carousel slide event
        $myCarousel.on('slide.bs.carousel', function (e) {
            var $animatingElems = $(e.relatedTarget).find("[data-animation ^= 'animated']");
            doAnimations($animatingElems);
        });
        $(window).load(function () {
            // The slider being synced must be initialized first
            $('#carousel_coming').flexslider({
                animation: "slide",
                controlNav: false,
                animationLoop: true,
                slideshow: false,
                loop: true,
                centeredSlides: true,
                itemWidth: 212,
                itemMargin: 20,
                asNavFor: '#slider_coming'
            });

            $('#slider_coming').flexslider({
                animation: "slide",
                controlNav: false,
                slideshow: true,
                sync: "#carousel_coming"
            });
        });

        function wpc_add_img_bg(img_sel, parent_sel) {

            if (!img_sel) {
                console.info('no img selector');
                return false;
            }
            var $parent, _this;

            $(img_sel).each(function () {
                _this = $(this);
                $parent = _this.closest(parent_sel);
                $parent = $parent.length ? $parent : _this.parent();
                $parent.css('background-image', 'url(' + this.src + ')');
                _this.hide();
            });

        }

        $(window).load(function () {
            wpc_add_img_bg('.featured-image img', '.featured-image');
            wpc_add_img_bg('.thumb_item .wpc_img', '.thumb_item');
        });

        //contact form validate
        $(".contact_form").on("submit", function (e) {

            var errorMessage = $(".errorMessage");
            var hasError = false;

            $(".inputValidation").each(function () {
                var $this = $(this);

                if ($this.val() == "") {
                    hasError = true;
                    $this.addClass("inputError");
                    errorMessage.html("<p>Error: Please correct errors above</p>");
                    e.preventDefault();
                }
                if ($this.val() != "") {
                    $this.removeClass("inputError");
                } else {
                    return true;
                }
            }); //Input

            errorMessage.slideDown(700);
        });


    })(jQuery);


