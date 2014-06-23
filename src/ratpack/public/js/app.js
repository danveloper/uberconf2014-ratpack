$(document).ready(function() {
   $("#newPhotoBtn").click(function(e) {
       e.preventDefault();
       $("#newPhotoFile").trigger("click");
   });
    $("#newPhotoFile").on('change', function() {
        $("#newPhotoForm").submit();
    })
});