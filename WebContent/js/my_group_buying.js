/**
 * Created by chenyufeng on 2018/7/29.
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
    // var userID = location.href.substr(index + 1);

    $.ajax({
        url: "searchGroupsByUserID/" + userID,
        type: "POST",
        dataType: "json",
        success: function (data) {
            console.log(data);
            // 清除原有的数据
            $("#my_open").empty();
            for (var i = 0; i < data.length; i++) {
                var str = '<div class="groups-m mc-sum-box"><div><div class="myorder-sum fl">' +
                    '<img src="/img/'+data[i].activity.product.cover_url+'"></div> <div class="myorder-text"> ' +
                    '<h1>' + data[i].activity.product.product_name + '</h1> ' +
                    '<h2>' + data[i].activity.product.product_info.substring(0,20) + '</h2>' +
                    '<div class="tuan_g_core"><div class="tuan_g_price"><span>成团价</span>' +
                    '<b>¥' + data[i].activity.group_buying_price + '</b> </div> <div class="tuan_g_btn"></div> </div> </div> </div>' +
                    ' <div class="groups-dowm-bnt">我开的团' +
                    '<a href="show_group_info.html?groupID=' + data[i].groupID + '" class="btn fr">查看团详情</a></div></div>';
                $("#my_open").append(str);
            }
        }
    });


    $.ajax({
        url: "searchJoinGroupListByUserID/" + userID,
        type: "POST",
        dataType: "json",
        success: function (data) {
            console.log(data);
            // 清除原有的数据
            $("#my_join").empty();
            for (var i = 0; i < data.length; i++) {
                var str = '<div class="groups-m mc-sum-box"><div><div class="myorder-sum fl">' +
                    '<img src="/img/'+data[i].group.activity.product.cover_url+'"></div> <div class="myorder-text"> ' +
                    '<h1>' + data[i].group.activity.product.product_name + '</h1> ' +
                    '<h2>' + data[i].group.activity.product.product_info.substring(0,20) + '</h2>' +
                    '<div class="tuan_g_core"><div class="tuan_g_price"><span>成团价</span>' +
                    '<b>¥' + data[i].group.activity.group_buying_price + '</b> </div> <div class="tuan_g_btn"></div> </div> </div> </div>' +
                    ' <div class="groups-dowm-bnt">您参与的团购' +
                    '<a href="show_group_info.html?groupID=' + data[i].group.groupID + '" class="btn fr">查看团详情</a></div></div>';
                $("#my_join").append(str);
            }
        }
    })
});