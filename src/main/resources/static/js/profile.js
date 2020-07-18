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

    $('.save_upload').click(function(event) {
        $image_crop.croppie('result', {
            type: 'canvas',
            size: 'viewport'
        }).then(function(response) {
            $.ajax({
                url: "/loadImage/1",
                type: "POST",
                data: { "image": response },
                success: function(result) {
                    $('#uploadimageModal').modal('hide');
                    $('.image_holder').html(result.status);
                }
            });
        })
    });

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

$('.crop_image').click(function(event) {
    $image_crop.croppie('result', {
        type: 'canvas',
        size: 'viewport'
    }).then(function(response) {
        $.ajax({
            url: "upload.php",
            type: "POST",
            data: { "image": response },
            success: function(data) {
                $('#uploadimageModal').modal('hide');
                $('#uploaded_image').html(data);
            }
        });
    })
});

$("#fuck").click(function(e) {
    e.preventDefault();
    loadUserImage(1);
})


function loadUserImage(id) {
    let res = '';

    const requestURL = `/loadImage/${id}`;
    let fd = new FormData();
    let files = $('#file')[0].files[0];
    fd.append("file", files);

    uploadWithAjax(requestURL, fd, files);


}

function uploadWithAjax(requestURL, fd, files) {

    $.ajax({
        type: "POST",
        url: requestURL,
        data: fd,
        contentType: false,
        processData: false,
        timeout: 30000, // sets timeout to 3 seconds
        success: function(response) {
            $('.image_holder').html(('<b>  Order Id selected: <img src="' + response.status + '" alt="Smiley face "></b>'))
        },

        error: function(jqXHR, status, errorThrown) {}

    });
}



// if (imgPath !== '') {
//     $(".image_preview").attr('src', imgPath);
//     $(".image_preview").show();
// }

// function submitWithAjaxRequest(blob, form) {
//     let fileName = $('#file').val();
//     let formArr = $('#mainform').serializeArray();
//     let formData = new FormData()
//     formData.append('file', blob, fileName);
//     $.each(formArr, function(index, item) {
//         formData.append(item.name, item.value);
//     })

//     $.ajax({
//         url: $(form).attr('action'),
//         type: 'POST',
//         cache: false,
//         contentType: false,
//         processData: false,
//         data: formData,
//         success: function(data) {
//             if (data.status == 'success') {
//                 location.reload();
//             } else {
//                 alert("Failed to upload image")
//             }
//         },
//         error: function(jqXHR, status, errorThrown) {
//             alert("Failed to upload image");
//         }
//     })
// }