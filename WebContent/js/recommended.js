/**
 * Created by chenyufeng on 2018/7/24.
 */
$(function () {
    //获得排序最高，价格最低的团购活动
    var index = location.href.lastIndexOf("=");
    var userID = location.href.substr(index+1);
    $.ajax({
        url:"getRecommendedGroupBuying/"+userID,
        type:"post",
        dataType:"json",
        success:function(data) {
            console.log(data);
            // 清除原有的数据

            $("#show_number").empty();
            $("#show_number").append('<p>'+data.requiredNumber+'人团</p><p>¥'+data.group_buying_price+'</p>');


            $("#product_img").empty();
            $("#product_img").append('<img src="/img/'+data.product.cover_url+'">');


            $("#show_product_info").empty();
            $("#show_product_info").append('<p class="tuan_g_name">' +
                data.product.product_name+'</p> <p class="tuan_g_cx">' +
                data.product.product_info+'</p>');

            $("#show_price").empty();
            $("#show_price").append(' <div id="triangle-right"></div><div class="tuan_g_price"><span>' +
                data.requiredNumber+'人团只需</span> <b>¥'+data.group_buying_price+'</b> </div> <div class="tuan_g_btn">我要拼</div>');

            $("#show_origin_price").empty();
            $("#show_origin_price").append('<s>原价:￥'+data.product.original_price+'</s>');
        }
    })

});