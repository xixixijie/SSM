
//地址格式 url?product_id=1&userID=2


$(function() {
    // var index1 = location.href.lastIndexOf("product_id=");
    // var index2 = location.href.lastIndexOf("&userID=");
    //
    // var userID = location.href.substr(index2+"&userID=".length);//用户id
    // var product_id =location.href.substring(index1+"product_id=".length, index2); //商品id
    //
    //
    // $("#product_id").val(product_id);
    // $("#userID").val(userID);

    //修改以上代码，商品id靠url传递，用户名靠cookie传递
    var index1 = location.href.lastIndexOf("product_id=");
    var product_id =location.href.substring(index1+"product_id=".length); //商品id

    var userID=$.cookie('userID');

    if(isNaN(userID)){
        userID=-1;
        //alert("1  "+userID);
    }

    //  var userID=1;
    //alert("cookie  "+userID);
    //获取商品图片和名称
    $.ajax({
        url:"getProduct?product_id="+product_id,
        type:"get",
        dataType:"json",
        success:function(data)
        {

            $("#productPhoto").attr("src","img/"+data.cover_url);
            $("#productName").html(data.product_name);
        }
    });



    //点赞按按钮，修改
    $(document).on("click",".layui-icon",function(){
        var click = this;
        var cid = this.getAttribute("name"); //将name属性记为评论对应的cid，然后提取使用

        // if(this.style.color=="rgb(255, 159, 30)"){
        //     alert("name= "+this.getAttribute("name"));
        //     this.style.color="rgb(76, 76, 76)";
        // }else{
        //    // alert("name= "+this.getAttribute("name"));
        //     this.style.color="rgb(255, 159, 30)";
        // }

        //判断用户是否已登入,若已登入才允许点赞
        if(userID==-1){
            loginJump();
        }else{
            ////获得修改点赞信息
            $.ajax({
                url:"changePraise?cid="+cid+"&userID="+userID,
                type:"get",
                dataType:"json",
                success:function(data)
                {
                    //alert("我没有错");

                    if(click.style.color=="rgb(255, 159, 30)"){
                        //alert("name= "+click.getAttribute("name"));
                        click.style.color="rgb(76, 76, 76)";
                    }else{
                        // alert("name= "+click.getAttribute("name"));
                        click.style.color="rgb(255, 159, 30)";
                    }
                }

            });
        }

    })



   //layui部分

    layui.use(['layer','flow'], function(){
        var $ = layui.jquery; //不用额外加载jQuery，flow模块本身是有依赖jQuery的，直接用即可。
        var layer = layui.layer
        ,flow = layui.flow;


        //流加载评论
        flow.load({
            elem: '#comment' //指定列表容器
            ,mb: 100
            ,done: function(page, next){

                var lis = [];
                $.get('getPopularComment?page='+page+'&product_id='+product_id+'&userID='+userID, function(res){
                    //假设你的列表返回在data集合中

                    layui.each(res, function(index, item) {

                        var str = '<li> <!-- 每一条评价的结构 --> <div class="acomment" >';
                        str += ' <div style="float: left; width: 75%">' + item.userID + '<input name="userID" type="hidden" value=""> </div>';
                        str += ' <div style="float: right; width: 25%">' + item.score + '<input id="score" type="hidden" value=""> </div>';
                        str += '<div class="acomment">';
                        if (item.ctext != null) {
                            str += '<div class="acomment">' + item.ctext + ' </div>';
                        }
                        str += '<div class="acomment">';
                        //需要遍历输出图片
                        if (item.cphoto != null) {
                            for (var i; i < item.cphoto.length; i++) {
                                str += '<image src="' + item.cphoto[i].photourl + '">';
                            }
                        }

                        str + ' </div>';
                        str += '</div>';
                        str += '<div> <div style="float: left"> '+item.cdate+'</div> <div style="float: right">';
                        //点赞按钮
                        if(item.praiseInfo==null ||item.praiseInfo.is_praise!=1) {
                            str += '<i class="layui-icon" name="'+item.cid+'" style="rgb(76, 76, 76);">&#xe6c6;</i>';
                        }else{
                            str += '<i class="layui-icon" name="'+item.cid+'" style="color: rgb(255, 159, 30);">&#xe6c6;</i>';
                        }
                        str +='('+item.praise+')';
                        //添加回复按钮
                        str += '</div> <div style="float: right"><button data-method="reply_btn" name="'+item.cid+'" class="layui-btn layui-btn-primary layui-btn-xs">回复</button>&nbsp;</div> </div>';
                        str += '<div class="acomment"> ';
                        if (item.reply != null) {

                            for(var i=0;i<item.reply.length;i++){
                                str+='<p>'+item.reply[i].userid+':'+item.reply[i].rtext+'</p>';
                            }
                        }
                        //需要遍历，输出评论
                        str+=' </div>';
                        str+='</div> </li>';
                        // $("#comment").append(str);
                        lis.push(str);
                    });

                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    if(res){
                        next(lis.join(''), 1);
                    }else{
                        next(lis.join('没有更多了'), 0);
                    }
                });

            }
        });

        //回复弹窗
        $(document).on("click",".layui-btn",function(){
            var that = this;
            var cid = this.getAttribute("name"); //将name属性记为评论对应的cid，然后提取使用
            //判断是否登入
            if(-1){
                loginJump();
            }else{
                layer.open({
                    title: '回复',
                    content: '<input id="rtext" name="title" lay-verify="title" autocomplete="off" placeholder="请输入回复" class="layui-input" type="text">',
                    yes: function (index, layero) {
                        //提交回复
                        // alert("1+"+userID);
                        // alert("2+"+cid);
                        // alert("3+"+$("#rtext").val());

                        $.ajax({
                            url:"saveReply?cid="+cid+"&userID="+userID+"&rtext="+$("#rtext").val(),
                            type:"get",
                            dataType:"json",
                            success:function(data)
                            {
                                //alert("我没有错");
                                //刷新页面？
                                window.location.reload();
                            }

                        });


                        layer.close(index); //如果设定了yes回调，需进行手工关闭
                    }
                });
            }


        });


    });

    //确认为登入是弹出，选择跳转登入界面或关闭登入跳转
    function loginJump(){
        layer.open({
            title: '请登入',
            content: '您还未登入',
            btn:['点击登入','取消'],
            yes: function (index, layero) {
                //提交回复
                // alert("1+"+userID);
                // alert("2+"+cid);
                // alert("3+"+$("#rtext").val());

                window.location.href="login.html?backurl="+window.location.href;


                layer.close(index); //如果设定了yes回调，需进行手工关闭
            },
            btn2:function (index, layero) {

                layer.close(index); //如果设定了yes回调，需进行手工关闭
            },
        });
    }



});