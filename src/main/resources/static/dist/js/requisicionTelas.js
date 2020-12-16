var telasFaltantes=[];

$('#selectAll').click(function (e) {
    if ($(this).hasClass('checkedAll')) {
        $('.messageCheckbox').prop('checked', false);
        $(this).removeClass('checkedAll');
        $(".messageCheckbox").removeClass('checkedThis');
        var inputElements = document.getElementsByClassName('messageCheckbox');
        for (var i = 0; i<inputElements.length; ++i) {
            if (!inputElements[i].checked) {
                var removeIndex = telasFaltantes.indexOf(+inputElements[i].value)
                telasFaltantes.splice(removeIndex, 1);
            }
        }
    } else {
        $('.messageCheckbox').prop('checked', true);
        $(this).addClass('checkedAll');
        $(".messageCheckbox").addClass('checkedThis');
        var inputElements = document.getElementsByClassName('messageCheckbox');
        for (var i = 0; i<inputElements.length; ++i) {
            if (inputElements[i].checked) {
                telasFaltantes.push(+inputElements[i].value);
            }
            telasFaltantes = [...new Set(telasFaltantes)];
        }
    }
    console.log(telasFaltantes);
});
$(".messageCheckbox").change(function (e) { 
    e.preventDefault();
    if ($(this).hasClass('checkedThis')) {
        $(this).removeClass('checkedThis');
        var removeIndex = telasFaltantes.indexOf(+$(this).val())
        telasFaltantes.splice(removeIndex, 1);
    } else {
        $(this).addClass('checkedThis');
        telasFaltantes.push(+$(this).val());
    }
    console.log(+$(this).val());
    console.log(telasFaltantes);
});
