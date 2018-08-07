/**
 * Created by chenyufeng on 2018/7/23.
 */
$(function () {
    var index = location.href.lastIndexOf("=");
    var activityID = location.href.substr(index + 1);
    $.ajax({
        url: "searchActivityInfo/" + activityID,
        type: "POST",
        dataType: "json",
        success: function (data) {
            console.log(data);
            $("#show_number").empty();
            $("#show_number").append('<p>' + data.requiredNumber + '人团</p><p>¥' + data.group_buying_price + '</p>');


            $("#product_img").empty();
            $("#product_img").append('<img src="/img/' + data.product.cover_url + '">');


            $("#show_product_info").empty();
            $("#show_product_info").append('<p class="tuan_g_name">' +
                data.product.product_name + '</p> <p class="tuan_g_cx">' +
                data.product.product_info + '</p>');

            $("#show_price").empty();
            $("#show_price").append(' <div id="triangle-right"></div><div class="tuan_g_price"><span>' +
                data.requiredNumber + '人团只需</span> <b>¥' + data.group_buying_price + '</b> </div> <div class="tuan_g_btn">我要拼</div>');

            $("#show_origin_price").empty();
            $("#show_origin_price").append('<s>原价:￥' + data.product.original_price + '</s>');

            // 清除原有的数据
            $("#show_groups_one").empty();
            var str2 = '<div><div class="myorder-sum fl"><img src="/img/open.png"></div><div class="myorder-text">' +
                '<h1>你是下一个团长么?</h1>' + ' <div class="tuan_g_core"> <div class="tuan_g_price"> ' + '<span>立刻开团</span>' +
                '</div> <div class="tuan_g_btn"></div> ' +
                '</div> </div> </div> <div class="groups-dowm-bnt"><a href="open_group.html?activityID=' + data.activityID + '" class="btn fr">我来开团</a>' +
                '</div>';
            $("#show_groups_one").append(str2);
        }
    });

    $.ajax({
        url: "searchGroups/" + activityID,
        type: "POST",
        dataType: "json",
        success: function (data) {
            console.log(data);
            $("#show_groups").empty();
            for (var i = 0; i < data.length; i++) {
                var str = '<div><div class="myorder-sum fl"><img src="/img/join.png"></div><div class="myorder-text">' +
                    ' <h1>团长：' + data[i].leader.name + '</h1> <div class="tuan_g_core"> <div class="tuan_g_price"> ' +
                    '<span>当前' + data[i].current_num + '/' + data[i].activity.requiredNumber + '</span></div> <div class="tuan_g_btn"></div> ' +
                    '</div> </div> </div> <div class="groups-dowm-bnt">拼团进行中 <a href="join_group.html?groupID=' + data[i].groupID + '" class="btn fr">加入团购</a>' +
                    ' <a href="show_group_info.html?groupID=' + data[i].groupID + '" class="btn fr">查看团详情</a> </div>';
                $("#show_groups").append(str);
            }
        }
    });


});