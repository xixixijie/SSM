//地址格式 url?product_id=1&userID=2
$(function() {

//layui的评分部分
    layui.use('rate', function(){
        var rate = layui.rate;
        //渲染
        var ins1 = rate.render({
            elem: '#score1'  //绑定元素
            ,text: true

            ,setText: function(value) {
                var arrs = {

                    '1': '非常糟糕'
                    , '2': '不喜欢'
                    , '3': '一般'
                    , '4': '喜欢'
                    , '5': '非常喜欢'
                };
                this.span.text(arrs[value] || (""));
                $("#score").val(value);

            }
        });

    });




    var index1 = location.href.lastIndexOf("product_id=");
    var index2 = location.href.lastIndexOf("&userID=");

    var userID = location.href.substr(index2+"&userID=".length);//用户id
    var product_id =location.href.substring(index1+"product_id=".length, index2); //商品id


    $("#product_id").val(product_id);
    $("#userID").val(userID);


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


//显示input的图片
    var files = [];
    $("#photo").change(function(){
        var file = this.files[0];
        //把当前文件追加到formData对象
        files.push(file);
        //文件预览
        //1. 创建一个filereader对象
        var fileReader = new FileReader();
        //2. 定义filereader的onload方法（base64字符串）
        //append(<img src="base64字符串">)
        fileReader.onload = function(e)
        {
            var base64 = e.target.result;
            $("#photos").prepend('<img class="commentphoto" src="'+base64+'"/>');
        }
        //3. 读
        fileReader.readAsDataURL(file);
    });



    //提交评论
    $("#btn_comment").click(function(){

        //1. 创建一个formdata对象
        var formData = new FormData(document.getElementById("myform"));
        //2. 把文件追加到formData中

        for(var i=0; i<files.length;i++)
        {
            formData.append("upload",files[i]);
        }


        //提交ajax
        $.ajax({
            url:"saveComment",
            type:"post",
            data:formData,
            dataType:"json",
            contentType:false,
            processData:false,
            cache:false,
            success:function(data)
            {
                //{result:true}
                if(data.result)
                {
                    alert("提交成功");
                    //跳转页面
                    window.history.back(-1);
                }
                else
                {
                    alert("提交失败");
                }
            }
        });
    });


});