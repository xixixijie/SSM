/**
 * Created by chenyufeng on 2018/7/24.
 */
function get_cookie(Name) {
    //查询检索的值
    var search = Name + "=";
    //返回值
    var returnvalue = "";
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


$(function () {
    var index = location.href.lastIndexOf("=");
    var userID = get_cookie("miUserId");
    // var userID = location.href.substr(index+1);

    $.ajax({
        url:"getMessage/"+userID,
        type:"post",
        dataType:"json",
        success:function(data) {
            console.log(data);
            // 清除原有的数据
            $("#show_message").empty();
            for (var i = 0; i < data.length; i++) {
               var str = '<a href="show_message.html?massageID='+data[i].messageID+'" class="aui-layout-item"><div class="aui-layout-item-img"> ' +
                   '<img src="img/my_message.png" alt=""></div><div class="aui-layout-item-text"> <h4>' +
                   '您有新消息'+'</h4> <p>' +
                   data[i].messageTitle+'</p></div></a>';
                $("#show_message").append(str);
            }
        }
    })

});