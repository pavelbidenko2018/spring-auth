$(document).ready(function() {
    loadUserData();
    loadUserImage();
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

function loadUserImage() {
    let res = '';

    $('#imageFormID').submit(function(e) {
        e.preventDefault();
        const requestURL = $(this).attr('action');
        let fd = new FormData();
        let files = $('#file')[0].files[0];
        fd.append("file", files);


        uploadWithAjax(requestURL, fd, files)
            .then((response) => {

                $('.image_preview').attr('src', res);
                $('.image_preview').show();

                $('.image_preview').attr('src', response.status);
                $('.image_preview').show();
            })
            .catch((error) => {
                console.log(error)
            })

        setTimeout(function() {}, 1000);

    });

}

function uploadWithAjax(requestURL, fd, files) {

    return new Promise((resolve, reject) => {
        $.ajax({
            type: "POST",
            url: requestURL,
            data: fd,
            contentType: false,
            processData: false,
            success: function(response) {
                res = response;
                resolve(response);

            },

            error: function(jqXHR, status, errorThrown) {
                reject(jqXHR, status, errorThrown)
            }

        });
    })

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