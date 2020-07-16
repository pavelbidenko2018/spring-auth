$(document).ready(function() {
    loadUserData();
    loadUserImage();
});

function loadUserData() {
    $("#mainProfileForm").submit(function(e) {
        debugger;
        e.preventDefault();
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

    $('#imageFormID').on('submit', function(e) {
        debugger;
        e.preventDefault();

        const form = $(this);

        let fd = new FormData();
        let files = $('#file')[0].files[0];

        fd.append("file", files);

        $.ajax({
            type: "POST",
            url: $(form).attr('action'),
            data: fd,
            contentType: false,
            processData: false,
            success: function(response) {
                if (response.status !== 'error') {
                    $('.image_preview').attr('src', response.status);
                    $('.image_preview').show();
                }
            },

            error: function(jqXHR, status, errorThrown) {
                console.log(jqXHR.responseText);
            }

        })
    })
}

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