<%--
  Created by IntelliJ IDEA.
  User: chenyufeng
  Date: 2018/7/25
  Time: 下午2:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理团购活动</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="/js/jquery-3.2.0.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/frameui.css">
    <link rel="stylesheet" href="css/recommended.css">
</head>
<body>
<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">查询团购活动</h3>
    </div>
    <div class="panel-body">
        <div class="form-group">
            <label for="search_activity_productName">商品名称</label>
            <input type="text" id="search_activity_productName" class="form-control"/>
        </div>
        <div class="form-group">
            <label for="search_activity_productName">开始日期</label>
            <input type="text" id="search_activity_groupStartDate" class="form-control"/>
        </div>
        <div class="form-group">
            <label for="search_activity_productName">结束日期</label>
            <input type="text" id="search_activity_groupEndDate" class="form-control"/>
        </div>
    </div>
    <button type="button" id="search_activity_button">搜索</button>
</div>
<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">查询结果</h3>
    </div>
    <div class="panel-body">
        <table class="table table-striped" id="show_activitys"></table>
    </div>
</div>
</table>

<script type="application/javascript">
    $("#search_activity_button").click(function () {
        var productName = $("#search_activity_productName").val();
        if (productName == '') {
            productName = -1;
        }
        var groupStartDate = $("#search_activity_groupStartDate").val();
        if (groupStartDate == '') {
            groupStartDate = -1;
        }
        var groupEndDate = $("#search_activity_groupEndDate").val();
        if (groupEndDate == '') {
            groupEndDate = -1;
        }
        $.ajax({
            url: "searchActivities/" + productName + "/" + groupStartDate + "/" + groupEndDate,
            type: "post",
            dataType: "json",
            success: function (data) {
                console.log(data);
                //1.清除原有的数据
                $('#show_activitys').empty();
                //2.追加符合条件的数据
                $('#show_activitys').append('<thead><tr><th></th><th>商品名称</th> <th>活动金额</th> <th>开始时间</th><th>结束时间</th> ' +
                    '<th>操作</th> </tr> </thead>');
                for (var i = 0; i < data.length; i++) {
                    var startDate = new Date(data[i].groupStartDate).toLocaleString();
                    var endDate = new Date(data[i].groupEndDate).toLocaleString();
                    if(data.activityStatus==3){
                        var str = '<tbody> <tr class="success"><td><input type="checkbox"></td> <td>' +
                            data[i].product.product_name + '</td> <td>' + data[i].group_buying_price + '</td> ' +
                            '<td>' + startDate + '</td> <td>' + endDate + '</td> ' +
                            '<td><button id="'+data[i].activityID+'%'+data[i].activityStatus+'%get_info_button" onclick="get_activity_info(this.id)">查看</button></td>' +
                            '</tr> </tbody>';
                        $('#show_activitys').append(str);
                    }else{
                        //已经开始的不能修改
                        var str = '<tbody> <tr class="danger"><td><input type="checkbox"></td> <td>' +
                            data[i].product.product_name + '</td> <td>' + data[i].group_buying_price + '</td> ' +
                            '<td>' + startDate + '</td> <td>' + endDate + '</td> ' +
                            '<td><button id="'+data[i].activityID+'%'+data[i].activityStatus+'%get_info_button" onclick="get_activity_info(this.id)">查看</button></td>' +
                            '</tr> </tbody>';
                        $('#show_activitys').append(str);
                    }
                }
            }
        });
    });
</script>


<div class="modal fade" id="show_activity_info" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    团购具体信息
                </h4>
            </div>
            <div class="modal-body">
                <div id="tuan" class="tuan">
                    <div class="tuan_g" data-vtuan="0" data-cat="1" data-num="12058">
                        <i></i>
                        <a href="#">
                            <div class="tuan_g_img">
                                <div class="tally_box" id="show_number">
                                </div>
                                <div id="product_img"></div>
                            </div>
                            <div class="tuan_g_info" id="show_product_info">
                            </div>

                            <div class="tuan_g_core" id="show_price">
                            </div>
                            <div class="tuan_g_yj" id="show_origin_price"></div>
                            <div id="modify_table">

                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <div id="modify_div">
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<script type="application/javascript">
    function get_activity_info(id) {
        var split_str = id.split('%');
        var activityID = split_str[0];
        var status = split_str[1];
        $.ajax({
            url: "searchActivityInfo/" + activityID,
            type: "post",
            dataType: "json",
            success: function (data) {
                console.log(data);
                // 清除原有的数据

                $("#show_number").empty();
                $("#show_number").append('<p>' + data.requiredNumber + '人团</p><p>¥' + data.group_buying_price + '</p>');


                $("#product_img").empty();
                $("#product_img").append('<img src="' + data.product.cover_url + '">');


                $("#show_product_info").empty();
                $("#show_product_info").append('<p class="tuan_g_name">' +
                    data.product.product_name + '</p> <p class="tuan_g_cx">' +
                    data.product.product_info + '</p>');

                $("#show_price").empty();
                $("#show_price").append(' <div id="triangle-right"></div><div class="tuan_g_price"><span>' +
                    data.requiredNumber + '人团只需</span> <b>¥' + data.group_buying_price + '</b> </div> <div class="tuan_g_btn">我要拼</div>');

                $("#show_origin_price").empty();
                $("#show_origin_price").append('<s>原价:￥' + data.product.original_price + '</s>');


                if (status != 3) {
                    //还没开始的，可以被修改
                    $("#modify_div").empty();
                    $("#modify_div").append('</div> <div class="form-group">' +
                        ' <label for="requiredNumber">需要人数</label> <input type="text" class="form-control"' +
                        ' id="requiredNumber"value="'+data.requiredNumber+'"> </div> <div class="form-group"> <label for="group_buying_price">活动价格' +
                        '</label> <input type="number" class="form-control" id="group_buying_price"value="'+data.group_buying_price+'"> </div>');
                    $("#modify_div").append(' <button type="button" class="btn btn-primary" id="'+data.activityID+'%modify_button" data-dismiss="modal"  onclick="modify_activity(this.id)">提交更改</button>');
                }
            }
        });
    }



    function modify_activity(id) {
        var split_str = id.split('%');
        var activityID = split_str[0];
        var requiredNumber =  $("#requiredNumber").val();
        var group_buying_price = $('#group_buying_price').val();

        $.ajax({
            url: "modifyActivityInfo/" + activityID+"/"+requiredNumber+"/"+group_buying_price,
            type: "post",
            dataType: "json",
            success: function (data) {
                console.log(data);
            }

        });
    }

</script>
</body>
</html>
