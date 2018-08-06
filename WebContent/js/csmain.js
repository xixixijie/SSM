$(function () {
    $("#btn_treatAsk").click(function () {
        var cs_id=$("#cs_id").val();
        var url = 'http://localhost:8082/customerchat.html'
        url = encodeURI(url);
        window.location.href=url;
    })
    $("#btn_treatAfter").click(function () {
        window.location.href="http://localhost:8082/cstreat.html";
    })
})