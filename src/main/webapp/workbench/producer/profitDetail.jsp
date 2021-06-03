<%--
  Created by IntelliJ IDEA.
  User: 86130
  Date: 2021/5/21
  Time: 22:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basepath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <title>营收页面--详情</title>
    <base href="<%=basepath%>">
    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script>
        $(function () {
            /*电影票表表头*/
            var html="<tr>\n" +
                "<td>电影名</td>\n" +
                 "<td>票价</td>"+
                "<td>卖出数量（张）</td>\n" +
                "</tr>";
            /*加载电影明细*/
            $.each(${movieDetail},function (i,n) {
               html+='<tr>'
               html+='<td>'+n.name+'</td>'
               html+='<td>'+n.ticketPrice+'</td>'
               html+='<td>'+n.count+'</td>'
               html+='</tr>'
            })
            $("#ticketSaleTable").html(html);
            /*套餐表表头*/
            html="<tr>\n" +
                "<td>套餐名</td>\n" +
                "<td>价格</td>\n" +
                "<td>卖出数量（份）</td>\n" +
                "</tr>";
           $.each(${ticketSetDetail},function (i,n) {
               html+='<tr>'
               html+='<td>'+n.setInclude+'</td>'
               html+='<td>'+n.price+'</td>'
               html+='<td>'+n.count+'</td>'
               html+='</tr>'
           })
            $("#setSaleTable").html(html);
        })
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
<div class="modal-body">
    <div>
        <h2>商家：${user.name}</h2>
        <b>总收入：</b><font color="red">${totalProfit}</font>元
        <br>已卖出电影票：${totalMovieSale}张 已卖出套餐：${totalSetSale}份
    </div>
    <div>
        <button class="btn" onclick="window.location.href='workbench/producer/index.jsp'">返回</button>
    </div>
    <div style="float: left">
        <b>已卖出电影票</b>
        <table id="ticketSaleTable">
        </table>
    </div>
    <div style="float: left">
        <b>已卖出套餐</b>
        <table id="setSaleTable"></table>
    </div>
</div>
</body>
</html>
