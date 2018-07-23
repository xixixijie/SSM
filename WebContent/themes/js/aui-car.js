$(function(){
    // 数量减   ok
    $(".minus").click(function() {
        var t = $(this).parent().find('.num');
        t.text(parseInt(t.text()) - 1);
        if (t.text() <= 1) {
            t.text(1);
        }
        TotalPrice();   //计算价格
    });
    // 数量加
    $(".plus").click(function() {
        var t = $(this).parent().find('.num');
        t.text(parseInt(t.text()) + 1);
        if (t.text() <= 1) {
            t.text(1);
        }
        TotalPrice();  //计算价格
    });
    /******------------分割线-----------------******/


    // 点击商品按钮选择
    $(".goodsCheck").click(function() {
        var goods = $(this).closest(".aui-car-box-list").find(".goodsCheck"); //获取本店铺的所有商品
        var goodsC = $(this).closest(".aui-car-box-list").find(".goodsCheck:checked"); //获取本店铺所有被选中的商品

        if (goods.length == goodsC.length) { //如果选中的商品等于所有商品
            $("#AllCheck").prop('checked', true); //全选按钮被选中
            TotalPrice();
        } else { //如果选中的商品不等于所有商品

            $("#AllCheck").prop('checked', false); //全选按钮也不被选中
            // 计算
            TotalPrice();
            // 计算
        }
    });

    // 点击全选按钮选择    ok
    $("#AllCheck").click(function() {
        if ($(this).prop("checked") == true) { //如果全选按钮被选中
            $(".goods-check").prop('checked', true); //所有按钮都被选中
            TotalPrice();
        } else {
            $(".goods-check").prop('checked', false); //else所有按钮不全选
            TotalPrice();
        }
    });


    //计算
    function TotalPrice() {
        var allprice = 0; //总价
        $(".aui-car-box-list").find(".goodsCheck").each(function() { //循环遍历每一个商品
            if ($(this).is(":checked")) { //如果该商品被选中

                var num = parseInt($(this).parent().find(".num").text()); //拿到商品的数量
                var price = parseFloat($(this).parent().find(".price").text()); //得到商品的单价
                //alert(num+" "+price);
                var total = price * num; //计算单个商品的总价
                allprice += total; //计算总价
            }
        });
        $("#AllTotal").text(allprice.toFixed(2)); //输出所选的所有商品的总价
    }
});
