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
                    location.reload();
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