var wordJSONArray;

var containerDiv = $('#container')[0];

var wordElementPrefix = 'word-';

$('input#button-transcript').click(function () {
    resetResult();
    // disableForm();

    var formData = new FormData($('form')[0]);
    $.ajax({
        url: postUrl,  //Server script to process data
        type: 'POST',
//            xhr: function () {  // Custom XMLHttpRequest
//                var myXhr = $.ajaxSettings.xhr();
//                if (myXhr.upload) { // Check if upload property exists
//                    myXhr.upload.addEventListener('progress', progressHandlingFunction, false); // For handling the progress of the upload
//                }
//                return myXhr;
//            },
        //Ajax events
        beforeSend: disableForm,
        success: loadResult,
//            error: errorHandler,
        // Form data
        data: formData,
        //Options to tell jQuery not to process data or worry about content-type.
        cache: false,
        contentType: false,
        processData: false
    });
});


function loadResult(data) {
    enableForm();

    wordJSONArray = JSON.parse(data);
    for (var i = 0; i <= wordJSONArray.length - 1; i++) {
        var wordAlternativesJSON = wordJSONArray[i];

        var wordElement = document.createElement('a');
        wordElement.setAttribute('href', '#');
        wordElement.setAttribute('class', 'word-a');
        wordElement.setAttribute('id', wordElementPrefix + i);
        wordElement.innerText = wordAlternativesJSON.alternatives[0].word;

        wordElement.addEventListener('click', function () {
            showAlternatives(this);
        });

        containerDiv.appendChild(wordElement);
    }

    updateTextArea();
}

function showAlternatives(wordElement) {
    clearAlternativesDiv();

    var alternativeListDivElement = document.createElement('div');

    var top = wordElement.getBoundingClientRect().top;
    var left = wordElement.getBoundingClientRect().left;

    alternativeListDivElement.setAttribute('class', 'alternatives-div');
    alternativeListDivElement.setAttribute('style',
        'top: ' + top + 'px;' +
        'left: ' + left + 'px;' +
        'z-index: ' + 1);

    var idString = wordElement.getAttribute('id');
    var id = parseInt(idString.substring(wordElementPrefix.length, idString.length));
    var wordAlternativesJSON = wordJSONArray[id];

    for (var i = 0; i <= wordAlternativesJSON.alternatives.length - 1; i++) {
        var alternativeElement = document.createElement('a');
//            alternativeElement.setAttribute('id', wordElementPrefix + i);
        alternativeElement.setAttribute('href', '#');
        alternativeElement.setAttribute('class', 'alternative-a');
        alternativeElement.setAttribute('style',
            'display: block');
        alternativeElement.innerText = wordAlternativesJSON.alternatives[i].word;
        alternativeElement.addEventListener('click', function () {
            console.log('this.innerText: ' + this.innerText);
            wordElement.innerText = this.innerText;
            console.log('wordElement.innerText: ' + wordElement.innerText);
            clearAlternativesDiv();
            updateTextArea();
        });

        alternativeListDivElement.appendChild(alternativeElement);
    }

    document.body.appendChild(alternativeListDivElement);
}

window.addEventListener('click', function () {
    if (!event.target.matches('.word-a')) {
        clearAlternativesDiv();
    }
});

function clearAlternativesDiv() {
    var alternativesDivArray = document.getElementsByClassName('alternatives-div');
    while (alternativesDivArray.length > 0) {
        document.body.removeChild(alternativesDivArray[0]);
    }
}

function resetResult() {
    var containerDiv = document.getElementById('container');
    while (containerDiv.hasChildNodes()) {
        containerDiv.removeChild(containerDiv.lastChild);
    }
    document.getElementById('textarea-result').value = "";
}

function updateTextArea() {
    var text = "";
    var wordElementArray = document.getElementsByClassName('word-a');
    for (var i = 0; i <= wordElementArray.length - 1; i++) {
        var thisWordText = document.getElementById("word-" + i).innerText;

        var prevWordElement = document.getElementById("word-" + (i - 1));
        if (prevWordElement) {
            if (prevWordElement.innerText == ".") {
                thisWordText = thisWordText.capitalizeFirstLetter()
            }
        }

        var nextWordElement = document.getElementById("word-" + (i + 1));
        if (nextWordElement) {
            if ((nextWordElement.innerText != ",") && (nextWordElement.innerText != ".")) {
                thisWordText += " ";
            }
        }

        text += thisWordText;
    }

    document.getElementById('textarea-result').value = text;
}

String.prototype.capitalizeFirstLetter = function () {
    return this.charAt(0).toUpperCase() + this.slice(1);
};

$(document).on('change', ':file', function () {
    var input = $(this);
    var label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    $('#filename').val(label);
});

function disableForm() {
    $('#img-load').show();
    // $('#lang').prop('disabled', true);
    // $('#button-transcript').prop('disabled', true);
    $(':input').prop('disabled', true);
    $('#button-browse').prop('disabled', true);
}

function enableForm() {
    $('#img-load').hide();
    // $('#lang').prop('disabled', false);
    // $('#button-transcript').prop('disabled', false);
    $(':input').prop('disabled', false);
    $('#button-browse').prop('disabled', false);
}