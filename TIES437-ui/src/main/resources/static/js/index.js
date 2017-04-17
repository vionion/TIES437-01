var resultDiv = $('#result');

$(document).ready(function () {
    $.ajax({
        url: cityUrl,
        type: 'GET',
        //Ajax events
        success: loadCity,
        //Options to tell jQuery not to process data or worry about content-type.
        cache: false,
        contentType: false,
        processData: false
    });
});

function loadCity(data) {
    console.log(data);

    var cityJSONArray = JSON.parse(data);
    for (var i = 0; i <= cityJSONArray.length - 1; i++) {
        var cityJSON = cityJSONArray[i];
        var cityOptionElement = document.createElement('option');
        cityOptionElement.innerText = cityJSON.name;
        cityOptionElement.value = cityJSON.uri;

        if (i == 0) {
            cityOptionElement.setAttribute('selected', 'selected');
        }

        document.getElementById('city').appendChild(cityOptionElement);
    }
}

$('input#button-search').click(function () {
    var formData = new FormData($('form')[0]);
    $.ajax({
        url: bookingUrl,  //Server script to process data
        type: 'POST',
        //Ajax events
        beforeSend: disableForm,
        success: loadCottage,
        // Form data
        data: formData,
        //Options to tell jQuery not to process data or worry about content-type.
        cache: false,
        contentType: false,
        processData: false
    });
});

function loadCottage(data) {
    enableForm();
    resetResult();

    // console.log(data);
    var responseJSONArray = JSON.parse(data);

    var rowElement;

    for (var i = 0; i <= responseJSONArray.length - 1; i++) {
        var responseJSON = responseJSONArray[i];

        var possibleStartDateListUlElement = $('<ul></ul>');
        for (var j = 0; j <= responseJSON.possibleStartDateList.length - 1; j++) {
            possibleStartDateListUlElement.append(
                $('<li></li>', {
                    text: responseJSON.possibleStartDateList[j]
                })
            );
        }

        var cottageElement = $('<div></div>', {
            href: '#',
            'class': 'thumbnail cottage-a col-md-12'
        }).append(
            $('<div></div>', {
                'class': 'row',
            }).append(
                $('<div></div>', {
                    'class': 'col-md-6',
                }).append(
                    $('<img/>', {
                        'src': responseJSON.imageURL
                    })
                )
            ).append(
                $('<div></div>', {
                    'class': 'col-md-6',
                }).append(
                    $('<div></div>', {
                        'class': 'caption',
                    }).append(
                        $('<div></div>', {
                            'class': 'container',
                        }).append(
                            $('<div></div>', {
                                'class': 'row',
                            }).append(
                                $('<div></div>', {
                                    'class': 'col-md-7',
                                }).append(
                                    $('<strong></strong>', {
                                        text: 'Address'
                                    })
                                )
                            ).append(
                                $('<div></div>', {
                                    'class': 'col-md-5',
                                    text: responseJSON.address
                                })
                            )
                        ).append(
                            $('<div></div>', {
                                'class': 'row',
                            }).append(
                                $('<div></div>', {
                                    'class': 'col-md-7',
                                }).append(
                                    $('<strong></strong>', {
                                        text: 'Number of Bedroom'
                                    })
                                )
                            ).append(
                                $('<div></div>', {
                                    'class': 'col-md-5',
                                    text: responseJSON.bedroomCount
                                })
                            )
                        ).append(
                            $('<div></div>', {
                                'class': 'row',
                            }).append(
                                $('<div></div>', {
                                    'class': 'col-md-7',
                                }).append(
                                    $('<strong></strong>', {
                                        text: 'Number of Places'
                                    })
                                )
                            ).append(
                                $('<div></div>', {
                                    'class': 'col-md-5',
                                    text: responseJSON.totalPlaceCount
                                })
                            )
                        ).append(
                            $('<div></div>', {
                                'class': 'row',
                            }).append(
                                $('<div></div>', {
                                    'class': 'col-md-7',
                                }).append(
                                    $('<strong></strong>', {
                                        text: 'Max Distance to a Lake (m)'
                                    })
                                )
                            ).append(
                                $('<div></div>', {
                                    'class': 'col-md-5',
                                    text: responseJSON.distanceToLake
                                })
                            )
                        ).append(
                            $('<div></div>', {
                                'class': 'row',
                            }).append(
                                $('<div></div>', {
                                    'class': 'col-md-7',
                                }).append(
                                    $('<strong></strong>', {
                                        text: 'City'
                                    })
                                )
                            ).append(
                                $('<div></div>', {
                                    'class': 'col-md-5',
                                    text: responseJSON.city
                                })
                            )
                        ).append(
                            $('<div></div>', {
                                'class': 'row',
                            }).append(
                                $('<div></div>', {
                                    'class': 'col-md-7',
                                }).append(
                                    $('<strong></strong>', {
                                        text: 'Max Distance from City (km)'
                                    })
                                )
                            ).append(
                                $('<div></div>', {
                                    'class': 'col-md-5',
                                    text: responseJSON.distance
                                })
                            )
                        ).append(
                            $('<div></div>', {
                                'class': 'row',
                            }).append(
                                $('<div></div>', {
                                    'class': 'col-md-7',
                                }).append(
                                    $('<strong></strong>', {
                                        text: 'Possible Booking Start Date'
                                    })
                                )
                            ).append(
                                $('<div></div>', {
                                    'class': 'col-md-5',
                                }).append(possibleStartDateListUlElement)
                            )
                        )
                    )
                )
            )
        );

        console.log(cottageElement[0]);

        if (i % 2 == 0) {
            rowElement = $('<div></div>', {
                'class': 'row'
            });
        }

        resultDiv.append(
            rowElement.append(cottageElement)
        );
    }
}

function resetResult() {
    resultDiv.empty();
}

function disableForm() {
    $('#img-load').show();
    $(':input').prop('disabled', true);
    $('#button-browse').prop('disabled', true);
}

function enableForm() {
    $('#img-load').hide();
    $(':input').prop('disabled', false);
    $('#button-browse').prop('disabled', false);
}

$(function () {
    // Bootstrap DateTimePicker v4
    $('#startDateString').datepicker({
        dateFormat: 'dd-mm-yy'
    });
});