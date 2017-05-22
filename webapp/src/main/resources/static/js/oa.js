function showTip(data) {
    $('.tip').text(data);
    $('.tip').css("display","block");
    setTimeout("$('.tip').css('display','none');",2000);
}