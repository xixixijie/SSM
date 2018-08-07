<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/26 0026
  Time: 9:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no, email=no"/>
    <meta charset="UTF-8">
    <title>收银台</title>
    <link rel="stylesheet" href="themes/css/core.css">
    <link rel="stylesheet" href="themes/css/icon.css">
    <link rel="stylesheet" href="themes/css/home.css">
    <link rel="icon" type="image/x-icon" href="favicon.ico">
    <link href="123.png" sizes="114x114" rel="apple-touch-icon-precomposed">
    <script src="themes/js/jquery.min.js"></script>

    <script type="text/javascript">
       $(document).ready(function(){
           var chk=$(".check");
           for(var i=0;i<chk.length;i++){
               if ($(this).is(":checked")){
                   $("this").attr("name",pay_method);
               }
           }
       })
    </script>
</head>

<body>

<form  action=""  method="post">

<header class="aui-header-default aui-header-fixed">
    <a href="javascript:history.back(-1)" class="aui-header-item">
        <i class="aui-icon aui-icon-back"></i>
    </a>
    <div class="aui-header-center aui-header-center-clear">
        <div class="aui-header-center-logo">
            <div class="">收银台</div>
        </div>
    </div>
    <a href="#" class="aui-header-item-icon"   style="min-width:0">
        <i class="aui-icon aui-icon-member"></i>
    </a>
</header>

<section class="aui-settle-content">
   <!--
     <div class="aui-settle-title">
        <p>需要支付:  <span>0.00元</span></p>
     </div>
    -->

    <div class="aui-settle-ways">
        选择支付方式
    </div>

    <div class="aui-settle-choice">

        <a href="#" class="aui-address-cell  aui-fl-arrow-clear">
            <div class="aui-address-cell-hd">
                <img src="themes/img/icon/apy-4.png" alt="">
            </div>
            <div class="aui-address-cell-bd">
                支付宝支付
            </div>
            <div class="aui-address-cell-ft"><span>花呗分期</span>&nbsp;&nbsp;
                <input type="checkbox"  class="check check-pay goods-check goodsCheck"
                     value="支付宝">
            </div>
        </a>

        <a href="#" class="aui-address-cell  aui-fl-arrow-clear">
            <div class="aui-address-cell-hd">
                <img src="themes/img/icon/apy-5.png" alt="">
            </div>
            <div class="aui-address-cell-bd">
                微信支付
            </div>
            <div class="aui-address-cell-ft">
                <input type="checkbox"  class="check check-pay goods-check goodsCheck"
                       value="微信">
            </div>
        </a>

        <a href="#" class="aui-address-cell  aui-fl-arrow-clear">
            <div class="aui-address-cell-hd">
                <img src="themes/img/icon/download4.jpg" alt="">
            </div>
            <div class="aui-address-cell-bd">
                在线支付
            </div>
            <div class="aui-address-cell-ft">
                <input  type="checkbox"   class="check check-pay goods-check goodsCheck"
                       value="在线支付">
            </div>
        </a>

        <a href="#" class="aui-address-cell  aui-fl-arrow-clear">
            <div class="aui-address-cell-hd">
                <img src="themes/img/icon/download3.jpg" alt="">
            </div>
            <div class="aui-address-cell-bd">
                小米钱包
            </div>
            <div class="aui-address-cell-ft">
                <input type="checkbox"  class="check check-pay goods-check goodsCheck"
                       value="小米钱包">
            </div>
        </a>

    </div>

</section>
    <div class="aui-settle-payment">
        <a href="updateOrder.action">支付</a>
    </div>
</form>


</body>
</html>