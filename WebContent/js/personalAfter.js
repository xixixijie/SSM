$(function () {
    $("#addAfter").click(function () {
        window.location.href="addAfter.html";
    })
    $("#getAfter").click(function () {
        window.location.href="searchAfter.html";
    })
    $("#chatAfter").click(function () {
        var cs_id=$("#cs_id").val();
        var url="http://localhost:8081/chatmain.html?username="+cs_id;
        window.location.href=url;
    })
})
