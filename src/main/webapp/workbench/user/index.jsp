<%--
  Created by IntelliJ IDEA.
  User: 86130
  Date: 2021/5/16
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%
    String basepath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="userMenu.html"%>
<html>
<head>
    <title>欢迎使用订票系统</title>
    <base href="<%=basepath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script>
        let sortMark = 0;
        $(function () {
            /*加载电影*/
            loadMovies()
            /*该函数位于userMenu中，用于加载当前账户余额*/
            loadBalance();
        })
        function loadMovies() {
            var name=$("#condition-name").val();
            var producer=$("#condition-producer").val();
            var showTime=$("#condition-showTime").val();
            var movieType=$("#condition-movieType").val();
            $.ajax({
                url:"user/loadMovies.do",
                method:"post",
                dataType:"json",
                data:{
                    "name":name,
                    "producer":producer,
                    "showTime":showTime,
                    "movieType":movieType,
                    "sortMark":sortMark
                },
                success:function (data) {
                    var html="<tr align=\"center\">\n" +
                        "<td>电影名称</td>\n" +
                        "<td>发行商</td>\n" +
                        "<td>上映日期</td>\n" +
                        "<td>电影类型</td>\n" +
                        "<td><a onclick='loadMovies()'> 评分</a></td>\n" +
                        "</tr>";
                    $.each(data,function (i,n) {
                        html+="<tr>";
                        html+="<td><a href='user/detail.do?id="+n.id+"'> "+n.name+"</a></td>";
                        html+="<td>"+n.producer+"</td>";
                        html+="<td>"+n.showTime+"</td>";
                        html+="<td>"+n.movieType+"</td>";
                        if(n.rank=="Non") html+="<td>暂无</td>";
                        else html+="<td>"+n.rank+"</td>";
                        html+="</tr>";
                    })
                    sortMark++;
                    $("#display-block").html(html);
                }
            })
        }
    </script>

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
</head>
<body>
    <div>
        <h1>电影订票系统--用户端</h1>

    </div>
    <div id="movie-display" align="center" class="table">
        <h5>条件搜索</h5>
        <table>
            <tr>
                <td>电影名称</td>
                <td><input type="text" id="condition-name"></td>
                <td>发行商</td>
                <td><input type="text" id="condition-producer"></td>
            </tr>
            <tr>
                <td>上映日期</td>
                <td><input type="text" id="condition-showTime"></td>
                <td>电影类型</td>
                <td><input type="text" id="condition-movieType"></td>
            </tr>
        </table>
        <button onclick="loadMovies()">搜索</button>
        <h4>电影一览</h4>
        <table id="display-block">
        </table>
    </div>
</body>
</html>
