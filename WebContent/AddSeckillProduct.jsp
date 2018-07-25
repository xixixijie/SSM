<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/7/23
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <script type="text/javascript" src="js/jquery-3.2.0.min.js" ></script>
    <link rel="stylesheet" href="assets/css/amazeui.min.css">
    <link rel="stylesheet" href="assets/css/app.css">
    <script src="assets/js/amazeui.js"></script>
    <script src="assets/js/app.js"></script>
    <script language="javascript">window.onload=
        function(){
            $.ajax({
                url:"selectClassifies",
                type:"post",
                dataType:"json",
                contentType:false,
                processData:false,
                cache:false,
                success:function(data)
                {
                    for(var i=0;i<data.length;i++){
                        $('select[name="classifyId"]').append("<option value='"+data[i].classifyID+"'>"+data[i].className+"</option>"
                        );
                    }

                }
            });
        }
    </script>
    <script language="javascript">
        function classifyChange(){
            var c=$('select[name="classifyId"]').val();

            $.ajax({
                url:"selectProductsByClassifyId",
                type:"post",
                data:{"classifyId":c},
                dataType:"json",
                success:function(data1)
                {
                    $('select[name="productId"]').empty();
                    if(data1.length>0){
                        $('#oldprice').val( data1[0].original_price);
                    }
                    for(var i=0;i<data1.length;i++){
                        $('select[name="productId"]').append("<option value='"+data1[i].product_id+"'>"+data1[i].product_name+"</option>"
                        );
                    }
                }
            });
        }
    </script>
    <script language="javascript">
        function productChange(){
            var c=$('select[name="productId"]').val();
            $.ajax({
                url:"setSeckillPrice.action",
                type:"post",
                data:{"productId":c},
                dataType:"json",
                success:function(data1)
                {
                    $('#oldprice').val(data1.original_price) ;
                }
            });
        }
    </script>
    <script language="javascript">
        function checkPrice() {
            var oldprice=$("#oldprice").val();
            var newprice=$("#seckillPrice");
            if(oldprice<newprice){
                $("#seckillPrice").val("");
                $("#seckillPrice").focus();
            }
        }
    </script>

    <meta charset="UTF-8">
    <title>增加秒杀商品</title>
</head>
<body>
<form class="am-form" action="addSeckillProduct.action" method="post" id="form1">
    <fieldset>
        <legend>添加秒杀商品</legend>

        <div class="am-form-group">
            <label for="doc-select-1">商品分类</label>
            <select id="doc-select-1" name="classifyId" onchange="classifyChange()"></select>
        </div>

        <div class="am-form-group">
            <label for="doc-select-1">商品名称</label>
            <select id="doc-select-1" name="productId" onchange="productChange()"></select>
        </div>


        <div class="am-form-group">
            <label >原价</label>
            <input type="number" id="oldprice" name="oldprice" readonly="readonly">
        </div>

        <div class="am-form-group">
            <label >秒杀价格</label>
            <input type="number" name="seckillprice" placeholder="秒杀价格必须比原价低" required="required" onchange="checkPrice()">
        </div>

        <div class="am-form-group">
            <label >秒杀数量</label>
            <input type="number" name="allamount" placeholder="" required="required">
        </div>

        <div class="am-form-group">
            <label >秒杀日期</label>
            <input type="text" name="date" class="am-form-field tpl-form-no-bg" placeholder="请点击选择日期" data-am-datepicker="" readonly="">
        </div>

        <div class="am-form-group">
            <label for="doc-select-1">开始时间</label>
            <select id="doc-select-1" name="time">
                <option value="08:00:00">上午8点</option>
                <option value="12:00:00">中午12点</option>
                <option value="18:00:00">下午6点</option>
                <option value="22:00:00">晚上10点</option>
            </select>
        </div>

        <p><button type="submit" class="am-btn am-btn-primary tpl-btn-bg-color-success " >提交</button></p>
    </fieldset>
</form>



</body>
</html>