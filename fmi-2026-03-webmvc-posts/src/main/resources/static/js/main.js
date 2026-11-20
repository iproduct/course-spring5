$(document).ready(function() {
    $("#locales").change(function () {
        var selectedOption = $('#locales').val();
        if (selectedOption != ''){
            window.location.replace(window.location.pathname + '?lang=' + selectedOption);
        }
    });

    // Custom file input init
    bsCustomFileInput.init();

    // Picture modal
    $('#exampleModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        var url = button.data('image-url'); // Extract info from data-* attributes
        var title = button.data('image-title'); // Extract info from data-* attributes
        var modal = $(this);
        modal.find('.modal-title').text(title);
        modal.find('.modal-body img').attr('src', url);
        modal.find('.modal-body img').attr('alt', title);
    })
});
