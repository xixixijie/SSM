

$(function() {
    var index1 = location.href.lastIndexOf("product_id=");
    var index2 = location.href.lastIndexOf("&userID=");

    // var userID = location.href.substr(index2+"&userID=".length);//用户id
    // var product_id =location.href.substring(index1+"product_id=".length, index2); //商品id

    var userID = 1;
    var product_id =2;
    $("#product_id").val(product_id);
    $("#userID").val(userID);


    alert("sb");
   //layui部分
    layui.use('flow', function(){
        var $ = layui.jquery; //不用额外加载jQuery，flow模块本身是有依赖jQuery的，直接用即可。
        var flow = layui.flow;
        
        alert("sb2");
        flow.load({
            elem: '#comment' //指定列表容器
            ,mb: 100
            ,done: function(page, next){
                $.get('getPopularComment/'+page+'/'+product_id+'/'+userID, function(res){
                    //假设你的列表返回在data集合中
                    layui.each(res.data, function(index, item){
                        var str='<li> <!-- 每一条评价的结构 --> <div class="acomment" >';
                        str+=' <div style="float: left; width: 75%">'+item.userID+'<input name="userID" type="hidden" value=""> </div>';
                        str+=' <div style="float: right; width: 25%">'+item.score+'<input id="score" type="hidden" value=""> </div>';
                        str+='<div class="acomment">';
                        str+='<div class="acomment">'+item.ctext+' </div>';

                        str+='<div class="acomment">';
                        //需要遍历输出图片
                        for(var i;i<item.cphoto.size();i++){
                            str+='<image src="'+item.cphoto[i].photourl+'">';
                        }
                        str+ ' </div>';
                        str+='</div>';
                        str+='<div> <div style="float: left"> 2018-7-25</div> <div style="float: right"><i class="layui-icon-praise" >&#xe6c6;</i></div> <div style="float: right">回复</div> </div>';
                        str+='<div class="acomment"> ';
                        for(var i=0;i<item.reply.size;i++){
                            str+='<p>'+item.reply[i].userid+':'+item.reply[i].rtext+'</p>';
                        }

                        //需要遍历，输出评论
                        str+=' </div>';
                        str+='</div> </li>';


                        lis.push(str);
                    });

                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    if(res){
                        next('', 1);
                    }else{
                        next('没有更多了', 0);
                    }
                });



                $.ajax({
                    url:"getPopularComment/"+page+"/"+(porduct_id)+"/"+userID,
                    type:"get",
                    dataType:"json",
                    success:function(data)
                    {
                        var str='<li> <!-- 每一条评价的结构 --> <div class="acomment" >';
                        str+=' <div style="float: left; width: 75%">用户名 <input name="userID" type="hidden" value=""> </div>';
                        str+=' <div style="float: right; width: 25%">评分 <input id="score" type="hidden" value=""> </div>';
                        str+='<div class="acomment">';
                        str+='<div class="acomment">评论文本 </div>';
                        //需要遍历输出图片
                        str+='<div class="acomment">图片 </div>';
                        str+='</div>';
                        str+='<div> <div style="float: left"> 2018-7-25</div> <div style="float: right"><i class="layui-icon-praise" >&#xe6c6;</i></div> <div style="float: right">回复</div> </div>';
                       //需要遍历，输出评论
                        str+='<div class="acomment"> <p>评论1</p> <p>评论2</p> <p>评论3</p> <p >更多评论</p> </div>';
                        str+='</div> </li>';

                        $("#comment").append(str);

                    }

                });



                //只有当前页小于总页数的情况下，才会继续出现加载更多
                next('', page < res.pages);
            }
        });
    });





});