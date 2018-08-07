<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/23 0023
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no, email=no"/>
    <meta charset="UTF-8">
    <title>我的购物车</title>
    <link rel="stylesheet" href="themes/css/core.css">
    <link rel="stylesheet" href="themes/css/icon.css">
    <link rel="stylesheet" href="themes/css/home.css">
    <link rel="icon" type="image/x-icon" href="favicon.ico">
    <link href="iTunesArtwork@2x.png" sizes="114x114" rel="apple-touch-icon-precomposed">
    <script src="js/jquery-3.2.0.min.js"></script>
    <script type="text/javascript">
        function selectProduct(){
            document.forms[0].action="selectAllProduct.action";
            document.forms[0].submit();

        }
    </script>
    <script>
        $(function(){
            // 数量减
            $(".minus").click(function() {
                var t = $(this).parent().find('.num');
                var t2=t.val()-1;
                t.attr("value",t2);
                if (t.val() <= 1) {
                    t.attr("value",1);
                }
                TotalPrice();   //计算价格
            });

            // 数量加
            $(".plus").click(function() {
                var t = $(this).parent().find('.num');
                var t3=parseInt(t.val())+1;
                t.attr("value",t3);
                if (t.val() <= 1) {
                    t.attr("value",1);
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

                        var num = $(this).parent().find(".num").val(); //拿到商品的数量
                        var price = parseFloat($(this).parent().find(".price").text()); //得到商品的单价
                        //alert(num+" "+price);
                        var total = price * num; //计算单个商品的总价
                        //alert(total);
                        allprice += total; //计算总价
                    }
                });
                $("#AllTotal").text(allprice.toFixed(2)); //输出所选的所有商品的总价
            }
        });

    </script>
</head>
<body>
<!--头部-->
<header class="aui-header-default aui-header-fixed ">
    <a href="javascript:history.back(-1)" class="aui-header-item">
        <i class="aui-icon aui-icon-back"></i>
    </a>
    <div class="aui-header-center aui-header-center-clear">
        <div class="aui-header-center-logo">
            <div class="">购物车</div>
        </div>
    </div>
    <a href="#" class="aui-header-item-icon"   style="min-width:0">
        <i class="aui-icon aui-icon-member"></i>
    </a>
</header>

<form    action=" " method="post">
    <section class="aui-car-content">
        <div class="aui-dri"></div>
        <div class="aui-car-box">

                <div class="aui-car-box-list">
                    <ul>
                        <c:forEach items="${result1}" var="p" >
                        <li>
                            <input type="hidden" value="${p.carProduct_id}" name="carProduct_id"/>
                            <div class="aui-car-box-list-item">
                                <input type="checkbox" class="check goods-check goodsCheck"
                                       name="productIds" value="${p.product_id}"/>

                                <div class="aui-car-box-list-img">
                                    <a href="ui-product.html">

                                        <img src="img/${p.products.cover_url}">
                                    </a>
                                </div>
                                <div class="aui-car-box-list-text">
                                    <h4>
                                        <a href="ui-product.html">${p.products.product_info}</a>
                                    </h4>
                                    <div class="aui-car-box-list-text-price">
                                        <div class="aui-car-box-list-text-pri">
                                            ￥<b class="price">${p.products.discount_price}</b>
                                        </div>

                                        <!--删除-->
                                        <div><a href="deleteCarProduct.action?carProduct_id=${p.carProduct_id}" >删除</a></div>

                                        <!--商品数量的改变 -->
                                        <div class="aui-car-box-list-text-arithmetic">
                                            <a href="javascript:;" class="minus">-</a>
                                            <input  type="number" class="num" name="cpnumber" value="${p.cpnumber}">
                                          <!--  <span class="num" name="cpnumber"></span>-->
                                            <a href="javascript:;" class="plus">+</a>
                                        </div>

                                    </div>
                                </div>

                            </div>
                            <div class="aui-dri"></div>
                        </li>
                        </c:forEach>
                    </ul>
                </div>

        </div>


    </section>

        <!--底层支付-->
        <div class="aui-payment-bar">
            <div class="all-checkbox"><input type="checkbox" class="check goods-check" id="AllCheck">全选</div>

            <div class="shop-total">
                <strong>合计：<i class="total" id="AllTotal">0.00</i></strong>
            </div>

           <!-- <a href="selectAddress.action" class="settlement">去结算</a>-->

            <button type="button" onclick="selectProduct()" class="settlement">去结算</button>
        </div>

</form>



</body>

</html>
