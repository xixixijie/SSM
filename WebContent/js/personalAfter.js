$(function () {
    $("#addAfter").click(function () {
        window.location.href="addAfter.html";
    })
    $("#getAfter").click(function () {
        window.location.href="searchAfter.html";
    })
    $("#chatAfter").click(function () {
        var userId=get_cookie("miUserId");
        var url="http://10.10.32.171:8081/chatmain.html?username="+userId;
        window.location.href=url;
    })
})
function get_cookie(Name) {
    var search = Name + "="//查询检索的值
    var returnvalue = "";//返回值
    if (document.cookie.length > 0) {
        sd = document.cookie.indexOf(search);
        if (sd!= -1) {
            sd += search.length;
            end = document.cookie.indexOf(";", sd);
            if (end == -1)
                end = document.cookie.length;
            //unescape() 函数可对通过 escape() 编码的字符串进行解码。
            returnvalue=unescape(document.cookie.substring(sd, end))
        }
    }
    return returnvalue;
}