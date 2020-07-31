const auth__window = document.getElementsByClassName("auth__window")[0];
const add__window = document.getElementsByClassName("add__window")[0];
const authErrorForm = document.getElementById('authErrorForm');
const form = document.getElementsByClassName("form__content")[0];

if (window.location.hash.substring(1) == 'login_error') {
    console.log(window.location.hash);
    auth__window.style.display = 'block';
    authErrorForm.style.display = 'block';
}

window.addEventListener('hashchange', function() {
    if (window.location.hash == "#login_error") {
        auth__window.style.display = 'block';
    }

}, false);

window.onclick = function(event) {

    if (event.target == auth__window || event.target == add__window) {
        auth__window.style.display = 'none';
        add__window.style.display = 'none';
    }
}

function hideModalWindow() {
    const modals = document.getElementsByClassName('modals');
    Array.from(modals).forEach(modal => {
        modal.style.display = 'none';
    });
}

function showModal(action) {
    document.getElementsByClassName("auth__window")[0].style.display = action;
}


$(function() {
    $('.upd').click(function() {
        let form = $(this).closest('form');

        const artId = form.serializeArray()[0].value;

        $.ajax({
            url: '/updateArticle',
            type: "POST",
            data: artId,
            dataType: "text",
            contentType: "application/json; charset=utf-8",
            success: function(response) {
                console.log(typeof response);

            }
        });

    });
});

function addArticleHandler() {

    $(document).ready(function() {
        console.log("--showing form--");
        $(".modals").show();
    })
}

function hideModalWindow() {
    $(".modals").hide();
}

$(document).ready(
    function() {

        const $menu = $('.hidden__menu ul');

        $(document).mouseup(e => {
            if (!$menu.is(e.target) &&
                $menu.has(e.target).length === 0) {
                $menu.hide();
            }
        });

        $('.burger').on('click', () => {
            $menu.show();
        });

    }
)

$("#emailForm").submit(function(event) {
    event.preventDefault();

    const form = $(this);

    $.ajax({
            method: 'POST',
            url: form.attr('action'),
            data: form.serialize(),
            success: function(response) {
                form.trigger("reset");
                showEmailInfoBox(response);
            },
            error: function(jqXHR, status, errorThrown) {
                console.log(jqXHR.responseText);
            }

        }

    )
})

function showEmailInfoBox(status) {
    let splash = $(id);

    splash.empty();
    splash.append('Email envoyée OK');
    splash.css("display", 'block')
        .delay(1000)
        .fadeIn(500, function() {
            $(this).fadeOut(500);
        });

}