/**
 * Created by chenyufeng on 2018/7/24.
 */
$(function () {
    var index = location.href.lastIndexOf("=");
    var groupID = location.href.substr(index+1);
    $.ajax({
        url:"searchGroupInfo/"+groupID,
        type:"POST",
        dataType:"json",
        success:function (data) {
            console.log(data);
            // 清除原有的数据
            $("#show_product_info").empty();
            var str = '<div class="myorder-sum fl"><img src="/img/' +
                data.activity.product.cover_url+'"></div> <div class="myorder-text"> <h1>' +
                data.activity.product.product_info.substr(0,20)+'</h1> <h2>'+data.activity.requiredNumber+'人团：<span class="mc-t">￥<b>' +
                +data.activity.group_buying_price+'</b>/件</span></h2> </div>';
            $("#show_product_info").append(str);

            $("#show_group_info").empty();
            var nowNum = data.current_num;
            var requiredNum = data.activity.requiredNumber;
            var str2='<ul class="colonel">';
            for (var i=0;i<nowNum;i++){
                str2+='<li><img src="img/join.jpg"></li>';
            }
            for (var j=0;j<requiredNum-nowNum;j++){
                str2+='<li><img src="img/notjoin.jpg"></li>';
            }
            str2+='</ul>';
            $("#show_group_info").append(str2);

            $("#show_num_info").empty();
            var str3='<p class="man-number">还差<span>' +
                (requiredNum-nowNum)+'</span>人，满' +
                +data.activity.requiredNumber+'人才能享受拼团优惠呦~</p>';
            $("#show_num_info").append(str3);

            $("#show_group_info2").empty();
            var str4='<ul>';
            str4+='<li class="colonel"><div class="man-name-img fl"><img src="img/join.jpg"></div>团长' +
                data.leader.name+'</li>';
            for (var k=1;k<nowNum;k++){
                str4+='<li><div class="man-name-img fl"><img src="img/join.jpg"></div>团员'+k+'</li>';
            }
            for (var l=0;l<requiredNum-nowNum;l++){
                str4+='<li><div class="man-name-img fl"><img src="img/notjoin.jpg"></div>座位没主人，快去邀请好友占领吧</li>';
            }
            str4+=' </ul>';
            $("#show_group_info2").append(str4);

            $("#show_num_info2").empty();
            $("#show_num_info2").append('<a href="my-purchase.html">还差'+(requiredNum-nowNum)+'人组团成功,点击返回团购首页</a>');
        }

    })
});