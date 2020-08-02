$(document).ready(function() {
    $('#table').bootstrapTable({});
    var currentPageNumber = 0;
    var totalPageNumber = 0;
    var currentSearchPhrase = "";
    getRecords(currentSearchPhrase, currentPageNumber);
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

    $("#searchBtn").click(function(){
        search();
    })

    $("#next").click(function(){
        if(currentPageNumber < totalPageNumber){
            currentPageNumber++;
            getRecords(currentSearchPhrase, currentPageNumber)
        }

    })

    $("#prev").click(function(){
        if(currentPageNumber > 0){
            currentPageNumber--;
            getRecords(currentSearchPhrase, currentPageNumber)
        }
    })

    function watchFileUploadUpdate(updateId){
        var source = new EventSource("/api/file_process_update/"+updateId);
        var percentage = 0;
        source.onmessage = function(event) {
            percentage = JSON. parse(event.data).message;
            if(percentage == 100){
                source.close();
                search();
                $(".progress").addClass("in");
                $(".progress").removeClass("show");
            }
            if($(".progress").hasClass("fade in")){
                $(".progress").removeClass("fade in");
                $(".progress").addClass("fade show");
            }
            $("#progressBar").css("width", percentage+"%");
            $("#progressText").text(percentage+"% complete");
        };
    }

    function search(){
        currentSearchPhrase = $("#search").val();
        currentPageNumber = 0;
        totalPageNumber = 0;
        getRecords(currentSearchPhrase, currentPageNumber);
    }

    function getRecords(searchPhrase, pageNumber){
        var source = new EventSource("/api/records?search="+searchPhrase+"&pageNumber="+pageNumber+"&size=10");
        var percentage = 0;
        source.onmessage = function(event) {
            result = JSON.parse(event.data);
            if(result.message !== undefined){
                var percentage = result.message;
                if($(".progress").hasClass("fade in")){
                    $(".progress").removeClass("fade in");
                    $(".progress").addClass("fade show");
                }
                $("#progressBar").css("width", percentage+"%");
                $("#progressText").text(percentage+"% complete");
            } else if(result.content !== undefined){
                source.close();
                $("#table").bootstrapTable("load", result.content);
                currentPageNumber = result.pageable.pageNumber;
                totalPageNumber = result.totalPages;
                $("#pageNumber").text(currentPageNumber+1);
                $("#totalPage").text(result.totalPages == 0 ? 1 : totalPageNumber);

                setTimeout(function(){
                    $(".progress").removeClass("fade show");
                    $(".progress").addClass("fade in");
                }, 1000)
            }
        };
    }

})