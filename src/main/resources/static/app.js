var buttonimg = document.getElementById('btn1');
buttonimg.addEventListener('click', function () {
    $('#coraline').toggle('slow');
});


var buttonNumber = document.getElementById('buttonNumber');
buttonNumber.addEventListener('click', function () {
    $("#h").empty();
    var number = document.getElementById('inputNumber');
    $("#h").append("The squared is: "+ (parseInt(number.value,10))*(parseInt(number.value,10)));
});



