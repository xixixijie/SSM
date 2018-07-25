<%--
  Created by IntelliJ IDEA.
  User: chenyufeng
  Date: 2018/7/25
  Time: 下午1:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发布团购活动</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="/js/jquery-3.2.0.min.js"></script>
</head>
<body>
<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">发布团购活动</h3>
    </div>
    <div class="panel-body">
        <%--首先三级联动获取商品--%>
            <div class="form-group">
                <label for="doc-select-1">商品分类</label>
                <select class="form-control" id="doc-select-1" name="classifyId" onchange="classifyChange()"></select>
            </div>

            <div class="form-group">
                <label for="doc-select-2">商品名称</label>
                <select class="form-control" id="doc-select-2" name="productId" onchange="productChange()"></select>
            </div>
            <div id="show_product_info">

            </div>
    </div>
</div>
<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">设置团购信息</h3>
    </div>
    <div class="panel-body">
        <div class="form-group">
            <label for="group_buying_price">团购价格</label>
            <input id="group_buying_price" type="text" class="form-control" value="0.00">
        </div>
        <div class="form-group">
            <label for="requiredNumber">需要的人数</label>
            <input id="requiredNumber" type="number" class="form-control" value="0">
        </div>
        <div class="form-group">
            <label for="groupStartDate">开始时间</label>
            <input id="groupStartDate" type="date" class="form-control" required>
        </div>
        <div class="form-group">
            <label for="groupEndDate">结束时间</label>
            <input id="groupEndDate" type="date" class="form-control" required>
        </div>
    </div>
    <button type="button" id="release_activity_button">发布</button>
</div>


<script type="application/javascript">
    $("#release_activity_button").click(function(){
        alert(11);
        var productID = $("#productID").val();
        var requiredNum = $("#requiredNumber").val();
        var group_buying_price = $("#group_buying_price").val();
        var groupStartDate = $("#groupStartDate").val();
        var groupEndDate = $("#groupEndDate").val();

        $.ajax({
            url:"releaseActivity/"+productID+"/"+requiredNum+"/"+group_buying_price+"/"+groupStartDate+"/"+groupEndDate,
            type:"post",
            dataType:"json",
            success:function(data)
            {
                console.log(data);
            }
        });
        window.location.href="generateActivity.jsp";
    });
</script>



<script language="javascript">window.onload=
    function(){
        $.ajax({
            url:"showClassify",
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
            url:"getProductsByClassifyID/"+c,
            type:"post",
            dataType:"json",
            success:function(data1)
            {
                $('select[name="productId"]').empty();
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
        $('#show_product_info').append('<input id="productID" value="'+c+'"></input>');
        $.ajax({
            url:"showDetailProduct/"+c,
            type:"post",
            dataType:"json",
            success:function(data)
            {
//                $('select[name="show_product_info"]').append(data);
            }
        });
    }
</script>



</body>
</html>
