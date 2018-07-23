/**
 * Created by chenyufeng on 2018/7/23.
 */
$(function () {
    var index = location.href.lastIndexOf("=");
    var groupID = location.href.substr(index + 1);
    $.ajax({
        url:"searchGroupInfo/"+groupID,
        type:"POST",
        dataType:"json",
        success:function (data) {
            console.log(data);
            // 清除原有的数据
            $("#show_product").empty();
            var str = '<h3 class="aui-list-product-fl-title">'+data[0].activity.product.product_info+'</h3>'
            +'<div class="aui-list-product-fl-mes"> <div><span class="aui-list-product-item-price"><em>¥</em>'
            +data[0].activity.group_buying_price+' </span> <span class="aui-list-product-item-del-price"> ¥'
            +data[0].activity.product.original_price+' </span></div></div> <div class="aui-list-product-fl-bag">' +
                '<span><img src="themes/img/icon/bag1.png" alt=""></span> <span><img src="themes/img/icon/bag2.png" ' +
                'alt=""></span> </div>';
            $("#show_product").append(str);

            var str2 = '<div class="aui-list-product-fl-mes"><div><span class="aui-list-product-item-price">'
                        +data[0].leader.name+'</span></div></div><div class="aui-list-product-fl-mes"><div><span class="aui-list-product-item-price">'
                        +'当前人数'+data[0].current_num+'/'+data[0].activity.requiredNumber+'</span> </div> </div>';
            $("#show_group_info").append(str2);

            $("#join_group_groupID").val(data[0].groupID);
            $("#show_price").append('  <span class="aui-red aui-size">总金额: <em>￥'+data[0].activity.group_buying_price+'</em></span>');
        }
    })
});