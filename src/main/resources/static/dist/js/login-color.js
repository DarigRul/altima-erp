
		$(document).ready(function () {
			$("#blackBackgroundChange").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
                }
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
            
            $("#catBackgroundChange").click(function () {
				var cookies = $.cookie();
				for (var cookie in cookies) {
					$.removeCookie(cookie);
                }
                $.cookie("catCSS", "particlesCats", { expires: 10000 });
                $.cookie("catJS", "/dist/js/login-cat.js", { expires: 10000 });
				window.location.replace("http://localhost:8070/login");
			});
            if ($.cookie("blackJS")) {
                $('<script>').attr({
                    src: $.cookie("blackJS")}).appendTo('body')
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
            if ($.cookie("catCSS")) {
                $(".changeColor").removeClass("particlesCircles blackBackground").addClass($.cookie("catCSS"));
                $('<script>').attr({
                    src: $.cookie("catJS")}).appendTo('body')
                }
		});