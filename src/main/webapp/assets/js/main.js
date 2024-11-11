(function ($) {
	"use strict";
	/*----------------------------------------
		 Hide Header
	----------------------------------------*/
	$( ".top-close-button" ).click(function( event ) {
	event.preventDefault();
		$( ".header-top-area" ).toggle();
	});

	/*----------------------------------------
	   Sticky Menu Activation
	---------------------------------*/
	$(window).on('scroll', function () {
		if ($(this).scrollTop() > 300) {
			$('.header-sticky').addClass('sticky');
		} else {
			$('.header-sticky').removeClass('sticky');
		}
	});

	/*----------------------------------------
		Off Canvas
	-------------------------------------------*/
	$(".off-canvas-btn").on('click', function () {
		$("body").addClass('fix');
		$(".off-canvas-wrapper").addClass('open');
	});

	$(".btn-close-off-canvas,.off-canvas-overlay").on('click', function () {
		$("body").removeClass('fix');
		$(".off-canvas-wrapper").removeClass('open');
	});

	/*----------------------------------------
		Responsive Mobile Menu
	------------------------------------------*/
	//Variables
	var $offCanvasNav = $('.mobile-menu'),
	$offCanvasNavSubMenu = $offCanvasNav.find('.dropdown');

	//Add Toggle Button With Off Canvas Sub Menu
	$offCanvasNavSubMenu.parent().prepend('<span class="menu-expand"><i></i></span>');

	//Close Off Canvas Sub Menu
	$offCanvasNavSubMenu.slideUp();

	//Category Sub Menu Toggle
	$offCanvasNav.on('click', 'li a, li .menu-expand', function(e) {
	var $this = $(this);
	if ( ($this.parent().attr('class').match(/\b(menu-item-has-children|has-children|has-sub-menu)\b/)) && ($this.attr('href') === '#' || $this.hasClass('menu-expand')) ) {
		e.preventDefault();
		if ($this.siblings('ul:visible').length){
			$this.parent('li').removeClass('active');
			$this.siblings('ul').slideUp();
		} else {
			$this.parent('li').addClass('active');
			$this.closest('li').siblings('li').removeClass('active').find('li').removeClass('active');
			$this.closest('li').siblings('li').find('ul:visible').slideUp();
			$this.siblings('ul').slideDown();
		}
	}
	});
	/*----------------------------------------*/
	/*------ Popup Video
	-------------------------------------------------*/
	$('.popup-vimeo').magnificPopup({
		type: 'iframe',
		disableOn: function () {
			if ($(window).width() < 300) {
				return false;
			}
			return true;
		}
	});
	/*----------------------------------------*/
	/*------ Popup Image
	-------------------------------------------------*/
	$('.popup-gallery').magnificPopup({
		delegate: 'a',
		type: 'image',
		tLoading: 'Loading image #%curr%...',
		mainClass: 'mfp-img-mobile',
		gallery: {
			enabled: true,
			navigateByImgClick: true,
			preload: [0,1] // Will preload 0 - before current, and 1 after the current image
		},
		image: {
			tError: '<a href="%url%">The image #%curr%</a> could not be loaded.'
		}
	});
	/*--------------------------------
    Scroll To Top
	-------------------------------- */
	function scrollToTop() {
		var $scrollUp = $('.scroll-to-top'),
			$lastScrollTop = 0,
			$window = $(window);

		$window.on('scroll', function () {
			var topPos = $(this).scrollTop();
			if (topPos > $lastScrollTop) {
				$scrollUp.removeClass('show');
			} else {
				if ($window.scrollTop() > 200) {
					$scrollUp.addClass('show');
				} else {
					$scrollUp.removeClass('show');
				}
			}
			$lastScrollTop = topPos;
		});

		$scrollUp.on('click', function (evt) {
			$('html, body').animate({
				scrollTop: 0
			}, 600);
			evt.preventDefault();
		});
	}
	scrollToTop();
	/*----------------------------------------*/
	/*  Nice Select
	/*----------------------------------------*/
	$(document).ready(function () {
		$('.nice-select').niceSelect();
	});
	/*----------------------------------------*/
	/* Toggle Function Active
	/*----------------------------------------*/
	// showlogin toggle
	$('#showlogin').on('click', function () {
		$('#checkout-login').slideToggle(900);
	});
	// showlogin toggle
	$('#showcoupon').on('click', function () {
		$('#checkout_coupon').slideToggle(900);
	});
	// showlogin toggle
	$('#cbox').on('click', function () {
		$('#cbox-info').slideToggle(900);
	});

	// Ship box toggle
	$('#ship-box').on('click', function () {
		$('#ship-box-info').slideToggle(1000);
	});
	/*----------------------------------------*/
	/*  商品 Grid Activation
	/*----------------------------------------*/
	$('.shop_toolbar_btn > button').on('click', function (e) {
	
		e.preventDefault();
		
		$('.shop_toolbar_btn > button').removeClass('active');
		$(this).addClass('active');
		
		var parentsDiv = $('.shop_wrapper');
		var viewMode = $(this).data('role');
		
		
		parentsDiv.removeClass('grid_3 grid_4 grid_5 grid_list').addClass(viewMode);

		if(viewMode == 'grid_3'){
			parentsDiv.children().addClass('col-lg-4 col-md-6 col-sm-6').removeClass('col-lg-3 col-cust-5 col-12');
			
		}

		if(viewMode == 'grid_4'){
			parentsDiv.children().addClass('col-lg-3 col-md-6 col-sm-6').removeClass('col-lg-4 col-cust-5 col-12');
		}
		
		if(viewMode == 'grid_list'){
			parentsDiv.children().addClass('col-12').removeClass('col-lg-3 col-lg-4 col-md-6 col-sm-6 col-cust-5');
		}
			
	});
	/*----------------------------------------*/
	/*  Cart Plus Minus Button
	/*----------------------------------------*/
	$('.cart-plus-minus').append(
		'<div class="dec qtybutton"><i class="fa fa-minus"></i></div><div class="inc qtybutton"><i class="fa fa-plus"></i></div>'
	);
	$('.qtybutton').on('click', function () {
		var $button = $(this);
		var oldValue = $button.parent().find('input').val();
		if ($button.hasClass('inc')) {
			var newVal = parseFloat(oldValue) + 1;
		} else {
			// Don't allow decrementing below zero
			if (oldValue > 1) {
				var newVal = parseFloat(oldValue) - 1;
			} else {
				newVal = 1;
			}
		}
		$button.parent().find('input').val(newVal);
        vmm.num = newVal
	});
	/*----------------------------------------*/
	/*  Countdown
	/*----------------------------------------*/
	$('[data-countdown]').each(function() {
		var $this = $(this), finalDate = $(this).data('countdown');
		$this.countdown(finalDate, function(event) {
			$this.html(event.strftime('<div class="single-countdown"><span class="single-countdown_time">%D</span><span class="single-countdown_text">Days</span></div><div class="single-countdown"><span class="single-countdown_time">%H</span><span class="single-countdown_text">Hours</span></div><div class="single-countdown"><span class="single-countdown_time">%M</span><span class="single-countdown_text">Min</span></div><div class="single-countdown"><span class="single-countdown_time">%S</span><span class="single-countdown_text">Sec</span></div>'));
		});
	});
	/*---------------------------------
	 	MailChimp
    -----------------------------------*/
    $('#mc-form').ajaxChimp({
        language: 'en',
        callback: mailChimpResponse,
        // ADD YOUR MAILCHIMP URL BELOW HERE!
        url: 'http://devitems.us11.list-manage.com/subscribe/post?u=6bbb9b6f5827bd842d9640c82&amp;id=05d85f18ef'
    });
    function mailChimpResponse(resp) {
        if (resp.result === 'success') {
            $('.mailchimp-success').html('' + resp.msg).fadeIn(900);
            $('.mailchimp-error').fadeOut(400);
        } else if (resp.result === 'error') {
            $('.mailchimp-error').html('' + resp.msg).fadeIn(900);
        }
	}

	/*--------------------------
	06. Ajax Contact Form JS
	---------------------------*/
	const form = $('#contact-form'),
	formMessages = $('.form-message');

	 $(form).submit(function (e) {
		 e.preventDefault();
		 var formData = form.serialize();
		 $.ajax({
			 type: 'POST',
			 url: form.attr('action'),
			 data: formData
		 }).done(function (response) {
			 // Make sure that the formMessages div has the 'success' class.
			 $(formMessages).removeClass('alert alert-danger');
			 $(formMessages).addClass('alert alert-success fade show');

			 // Set the message text.
			 formMessages.html("<button type='button' class='close' data-dismiss='alert'>&times;</button>");
			 formMessages.append(response);

			 // Clear the form.
			 $('#contact-form input,#contact-form textarea').val('');
		 }).fail(function (data) {
			 // Make sure that the formMessages div has the 'error' class.
			 $(formMessages).removeClass('alert alert-success');
			 $(formMessages).addClass('alert alert-danger fade show');

			 // Set the message text.
			 if (data.responseText !== '') {
				 formMessages.html("<button type='button' class='close' data-dismiss='alert'>&times;</button>");
				 formMessages.append(data.responseText);
			 } else {
				 $(formMessages).text('Oops! An error occurred and your message could not be sent.');
			 }
		 });
	 });
	/*----------------------------------------*/
	/*  Slick Carousel
	----------------------------------------*/
	var $html = $('html');
	var $body = $('body');
	var $elementCarousel = $('.obrien-slider, .product-slider');
	// Check if element exists
	$.fn.elExists = function () {
		return this.length > 0;
	};

	/*For RTL*/
	if ($html.attr('dir') == 'rtl' || $body.attr('dir') == 'rtl') {
		$elementCarousel.attr('dir', 'rtl');
	}
	if ($elementCarousel.elExists()) {
		var slickInstances = [];

		/*For RTL*/
		if ($html.attr('dir') == 'rtl' || $body.attr('dir') == 'rtl') {
			$elementCarousel.attr('dir', 'rtl');
		}

		$elementCarousel.each(function (index, element) {
			var $this = $(this);

			// Carousel Options

			var $options = typeof $this.data('slick-options') !== 'undefined' ? $this.data('slick-options') : '';

			var $spaceBetween = $options.spaceBetween ? parseInt($options.spaceBetween, 10) : 0,
				$spaceBetween_xl = $options.spaceBetween_xl ? parseInt($options.spaceBetween_xl, 10) : 0,
				$rowSpace = $options.rowSpace ? parseInt($options.rowSpace, 10) : 0,
				$rows = $options.rows ? $options.rows : false,
				$vertical = $options.vertical ? $options.vertical : false,
				$focusOnSelect = $options.focusOnSelect ? $options.focusOnSelect : false,
				$pauseOnHover = $options.pauseOnHover ? $options.pauseOnHover : false,
				$pauseOnFocus = $options.pauseOnFocus ? $options.pauseOnFocus : false,
				$asNavFor = $options.asNavFor ? $options.asNavFor : '',
				$fade = $options.fade ? $options.fade : false,
				$autoplay = $options.autoplay ? $options.autoplay : false,
				$autoplaySpeed = $options.autoplaySpeed ? parseInt($options.autoplaySpeed, 10) : 5000,
				$swipe = $options.swipe ? $options.swipe : true,
				$swipeToSlide = $options.swipeToSlide ? $options.swipeToSlide : true,
				$touchMove = $options.touchMove ? $options.touchMove : false,
				$verticalSwiping = $options.verticalSwiping ? $options.verticalSwiping : true,
				$draggable = $options.draggable ? $options.draggable : true,
				$arrows = $options.arrows ? $options.arrows : false,
				$dots = $options.dots ? $options.dots : false,
				$adaptiveHeight = $options.adaptiveHeight ? $options.adaptiveHeight : true,
				$infinite = $options.infinite ? $options.infinite : false,
				$centerMode = $options.centerMode ? $options.centerMode : false,
				$centerPadding = $options.centerPadding ? $options.centerPadding : '',
				$variableWidth = $options.variableWidth ? $options.variableWidth : false,
				$speed = $options.speed ? parseInt($options.speed, 10) : 500,
				$appendArrows = $options.appendArrows ? $options.appendArrows : $this,
				$prevArrow =
				$arrows === true ?
				$options.prevArrow ?
				'<span class="' +
				$options.prevArrow.buttonClass +
				'"><i class="' +
				$options.prevArrow.iconClass +
				'"></i></span>' :
				'<button class="tty-slick-text-btn tty-slick-text-prev"><i class="ion-chevron-left"></i></span>' :
				'',
				$nextArrow =
				$arrows === true ?
				$options.nextArrow ?
				'<span class="' +
				$options.nextArrow.buttonClass +
				'"><i class="' +
				$options.nextArrow.iconClass +
				'"></i></span>' :
				'<button class="tty-slick-text-btn tty-slick-text-next"><i class="ion-chevron-right"></i></span>' :
				'',
				$rows = $options.rows ? parseInt($options.rows, 10) : 1,
				$rtl = $options.rtl || $html.attr('dir="rtl"') || $body.attr('dir="rtl"') ? true : false,
				$slidesToShow = $options.slidesToShow ? parseInt($options.slidesToShow, 10) : 1,
				$slidesToScroll = $options.slidesToScroll ? parseInt($options.slidesToScroll, 10) : 1;

			/*Responsive Variable, Array & Loops*/
			var $responsiveSetting =
				typeof $this.data('slick-responsive') !== 'undefined' ? $this.data('slick-responsive') : '',
				$responsiveSettingLength = $responsiveSetting.length,
				$responsiveArray = [];
			for (var i = 0; i < $responsiveSettingLength; i++) {
				$responsiveArray[i] = $responsiveSetting[i];
			}

			// Adding Class to instances
			$this.addClass('slick-carousel-' + index);
			$this.parent().find('.slick-dots').addClass('dots-' + index);
			$this.parent().find('.slick-btn').addClass('btn-' + index);

			if ($spaceBetween != 0) {
				$this.addClass('slick-gutter-' + $spaceBetween);
			}
			if ($spaceBetween_xl != 0) {
				$this.addClass('slick-gutter-xl-' + $spaceBetween_xl);
			}
			var $slideCount = null;
			$this.on('init', function (event, slick) {
				$this.find('.slick-active').first().addClass('first-active');
				$this.find('.slick-active').last().addClass('last-active');
				$slideCount = slick.slideCount;
				if ($slideCount <= $slidesToShow) {
					$this.children('.slick-dots').hide();
				}
				var $firstAnimatingElements = $('.slick-slide').find('[data-animation]');
				doAnimations($firstAnimatingElements);
			});

			$this.slick({
				slidesToShow: $slidesToShow,
				slidesToScroll: $slidesToScroll,
				asNavFor: $asNavFor,
				autoplay: $autoplay,
				autoplaySpeed: $autoplaySpeed,
				speed: $speed,
				infinite: $infinite,
				rows: $rows,
				arrows: $arrows,
				dots: $dots,
				adaptiveHeight: $adaptiveHeight,
				vertical: $vertical,
				focusOnSelect: $focusOnSelect,
				pauseOnHover: $pauseOnHover,
				pauseOnFocus: $pauseOnFocus,
				centerMode: $centerMode,
				centerPadding: $centerPadding,
				variableWidth: $variableWidth,
				swipe: $swipe,
				swipeToSlide: $swipeToSlide,
				touchMove: $touchMove,
				draggable: $draggable,
				fade: $fade,
				appendArrows: $appendArrows,
				prevArrow: $prevArrow,
				nextArrow: $nextArrow,
				rtl: $rtl,    
				customPaging : function(slider, i) {
					var thumb = $(slider.$slides[i]).data();
					var number = i + 1;
					if(number < 10){
						return '<button type="button" class="dot">'+'0'+number+'</button>';
					}
					return '<button type="button" class="dot">'+number+'</button>';
				},
				responsive: $responsiveArray
			});

			$this.on('beforeChange', function (e, slick, currentSlide, nextSlide) {
				$this.find('.slick-active').first().removeClass('first-active');
				$this.find('.slick-active').last().removeClass('last-active');
				var $animatingElements = $('.slick-slide[data-slick-index="' + nextSlide + '"]').find(
					'[data-animation]'
				);
				doAnimations($animatingElements);
			});

			function doAnimations(elements) {
				var animationEndEvents = 'webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend';
				elements.each(function () {
					var $el = $(this);
					var $animationDelay = $el.data('delay');
					var $animationDuration = $el.data('duration');
					var $animationType = 'animated ' + $el.data('animation');
					$el.css({
						'animation-delay': $animationDelay,
						'animation-duration': $animationDuration
					});
					$el.addClass($animationType).one(animationEndEvents, function () {
						$el.removeClass($animationType);
					});
				});
			}

			$this.on('afterChange', function (e, slick) {
				$this.find('.slick-active').first().addClass('first-active');
				$this.find('.slick-active').last().addClass('last-active');
			});

			// Updating the sliders in tab
			$('body').on('shown.bs.tab', 'a[data-toggle="tab"], a[data-toggle="pill"]', function (e) {
				$this.slick('setPosition');
			});
		});
		// Added mousewheel for specific slider
		$('.single-blog_slider, .mousewheel-slider').on('wheel', function(e) {
			e.preventDefault();
	
			if (e.originalEvent.deltaY < 0) {
				$(this).slick('slickNext');
			} else {
				$(this).slick('slickPrev');
			}
		});
	};
		
})(jQuery);

