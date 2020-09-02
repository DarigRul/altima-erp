$(document).ready(function () {
  $("#colorDefaultAltima").click(function () {
    var cookies = $.cookie();
    for (var cookie in cookies) {
      $.removeCookie(cookie);
    }
    location.reload();
  });
  $("#colorRedAltima").click(function () {
    var cookies = $.cookie();
    for (var cookie in cookies) {
      $.removeCookie(cookie);
    }
    $('#userCircle').attr('src', '/dist/img/userCircleRed.png');
    $.cookie("changeColorRed", "bg-danger", { expires: 10000 });
    $.cookie("changeColor1", "active-danger", { expires: 10000 });
    $.cookie("changeColor2", "text-danger", { expires: 10000 });
    $.cookie("changeColor3", "btn-danger", { expires: 10000 });
    $.cookie("changeCalendarRed1", "fc-button-danger", { expires: 10000 });
    $.cookie("changeUserRed", "userCircleRed", { expires: 10000 });
    location.reload();
  });
  $("#colorGreenAltima").click(function () {
    var cookies = $.cookie();
    for (var cookie in cookies) {
      $.removeCookie(cookie);
    }
    $('#userCircle').attr('src', '/dist/img/userCircleGreen.png');
    $.cookie("changeColorGreen", "bg-success", { expires: 10000 });
    $.cookie("changeColorGreen1", "active-success", { expires: 10000 });
    $.cookie("changeColorGreen2", "text-success", { expires: 10000 });
    $.cookie("changeColorGreen3", "btn-success", { expires: 10000 });
    $.cookie("changeCalendarGreen1", "fc-button-success", { expires: 10000 });
    $.cookie("changeUserGreen", "userCircleGreen", { expires: 10000 });
    location.reload();
  });
  $("#colorGrayAltima").click(function () {
    var cookies = $.cookie();
    for (var cookie in cookies) {
      $.removeCookie(cookie);
    }
    $('#userCircle').attr('src', '/dist/img/userCircleGray.png');
    $.cookie("changeColorGray", "bg-secondary", { expires: 10000 });
    $.cookie("changeColorGray1", "active-secondary", { expires: 10000 });
    $.cookie("changeColorGray2", "text-secondary", { expires: 10000 });
    $.cookie("changeColorGray3", "btn-secondary", { expires: 10000 });
    $.cookie("changeCalendarGray1", "fc-button-secondary", { expires: 10000 });
    $.cookie("changeUserGray", "userCircleGray", { expires: 10000 });
    location.reload();
  });
  $("#colorYellowAltima").click(function () {
    var cookies = $.cookie();
    for (var cookie in cookies) {
      $.removeCookie(cookie);
    }
    $('#userCircle').attr('src', '/dist/img/userCircleYellow.png');
    $.cookie("changeColorYellow", "bg-warning", { expires: 10000 });
    $.cookie("changeColorYellow1", "active-warning", { expires: 10000 });
    $.cookie("changeColorYellow2", "text-warning", { expires: 10000 });
    $.cookie("changeColorYellow3", "btn-warning", { expires: 10000 });
    $.cookie("changeCalendarYellow1", "fc-button-warning", { expires: 10000 });
    $.cookie("changeUserYellow", "userCircleYellow", { expires: 10000 });
    location.reload();
  });
  if ($.cookie("changeColorRed")) {
    $(".card-header").removeClass("bg-altima").addClass($.cookie("changeColorRed"));
    $(".nav-link.active-altima").removeClass("active-altima").addClass($.cookie("changeColor1"));
    $("i.text-altima").removeClass("text-altima").addClass($.cookie("changeColor2"));
    $(".breadcrumb-item.altima").removeClass("altima").addClass($.cookie("changeColor2"));
    $("a.altima").removeClass("altima").addClass($.cookie("changeColor2"));
    $("h1 > a.btn-altima").removeClass("btn-altima").addClass($.cookie("changeColor3"));
    $("h1 > a.btn-danger > span.text-altima").removeClass("text-altima").addClass($.cookie("changeColor2"));
    $(".modal-header").removeClass("bg-altima").addClass($.cookie("changeColorRed"));
    $(".fc-button").removeClass("fc-button-primary").addClass($.cookie("changeCalendarRed1"));
    $('#userCircle').attr('src', '/dist/img/' + $.cookie('changeUserRed') + '.png');
  }
  if ($.cookie("changeColorGreen")) {
    $(".card-header").removeClass("bg-altima").addClass($.cookie("changeColorGreen"));
    $(".nav-link.active-altima").removeClass("active-altima").addClass($.cookie("changeColorGreen1"));
    $("i.text-altima").removeClass("text-altima").addClass($.cookie("changeColorGreen2"));
    $(".breadcrumb-item.altima").removeClass("altima").addClass($.cookie("changeColorGreen2"));
    $("a.altima").removeClass("altima").addClass($.cookie("changeColorGreen2"));
    $("h1 > a.btn-altima").removeClass("btn-altima").addClass($.cookie("changeColorGreen3"));
    $("h1 > a.btn-success > span.text-altima").removeClass("text-altima").addClass($.cookie("changeColorGreen2"));
    $(".modal-header").removeClass("bg-altima").addClass($.cookie("changeColorGreen"));
    $(".fc-button").removeClass("fc-button-primary").addClass($.cookie("changeCalendarGreen1"));
    $('#userCircle').attr('src', '/dist/img/' + $.cookie('changeUserGreen') + '.png');
  }
  if ($.cookie("changeColorYellow")) {
    $(".card-header").removeClass("bg-altima").addClass($.cookie("changeColorYellow"));
    $(".nav-link.active-altima").removeClass("active-altima").addClass($.cookie("changeColorYellow1"));
    $("i.text-altima").removeClass("text-altima").addClass($.cookie("changeColorYellow2"));
    $(".breadcrumb-item.altima").removeClass("altima").addClass($.cookie("changeColorYellow2"));
    $("a.altima").removeClass("altima").addClass($.cookie("changeColorYellow2"));
    $("h1 > a.btn-altima").removeClass("btn-altima").addClass($.cookie("changeColorYellow3"));
    $("h1 > a.btn-warning > span.text-altima").removeClass("text-altima").addClass($.cookie("changeColorYellow2"));
    $(".modal-header").removeClass("bg-altima").addClass($.cookie("changeColorYellow"));
    $(".fc-button").removeClass("fc-button-primary").addClass($.cookie("changeCalendarYellow1"));
    $('#userCircle').attr('src', '/dist/img/' + $.cookie('changeUserYellow') + '.png');
  }
  if ($.cookie("changeColorGray")) {
    $(".card-header").removeClass("bg-altima").addClass($.cookie("changeColorGray"));
    $(".nav-link.active-altima").removeClass("active-altima").addClass($.cookie("changeColorGray1"));
    $("i.text-altima").removeClass("text-altima").addClass($.cookie("changeColorGray2"));
    $(".breadcrumb-item.altima").removeClass("altima").addClass($.cookie("changeColorGray2"));
    $("a.altima").removeClass("altima").addClass($.cookie("changeColorGray2"));
    $("h1 > a.btn-altima").removeClass("btn-altima btn-danger").addClass($.cookie("changeColorGray3"));
    $("h1 > a.btn-secondary > span.text-altima").removeClass("text-altima").addClass($.cookie("changeColorGray2"));
    $(".modal-header").removeClass("bg-altima").addClass($.cookie("changeColorGray"));
    $(".fc-button").removeClass("fc-button-primary").addClass($.cookie("changeCalendarGray1"));
    $('#userCircle').attr('src', '/dist/img/' + $.cookie('changeUserGray') + '.png');
  }
});
