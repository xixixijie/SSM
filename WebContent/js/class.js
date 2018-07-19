/**
 * Created by xixi on 2018/7/19.
 */
$(function(){

    //获得所有分类参数
    $.ajax({
        url:"showClassify.action",
        type:"post",
        dataType:"json",
        success:function(data)
        {
            if(data.length==0)
            {

            }
            else
            {

                for(var i=0; i<data.length;i++)
                {

                    var str='	<a href="#" class="aui-scroll-item aui-crt">'+
                        '<div class="aui-scroll-item-icon"></div>'+
                        '<div class="aui-scroll-item-text">'+data[i].className+'</div>'+
                        ' </a>';
                    $("#leftDiv").append(str);
                }

                var script=document.createElement("script");
                script.type="text/javascript";
                script.src="themes/js/aui.js";
                document.getElementsByTagName('head')[0].appendChild(script);
            }
        }

    });

    // $(".aui-scroll-item").click(function () {
    //     $(this).addClass("aui-crt");
    //
    // }, function () {
    //
    //     $(this).removeClass("aui-crt");
    // });


})




