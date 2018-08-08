$(function () {
    var user_id=get_cookie("miUserId");
    $.ajax({
        url:"getFinish/"+user_id,
        type:"post",
        dataType:"json",
        success:function (data) {
            if(data.length==0)
            {
                alert("没有已完成的订单");
            }
            //返回售后申请
            else{
                //先清空
                $('#finishOrder').html("");
                var str='<div align="center" class="list">';
                str+='<table class="table table-striped">'
                str+='<thead> <tr> <th>商品名称</th>  <th>时间</th> <th>操作</th></tr>  </thead>'
                for (var i=0;i<data.length;i++)
                {
                    str+='<tr><td>'+data[i].product_name+'</td>';
                    str+='<td>'+data[i].create_date+'</td>';
                    str+='<td><a id="'+data[i].order_id+'" onclick="showAdd()" href="#">'+'申请售后'+'</a></td>';
                    str+='</tr>';

                }
                str+='</table>';
                str+='</div>';
                $("#finishOrder").append(str);
            }

        }
    })
})
function showAdd() {
    $('#addAfter').modal('show');
    var order_id=event.target.id;
    var st='<input type="text" id="order_id" value="'+order_id+'" hidden="hidden">';
    $("#oid").append(st);
}
function addAfter()
    {
        var order_id=$("#order_id").val();
        var prerequirement=$("#requirement").val();
        var prereason=$("#reason").val();
        if(prerequirement==''||prereason=='')
        {
            alert("请输入要求和理由");
        }
        else{
            var reason=encodeURI(encodeURI(prereason),'');
            var requirement=encodeURI(encodeURI(prerequirement),'');
            var user_id=$("#user_id").val();
            //新建售后服务单
            $.ajax({
                url:"addAfter/"+user_id+"/"+reason+"/"+requirement+"/"+order_id,
                type:"post",
                dataType:"json",
                success:function (data) {
                    if(data.result)
                    {
                        alert("申请成功");

                    }
                    else{
                        alert("申请失败")
                    }

                }
            })
            window.location.reload();
        }
    }
function get_cookie(Name) {
    var search = Name + "="//查询检索的值
    var returnvalue = "";//返回值
    if (document.cookie.length > 0) {
        sd = document.cookie.indexOf(search);
        if (sd!= -1) {
            sd += search.length;
            end = document.cookie.indexOf(";", sd);
            if (end == -1)
                end = document.cookie.length;
            //unescape() 函数可对通过 escape() 编码的字符串进行解码。
            returnvalue=unescape(document.cookie.substring(sd, end))
        }
    }
    return returnvalue;
}