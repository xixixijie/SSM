/**
 * Created by xixi on 2018/7/19.
 */
$(function(){

    //获得所有分类参数
    // $.ajax({
    //     url:"showClassify",
    //     type:"post",
    //     dataType:"json",
    //     success:function(data)
    //     {
    //
    //             $("#leftDiv").empty();
    //             for(var i=0; i<data.length;i++)
    //             {
    //
    //                 var str='	<a href="#" class="aui-scroll-item aui-crt">'+
    //                     '<div class="aui-scroll-item-icon"></div>'+
    //                     '<div class="aui-scroll-item-text">'+data[i].className+'</div>'+
    //                     ' </a>';
    //                 $("#leftDiv").append(str);
    //             }
    //             //动态加载js
    //             // var script=document.createElement("script");
    //             // script.type="text/javascript";
    //             // script.src="themes/js/aui.js";
    //             // document.getElementsByTagName('head')[0].appendChild(script);
    //
    //     }
    //
    // });

    //


    //加载所有商品
    for(var i=1;i<11;i++){
        $.ajax({
            url:"getProductsByClassifyID/"+i,
            type:"get",
            dataType:"json",
            success:function(data)
            {
                //alert(data.length);
                for(var j=0; j<data.length;j++)
                {
                    //alert(data[j].classify.classifyID+data[j].product_id+data[j].product_name+data[j].cover_url)

                    //alert(j)

                    var str='<a href="'+data[j].product_id+'" class="aui-grid-row-item">'+
                        '<i class="aui-icon-large aui-icon-sign"><img src="themes/img/ad/x-sf-1.jpg" alt=""></i>'+
                        '<p class="aui-grid-row-label">'+data[j].product_name+'</p>'+
                        '</a>';

                    //alert("#block"+i+ data[j].product_name )
                    $("#block"+data[j].classify.classifyID).append(str);
                }


            }

        });

    }


})




