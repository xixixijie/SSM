$(function () {
            $.ajax({
                url:"getUntreatAfter",
                type:"post",
                dataType:"json",
                success:function (data) {
                    if(data.length==0)
                    {
                        alert("没有未处理的售后申请");
                    }
                    //返回售后申请
                    else{
                        //先清空
                        $('#afterList').html("");
                        var str='<div align="center" class="list">';
                        str+='<table class="table table-striped">'
                        str+='<thead> <tr> <th>售后服务单号</th>  <th>申请时间</th> <th>操作</th></tr>  </thead>'
                        for (var i=0;i<data.length;i++)
                        {
                            str+='<tr><td>'+data[i].after_id+'</td>';
                            str+='<td>'+data[i].after_date+'</td>';
                            str+='<td><a id="'+data[i].after_id+'" onclick="getDetail()" href="#">'+'查看详情'+'</a></td>';
                            str+='</tr>';
                        }
                        str+='</table>';
                        str+='</div>';
                        $("#afterList").append(str);
                    }

                }
            })

})
function getDetail() {
    var after_id=event.target.id;
    $.ajax({
        url:"getUntreatDetail/"+after_id,
        type:"post",
        dataType:"json",
        success:function (data) {
            //清空之前的查询数据
            $('#afterDetail').html("");
            var a='<div>';
            a+='<div><input id="resultAfter_id" value="'+data.after_id+'" hidden="hidden"></input>'+'售后服务单号：'+data.after_id+'</div>'
            a+='<div>时间：'+data.after_date+'</div>';
            a+='<div>订单号：'+data.order_id+'</div>';
            a+='<div>理由：'+data.reason+'</div>';
            a+='<div>要求：'+data.requirement+'</div>';
            a+='<div>处理结果<input type="text" id="result" placeholder="请输入处理结果"></div>'
            a+='<button id="btn_submitChange" onclick="submitChange()">提交处理</button>'
            a+='</div>';
            $('#detail').modal('show');
            $("#afterDetail").append(a);
        }
    })
}
function submitChange() {
    var cs_id = $("#cs_id").val();
    var preresult = $("#result").val();
    var resultAfter_id = $("#resultAfter_id").val();
    if (preresult == '') {
        alert("请输入处理结果")
    }
    else {
        var result = encodeURI(encodeURI(preresult), '');
        $.ajax({
            url: "updateAfter/" + resultAfter_id + "/" + result + "/" + cs_id,
            type: "post",
            dataType: "json",
            success: function (data) {
                if (data.result) {
                    alert("处理成功");
                    $('#detail').modal('hidden');
                }
                else {
                    alert("处理失败");
                    $('#detail').modal('hidden');
                }
            }
        })
        window.location.reload();
    }
}