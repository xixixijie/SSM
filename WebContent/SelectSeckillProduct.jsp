
<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2018/7/24
  Time: 11:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>秒杀商品管理</title>
    <script type="text/javascript" src="js/jquery-3.2.0.min.js" ></script>
    <link rel="stylesheet" href="assets/css/amazeui.min.css">
    <link rel="stylesheet" href="assets/css/app.css">
    <script src="assets/js/amazeui.js"></script>
    <script src="assets/js/app.js"></script>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script language="javascript">
        function updateSeckill(seckillId){
            $.ajax({
                url:"updateSeckill.action",
                type:"post",
                data:{"seckillId":seckillId},
                dataType:"json",
                success:function(data1)
                {
                    $("#seckillProductId").val(data1.seckillproduct_id);
                    $("#productName").val(data1.product.product_name);
                    $("#oldPrice").val(data1.product.original_price);
                    $("#seckillPrice").val(data1.seckill_price);
                    $("#allAmount").val(data1.all_amount);
                    $('#myModal').modal('show');
                }
            });
        }
    </script>
    <script language="javascript">
        function changeSeckillProduct() {
            $("#form1").submit();
        }
    </script>

</head>
<body>
<div class="tpl-content-wrapper">
    <div class="row-content am-cf">
        <div class="row">
            <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                <div class="widget am-cf">
                    <div class="widget-head am-cf">
                        <legend>秒杀商品管理</legend>


                    </div>
                    <div class="widget-body  am-fr">


                        <form action="selectSeckillProducts.action" method="post">
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
                                <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">

                                    <input type="text" name="startdate" class="am-form-field tpl-form-no-bg" placeholder="点击选择起始日期" data-am-datepicker="" readonly="">
                                </div>
                            </div>
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
                                <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">

                                    <input type="text" name="enddate" class="am-form-field tpl-form-no-bg" placeholder="点击选择截止日期" data-am-datepicker="" readonly="">
                                </div>
                            </div>
                            <div class="am-u-sm-12 am-u-md-12 am-u-lg-3">
                                <div class="am-input-group am-input-group-sm tpl-form-border-form cl-p">

                                    <input type="text" name="name" class="am-form-field " placeholder="请输入商品名">
                                    <span class="am-input-group-btn">
                                        <button class="am-btn  am-btn-default am-btn-success tpl-table-list-field am-icon-search" type="submit"></button>
                                    </span>
                                </div>
                            </div>

                        </form>

                        <br>
                        <div class="am-u-sm-12">
                            <table width="100%" class="am-table am-table-striped am-table-hover " id="example-r">
                                <thead>
                                <tr>
                                    <th>商品名称</th>
                                    <th>秒杀价格</th>
                                    <th>秒杀个数</th>
                                    <th>秒杀开始时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="sec">
                                    <tr>
                                        <td>${sec.product.product_name}</td>
                                        <td>${sec.seckill_price}</td>
                                        <td>${sec.all_amount}</td>
                                        <td>${sec.start_time}</td>

                                        <td>
                                            <c:if test="${sec.canDelete}">
                                                <div class="tpl-table-black-operation">
                                                    <a href="javascript:void(0);"  onclick="updateSeckill(${sec.seckillproduct_id})">
                                                        <i class="am-icon-pencil"></i> 编辑
                                                    </a>
                                                    <a href="deleteSeckillProduct?seckillProductId=${sec.seckillproduct_id}" class="tpl-table-black-operation-del">
                                                        <i class="am-icon-trash"></i> 删除
                                                    </a>
                                                </div>
                                            </c:if>
                                            <c:if test="${!sec.canDelete}">
                                                <div class="tpl-table-black-operation">
                                                    <a href="javascript:return false;" style="cursor: default;">
                                                        <i class="am-icon-pencil" style="opacity: 0.2"></i> 编辑
                                                    </a>
                                                    <a href="javascript:return false;" style="cursor: default;" class="tpl-table-black-operation-del">
                                                        <i class="am-icon-trash" style="opacity: 0.2"></i> 删除
                                                    </a>
                                                </div>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="am-u-lg-12 am-cf">

                            <div class="am-fr">
                                <ul class="am-pagination tpl-pagination">
                                    <c:if test="${pageNumPro==1}">
                                        <li class="disabled"><a href="#">&laquo;</a></li>
                                    </c:if>
                                    <c:if test="${pageNumPro!=1}">
                                        <li><a href="selectSeckillProductsByPage.action?pageNumPro=${pageNumPro-1}">&laquo;</a></li>
                                    </c:if>

                                    <c:forEach begin="1" end="${pageCount}" var="p">
                                        <c:if test="${p==pageNumPro}">
                                            <li class="active"><a href="#">${p}</a></li>
                                        </c:if>
                                        <c:if test="${p!=pageNumPro}">
                                            <li><a href="selectSeckillProductsByPage.action?pageNumPro=${p}">${p} </a></li>
                                        </c:if>
                                    </c:forEach>

                                    <c:if test="${pageNumPro==pageCount}">
                                        <li class="disabled"><a href="#">&raquo;</a></li>
                                    </c:if>
                                    <c:if test="${pageNumPro!=pageCount}">
                                        <li><a href="selectSeckillProductsByPage.action?pageNumPro=${pageNumPro+1}">&raquo;</a></li>
                                    </c:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">修改秒杀商品</h4>
            </div>
            <form class="am-form" action="editSeckillProduct.action" method="post" id="form1">
                <fieldset>
                    <div class="am-form-group">
                        <label >秒杀商品id</label>
                        <input type="number" id="seckillProductId" name="seckillProductId" readonly="readonly">
                    </div>
                    <div class="am-form-group">
                        <label >商品名称</label>
                        <input type="text" id="productName" name="productName" readonly="readonly">
                    </div>
                    <div class="am-form-group">
                        <label >商品原价</label>
                        <input type="number" id="oldPrice" name="oldPrice" readonly="readonly">
                    </div>

                    <div class="am-form-group">
                        <label >秒杀价格</label>
                        <input type="number" id="seckillPrice" name="seckillPrice" placeholder="秒杀价格必须比原价低" required="required" onchange="checkPrice()">
                    </div>

                    <div class="am-form-group">
                        <label >秒杀数量</label>
                        <input type="number" id="allAmount" name="allAmount" placeholder="" required="required">
                    </div>
                </fieldset>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" >关闭</button>
                <button type="button" class="btn btn-primary" onclick="changeSeckillProduct()">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

</body>
</html>


