$(document).ready(function() {

    $image_crop = $('#image_demo').croppie({
        enableExif: true,
        viewport: {
            width: 180,
            height: 180,
            type: 'circle' // square
        },
        boundary: {
            width: 300,
            height: 300
        }
    });

    $('#file').on('change', function() {
        var reader = new FileReader();
        reader.onload = function(event) {
            $image_crop.croppie('bind', {
                url: event.target.result
            }).then(function() {
                console.log('jQuery bind complete');
            });
        }
        reader.readAsDataURL(this.files[0]);
        $('#uploadimageModal').modal('show');
    });

    $('.save_upload').click(function(event) {
        let id = $(this).val();
        $image_crop.croppie('result', {
            type: 'canvas',
            size: 'viewport'
        }).then(function(response) {

            $("#userpic").attr('src', response);

            $.ajax({
                url: "/loadImage/" + id,
                type: "POST",
                data: {
                    "image": response
                },
                success: function(result) {
                    $('#uploadimageModal').modal('hide');

                }
            });
        })
    });

    $(".editable").click(
        function() {

            $(this).siblings("input").attr('readonly', null);

        }
    )

    loadUserData();

    populateCountries();

    stepBar();

});

function populateCountries() {

    let dropdown = $("#countryID");
    const url = "https://restcountries.eu/rest/v2/all";

    // dropdown.empty();
    //  dropdown.append('<option selected="true" disabled>Choose State/Province</option>');

    dropdown.prop('selectedIndex', 0);

    $.getJSON(url, gotData);
}

function gotData(data) {

    $(data).each(function(index, item) {
        $("#countryID").append($("<option />").val(item.name).text(item.name));
    });
}

function loadUserData() {

    $("#mainProfileForm").submit(function(event) {
        debugger;
        event.preventDefault();
        const form = $(this);
        $.ajax({
            type: "POST",
            url: $(form).attr('action'),
            data: $(form).serialize(),
            dataType: "json",

            success: function(data) {
                if (data.status !== null) {
                    showInfoBox(data.status);
                    $(form)[0].reset();
                }
            },

            error: function(jqXHR, status, errorThrown) {
                console.log(jqXHR.responseText);
            }

        })
    })

}

function showInfoBox(status) {
    let splash = $("#mainFormSplashID");

    splash.empty();
    splash.append((status === "edit") ? "Data updated" : "Data send OK")
        .css("display", 'block')
        .delay(1000)
        .fadeIn(500, function() {
            $(this).fadeOut(500);
        });
}

function stepBar() {
    debugger;
    const previousBtn = $("#previous");
    const nextBtn = $("#next");
    const finishBtn = $("#finish");
    const bullets = [...$(".bullet")];
    const content = $("#content");

    const MAX_STEPS = 3;
    let currentStep = 1;

    $(nextBtn).click(() => {
        $(bullets[currentStep - 1]).addClass('completed');
        currentStep++;
        $(previousBtn).prop('disabled', false);
        if (currentStep === MAX_STEPS) {
            $(nextBtn).prop('disabled', true);
            $(finishBtn).prop('disabled', false);
        }
        $(content).text(`Step ${currentStep}`);
    });
    $(previousBtn).click(() => {
        $(bullets[currentStep - 2]).removeClass('completed');
        currentStep--;
        $(finishBtn).prop('disabled', false);
        $(nextBtn).prop('disabled', false);
        if (currentStep === 1) {
            $(previousBtn).prop('disabled', true);
        }
    })

    $(finishBtn).click(() => {
        location.reload();
    })

}