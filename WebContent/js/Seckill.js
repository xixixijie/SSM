
$(function() {

    //从后台获取时间信息
    $.ajax({
        url:"getTime.action",
        type:"get",
        dataType:"json",
        success:function(data)
        {
            for(var i=1;i<=data.length;i++){
                var str="";
                str=str+'<a href="#" id="'+data[i-1].time+'">';
                str=str+'<span>'+data[i-1].time+'</span>';
                if(i==2){
                    str=str+'<em id="cutDown"></em>';
                }else {
                    str=str+'<em>'+data[i-1].state+'</em>';
                }

                str=str+'</a>';
                $("#time"+i).empty();
                $(str).appendTo("#time"+i);
            }
            $("#time1").click();

            //设置倒计时结束时间
            var deadline=data[1].time;
            var time=deadline.split(" ")[1];
            var day=deadline.split(" ")[0];
            var y;
            var m;
            var d;
            var myDate=new Date();

            if(day=="今日"){
                y=myDate.getFullYear();
                m=myDate.getMonth()+1;
                d=myDate.getDate();
            }else {
                myDate.setDate(myDate.getDate()+1);
                y=myDate.getFullYear();
                m=myDate.getMonth();
                d=myDate.getDate();
            }
            if(d<10){
                d="0"+d;
            }
            var endtime=new Date(m+" "+d+","+y+" "+time);
            var now = new Date();
            taskid = setInterval(countdown,1000);
            //倒计时
            function countdown()
            {
                var now = new Date();

                if(now>=endtime)
                {
                    //终止循环任务
                    clearInterval(taskid);
                    location.reload();
                }
                else
                {

                    //1. 获得结束时间-当前时间(毫秒数)
                    var diff = endtime - now;
                    //2. 根据毫秒数算天，小时，分钟，秒
                    var seconds = padLeft(parseInt(diff/1000%60));
                    var minutes = padLeft(parseInt(diff/1000/60%60));
                    var hours = padLeft(parseInt(diff/1000/60/60%24));
                    var days = parseInt(diff/1000/60/60/24);
                    $("#cutDown").empty();
                    $("#cutDown").append("距开始"+hours+":"+minutes+":"+seconds);

                }
            }
            //时间不足10补0
            function padLeft(num)
            {
                if(num<10)
                {
                    return "0"+num;
                }
                return num;
            }

        }
    });
});

//获取当前场秒杀商品
function getSeckillProducts(t){
    $.ajax({
        url:"getSeckillProducts/"+t,
        type:"get",
        dataType:"json",
        success:function(data)
        {
            $("#seckills"+t).empty();
            if(data.length==0){
                $("#tip").empty();
                $("#tip").append('<i class="aui-icon aui-prompt-sm"></i>非常抱歉，本场秒杀暂时没有商品');
            }
            var judge=false;
            for(var i=0;i<data.length;i++){

                var str="";
                str = str+'<li>' +
                    '                        <div class="aui-list-title-info">' +
                    '                            <a href="javascript:;" class="aui-list-product-fl-item">' +
                    '                                <div class="aui-list-product-fl-img">' +
                    '                                    <img src="img/'+data[i].product.cover_url+'" alt="">' +
                    '                                </div>' +
                    '                                <div class="aui-list-product-fl-text">' +
                    '                                    <h3 class="aui-list-product-fl-title">'+data[i].product.product_name+data[i].product.product_info+"【仅"+data[i].all_amount+"台】"+
                    '</h3>' +
                    '<div class="aui-list-product-fl-mes">' +
                    '<div>' +
                    '<span class="aui-list-product-item-price">' +
                    '<em>¥</em>'+data[i].seckill_price+'</span>' +
                    '<span class="aui-list-product-item-del-price">'+data[i].product.original_price+'</span>' +
                    '                                        </div>\n' +
                    '                                        <div class="aui-btn-purchase">\n' ;
                    if(t=='1'){
                        if(data[i].canBuy){
                            judge=true;
                            str=str+'<button onclick="buy('+data[i].seckillproduct_id+')" style="background-color: #f44336;border: none;color:white; text-align: center;font-size: 22px;">立即抢购</button>'
                        }else{
                            str=str+'<button style="background-color: #e3e5f4;border: none;color:#131313; text-align: center;font-size: 22px;">已售空</button>'
                        }
                    }else{
                        str=str+'<button onclick="remindonclick('+data[i].seckillproduct_id+')" style="background-color: #0ff40f;border: none;color:white; text-align: center;font-size: 22px;">点我设置提醒</button>'
                    }

                str=str+ '                                        </div>' +
                    '                                    </div>' +
                    '                                </div>' +
                    '</a>' +
                    '</div>' +
                    '</li>'
                $(str).appendTo("#seckills"+t);
            }
            if(t=='1'){
                if(judge){
                    $("#tip").empty();
                    $("#tip").append('<i class="aui-icon aui-prompt-sm"></i>还有商品没有卖完哦');
                }else {
                    $("#tip").empty();
                    $("#tip").append('<i class="aui-icon aui-prompt-sm"></i>本场商品已经售罄，您可以参加下一场秒杀');
                }
            }else{
                $("#tip").empty();
                $("#tip").append('<i class="aui-icon aui-prompt-sm"></i>本场秒杀还没有开始，您可以点击预约哦');
            }

        }




    });
}

//跳转到秒杀窗口
function buy(seckillProductId){
    $.ajax({
        url:"judgeSeckillProduct/"+seckillProductId,
        type:"POST",
        dataType:"json",
        success:function(data){
            if(data){
                window.location.href="AddSeckillOrder.html?seckillProductId="+seckillProductId;
            }else{
                alert("本商品已售完，请选择其他商品");
                location.reload();
            }
        },

    });

}

//弹出提示窗，提示是否设置提醒
function remindonclick(seckillProductId) {
    art.dialog({
        id: 'testID',
        content: '如果确定设置提醒，系统将会在秒杀开始前15分钟提醒您。如果不需要，请按取消按钮',
        button: [
            {
                name: '提醒我',
                callback: function() {
                    id:'button-generate';
                    this.content('已经设置提醒，窗口将在2秒后自动关闭').time(2);
                    setRemin(seckillProductId);
                    returnfalse;
                },
                focus: true
            },
            {
                name: '取消'
            }
        ]
    });
}

//设置提醒信息
function setRemin(seckillProductId) {
    var userId=1;
    $.ajax({
        url:"setRemind/"+userId+"/"+seckillProductId,
        type:"get",
        dataType:"json",
        success:function(data)
        {
        }
    });
}