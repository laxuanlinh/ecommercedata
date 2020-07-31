$(document).ready(function() {

    $('#table').DataTable();
    $("#form").submit(function(event){
        event.preventDefault();
        var form = new FormData($("#form")[0]);
        $.ajax({
            url: "/api/file",
            method: "POST",
            data: form,
            processData: false,
            contentType: false,
            success: function(result){
                watchFileUploadUpdate(result);
                $("#form").find(":input[name='file']").val("");
            },
            error: function(er){}
        });
    })

    function watchFileUploadUpdate(updateId){
        var source = new EventSource("/api/file_process_update/"+updateId);
        var percentage = 0;
        source.onmessage = function(event) {
            percentage = event.data;
            if(percentage == 100){
                source.close();
            }

            $("#update").html(percentage+"%")
        };
    }

})