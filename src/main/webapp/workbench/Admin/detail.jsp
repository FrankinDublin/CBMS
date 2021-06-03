<%--
  Created by IntelliJ IDEA.
  User: 86130
  Date: 2021/5/19
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basepath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<%@include file="adminMenu.html" %>
<html>
<head>
    <title>电影详情</title>
    <base href="<%=basepath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <style>
        a{text-decoration: none}
        table{
            border-collapse: collapse;
        }
        table,table tr td {
            border:1px solid #ccc;
        }
        table tr td{
            padding: 5px 10px;
        }
    </style>
    <script>
        $(function () {
            loadComments();
            /*修改电影信息按钮的函数*/
            $("#editMovieBtn").click(function () {
                /*获取当前信息，填充表格*/
                var curMovieName= "${movie.name}";
                var curMovieShowTime= "${movie.showTime}";
                var curTicketPrice = "${movie.ticketPrice}";
                var curMovieType = "${movie.movieType}";
                var curIntroduce = "${movie.introduce}";
                $("#editMovieName").val(curMovieName);
                $("#editShowTime").val(curMovieShowTime);
                $("#editTicketPrice").val(curTicketPrice);
                $("#editMovieType").val(curMovieType);
                $("#editIntroduce").val(curIntroduce);
                $("#editMovie-modal").modal("show");
            })
            /*提交修改电影信息的函数*/
            $("#updateMovieBtn").click(function () {
                /*获取修改后的信息*/
                var editName=$("#editMovieName").val();
                var editShowTime=$("#editShowTime").val();
                var editTicketPrice=$("#editTicketPrice").val();
                var editMovieType=$("#editMovieType").val();
                var editIntroduce=$("#editIntroduce").val();
                if(editName==""||editShowTime==""||editTicketPrice=="")
                    $("#editMovie-msg").html("带*为必填项")
                else{
                    $.ajax({
                        url:"admin/updateMovie.do",
                        dataType:"text",
                        method:"post",
                        data:{
                            "id":"${movie.id}",
                            "name":editName,
                            "showTime":editShowTime,
                            "ticketPrice":editTicketPrice,
                            "movieType":editMovieType,
                            "introduce":editIntroduce
                        },
                        success:function (data) {
                            if(data=="true"){
                                alert("电影修改成功！")
                                $("#editMovie-modal").modal("hide");
                                $("#editMovieForm")[0].reset();
                                loadMovies();
                            }else {
                                alert("修改失败，未知原因")
                            }
                        }
                    })
                }
            })
            /*删除评论*/
            $("#deleteMovieBtn").click(function () {
                if (confirm("确定删除该电影🐎？")){
                    $.ajax({
                        url: "admin/deleteMovie.do",
                        method: "post",
                        dataType: "json",
                        data: {
                            "movieId": "${movie.id}"
                        },
                        success: function (data) {
                            if(data){
                                alert("删除成功")
                                window.location.href="workbench/Admin/index.jsp"
                            }else{
                                alert("删除失败，未知原因")
                            }
                        }
                    })
                }
            })
            /*提交修改评论*/
            $("#updateCommentBtn").click(function () {
                if($("#editComment").val()=="")
                    alert("评论内容不能为空")
                else{
                    var comment = $("#editComment").val();
                    var id = $("#commentId").val();
                    $.ajax({
                        url:"admin/updateComment.do",
                        method:"post",
                        dataType:"text",
                        data: {
                            "id":id,
                            "comment":comment
                        },
                        success:function (data) {
                            if(data=="true"){
                                alert("修改成功");
                                loadComments();
                                $("#editComment-modal").modal("hide");
                            }else alert("修改失败，未知原因")
                        }
                    })
                }
                })

        })
        function loadComments() {
            /*加载评论*/
            $.ajax({
                url:"comment/loadComments.do",
                method:"post",
                dataType:"json",
                data:{
                    movieId:"${movie.id}"
                },
                success:function (data) {
                    var html="";
                    /*计算评分*/
                    var totalGrade=0;
                    var count = 0;
                    $.each(data,function (i,n) {
                        totalGrade+=parseInt(n.grade);
                        count+=1;
                        html+="<div>";
                        html+="<small>--------------------------------------------------------</small><br>"
                        html+="<h4>"+n.createBy+"    <small>评分："+n.grade+"分</small></h4>"
                        html+="<h4 id="+n.id+">"+n.comment+"</h4>"
                        html+="<h6>"+(n.editFlag==1?n.editTime:n.createTime)+"</h6>"
                        html+="<button onclick='editComment(\""+n.id+"\")'>修改</button> <button onclick='deleteComment(\""+n.id+"\")'>删除</button>"
                        html+="</div>"
                    })
                    avgGrade = totalGrade/count ;
                    if(isNaN(avgGrade)) {
                        $("#grade").html("暂无评分");
                        updateGrade("Non");
                    }
                    else {
                        $("#grade").html(avgGrade.toFixed(1));
                        updateGrade(avgGrade.toFixed(1)+"");
                    }
                    $("#comment").html(html);
                }
            })

        }
        /*修改用户评论*/
        function editComment(id) {
            var comment = $("#"+id).html();
            $("#commentId").val(id);
            $("#editComment").val(comment);
            $("#editComment-modal").modal("show");
        }
        /*删除用户评论*/
        function deleteComment(id) {
            if(confirm("确定删除该评论🐎")){
                $.ajax({
                    url:"admin/deleteComment.do",
                    method:"post",
                    dataType:"json",
                    data:{
                        "commentId":id
                    },
                    success:function (data) {
                        if(data){
                            alert("删除成功")
                            loadComments()
                        }else{
                            alert("删除失败，未知原因")
                        }
                    }
                })
            }
        }
        /*更新后台评分*/
        function updateGrade(grade) {
            $.ajax({
                url:"detail/updateGrade.do",
                method:"post",
                dataType:"text",
                data:{
                    movieId:"${movie.id}",
                    "grade":grade
                }
            })
        }
    </script>
</head>
<body>
    <%--保存评论Id的隐藏域--%>
    <input type="hidden" id="commentId">
    <h1>电影详情--${movie.name}</h1>
    <h3>类型：${movie.movieType} 发行商：${movie.producer}</h3>
    <h3>上映日期:${movie.showTime} 评分:<font id="grade"></font></h3>
    <div>
        <h2>简介</h2>
        ${movie.introduce}
    </div>
    <button class="btn" id="editMovieBtn">修改电影内容</button> <button class="btn" id="deleteMovieBtn">删除电影</button>
    <button onclick="window.location.href='workbench/Admin/index.jsp'" class="btn">返回</button>
    <div id="movie-showComment">
        <h3>评论区</h3>
        <div id="comment"></div>
    </div>
    <%--修改电影信息的模态窗口--%>
    <div class="modal fade" id="editMovie-modal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">电影信息修改</h4>
                </div>
                <div class="modal-body">
                    <form id="editMovieForm">
                        <table>
                            <tr>
                                <td>电影名称：</td>
                                <td><input type="text" id="editMovieName"><font color="red">*</font></td>
                            </tr>
                            <tr>
                                <td>上映日期：<br></td>
                                <td><input type="date" id="editShowTime"><font color="red">*</font></td>
                            </tr>
                            <tr>
                                <td>票价:</td>
                                <td><input type="text" id="editTicketPrice"><font color="red">*</font></td>
                            </tr>
                            <tr>
                                <td>电影类型:</td>
                                <td><input type="text" id="editMovieType"></td>
                            </tr>
                            <tr>
                                <td>简介:</td>
                                <td><textarea id="editIntroduce"></textarea></td>
                            </tr>
                        </table>
                        <font color="red" id="editMovie-msg"></font>
                    </form>
                </div>
                <div class="modal-footer">
                    <button id="updateMovieBtn">提交</button>
                    <button data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
    <%--修改评论信息的模态窗口--%>
    <div class="modal fade" id="editComment-modal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title">评论修改</h4>
                </div>
                <div class="modal-body">
                    评论内容：<textarea id="editComment"></textarea>
                </div>
                <div class="modal-footer">
                    <button id="updateCommentBtn">提交</button>
                    <button data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
