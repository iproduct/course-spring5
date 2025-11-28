M.AutoInit();
$(document).ready(function() {
    $(".locales").change(function () {
        var selectedOption = $('.locales').val();
        if (selectedOption != ''){
            window.location.replace(window.location.pathname + '?lang=' + selectedOption);
        }
    });
});

