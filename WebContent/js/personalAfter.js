$(function() {

    $("#addAfter").click(function () {
        alert("addAfter");
        //新增售后服务单
        $.ajax({
            url: "addAfter.action",
            type: "post",
            dataType:"json",
        })

    }
    $("#getAfter").click(function () {
        alert("getAfter");
        //获得售后服务单列表
        $.ajax({
            url: "getAfter.action",
            type: "post",
            dataType:"json",
        })

    }
}
