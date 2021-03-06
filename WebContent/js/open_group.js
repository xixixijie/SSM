/**
 * Created by chenyufeng on 2018/7/23.
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
            $("#product_url").append('<img src="/img/'+data.product.cover_url+'" alt="">');
        }
    });
    var userID = get_cookie("miUserId");
    // var userID = 1;
    $.ajax({
        url: "getAddresses/" + userID,
        type: "POST",
        dataType: "json",
        success: function (data) {
            console.log(data);
            // 清除原有的数据
            $("#address_select").empty();
            for (var i=0;i<data.length;i++){
                var str = '<option value="'+data[i].address_ID+'">地址：'+data[i].address+' 姓名：'+data[i].accept_Name+' 电话：'+data[i].accept_Phone+'</option>';
                $("#address_select").append(str);
            }
        }
    })
});