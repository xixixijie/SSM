/**
 * Created by chenyufeng on 2018/7/24.
 */
$(function () {
    var index = location.href.lastIndexOf("=");
    var userID = location.href.substr(index+1);

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