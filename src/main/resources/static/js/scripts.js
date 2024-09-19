		(function($){
	"use strict";

	$('[data-bg-image]').each(function(){
		$(this).css({ 'background-image': 'url('+$(this).data('bg-image')+')' });
	});

	$('[data-bg-color]').each(function(){
		$(this).css({ 'background-color': $(this).data('bg-color') });
	});
	//menu popup

    $('#toggle').on('click', function() {
	    $(this).toggleClass('active');
	    $('#overlay').toggleClass('open');
  	});

  	$(window).load(function() {
		// Animate loader off screen
		$("#loader").hide();
		
	});


	//order popup

	$('.order_btn').magnificPopup({
	  type:'inline',
	  removalDelay: 500, 
	  mainClass: 'mfp-zoom-in',
	  callbacks: {
	    beforeOpen: function() {
	       this.st.mainClass = this.st.el.attr('data-effect');
	    }
	  },
	  midClick: true 

	});
	$('.close-window').on('click',function() {
        $.magnificPopup.close();
    });


    // Circle chart
	$('.circle-chart').each(function(ci){
		var _circle = $(this),
			_id = 'circle-chart' + ci,
			_width = _circle.data('circle-width'),
			_percent = _circle.data('percent'),
			_text = _circle.data('text');

		_percent = (_percent + '').replace('%', '');
		_width = parseInt(_width, 10);

		_circle.attr('id', _id);
		var _cc = Circles.create({
			id:         _id,
			value:		_percent,
			text: 		_text,
			radius:     100,
			width:      _width,
			colors:     ['rgba(255,255,255, .05)', '#fb802d']
		});

	});

	// header search action
	$('#header-search').on('click', function(){
		$('#overlay-search').addClass('active');

		setTimeout(function(){
			$('#overlay-search').find('input').eq(0).focus();
		}, 400);
	});
	$('#overlay-search').find('.close-window').on('click', function(){
		$('#overlay-search').removeClass('active');

	});

	
	/* Modal video player */
	$('.video-player').each(function(){
		var _video = $(this);
		_video.magnificPopup({
			type: 'iframe'
		});
	});
	$('.featured-image').on('click', function(){
		$(this).find('.video-player').on('click');
	});

	$(document).ready(function(){

		// Sticky menu execution
		if( $('body').hasClass('sticky-menu') ) {

			var headerBottom = $('#header').offset().top + $('#header').height();

			var lastScrollTop = 0;
			$(window).scroll(function(event){
				var st = $(this).scrollTop();

				if( $(window).width()<992 ){
					if (st > lastScrollTop){
						// downscroll code
						$("body").removeClass("stick");
					} else {
						// upscroll code
						$("body").addClass("stick");
					}
				}
				else{
					if (st >= headerBottom ) {
				        $("body").addClass("stick");
				    } else {
				    	$("body").removeClass("stick");
				    }
				}
				if( st == 0 ) {
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

	$('.ticket-carousel').each(function(){
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


	
	$('.comming-slider').each(function(){
		var $this = $(this);
		var comming = new Swiper ('#commingslider', {
		  loop: true,
		  centeredSlides: true,
		  spaceBetween : 20,
		  slidesPerView: 'auto',
		  autoHeight: true,
		  nextButton: $this.find('.swiper-button-next'),
		  prevButton: $this.find('.swiper-button-prev'),
		  breakpoints :{
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

	$('.single-slider').each(function(){
		var $this = $(this);
		var single = new Swiper ('#singleslider', {
		  spaceBetween : 0,
		  slidesPerView: 'auto',
		  autoHeight: true,
		  centeredSlides: true,
		  loop:true,
		  nextButton: $this.find('.swiper-button-next'),
		  prevButton: $this.find('.swiper-button-prev'),
		  breakpoints :{
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
	function wpcProgress(){
        if($('.wpc-skills').length) {
             $('.wpc-skills').not('.animated').each(function(){
                var self = $(this);    
                if($(window).scrollTop() >= self.offset().top - $(window).height() ){
                    self.addClass('animated').find('.timer').countTo();

                    self.find('.line-fill').each(function(){
                        var objel = $(this);
                        var pb_width = objel.attr('data-width-pb');
                        objel.css({'width':pb_width});
                    });
                }
 
             });      
         }
    } 
    // Carousel
	$('.wpc-testimonails').each(function(){
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
	$('.entry-order-content').each(function(){
		//jQuery time
		var current_fs, next_fs, previous_fs; //fieldsets
		var left, opacity, scale; //fieldset properties which we will animate
		var animating; //flag to prevent quick multi-click glitches

		$(".next").on('click',function(){
			if(animating) return false;
			animating = true;
			
			current_fs = $(this).parent();
			next_fs = $(this).parent().next();
			
			//show the next fieldset
			next_fs.show(); 
			//hide the current fieldset with style
			current_fs.animate({opacity: 0}, {
				step: function(now, mx) {
					//as the opacity of current_fs reduces to 0 - stored in "now"
					//1. scale current_fs down to 80%
					scale = 1 - (1 - now) * 0.2;
					//2. bring next_fs from the right(50%)
					left  = (now * 50)+"%";
					//3. increase opacity of next_fs to 1 as it moves in
					opacity = 1 - now;
					current_fs.css({'transform': 'scale('+scale+')'});
					next_fs.css({'left': left, 'opacity': opacity});
				}, 
				duration: 800, 
				complete: function(){
					current_fs.hide();
					animating = false;
				}, 
				//this comes from the custom easing plugin
				easing: 'easeInOutBack'
			});
		});

		$(".previous").on('click',function(){
			if(animating) return false;
			animating = true;
			
			current_fs = $(this).parent();
			previous_fs = $(this).parent().prev();
			
			//show the previous fieldset
			previous_fs.show(); 
			//hide the current fieldset with style
			current_fs.animate({opacity: 0}, {
				step: function(now, mx) {
					//as the opacity of current_fs reduces to 0 - stored in "now"
					//1. scale previous_fs from 80% to 100%
					scale = 0.8 + (1 - now) * 0.2;
					//2. take current_fs to the right(50%) - from 0%
					left = ((1-now) * 50)+"%";
					//3. increase opacity of previous_fs to 1 as it moves in
					opacity = 1 - now;
					current_fs.css({'left': left});
					previous_fs.css({'transform': 'scale('+scale+')', 'opacity': opacity});
				}, 
				duration: 800, 
				complete: function(){
					current_fs.hide();
					animating = false;
				}, 
				//this comes from the custom easing plugin
				easing: 'easeInOutBack'
			});
		});

		$(".submit").on('click',function(){
			return false;
		});

	});

	$(document).keydown(function(e){
 		if(e.keyCode == 27) {
 			if ($('#toggle').hasClass('active')) {
	            $('#toggle').removeClass('active');
	        } 
	        if ($('.overlay').hasClass('open')) {
	            $('.overlay').removeClass('open');
	        } 
	        if ($('#overlay-search').hasClass('active')) {
	            $('#overlay-search').removeClass('active');
	        } 
	    }
	});

	var price = 13; //price
	$(document).ready(function() {
		var $cart = $('#selected-seats'), //Sitting Area
		$counter = $('#counter'), //Votes
		$total = $('#total'); //Total money
		
		var sc = $('#seat-map').seatCharts({
			map: [  //Seating chart
				'aaaaaaa_aaaaaaa_aaaaaaa',
				'aaaaaaa_aaaaaaa_aaaaaaa',
				'aaaaaaa_aaaaaaa_aaaaaaa',
				'aaaaaaa_aaaaaaa_aaaaaaa',
				'aaaaaaa_aaaaaaa_aaaaaaa'
			],
			naming : {
				top : false,
				getLabel : function (character, row, column) {
					return column;
				}
			},
			legend : { //Definition legend
				node : $('#legend'),
				items : [
					[ 'a', 'available',   'Available' ],
					[ 'a', 'unavailable', 'Unavailable'],
					[ 'a', 'selected', 'selected'],
				]					
			},
			click: function () { //Click event
				if (this.status() == 'available') { //optional seat
					$('<li>R'+(this.settings.row+1)+' S'+this.settings.label+'</li>')
						.attr('id', 'cart-item-'+this.settings.id)
						.data('seatId', this.settings.id)
						.appendTo($cart);

					$counter.text(sc.find('selected').length+1);
					$total.text(recalculateTotal(sc)+price);
								
					return 'selected';
				} else if (this.status() == 'selected') { //Checked
						//Update Number
						$counter.text(sc.find('selected').length-1);
						//update totalnum
						$total.text(recalculateTotal(sc)-price);
							
						//Delete reservation
						$('#cart-item-'+this.settings.id).remove();
						//optional
						return 'available';
				} else if (this.status() == 'unavailable') { //sold
					return 'unavailable';
				} else {
					return this.style();
				}
			}
		});
		//sold seat
		sc.get(['2_9', '2_11', '2_12','2_13','2_14','2_15','2_10','3_11','3_12','3_13',]).status('unavailable');
			
	});
	//sum total money
	function recalculateTotal(sc) {
		var total = 0;
		sc.find('selected').each(function () {
			total += price;
		});
				
		return total;
	}

	function doAnimations( elems ) {
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
	$(window).load(function() {
 	 	// The slider being synced must be initialized first
		  $('#carousel_coming').flexslider({
		    animation: "slide",
		    controlNav: false,
		    animationLoop: true,
		    slideshow: false,
		    loop:true,
		    centeredSlides:true,
		    itemWidth: 212,
		    itemMargin: 20,
		    asNavFor: '#slider_coming'
		  });
		 
		  $('#slider_coming').flexslider({
		    animation: "slide",
		    controlNav: false,
		    slideshow:true,
		    sync: "#carousel_coming"
		  });
	});

    function wpc_add_img_bg(img_sel, parent_sel) {

        if (!img_sel) {
            console.info('no img selector');
            return false;
        }
        var $parent, _this;

        $(img_sel).each(function() {
            _this = $(this);
            $parent = _this.closest(parent_sel);
            $parent = $parent.length ? $parent : _this.parent();
            $parent.css('background-image', 'url(' + this.src + ')');
            _this.hide();
        });

    }
    $(window).load(function() {
        wpc_add_img_bg('.featured-image img', '.featured-image');
        wpc_add_img_bg('.thumb_item .wpc_img', '.thumb_item');
    });
    
    //contact form validate
    $(".contact_form").on("submit", function(e){
  
	  	var errorMessage  = $(".errorMessage");
	  	var hasError = false;
	  
	  	$(".inputValidation").each(function(){
	  	  	var $this = $(this);
	    
	    if($this.val() == ""){
	      hasError = true;
	      $this.addClass("inputError");
	      errorMessage.html("<p>Error: Please correct errors above</p>");
	      e.preventDefault();
	    }if($this.val() != ""){
	      	$this.removeClass("inputError"); 
	    }else{
	      	return true; 
	    }
	}); //Input
	  
	  errorMessage.slideDown(700);
	}); 


})(jQuery);


