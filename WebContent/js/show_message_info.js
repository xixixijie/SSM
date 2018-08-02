/**
 * Created by chenyufeng on 2018/7/26.
 */
$(function () {
    var index = location.href.lastIndexOf("=");
    var messageID = location.href.substr(index+1);
    $.ajax({
        url:"searchMessageInfo/"+messageID,
        type:"POST",
        dataType:"json",
        success:function (data) {
            console.log(data);
            $("#show_message").empty();
            $("#show_message").append(data.messageBody);
        }
    })
});