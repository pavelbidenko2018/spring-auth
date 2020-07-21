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

});

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
                if (data.status == 'success') {
                    $(form)[0].reset();
                }
            },

            error: function(jqXHR, status, errorThrown) {
                console.log(jqXHR.responseText);
            }

        })
    })

}