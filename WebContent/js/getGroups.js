/**
 * Created by chenyufeng on 2018/7/23.
 */
$(function () {
    var index = location.href.lastIndexOf("=");
    var activityID = location.href.substr(index+1);

    $.ajax({
        url:"searchGroups/"+activityID,
        type:"POST",
        dataType:"json",
        success:function (data) {
            console.log(data);
            // 清除原有的数据
            $("#show_groups").empty();
            for(var i=0;i<data.length;i++){
                var str = '<li class="aui-slide-item-item"> <a href="join_group.html?groupID='+data[0].groupID+'" class="v-link"> <img class="v-img" src="img/join.png"> <p class="aui-slide-item-title aui-slide-item-f-els">团长：' +
                    data[i].leader.name+'</p><p class="aui-slide-item-info"><span class="aui-slide-item-price">当前人数'+data[i].current_num+'/'+data[i].activity.requiredNumber+'</span> </p> </a> </li>';
                $("#show_groups").append(str);
            }
            var str2 = '<li class="aui-slide-item-item"><a href="open_group.html?activityID='+data[0].activity.activityID+'" class="v-link"><img class="v-img" src="img/open.png"> <p class="aui-slide-item-title aui-slide-item-f-els"></p> <p class="aui-slide-item-info"> <span class="aui-slide-item-price">我来开团</span> </p> </a></li>';
            $("#show_groups").append(str2);
            $("#show_product_name").append('<h3>'+data[0].activity.product.product_info+'</h3>');
            $('#show_product_price').append(' <span class="aui-list-product-item-price"><em>¥</em>'+data[0].activity.group_buying_price+' </span><span class="aui-list-product-item-del-price">'+data[0].activity.product.original_price+'</span>');
            $('#show_product_pic').append(' <img src="'+data[0].activity.product.cover_url+'" alt="">');
        }
    })

});