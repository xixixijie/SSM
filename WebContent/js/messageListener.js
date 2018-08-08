/**
 * Created by chenyufeng on 2018/7/30.
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


var int=self.setInterval("getMessage()",1000);
var userID = get_cookie("miUserId");
// var userID=1;
function getMessage() {
    $.ajax({
        url: "getMessage/" + userID,
        type: "post",
        dataType: "json",
        success: function (data) {
            console.log(data);
            for (var i = 0; i < data.length; i++) {
                if (data[i].isRead == 0) {
                    var str = data[i].messageTitle + data[i].messageBody + data[i].user.name;
                    var messageID = data[i].messageID;
                    //从cookie中获取userID
//                        $.cookie('userID', 1, { expires: 7 ,path:'/'});
                    //从cookie中取userID
                    var userID = 1;
//                        userID = $.cookie('userID');
                    Lobibox.notify('info', {
                        size: 'mini',
                        rounded: true,
                        delayIndicator: false,
                        delay: false,
                        msg: '尊敬的' + data[i].user.name + '， ' + data[i].messageTitle,
                        onClick: function () {
                            // your code goes here
                            //设置已读
                            $.ajax({
                                url: "readMessage/" + messageID,
                                type: "post",
                                dataType: "json",
                                success: function (data) {
                                    //donothing
                                }
                            });
                            //显示消息页面
                            window.location.href = "my_message.html?userID=" + userID;
                            int = self.setInterval("getMessage()", 1000)
                        }
                    });
                    int = window.clearInterval(int)
                }
            }
        }
    });
}