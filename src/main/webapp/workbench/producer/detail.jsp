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
<%@include file="producerMenu.html" %>
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
                        html+="<h4>"+n.comment+"</h4>"
                        html+="<h6>"+(n.editFlag==1?n.editTime:n.createTime)+"</h6>"
                        html+="</div>"
                    })
                    avgGrade = totalGrade/count ;
                    if(isNaN(avgGrade)) $("#grade").html("暂无评分");
                    else $("#grade").html(avgGrade.toFixed(1));
                    $("#comment").html(html);
                }
            })

        }
    </script>
</head>
<body>
    <h1>电影详情--${movie.name}</h1>
    <h3>类型：${movie.movieType} 发行商：${movie.producer}</h3>
    <h3>上映日期:${movie.showTime} 评分:<font id="grade"></font></h3>
    <div>
        <h2>简介</h2>
        ${movie.introduce}
    </div>
    <button onclick="window.location.href='workbench/producer/index.jsp'" class="btn">返回</button>
    <div id="movie-showComment">
        <h3>评论区</h3>
        <div id="comment"></div>
    </div>
</body>
</html>
