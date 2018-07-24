/**
 * Created by chenyufeng on 2018/7/23.
 */
$(function () {
    var index = location.href.lastIndexOf("=");
    var activityID = location.href.substr(index + 1);
    $.ajax({
        url:"searchActivityInfo/"+activityID,
        type:"POST",
        dataType:"json",
        success:function (data) {
            console.log(data);
            // 清除原有的数据
            $("#show_product").empty();
            var str = '<h3 class="aui-list-product-fl-title">'+data.product.product_info+'</h3>'
                +'<div class="aui-list-product-fl-mes"> <div><span class="aui-list-product-item-price"><em>¥</em>'
                +data.group_buying_price+' </span> <span class="aui-list-product-item-del-price"> ¥'
                +data.product.original_price+' </span></div></div> <div class="aui-list-product-fl-bag">' +
                '<span><img src="themes/img/icon/bag1.png" alt=""></span> <span><img src="themes/img/icon/bag2.png" ' +
                'alt=""></span> </div>';
            $("#show_product").append(str);
            $("#open_group_activityID").val(data.activityID);
            $("#show_price").append('  <span class="aui-red aui-size">总金额: <em>￥'+data.group_buying_price+'</em></span>');
            $("#product_url").append('<img src="'+data.product.cover_url+'" alt="">');
        }
    })
});