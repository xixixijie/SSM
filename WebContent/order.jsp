<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/24 0024
  Time: 20:30
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
    <title>购物车</title>
    <link rel="stylesheet" href="themes/css/core.css">
    <link rel="stylesheet" href="themes/css/icon.css">
    <link rel="stylesheet" href="themes/css/home.css">
    <link rel="icon" type="image/x-icon" href="favicon.ico">
    <link href="iTunesArtwork@2x.png" sizes="114x114" rel="apple-touch-icon-precomposed">
    <script src="themes/js/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var totalPrice=0;
            //循环遍历每一个商品并计算价格
            $(".aui-list-product-float-item").find(".price").each(function(){
                var price=parseFloat($(this).text());
                var num=parseInt($(this).parent().find(".buynum").text());
                var total=price*num;
                totalPrice+=total;
            });
           //$("#AllMoney").text(totalPrice.toFixed(2)); //输出所选的所有商品的总价
           $("#AllMoney").attr("value",totalPrice.toFixed(2));
        })
    </script>

    <script type="text/javascript">
        function selectProduct(){
            document.forms[0].action="createOrder.action";
            document.forms[0].submit();

        }
    </script>
</head>

<body>



<header class="aui-header-default aui-header-fixed ">
    <a href="javascript:history.back(-1)" class="aui-header-item">
        <i class="aui-icon aui-icon-back"></i>
    </a>
    <div class="aui-header-center aui-header-center-clear">
        <div class="aui-header-center-logo">
            <div class="">确认订单</div>
        </div>
    </div>
    <a href="#" class="aui-header-item-icon"   style="min-width:0">
        <i class="aui-icon aui-icon-member"></i>
    </a>
</header>

<form  action=""  method="post">
<section class="aui-address-content">

    <div class="aui-address-box">
        <div class="aui-address-box-list">
            <a href="#" class="aui-address-box-default" >

                        <select name="address_id" id="" style="width:300px;border:0px; height: 40px;">   <!--对地址进行查询遍历-->
                            <c:forEach  items="${result2}" var="addr">
                                <option value="${addr.address_ID}">
                                    <strong>${addr.accept_Name}</strong>
                                    <strong>${addr.accept_Phone}</strong>
                                        ${addr.address}
                                </option>
                            </c:forEach>

                        </select>
            </a>

        </div>
    </div>

    <!--查数据-->
    <div class="aui-dri"></div>
    <div class="aui-list-product-float-item">

           <c:forEach  items="${result3}" var="pis" varStatus="status">

               <input type="hidden" name="orderProducts[${status.index}].product_id"  value="${pis.product_id}">
               <input type="hidden" name="orderProducts[${status.index}].product_price" value="${pis.discount_price}">
               <input type="hidden" name="orderProducts[${status.index}].product_number" value="${pis.carProducts.cpnumber}">

               <a href="javascript:;" class="aui-list-product-fl-item">

            <div class="aui-list-product-fl-img">
                <img src="img/${pis.cover_url}" alt="">
            </div>


            <div class="aui-list-product-fl-text">
                <h3 class="aui-list-product-fl-title">${pis.product_info}</h3>
                <div class="aui-list-product-fl-mes">
                    <div>
                            <em>¥</em>
							<span class="aui-list-product-item-price price" >
                                    ${pis.discount_price}
							</span>
                            <span class="aui-list-product-item-del-price">
                                 <em>¥</em>
                                 ${pis.original_price}
							</span>

                            <em>数量：</em>
                            <span class="aui-list-product-item-price buynum">
                                ${pis.carProducts.cpnumber}
							</span>

                    </div>
                </div>

                <div class="aui-list-product-fl-bag">
                    <span><img src="themes/img/icon/bag1.png" alt=""></span>
                    <span><img src="themes/img/icon/bag2.png" alt=""></span>
                </div>
            </div>
        </a>

        </c:forEach>

    </div>

    <!--假数据-->
    <div class="aui-address-well">
        <a href="#" class="aui-address-cell aui-fl-arrow">
            <div class="aui-address-cell-bd">支持配送</div>
            <div class="aui-address-cell-ft">
                <p>在线支付</p>
                <p>顺丰快递</p>
                <p class="aui-address-text"><i class="aui-icon aui-prompt-sm"></i>11月12日[周五]09:00-15:00</p>
            </div>
        </a>
        <div class="aui-prompt"><i class="aui-icon aui-prompt-sm"></i>您的收货时间为工作日，请您做好收货准备。</div>
        <a href="#" class="aui-address-cell aui-fl-arrow aui-fl-arrow-clear">
            <div class="aui-address-cell-bd">发票</div>
            <div class="aui-address-cell-ft">个人</div>
        </a>
        <div class="aui-dri"></div>
        <a href="#" class="aui-address-cell aui-fl-arrow aui-fl-arrow-clear">
            <div class="aui-address-cell-bd">
                运费险
                <p style="color:#888">7天内退货，15天内可换货</p>
            </div>
            <div class="aui-address-cell-ft">￥0.50&nbsp;&nbsp;<input class="aui-switch" type="checkbox" checked="checked"></div>
        </a>
        <div class="aui-dri"></div>
        <a href="#" class="aui-address-cell aui-fl-arrow aui-fl-arrow-clear">
            <div class="aui-address-cell-bd">优惠券</div>
            <div class="aui-address-cell-ft">不可用</div>
        </a>
        <div class="aui-dri"></div>
        <a href="#" class="aui-address-cell aui-fl-arrow aui-fl-arrow-clear">
            <div class="aui-address-cell-bd">礼品卡</div>
            <div class="aui-address-cell-ft">不可用</div>
        </a>
        <div class="aui-dri"></div>
        <a href="#" class="aui-address-cell aui-fl-arrow aui-fl-arrow-clear">
            <div class="aui-address-cell-bd">积分</div>
            <div class="aui-address-cell-ft">0</div>
        </a>
        <div class="aui-dri"></div>
    </div>

    <!--确认金额-->
    <div class="aui-payment-bar">
        <div class="shop-total">
            应付总额:
          <input  class="aui-red aui-size"  id="AllMoney" value="0.00" disabled="true"/>

        </div>
     <!--  <a  href="createOrder.action" class="settlement">提交订单</a>-->
        <button type="button" onclick="selectProduct()" class="settlement">提交订单</button>
    </div>
</section>
</form>

</body>
</html>
