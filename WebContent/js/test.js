/**
 * Created by xixi on 2018/7/19.
 */
$(function(){

    $("#compu").click(function(){
        alert("haha");
        //获得参赛选手的信息
        $.ajax({
            url:"showClassify.action",
            type:"post",
            dataType:"json",
            success:function(data)
            {
                if(data.length==0)
                {
                    $("#loadmore").html("没有更多数据了");
                }
                else
                {
                    for(var i=0; i<data.length;i++)
                    {
                        alert(data[i].className)
                    }
                }
            }

        });
    });

})