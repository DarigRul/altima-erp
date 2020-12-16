
		$(document).ready(function () {

			//--------------COLORES PARTICLES -------------
			
			$("#blackBackgroundChange").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
				$.cookie("blackColor", "blackBackground", { expires: 10000 });
                $.cookie("blackJS", "/dist/js/login-particles.js", { expires: 10000 });
                window.location.replace("http://localhost:8070/login");
			});

			$("#blueBackgroundChange").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
				$.cookie("blueColor", "blueBackground", { expires: 10000 });
				$.cookie("blueJS", "/dist/js/login-particles.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			$("#whiteBackgroundChange").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
				$.cookie("whiteColor", "whiteBackground", { expires: 10000 });
				$.cookie("whiteJS", "/dist/js/login-black.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			$("#redBackgroundChange").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("redColor", "redBackground", { expires: 10000 });
                $.cookie("redJS", "/dist/js/login-particles.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			$("#greenBackgroundChange").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("greenColor", "greenBackground", { expires: 10000 });
                $.cookie("greenJS", "/dist/js/login-particles.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			$("#yellowBackgroundChange").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("yellowColor", "yellowBackground", { expires: 10000 });
                $.cookie("yellowJS", "/dist/js/login-black.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});
			
			//--------------COLORES NYAN CAT -------------

            
			$("#blueCat").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("catBlueCSS", "particlesCatsBlue", { expires: 10000 });
                $.cookie("catBlueJS", "/dist/js/login-cat.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});
			$("#redCat").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("catRedCSS", "particlesCatsRed", { expires: 10000 });
                $.cookie("catRedJS", "/dist/js/login-cat.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});
			$("#greenCat").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("catGreenCSS", "particlesCatsGreen", { expires: 10000 });
                $.cookie("catGreenJS", "/dist/js/login-cat.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});
			$("#yellowCat").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("catYellowCSS", "particlesCatsYellow", { expires: 10000 });
                $.cookie("catYellowJS", "/dist/js/login-cat-dark.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			$("#blackCat").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("catBlackCSS", "particlesCatsBlack", { expires: 10000 });
                $.cookie("catBlackJS", "/dist/js/login-cat.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			$("#whiteCat").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("catWhiteCSS", "particlesCatsWhite", { expires: 10000 });
                $.cookie("catWhiteJS", "/dist/js/login-cat-dark.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			//--------------- BUBBLE COLORES -------------

			$("#blueBubble").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("bubbleBlueCSS", "particlesBubbles", { expires: 10000 });
                $.cookie("bubbleBlueClass", "blueBackground", { expires: 10000 });
                $.cookie("bubbleBlueJS", "/dist/js/login-bubble.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			$("#redBubble").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("bubbleRedCSS", "particlesBubbles", { expires: 10000 });
                $.cookie("bubbleRedClass", "redBackground", { expires: 10000 });
                $.cookie("bubbleRedJS", "/dist/js/login-bubble.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			$("#greenBubble").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("bubbleGreenCSS", "particlesBubbles", { expires: 10000 });
                $.cookie("bubbleGreenClass", "greenBackground", { expires: 10000 });
                $.cookie("bubbleGreenJS", "/dist/js/login-bubble.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			$("#yellowBubble").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("bubbleYellowCSS", "particlesBubbles", { expires: 10000 });
                $.cookie("bubbleYellowClass", "yellowBackground", { expires: 10000 });
                $.cookie("bubbleYellowJS", "/dist/js/login-bubble.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			$("#blackBubble").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("bubbleBlackCSS", "particlesBubbles", { expires: 10000 });
                $.cookie("bubbleBlackClass", "blackBackground", { expires: 10000 });
                $.cookie("bubbleBlackJS", "/dist/js/login-bubble-white.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			$("#whiteBubble").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("bubbleWhiteCSS", "particlesBubbles", { expires: 10000 });
                $.cookie("bubbleWhiteClass", "whiteBackground", { expires: 10000 });
                $.cookie("bubbleWhiteJS", "/dist/js/login-bubble-white.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});

			///----------------SNOW COLORS -----------------
			$("#snowBackgroundChange").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
				}
                $.cookie("snowCSS", "particlesSnows", { expires: 10000 });
                $.cookie("snowJS", "/dist/js/login-snow.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});
			
			$(document).on("click", "#imageBackgroundChange", function() {
			var cookies = $.cookie();
			 	for (var cookie in cookies) {
			 		$.removeCookie(cookie);
			 	}
                 $.cookie("imageCSS", "particlesIMG", { expires: 10000 });
			 	var imagen = $("#imgSrc").val();
                 $.cookie("imageSRC", imagen, { expires: 10000 });
			 	window.location.replace("http://localhost:8070/login");
			 });

			// ----------------------- PARTICLES ----------------------

            if ($.cookie("blackColor")) {
			$(".changeColor").removeClass("blackBackground").addClass($.cookie("blackColor"));
            $('<script>').attr({
                src: $.cookie("blackJS")}).appendTo('body')
            }
			if ($.cookie("whiteColor")) {
            $(".changeColor").removeClass("blackBackground").addClass($.cookie("whiteColor"));
            $('<script>').attr({
                src: $.cookie("whiteJS")}).appendTo('body')
			}
			if ($.cookie("blueColor")) {
            $(".changeColor").removeClass("blackBackground").addClass($.cookie("blueColor"));
            $('<script>').attr({
                src: $.cookie("blueJS")}).appendTo('body')
			}
			if ($.cookie("redColor")) {
            $(".changeColor").removeClass("blackBackground").addClass($.cookie("redColor"));
            $('<script>').attr({
                src: $.cookie("redJS")}).appendTo('body')
			}
			if ($.cookie("greenColor")) {
            $(".changeColor").removeClass("blackBackground").addClass($.cookie("greenColor"));
            $('<script>').attr({
                src: $.cookie("greenJS")}).appendTo('body')
			}
			if ($.cookie("yellowColor")) {
            $(".changeColor").removeClass("blackBackground").addClass($.cookie("yellowColor"));
            $('<script>').attr({
                src: $.cookie("yellowJS")}).appendTo('body')
			}

			//----------------------------CAT-----------------------

            if ($.cookie("catBlueCSS")) {
                $(".changeColor").removeClass("blackBackground particlesCircles").addClass($.cookie("catBlueCSS"));
                $('<script>').attr({
                    src: $.cookie("catBlueJS")}).appendTo('body')
			}
			if ($.cookie("catRedCSS")) {
                $(".changeColor").removeClass("blackBackground particlesCircles").addClass($.cookie("catRedCSS"));
                $('<script>').attr({
                    src: $.cookie("catRedJS")}).appendTo('body')
			}
			if ($.cookie("catGreenCSS")) {
                $(".changeColor").removeClass("blackBackground particlesCircles").addClass($.cookie("catGreenCSS"));
                $('<script>').attr({
                    src: $.cookie("catGreenJS")}).appendTo('body')
			}
			if ($.cookie("catYellowCSS")) {
                $(".changeColor").removeClass("blackBackground particlesCircles").addClass($.cookie("catYellowCSS"));
                $('<script>').attr({
                    src: $.cookie("catYellowJS")}).appendTo('body')
			}
			if ($.cookie("catBlackCSS")) {
                $(".changeColor").removeClass("blackBackground particlesCircles").addClass($.cookie("catBlackCSS"));
                $('<script>').attr({
                    src: $.cookie("catBlackJS")}).appendTo('body')
			}
			if ($.cookie("catWhiteCSS")) {
                $(".changeColor").removeClass("blackBackground particlesCircles").addClass($.cookie("catWhiteCSS"));
                $('<script>').attr({
                    src: $.cookie("catWhiteJS")}).appendTo('body')
			}

			//------------------------- BUBBLE ---------------

			if ($.cookie("bubbleBlueCSS")) {
                $(".changeColor").removeClass("blackBackground particlesCircles").addClass($.cookie("bubbleBlueCSS")+' '+ $.cookie("bubbleBlueClass"));
                $('<script>').attr({
                    src: $.cookie("bubbleBlueJS")}).appendTo('body')
			}
			if ($.cookie("bubbleRedCSS")) {
                $(".changeColor").removeClass("blackBackground particlesCircles").addClass($.cookie("bubbleRedCSS")+' '+ $.cookie("bubbleRedClass"));
                $('<script>').attr({
                    src: $.cookie("bubbleRedJS")}).appendTo('body')
			}
			if ($.cookie("bubbleGreenCSS")) {
                $(".changeColor").removeClass("blackBackground particlesCircles").addClass($.cookie("bubbleGreenCSS")+' '+ $.cookie("bubbleGreenClass"));
                $('<script>').attr({
                    src: $.cookie("bubbleGreenJS")}).appendTo('body')
			}
			if ($.cookie("bubbleYellowCSS")) {
                $(".changeColor").removeClass("blackBackground particlesCircles").addClass($.cookie("bubbleYellowCSS")+' '+ $.cookie("bubbleYellowClass"));
                $('<script>').attr({
                    src: $.cookie("bubbleYellowJS")}).appendTo('body')
			}
			if ($.cookie("bubbleBlackCSS")) {
                $(".changeColor").removeClass("blackBackground particlesCircles").addClass($.cookie("bubbleBlackCSS")+' '+ $.cookie("bubbleBlackClass"));
                $('<script>').attr({
                    src: $.cookie("bubbleBlackJS")}).appendTo('body')
			}
			if ($.cookie("bubbleWhiteCSS")) {
                $(".changeColor").removeClass("WhiteBackground particlesCircles").addClass($.cookie("bubbleWhiteCSS")+' '+ $.cookie("bubbleWhiteClass"));
                $('<script>').attr({
                    src: $.cookie("bubbleWhiteJS")}).appendTo('body')
			}
			//------------------------- SNOW ---------------

			if ($.cookie("snowCSS")) {
                $(".changeColor").removeClass("particlesCircles blackBackground").addClass($.cookie("snowCSS"));
                $('<script>').attr({
                    src: $.cookie("snowJS")}).appendTo('body')
			}
			
			//-------------------------- IMAGEN ---------
			if ($.cookie("imageSRC")) {
                $(".changeColor").removeClass("particlesCircles blackBackground").addClass($.cookie("imageCSS"));
				$('.particlesIMG').css("background-image", "url("+$.cookie("imageSRC") +")");
            }
		});