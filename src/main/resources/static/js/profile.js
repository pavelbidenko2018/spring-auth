$(document).ready(function() {

    $image_crop = $('#image_demo').croppie({
        enableExif: true,
        viewport: {
            width: 180,
            height: 180,
            type: 'circle' //square
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

    loadUserData();
    loadImage();

});

function loadImage() {
    $('.save_upload').click(function(event) {
        let val = $(this).val();
        $image_crop.croppie('result', {
            type: 'canvas',
            size: 'viewport'
        }).then(function(response) {
            $.ajax({
                url: "loadImage/" + val,
                type: "POST",
                cache: false,
                data: { "image": response },
                success: function(result) {
                    $('#uploadimageModal').modal('hide');
                    $('.image_holder').html(result.status);
                }
            }).done(function(result) {
                location.reload();

            })

        })
    })
}

function setInFrame(result) {

    $('.image_holder').html(result.status);

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

// $('.save_upload').click(function(event) {
//     $image_crop.croppie('result', {
//         type: 'canvas',
//         size: 'viewport'
//     }).then(function(response) {
//         $.ajax({
//             url: "/loadImage/1",
//             type: "POST",
//             data: { "image": response },
//             success: function(result) {
//                 $('#uploadimageModal').modal('hide');
//                 $('.image_holder').html(result.status);
//                 alert('Picture is uploaded...');
//             }
//         });
//     })
// });