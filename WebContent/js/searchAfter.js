$(function () {
        var user_id=$("#user_id").val();
        //获取当前用户的售后服务单
        $.ajax({
            url:"getAfter/"+user_id,
            type:"post",
            dataType:"json",
            success:function (data) {
                if(data.length==0)
                {
                    alert("该用户没有售后申请");
                }
                //返回售后申请
                else{
                    //先清空
                    $('#afterList').html("");
                    var str='<div align="center" class="list">';
                    str+='<table class="table table-striped">'
                    str+='<thead> <tr> <th>售后服务单号</th>  <th>订单号</th> <th>状态</th> <th>操作</th></tr>  </thead>'
                    for (var i=0;i<data.length;i++)
                    {
                        str+='<tr><td>'+data[i].after_id+'</td>';
                        str+='<td>'+data[i].order_id+'</td>';
                        if(data[i].status==0){
                            str+='<td> <font color="red">未处理</font></td>';
                        }
                        else{
                            str+='<td> <font color="green">处理完成</font></td>'
                        }
                        str+='<td><a id="'+data[i].after_id+'" onclick="getDetail()" href="#">'+'查看详情'+'</a></td>';
                        str+='</tr>';
                    }
                    str+='</table>';
                    str+='</div>'
                    $("#afterList").append(str);
                }

            }
        })
    })
function getDetail() {
    $('#afterDetail').html("");
    var after_id=event.target.id;
    $.ajax({
        url:"getAfterDetail/"+after_id,
        type:"post",
        dataType:"json",
        success:function (data) {
            var a='<div>';
            a+='<div>'+'售后服务单号：'+data.after_id+'</div>';
            a+='<div> 订单号：'+data.order_id+'</div>';
            a+='<div>'+'理由:'+data.reason+'</div>';
            a+='<div> 要求:'+data.requirement+'</div>';
            if(data.status==1){
                a+='<div> 客服处理:'+data.result+'</div>';
                a+='<div> 客服编号:'+data.cs_id+'</div>';
            }
            a+='</div>';
            $('#detail').modal('show');
            $("#afterDetail").append(a);
        }
    })
}