/**
 * Created by chenyufeng on 2018/7/30.
 */
$(function(){
    $("#searchInput").bind('keypress',function(event){
        if(event.keyCode == "13")
        {
            search_clicked();
        }
    });
});



function search_clicked() {
    var pageNum = $("#pageNum_started").val();
    var productName = -1;
    var groupStartDate = -1;
    var groupEndDate = -1;
    var searchProductName = $("#searchInput").val();
    $.ajax({
        url: "searchActivities/" + productName + "/" + groupStartDate + "/" + groupEndDate + "/" + pageNum,
        type: "post",
        dataType: "json",
        success: function (data) {
            console.log(data);
            //2.追加符合条件的数据
            if (data.length == 0) {
                alert("没有符合条件的结果");
            }else {
                $("#pageNum_started").val(1);
                //1.清除原有的数据
                $('#search_activity_div').empty();
                for (var i = 0; i < data.length; i++) {
                    if (data[i].activityStatus == 2) {
                        if(data[i].product.product_name.indexOf(searchProductName)>=0||
                            data[i].product.product_info.indexOf(searchProductName)>=0
                        ){
                            var str = '<li id="activity' + data[i].activityID + '"><div class="aui-list-title-info"><a href="javascript:;" class="aui-list-product-fl-item"> <div class="aui-list-product-fl-img">';
                            str += '<img src="/img/' + data[i].product.cover_url + '" alt="">';
                            str += '</div><div class="aui-list-product-fl-text"><span class="aui-Purchase-tag">';
                            str += data[i].requiredNumber;
                            str += '人团</span>';
                            str += '<h3 class="aui-list-product-fl-title">' + data[i].product.product_name + '</h3>';
                            str += '<div class="aui-list-product-fl-mes"><div><span class="aui-list-product-item-price"><em>¥</em>' + data[i].group_buying_price + '</span>';
                            str += '<span class="aui-list-product-item-del-price">¥' + data[i].product.original_price + '</span></div>';
                            str += '</div> </div> </a> <div class="aui-list-title-btn"> <a href="single_group_buying.html?activityID=' + data[i].activityID + '" class="red-color">马上参团</a> </div> </div> </li>';
                            $("#search_activity_div").append(str);
                        }
                    }
                }
            }
        }
    });
}